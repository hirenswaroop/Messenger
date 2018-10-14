import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

public class ClientUI{
	private BufferedReader userIn;
	private Client c;
	private String name;
	private String hostName;
	
	public static void main(String[] args) throws IOException {
		ClientUI user = new ClientUI();
		user.hostName = JOptionPane.showInputDialog("What is the IP address you want to connect to?");
		user.name = JOptionPane.showInputDialog("What is your name?");
		user.c = new Client(user.hostName, 2222, user);
		user.c.start();
		user.userIn = new BufferedReader(new InputStreamReader(System.in));
		user.Read();
	}
	
	/**
	 * Read the user's input and sends it to the client
	 */
	public void Read() {
		String userInput;	
		try {
			while ((userInput = userIn.readLine()) != null) {
				c.sendMsg(name + ": " + userInput);
			}
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		} catch (NullPointerException e) {
			System.err.println("Connect to a valid host");
			System.exit(1);
		}
	}
	
	/**
	 * Prints the message
	 * @param message contains the message to be sent
	 */
	public void printMsg(String message) {
		System.out.println(message);
	}
}