package code;

import java.net.InetAddress;
import java.net.Socket;

public class FileRetrievalClient
{
    public static void main(String[] args)
    {
        ClientSideEngine engine = new ClientSideEngine();
        ClientAppInterface appInterface = new ClientAppInterface(engine);
        
        //Socket socket = new Socket(InetAddress.getByName(address), port);
        
        // read commands from the user
        appInterface.readCommands();
    }
}
