package com.netmap.jgroups;

import org.jgroups.JChannel;

public class SimpleChat {
	private JChannel channel;
	private String user_name = System.getProperty("user.name", "n/a");

	private void start() throws Exception {
		channel = new JChannel();
		channel.connect("ChatCluster");
	}

	public static void main(String[] args) throws Exception {
		new SimpleChat().start();
	}
}