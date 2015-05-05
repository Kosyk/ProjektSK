import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class FileThread implements Runnable{
	
	@Override
	public void run() {
		try {
			DatagramSocket fileServer = new DatagramSocket(Config.FILEPORT);
		        while (true){
		        	
		            DatagramPacket request= new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);

		            fileServer.receive(request);

		            int length = request.getLength();
		            String message =
		                    new String(request.getData(), 0, length, "utf8");

		            InetAddress address = request.getAddress();
		            int port = request.getPort();

		           if (message.equals("Lista")){
			           
			            byte[] byteResponse = "Tu będą ścieżki".getBytes("utf8");
			            Thread.sleep(1000);
			            DatagramPacket response = new DatagramPacket(
			                        byteResponse, byteResponse.length, address, port);
			            fileServer.send(response);
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
