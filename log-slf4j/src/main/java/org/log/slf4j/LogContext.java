package org.log.slf4j;

public class LogContext {
	
	
	private final static ThreadLocal<Long> TRACE_CONTEXT = new ThreadLocal<>();
	
	public static long  getTraceId() {
		Long traceId = TRACE_CONTEXT.get();
		if(traceId==null) {
			TRACE_CONTEXT.set(System.currentTimeMillis());
		}
		return TRACE_CONTEXT.get();
	}

}
