package online.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import online.Config;
import core.Log;

public class Client implements AutoCloseable {
	public final InetAddress address;
	public final int port;
	private final Socket tcp;
	public final String name;

	private final DatagramSocket udp;

	private Client(InetAddress address, int port) throws IOException {
		this.address = address;
		this.port = port;
		tcp = new Socket(address, port);
		udp = new DatagramSocket();
		name = "Client_" + Integer.toHexString((int) (Math.random() * 0x1000000)).toUpperCase();
	}

	public static Client createClient() {
		return (createClient(Config.host, Config.port));
	}

	public static Client createClient(String host, int port) {
		try {
			return createClient(InetAddress.getByName(host), port);
		} catch (UnknownHostException e) {
			Log.error("Unknow Server : " + host + ":" + port);
			return null;
		}
	}

	public static Client createClient(InetAddress address, int port) {
		try {
			return new Client(address, port);
		} catch (IOException e) {
			Log.error("Can't connect to server \"" + Config.host + ":" + Config.port + "\".");
			return null;
		}
	}

	@Override
	public void close() {
		try {
			tcp.close();
		} catch (IOException e) {
			Log.error(e.getMessage());
		}
		udp.close();
	}

	public boolean sendTcp(byte[] datas) {
		try {
			tcp.getOutputStream().write(datas);
			return true;
		} catch (IOException e) {
			Log.error(e.getMessage());
			Log.error("Can't send the message : " + String.valueOf(datas));
			return false;
		}
	}

	public boolean sendUdp(byte[] datas) {
		try {
			udp.send(new DatagramPacket(datas, datas.length, address, port));
			return true;
		} catch (IOException e) {
			Log.error(e.getMessage());
			Log.error("Can't send the message : " + String.valueOf(datas));
			return false;
		}
	}

	public static void main(String[] args) {
		Client client = Client.createClient();
		if (client == null)
			return;
		client.sendTcp("Test TCP".getBytes());
		client.sendUdp("Test UDP".getBytes());
	}
}
