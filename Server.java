import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	private ServerSocket serverSocket = null;
	ArrayList <MiniServer> sockets = new ArrayList <MiniServer>();
	
	public static void main(String[] args) throws IOException {	
		Server s = new Server();
		
		try {
			s.serverSocket = new ServerSocket(2222);
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port: 2222");
			System.out.println(e.getMessage());
		}
		s.acceptClient();
		s.serverSocket.close();
	}
	
	/**
	 * Connects the server each MiniServer to the Server
	 */
	public void acceptClient() {
		while (true) {
			try {
			Socket clientSocket = serverSocket.accept();
			MiniServer mini = new MiniServer (clientSocket, this);
			sockets.add(mini);
			mini.start();
			} catch (IOException e) {
				System.out.println("Exception caught when trying to listen on port: 2222");
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Sends message received to all MiniServers except itself
	 * @param cs the MiniServer that sent the message
	 * @param message contains the message to be sent
	 */
	public void getMessage(MiniServer cs, String message) {
		for (int i = 0; i < sockets.size(); i++) {
			if (sockets.get(i) != cs) {
				sockets.get(i).broadCast(message);
			}
		}
	}
}