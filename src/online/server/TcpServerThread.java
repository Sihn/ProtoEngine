package online.server;

import java.io.IOException;

import core.Log;

public class TcpServerThread extends Thread {
	private final Server server;

	public TcpServerThread(Server server) {
		this.server = server;
	}

	@Override
	public void run() {
		try {
			Log.info("Thread TCP started.");
			Log.info("Wait for client...");
			while (!server.tcp.isClosed()) {
				ClientThread thread = new ClientThread(server, server.tcp.accept());
				server.clientsThreads.add(thread);
				thread.start();
				Log.info("Client added.");
			}
		} catch (IOException e) {
			Log.info("Thread TCP stopped.");
		}
	}
}
