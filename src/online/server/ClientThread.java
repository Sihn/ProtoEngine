package online.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;

import online.Config;
import core.Log;

public class ClientThread extends Thread {
	private final Server server;
	public final InputStream in;
	public final PrintStream out;

	public ClientThread(Server server, Socket client) throws IOException {
		this.server = server;
		in = client.getInputStream();
		out = new PrintStream(client.getOutputStream());
	}

	@Override
	public void run() {
		try {
			byte[] datas = new byte[Config.dataLenght];
			int count;
			while ((count = in.read(datas)) != -1) {
				server.traitment(Arrays.copyOf(datas, count));
			}
			/* while (!server.tcp.isClosed() && !client.isClosed()) {
			 * byte[] datas = new byte[Config.dataLenght];
			 * int count = in.read(datas);
			 * Log.debug(count + "|" + String.valueOf(datas) + "/server.tcp.isClosed()=" + server.tcp.isClosed() + "/client.isClosed()=" + client.isClosed());
			 * if (count > 0)
			 * server.traitment(Arrays.copyOf(datas, count));
			 * } */
		} catch (IOException e) {
			Log.info("Client stopped.");
		}
	}
}
