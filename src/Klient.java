import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class Klient {

    
    public void Hello(DatagramSocket socket) throws Exception{
    	String message = "Witam";
        InetAddress serverAddress = InetAddress.getByName("localhost");

        byte[] stringContents = message.getBytes("utf8"); //Pobranie strumienia bajtów z wiadomosci

        DatagramPacket sentPacket = new DatagramPacket(stringContents, stringContents.length);
        sentPacket.setAddress(serverAddress);
        sentPacket.setPort(Config.PORT);
        socket.send(sentPacket);
    }
        
    public void Recive(DatagramSocket socket) throws Exception{
        DatagramPacket reclievePacket = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
        
        try{
            socket.receive(reclievePacket);
            System.out.println("Serwer otrzymał wiadomość");
            int length = reclievePacket.getLength();
            String message =
                    new String(reclievePacket.getData(), 0, length, "utf8");
            
            System.out.println(message+" odebrane");
        }catch (SocketTimeoutException ste){
            System.out.println("Serwer nie odpowiedział, więc albo dostał wiadomość albo nie...");
        }
    }
    
}