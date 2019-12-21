import java.io.*;
import java.util.*;
import java.net.*;

public class Server{

	public static Vector<UserHandler> listCLient = new Vector<>();

	private static int PORT = 3000;
	
	static int ID = 0;

	public static void main(String[] args) throws IOException
	{
		// server is listening on port
		ServerSocket serverSocket = new ServerSocket(PORT);

		Socket socket;

		while (true)
		{
			socket = serverSocket.accept();

			System.out.println("New connexion : " + socket);

			DataInputStream reader = new DataInputStream(socket.getInputStream());
			DataOutputStream write = new DataOutputStream(socket.getOutputStream());

			System.out.println("welcome to user_"+ID);

			// Create a new handler for this request.
			UserHandler mtch = new UserHandler(socket,"user_" + ID, reader, write);

			// Create a new Thread with this object.
			Thread t = new Thread(mtch);

			System.out.println("user_"+ID+" is added to user list");

			// add user to list
			listCLient.add(mtch);

			t.start();

			ID++;

		}
	}

	public static Vector<UserHandler> getListCLient() {
		return listCLient;
	}

	public static void setListUser(Vector<UserHandler> listCLient) {
		Server.listCLient = listCLient;
	}
}