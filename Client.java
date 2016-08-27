package Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread 
{

	private Socket socket;
	private String host;
	private int port;
	private Server server;
	private DataOutputStream dout;
	private DataInputStream din;


	public Client(String host, int port, Server server) 
	{

		this.host = host;
		this.port = port;
		this.server = server;

		try 
		{
			
			this.socket = new Socket(host, port);
			this.din = new DataInputStream(socket.getInputStream());
			this.dout = new DataOutputStream(socket.getOutputStream());


		} catch (IOException ie) {
			System.out.println(ie);
		}
		
				start();
	}
	
	
	public Client(Socket s, Server server) {
		try {
			
			this. socket = s;
			this.server = server;
			this.host = s.getInetAddress().getHostAddress();
			this.port = s.getPort();
			this.din = new DataInputStream(socket.getInputStream());
			this.dout = new DataOutputStream(socket.getOutputStream());


		} catch (IOException ie) {
			System.out.println(ie);
		}
						start();

	}


	@Override
	public void run() {
		try {

			DataInputStream din = new DataInputStream(socket.getInputStream());
			while (true) {

				String msg = din.readUTF();
				
				System.out.println("Message received from " + socket.getInetAddress().getHostAddress());
				System.out.println(msg);
			}
		} catch (EOFException ie) {
		} catch (IOException ie) {

		} finally {
	
			try{
				
			for (int i = 0; i < this.server.peers.size(); i++) {

				if (this.server.peers.get(i).getHost().equals(socket.getInetAddress().getHostAddress()) && this.server.peers.get(i).getPort() == socket.getPort() ) {
					this.server.peers.remove(i);
				}
			}

			if(socket != null)
			{
			System.out.println("Connection terminated with " + socket.getInetAddress().getHostAddress() );
			}
			
		} catch (Exception e6) {}
		
		
			this.interrupt();
			this.stop();

		}
	}

	public void processMessage(String message) {
		try {
			dout.writeUTF(message);
		} catch (IOException ie) {
			System.out.println(ie);
		}
	}

	public Socket getSocket() {
		return socket;
	}
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}





}