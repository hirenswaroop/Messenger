import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {
	private String hostName;
	private int portNumber;
	private PrintWriter out;
	private BufferedReader in;
	private ClientUI user;
	
	public Client (String hostName, int portNumber, ClientUI UI){
		super("Client");
		this.hostName = hostName;
		this.portNumber = portNumber;
		user = UI;
	}
	
	/**
	 * Creates a clientSocket that connects to the desired host and port
	 * number.
	 */
	public void run() {
		try {
			Socket clientSocket = new Socket(hostName, portNumber);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			String recievedInput;	
			while ((recievedInput = in.readLine()) != null) {
				user.printMsg(recievedInput);
			}
			clientSocket.close();
		} catch (UnknownHostException e) {
			System.err.println("Don't know host: " + hostName);
			System.exit(1);
			
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}
	
	/**
	 * Sends the message
	 * @param Message contains the message to be sent
	 */
	public void sendMsg(String Message) {
		out.println(Message);
	}
}