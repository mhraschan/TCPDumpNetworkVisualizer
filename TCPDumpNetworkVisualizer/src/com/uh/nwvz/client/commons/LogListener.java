package com.uh.nwvz.client.commons;

public interface LogListener {

	public void logError(String message);
	
	public void logWarn(String message);
	
	public void logInfo(String message);
}
