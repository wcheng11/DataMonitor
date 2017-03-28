package ty.receiver;

import ty.pub.RawDataPacket;

public interface Receiver {

	public RawDataPacket next();
	
}
