package ty.receiver.imp;

import ty.pub.RawDataPacket;
import ty.receiver.Receiver;

public class OnlineReceiver implements Receiver {

	UDPServer server;
	
	public OnlineReceiver(int port){
		server = new UDPServer(port);
	}
	
	public RawDataPacket next() {
		// TODO Auto-generated method stub
		return server.listen();
	}

}
