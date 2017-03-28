package ty.receiver;

import ty.receiver.imp.OnlineReceiver;

public class ReceiverFactory {

	public static Receiver getOnlineReicever(int port){
		return new OnlineReceiver(port);
	}
	
}
