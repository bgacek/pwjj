package pwjj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread
{
	
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		Socket socket = new Socket(InetAddress.getByName("localhost"), 9123);
		PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		Scanner writeAnAnswer = new Scanner(System.in);
		String[] answer = new String[4];
		int counter = 0;
		int i = 0;
			
		do
		{
			if(counter == 5)
			{
				answer[i] = writeAnAnswer.nextLine();
				output.println(answer[i]);
				System.out.println(input.readLine());
				i++;
				counter = 0;
			}
			else
			{
				System.out.println(input.readLine());
				counter++;
			}
		}
		while(socket.isConnected());
		
		socket.close();
		writeAnAnswer.close();
	}
}
