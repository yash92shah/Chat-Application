package Chat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server  {
	
	public static int port = 0;
	public static Server sersoc;
	public static ArrayList < Client > peers = new ArrayList < Client > ();

	public static String getIP() throws IOException
	{
		return InetAddress.getLocalHost().getHostAddress();
	}

	public static String myip() throws IOException {
		
		System.out.println("Your IP is " + getIP());
		return getIP();
	}


	public static int myport() {
		
		System.out.println("Your Port is " + port);
		return port;
	}

	public static void connect(String destinationIP, int destinationPort) throws UnknownHostException, IOException, InterruptedException {

			try {

				Client client1 = new Client(destinationIP, destinationPort, sersoc);

				if (client1.getSocket().isConnected()) {
					peers.add(client1);
					System.out.println("Connected with " + destinationIP );

				} 
				
				else {
					System.out.println("Not connected");
				}

			} catch (Exception e2) {
				System.out.println("Not connected");
			}
	
	}

	public static void list() throws InterruptedException, UnknownHostException, IOException {

			System.out.println("Peer Id:		Peer IP Address:		Peer Port number");
			for (int i = 0; i < peers.size(); i++) {
				System.out.println(i + 1 + "    " + peers.get(i).getHost() + "               " + peers.get(i).getPort());

			}

	}

	public static void send(int connectionId, String message) {

		try {
			        peers.get(connectionId-1).processMessage(message);
					System.out.println("Message Sent");				
	
		} catch (Exception e2) {
			System.out.println("Cannot send message");
		}


	}

	public static void terminate(String socketHost, int socketPort) throws IOException {


		for (int i = 0; i < peers.size(); i++) {

			if (peers.get(i).getHost().equals(socketHost)) {
				peers.get(i).getSocket().shutdownInput();
				peers.get(i).getSocket().shutdownOutput();
				peers.get(i).getSocket().close();
				peers.remove(i);

			}
		}

	}
	
	public static void help() {
		
		System.out.println("******Help Menu*******");
		
		System.out.println("myip - displays IP of your computer");
		
		System.out.println("myport - displays your port number");
		
		System.out.println("connect - connects to other peers - enter the dest ip address and the port number");
		
		System.out.println("list - list all the connected connections");
		
		System.out.println("send - sends the message to the connected peer ( write send peerid message");
		
		System.out.println("terminate - terminates the connection between peer - write terminate peerid");
		
		System.out.println("exit - breaks all connections and exits");


	}

	public static void setPort(int p) 
	{	
				port = p;
	}
	


	private ServerSocket ssocket;

	public Server(int port) throws IOException, InterruptedException {
		listen(port);
	}
	
	private void listen(int port) throws IOException, InterruptedException {

		InetAddress address = InetAddress.getByName(getIP());
		
		ssocket = new ServerSocket(port, 0, address);

		sersoc = this;

		//System.out.println("Listening on IP " + ss.getInetAddress().getHostAddress() + ", Port " + ss.getLocalPort());

		while (true) {
			Socket s = ssocket.accept();
			System.out.println(" Connected with  " + s.getInetAddress().getHostAddress() );
			peers.add(new Client(s, this));
		}
	}


	public ArrayList < Client > clientsList() {
		return peers;
	}


}