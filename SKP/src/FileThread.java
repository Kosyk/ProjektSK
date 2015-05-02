import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class FileThread implements Runnable{
	
	@Override
	public void run() {
		try {
			DatagramSocket datagramSocket = new DatagramSocket(Config.FILEPORT);
		        while (true){
		        	
		            DatagramPacket recv= new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);

		            datagramSocket.receive(recv);

		            int length = recv.getLength();
		            String message =
		                    new String(recv.getData(), 0, length, "utf8");

		            InetAddress address = recv.getAddress();
		            int port = recv.getPort();

		           if (message.equals("Lista")){
			           
			            byte[] byteResponse = "Tu będą ścieżki".getBytes("utf8");
			            Thread.sleep(1000);
			            DatagramPacket response = new DatagramPacket(
			                        byteResponse, byteResponse.length, address, port);
			            datagramSocket.send(response);
		           }
		           else {
		        	   //wysyłanie pliku - ścieżka jako message
		           }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
