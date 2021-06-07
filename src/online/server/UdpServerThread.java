package online.server;

import core.Log;

public class UdpServerThread extends Thread {
	private final Server server;

	public UdpServerThread(Server server) {
		this.server = server;
	}

	@Override
	public void run() {
		Log.info("Thread UDP started.");
		while (!server.udp.isClosed()) {}
		Log.info("Thread UDP stopped.");
	}
}
