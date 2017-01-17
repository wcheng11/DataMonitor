package edu.thss.monitor.pub.util;

import java.io.Serializable;

import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.UUIDHexGenerator;


public class UUIDGenerator extends UUIDHexGenerator{
	public Serializable generate(SessionImplementor session, Object obj) {
		return ((String)super.generate(session, obj)).toUpperCase();
	}
}
