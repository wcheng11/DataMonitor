package ty.pub;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.protobuf.ByteString;

public class BeanUtil {

	static Kryo kryo = new Kryo();

	/**  
     * 对象转数组  
     * @param obj  
     * @return  
     */  
    public synchronized static byte[] toByteArray (Object obj) {         
        ByteArrayOutputStream bos = new ByteArrayOutputStream();      
        Output output = new Output(bos, 4096);
		kryo.writeObject(output, obj); 
		byte [] bytes = output.toBytes();
		output.close();      
        return bytes;    
    }   
       
    /**  
     * 数组转对象  
     * @param bytes  
     * @return  
     */  
    public synchronized static <T> T toObject (byte[] bytes, Class<T> type) {              
        ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
		Input input = new Input(bis);
		T obj = kryo.readObject(input, type);
		input.close();      
        return obj;    
    }

	public static byte[] getByteForRawPacket(RawDataPacket packet) {
		// TODO Auto-generated method stub
		ProtoPacket.RawPacket.Builder builder = ProtoPacket.RawPacket.newBuilder();
		builder.setIp(packet.getIp());
		builder.setPacketSource(packet.getPacketSource());
		builder.setTimestamp(packet.getTimestamp());
		builder.setPacketData(ByteString.copyFrom((byte [])packet.getPacketData()));
		ProtoPacket.RawPacket rawPacket = builder.build();
		return rawPacket.toByteArray();
	}  
	
}
