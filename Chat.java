package Chat;

import java.io.IOException;
import java.util.Scanner;

public class Chat extends Server 
{

	public Chat(int port) throws IOException, InterruptedException 
	{
		super(port);
		
	}

	public static Server s;

	public static void main(String args[]) throws IOException, InterruptedException 
	{

	try
	{
	
		int prtno = Integer.parseInt(args[0]);
		
		if(prtno > 0 && prtno <= 65535 )
		{setPort(prtno);}
		
		else{
			System.out.println("Port number not within the range of 0 and 65535/ Enter correct Port number");
			Runtime.getRuntime().exit(0);}
	
	} 
	catch (Exception e6) 
	{
		System.out.println("Port number not within the range of 0 and 65535/ Enter correct Port number");
		Runtime.getRuntime().exit(0);
	}
		
	Thread.sleep(400);
	
	new Thread(new Runnable() 
	{
			@Override
			public void run() 
			{
				try 
				{	
					s = new Server(port);
			
				}
				catch (IOException e) 
				
				{
					
				} 
				catch (InterruptedException e) 
				{

				}
			}
		}).start();

		while (true) 
		{
			
			Thread.sleep(400);

			System.out.print("Enter the command: ");
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System. in );
			String cmd = scanner.nextLine();

			if (cmd.contains(" ")) 
			{
				try 
				{
					String[] info = cmd.split(" ");
					if (info[0].equals("connect"))
					{
						connect(info[1], Integer.parseInt(info[2]));
					}

				} 
				catch (Exception e7) {
					System.out.println("Connection commands not proper");
				}

				try {
					String[] info = cmd.split(" ");
					if (info[0].equals("send")) {
						String peers = null;

						for (int i = 2; i < info.length; i++) {
							peers = info[i] + " ";
						}
						send(Integer.parseInt(info[1]), peers);
					}

				} catch (Exception e6) {
					System.out.println("Sorry! Not able to send the message");

				}

				try {

					String[] info = cmd.split(" ");

					if (info[0].equals("terminate")) {
						
						int socketPort = peers.get(Integer.parseInt(info[1]) - 1).getPort();
						String socketHost = peers.get(Integer.parseInt(info[1]) - 1).getHost();
						terminate(socketHost, socketPort);
						
					}

				} catch (Exception e6) {
					System.out.println("Can not close this connection");
				}

			} else {

				if (cmd.equals("help")) 
				
				{help();}
				
				else if (cmd.equals("exit")) {Runtime.getRuntime().exit(0);}
				
				else if (cmd.equals("myip")) {myip();}
				
				else if (cmd.equals("myport")) {myport();}
				
				else if (cmd.equals("list")) {list();}

			}
		}
	}
}