package pwjj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerThread extends Thread 
{
	
	Socket socket;
	private static int size = 24;
	private static String data[] = new String[size];	
	public ServerThread(Socket socket) 
	{
		super();
		this.socket = socket;
	}
	
	public void run()
	{
		try 
		{

			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println(checkTheDriver());
			Statement connectToQuestion = connectToDatabase("localhost:port","bgacek","root","").createStatement();
			Statement readQuestions = connectToDatabase("localhost:port","bgacek","root","").createStatement();
			String queryForQuestions = "SELECT * from questions";
			saveQuestionsToDatabase(connectToQuestion);
			ResultSet result = executeQuery(readQuestions,queryForQuestions);
			copyDataFromDatabase(result);
			
			
			

				
			//test1
	
		}
		catch (Exception e) 
		{
			System.out.print(e);
		}
	}

	static boolean checkTheDriver() 
	{
		System.out.print(" Checking driver: ");
		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return true;
		} 
		catch (Exception e) 
		{
			System.out.println("Error while loading database");
			return false;
		}
	}
	
	
	private static Connection connectToDatabase(String adress, String dataBaseName, String userName, String password) 
	{
		System.out.println("Connecting to database:");
		String base = "jdbc:mysql://" + adress + "/" + dataBaseName;
		
		java.sql.Connection connection = null;
		try 
		{
			connection = DriverManager.getConnection(base, userName, password);
			System.out.println("Connected.");
		} 
		catch (SQLException e) 
		{
			System.out.println("");
			System.exit(1);
		}
		return connection;
	}
	

	
	private static Statement createStatement(Connection connection) 
	{
		try 
		{
			return connection.createStatement();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	

	private static void copyDataFromDatabase(ResultSet get) 
	{
		ResultSetMetaData get_information_about_tables;
		try 
		{	
			get_information_about_tables = get.getMetaData();
			int number_of_columns = get_information_about_tables.getColumnCount(); 
			int counter = 0;

			while(get.next()) 
			{
				for (int i = 1; i <= number_of_columns; i++) 
				{
					Object obj = get.getObject(i);
					
					if (obj != null)
					{
						data[counter] = obj.toString();
						counter++;	
					}
				}
			}
		}
		catch (SQLException e) 
		{
			System.out.println("Database read error " + e.toString());
			System.exit(3);
		}
	}
	private static ResultSet executeQuery(Statement state, String sql_querry) 
	{
		try 
		{
			return state.executeQuery(sql_querry);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	

	
	private static void closeConnection(Connection connection, Statement s) 
	{
		
		System.out.println("Closing database connection");
		try 
		{
			s.close();
			connection.close();
		} 
		catch (SQLException e) 
		{
			System.out.println("Database close error" + e.toString());
			System.exit(4);
		}
			System.out.println("Database closed");
	}
/*	
 * nie tak...
	public static void createDatabase()
	{
		String querryToCreateBase = "DROP DATABASE `bgacek`; CREATE DATABASE `bgacek`; CREATE TABLE `questions` ( `question`, `answerA`, `answerB`, `answerC`, `answerD`, `uanswer`)";
	}
	*/

	public static void saveQuestionsToDatabase(Statement connectToQuestion) throws SQLException
	{
		
		//w ten sposob bo mialem kiedys problem podczas wypelniania tabel w xamppie i bez wypisania nazw kolumn nie wypelnialo poprawnie bazy 
		
		connectToQuestion.executeUpdate("INSERT INTO `questions` ( `question`, `answerA`, `answerB`, `answerC`, `answerD`, `uanswer`) VALUES ('1.?', 'A)', 'B)', 'C)', 'D)', 'B');");
		connectToQuestion.executeUpdate("INSERT INTO `questions` ( `question`, `answerA`, `answerB`, `answerC`, `answerD`, `uanswer`) VALUES ('2.?', 'A)', 'B)', 'C)', 'D)', 'D');");
		connectToQuestion.executeUpdate("INSERT INTO `questions` ( `question`, `answerA`, `answerB`, `answerC`, `answerD`, `unaswer`) VALUES ('3.?', 'A)', 'B)', 'C)', 'D)', 'A');");
		connectToQuestion.executeUpdate("INSERT INTO `questions` ( `question`, `answerA`, `answerB`, `answerC`, `answerD`, `unaswer`) VALUES ('4.?', 'A)', 'B)', 'C)', 'D)', 'B');");
	}
	 
}