import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Serwer {

   
        public void Wait(DatagramSocket datagramSocket) throws Exception{

	        while (true){
	
	            DatagramPacket reclievedPacket
	                    = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
	           
					datagramSocket.receive(reclievedPacket);
					int length = reclievedPacket.getLength();
		            String message =
		                    new String(reclievedPacket.getData(), 0, length, "utf8");
		
		            // Port i host który wysłał nam zapytanie
		            InetAddress address = reclievedPacket.getAddress();
		            int port = reclievedPacket.getPort();
		            Thread.sleep(1000);
		            System.out.println(message);
		            
		           
			            byte[] byteResponse = "OK".getBytes("utf8");
			            DatagramPacket response
			        	
			      	  = new DatagramPacket(
			      	                        byteResponse, byteResponse.length, address, port);
			      	
			            datagramSocket.send(response);
		            break;
	
	         
	        }
	    	
        }
    
}