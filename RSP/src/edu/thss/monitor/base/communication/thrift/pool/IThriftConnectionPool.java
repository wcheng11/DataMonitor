package edu.thss.monitor.base.communication.thrift.pool;

import org.apache.thrift.transport.TSocket;
/**
 * Thrift连接池接口
 * @author yangtao
 */
public interface IThriftConnectionPool   
{   
    /**  
     * 取连接池中的一个连接  
     *   
     * @return  
     */  
    public TSocket getConnection();
    
    /**
     * 返回连接  
     *   
     * @param socket  
     */  
    public void releaseConn(TSocket socket);   
}  
