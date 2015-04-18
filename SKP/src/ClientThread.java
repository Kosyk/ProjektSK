import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class ClientThread  implements Runnable
{
public void run() {
	
    try {
    	//Wysyłanie na broadcast
    	DatagramSocket socket = new DatagramSocket();
    	String message = "Witam w Rumbie";
    	byte[] stringContents = message.getBytes("utf8");
        InetAddress serverAddress = InetAddress.getByName("192.168.20.255"); 

		DatagramPacket sent = new DatagramPacket(
				stringContents, stringContents.length, serverAddress, Config.PORT);
		socket.send(sent);
		
		//Słuchanie serwera
			DatagramPacket recv = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
			socket.setSoTimeout(1010);
			while(true){
			try{
			    socket.receive(recv);
			    int length = recv.getLength();
			    String name =
	                    new String(recv.getData(), 0, length, "utf8");

			    System.out.println("Serwer otrzymał wiadomość i nazywa się:"+name);
			}catch (SocketTimeoutException ste){
			}
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
   
}
} 