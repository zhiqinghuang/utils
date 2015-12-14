package com.netmap.database;

public class ThreadProcess implements Runnable {
	private long longQueryTimes = 0;

	public void run() {
		longQueryTimes = TestBatchCluster.processQueryAndUpdate();
		//longQueryTimes = TestBatchCluster.processSelectForUpdate();
	}

	public long getQueryTimes() {
		return longQueryTimes;
	}
}