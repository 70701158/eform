package org.skynet.bi.framework.util;

import org.springframework.beans.factory.annotation.Value;

/**
 * ID生成器
 * @author javadebug
 *
 */
public class IDGenerator {
	@Value("${id.datacenter-id}")
	private static int datacenterId;
	
	@Value("${id.worker-id}")
	private static int workerId;
	
	private static IDWorker worker = null;
	
	private static final Object LOCK = new Object();
	
	public static final long nextLong() {
		return getWorker().nextId();
	}
	
	private static IDWorker getWorker() {
		if (worker == null) {
			synchronized(LOCK) {
				if (worker == null) {
					worker = new IDWorker(workerId, datacenterId);
				}
			}
		}
		
		return worker;
	}
}
