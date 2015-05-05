import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class ServerThread implements Runnable
{
public void run() {
	try {
		DatagramSocket datagramSocket = new DatagramSocket(Config.PORT);
	        while (true){
	        	
	            DatagramPacket recv= new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);

	            datagramSocket.receive(recv);

	            int length = recv.getLength();
	            String message =
	                    new String(recv.getData(), 0, length, "utf8");

	            InetAddress address = recv.getAddress();
	            int port = recv.getPort();

	            System.out.println(message+" <---to dostał serwer");
	    
	           if (message.equals("Witam w Rumbie")){
	                //serwer odpowiada, jak się nazywa
		            String userName = System.getProperty("user.name");
		            Thread.sleep(1000);
		            byte[] byteResponse = userName.getBytes("utf8");
		            DatagramPacket response = new DatagramPacket(
		                        byteResponse, byteResponse.length, address, port);
		            datagramSocket.send(response);
	           }
        }
	} catch (Exception e) {
		e.printStackTrace();
	}

}
} 