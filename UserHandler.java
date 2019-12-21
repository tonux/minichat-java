import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

class UserHandler implements Runnable
{
    private String userName;
    boolean isActive;
    final DataInputStream reader;
    final DataOutputStream write;
    Socket socket;

    public UserHandler(Socket s, String name,
                       DataInputStream reader, DataOutputStream write) {
        this.reader = reader;
        this.write = write;
        this.userName = name;
        this.socket = s;
        this.isActive=true;

    }

    @Override
    public void run() {

        String received;
        while (true)
        {
            try
            {
                received = reader.readUTF();

                if(received.equals("quit")){
                    this.isActive=false;
                    this.socket.close();
                    break;
                }

                String message = null;
                String receiver = null;
                try{
                    StringTokenizer st = new StringTokenizer(received, ":");
                    receiver = st.nextToken();
                    message = st.nextToken();
                } catch (Exception e){

                }

                for (UserHandler userHandler : Server.listCLient)
                {
                    System.out.println(userHandler.userName + " send to "+receiver+ " message : "+message);

                    if (userHandler.userName.equals(receiver) && userHandler.isActive==true)
                    {
                        userHandler.write.writeUTF("<<"+this.userName+" >> : "+message);
                        break;
                    }
                }
            } catch (IOException e) {

                // e.printStackTrace();
            }

        }
        try
        {
            this.reader.close();
            this.write.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}