import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

class UserHandler implements Runnable
{
    Scanner scn = new Scanner(System.in);
    private String name;
    boolean isActive;
    final DataInputStream reader;
    final DataOutputStream write;
    Socket socket;

    public UserHandler(Socket s, String name,
                       DataInputStream reader, DataOutputStream write) {
        this.reader = reader;
        this.write = write;
        this.name = name;
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

                // System.out.println(received);

                if(received.equals("quit")){
                    this.isActive=false;
                    this.socket.close();
                    break;
                }

                String msgToSend = null;
                String recipient = null;
                try{
                    StringTokenizer st = new StringTokenizer(received, ":");
                    recipient = st.nextToken();
                    msgToSend = st.nextToken();
                } catch (Exception e){

                }

                for (UserHandler mc : Server.listCLient)
                {
                    System.out.println(mc.name + " : "+recipient);

                    if (mc.name.equals(recipient) && mc.isActive==true)
                    {
                        mc.write.writeUTF("<<"+this.name+" >> : "+msgToSend);
                        break;
                    }
                }
            } catch (IOException e) {

                e.printStackTrace();
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