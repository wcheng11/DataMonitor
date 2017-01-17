package edu.thss.monitor.rsp.scheduler;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.thrift7.TException;
import org.apache.thrift7.protocol.TBinaryProtocol;
import org.apache.thrift7.protocol.TProtocol;
import org.apache.thrift7.transport.TFramedTransport;
import org.apache.thrift7.transport.TSocket;
import org.apache.thrift7.transport.TTransportException;

import backtype.storm.generated.ExecutorSummary;
import backtype.storm.generated.Nimbus;
import backtype.storm.generated.NotAliveException;
import backtype.storm.generated.SupervisorSummary;
import backtype.storm.generated.Nimbus.Client;
import backtype.storm.scheduler.Cluster;
import backtype.storm.scheduler.EvenScheduler;
import backtype.storm.scheduler.ExecutorDetails;
import backtype.storm.scheduler.IScheduler;
import backtype.storm.scheduler.SchedulerAssignment;
import backtype.storm.scheduler.SupervisorDetails;
import backtype.storm.scheduler.Topologies;
import backtype.storm.scheduler.TopologyDetails;
import backtype.storm.scheduler.WorkerSlot;

public class SelfAdjustScheduler implements IScheduler {

    public void schedule(Topologies topologies, Cluster cluster) {
    	System.out.println("MonitorScheduler: 开始调度...");
        // Gets the topology which we want to schedule
        TopologyDetails topology = topologies.getByName("MONITOR");
        // make sure the special topology is submitted,
        if (topology != null) {
            boolean needsScheduling = cluster.needsScheduling(topology);
     
            //跟Nimbus交互
            TSocket socket = new TSocket("192.168.10.21", 6627);
            TFramedTransport transport =new TFramedTransport(socket);
 		  
 		   	TProtocol protocol = new TBinaryProtocol(transport);  
 		   	Client client=new Nimbus.Client(protocol);
 		   	try {
				transport.open();
				List<SupervisorSummary> ess=client.getClusterInfo().get_supervisors();
				
			
			} catch (TTransportException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 		   	
 		    
 		   	
            if (!needsScheduling) {
            	System.out.println("MonitorScheduler DOES NOT NEED scheduling.");
            } else {
            	System.out.println("MonitorScheduler needs scheduling.");
                // find out all the needs-scheduling components of this topology
                Map<String, List<ExecutorDetails>> componentToExecutors = cluster.getNeedsSchedulingComponentToExecutors(topology);
                System.out.println("needs scheduling(component->executor): " + componentToExecutors);
                System.out.println("needs scheduling(executor->components): " + cluster.getNeedsSchedulingExecutorToComponents(topology));
                SchedulerAssignment currentAssignment = cluster.getAssignmentById(topology.getId());
                if (currentAssignment != null) {
                	System.out.println("current assignments: " + currentAssignment.getExecutorToSlot());
                } else {
                	System.out.println("current assignments: {}");
                }
                //special-spout
                if (!componentToExecutors.containsKey("onlineSpout")) {
                	System.out.println("Our special-spout DOES NOT NEED scheduling.");
                } else {
                    System.out.println("Our special-spout needs scheduling.");
                    List<ExecutorDetails> executors = componentToExecutors.get("onlineSpout");

                    // find out the our "special-supervisor" from the supervisor metadata
                    Collection<SupervisorDetails> supervisors = cluster.getSupervisors().values();
                    SupervisorDetails specialSupervisor = null;
                    for (SupervisorDetails supervisor : supervisors) {
                        Map meta = (Map) supervisor.getSchedulerMeta();
                        if (meta.get("name").equals("onlineSpout_location")) {
                            specialSupervisor = supervisor;
                            break;
                        }
                    }

                    // found the special supervisor
                    if (specialSupervisor != null) {
                    	System.out.println("Found the onlineSpout_location supervisor");
                        List<WorkerSlot> availableSlots = cluster.getAvailableSlots(specialSupervisor);
                        
                        // if there is no available slots on this supervisor, free some.
                        // TODO for simplicity, we free all the used slots on the supervisor.
                        if (availableSlots.isEmpty() && !executors.isEmpty()) {
                            for (Integer port : cluster.getUsedPorts(specialSupervisor)) {
                                cluster.freeSlot(new WorkerSlot(specialSupervisor.getId(), port));
                            }
                        }

                        // re-get the aviableSlots
                        availableSlots = cluster.getAvailableSlots(specialSupervisor);

                        // since it is just a demo, to keep things simple, we assign all the
                        // executors into one slot.
                        cluster.assign(availableSlots.get(0), topology.getId(), executors);
                        System.out.println("We assigned executors:" + executors + " to slot: [" + availableSlots.get(0).getNodeId() + ", " + availableSlots.get(0).getPort() + "]");
                    } else {
                    	System.out.println("There is no supervisor named special-supervisor!!!");
                    }
                }
            }
        }
        // let system's even scheduler handle the rest scheduling work
        // you can also use your own other scheduler here, this is what
        // makes storm's scheduler composable.
        new EvenScheduler().schedule(topologies, cluster);
    }

	@Override
	public void prepare(Map conf) {
		
	}

}