package online.server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;

import online.Config;
import core.Log;

public class Server implements AutoCloseable {
	public final int port;
	public final ServerSocket tcp;
	public final DatagramSocket udp;
	public final ArrayList<ClientThread> clientsThreads = new ArrayList<>();

	public Server(int port) throws IOException {
		this.port = port;
		tcp = new ServerSocket(port);
		udp = new DatagramSocket(port);
	}

	@Override
	public void close() throws IOException {
		Log.info("Stop servers...");
		tcp.close();
		udp.close();
		Log.info("Servers stoped.");
	}

	public void run() {
		Log.info("Launch TCP and UDP servers...");
		new TcpServerThread(this).start();
		new UdpServerThread(this).start();
	}

	public void sendTcpToAll(byte[] datas) throws IOException {
		for (ClientThread client : clientsThreads) {
			sendTcp(client, datas);
		}
	}

	public void sendTcp(ClientThread client, byte[] datas) throws IOException {
		client.out.write(datas);
		client.out.flush();
	}

	public void sendUdp(byte[] datas) throws IOException {}

	public void traitment(byte[] datas) throws IOException {
		Log.info(new String(datas));
		sendTcpToAll(datas);
	}

	public static void main(String[] args) {
		try {
			Server server = new Server(Config.port);
			server.run();
			Thread.sleep(30000);
			server.close();
		} catch (IOException | InterruptedException e) {
			Log.error(e.getMessage());
		}
	}
}
