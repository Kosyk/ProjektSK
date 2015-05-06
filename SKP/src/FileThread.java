import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class FileThread implements Runnable{
	
	public void run() {
		try {
			DatagramSocket fileServer = new DatagramSocket(Config.FILEPORT);
			
			//Nasłuchiwanie
			while (true){     	
	           DatagramPacket request= new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);

	           fileServer.receive(request);
	           int length = request.getLength();
	           String message =
	                    new String(request.getData(), 0, length, "utf8");

	           InetAddress address = request.getAddress();
	           int port = request.getPort();

	           if (message.equals("Lista")){
	        	    for(int i=0; i<Program.pathList.size();i++){
	        	    	byte[] byteResponse = Program.pathList.get(i).getBytes("utf8");
			            Thread.sleep(1000);
			            DatagramPacket response = new DatagramPacket(
			                        byteResponse, byteResponse.length, address, port);
			            fileServer.send(response);
	        	    }
           
	           }
	           else { //wysyła plik, message jako numer ścieżki
	        	   sendFile(Integer.parseInt(message), fileServer, address, port );
	        	   Thread.sleep(1000);      
	           }
        }    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	void sendFile(int pathNumber, DatagramSocket datagramSocket, InetAddress address, int port){
        try {
        	 byte byteArray[]=new byte[2000];
             FileInputStream fileInputStream=new FileInputStream(Program.pathList.get(pathNumber));
             int i=0;
             while(fileInputStream.available()!=0){
            	 byteArray[i]=(byte)fileInputStream.read();
                 i++;
             }                     
             fileInputStream.close();
			datagramSocket.send(new DatagramPacket(byteArray,i,address, port));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
