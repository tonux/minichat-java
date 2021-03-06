import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class User
{
    final static String ServerURL = "localhost";
    final static int ServerPort = 3000;

    public static void main(String args[]) throws IOException
    {
        Scanner scn = new Scanner(System.in);

        Socket socket = new Socket(ServerURL, ServerPort);

        DataInputStream reader = new DataInputStream(socket.getInputStream());
        DataOutputStream write = new DataOutputStream(socket.getOutputStream());

        // sender message thread
        Thread sendMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {
                while (true) {

                    String message = scn.nextLine();

                    try {
                        write.writeUTF(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // reader Message thread
        Thread readMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {

                try {
                        while (true ){
                        String messageReceive = reader.readUTF();
                        System.out.println(messageReceive);
                        }
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
            }
        });

        sendMessage.start();
        readMessage.start();

    }
}