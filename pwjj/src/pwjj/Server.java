package pwjj;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server 
{
	
	public static synchronized void main(String[] args) throws Exception 
	{
		
			ServerSocket serwerSocket = new ServerSocket(9123);
			Socket klientSocket = serwerSocket.accept();
			ServerThread serwerGniazdo = new ServerThread(klientSocket);
			
			if(klientSocket.isConnected())
			{	
				serwerGniazdo.start();
			}
			serwerSocket.close();
	}
}
