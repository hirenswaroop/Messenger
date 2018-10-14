import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MiniServer extends Thread {
	private Socket client = null;
	private String inputLine;
	private Server s;
	private PrintWriter out;
	private BufferedReader in;
	
	public MiniServer (Socket client, Server server) {
		super ("MiniServer");
		this.client = client;
		s = server;
	}
	
	/**
	 * Reads message sent to the Server
	 */
	public void run() {
		try {
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			while ((inputLine = in.readLine()) != null) {
				s.getMessage(this, inputLine);
				System.out.println(inputLine);
			}
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to host");
			System.exit(-1);
		}
	}
	
	/**
	 * Broadcasts a message
	 * @param message contains the message to be sent
	 */
	public void broadCast (String message) {
		out.println(message);
	}
}