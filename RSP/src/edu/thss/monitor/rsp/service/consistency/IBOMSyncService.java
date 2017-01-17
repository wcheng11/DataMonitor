package edu.thss.monitor.rsp.service.consistency;

import edu.thss.monitor.pub.entity.service.ParsedDataPacket;

public interface IBOMSyncService{
	public void BOMSync(ParsedDataPacket dp);
	public void IPSync(ParsedDataPacket dp);

}
