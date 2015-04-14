import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class ServerThread implements Runnable
{
public void run() {
	try {
		DatagramSocket datagramSocket = new DatagramSocket(Config.PORT);

        byte[] byteResponse = "OK".getBytes("utf8");
        datagramSocket.setSoTimeout(1010);
        while (true){

            DatagramPacket reclievedPacket
                    = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);

            datagramSocket.receive(reclievedPacket);

            int length = reclievedPacket.getLength();
            String message =
                    new String(reclievedPacket.getData(), 0, length, "utf8");

            InetAddress address = reclievedPacket.getAddress();
            int port = reclievedPacket.getPort();

            System.out.println(message);
            Thread.sleep(1000); 
            DatagramPacket response
                    = new DatagramPacket(
                        byteResponse, byteResponse.length, address, port);

            datagramSocket.send(response);

        }
	} catch (Exception e) {
		e.printStackTrace();
	}

}
} 


class Recive  implements Runnable
{
public void run() {
	
    try {
    	DatagramSocket socket=new DatagramSocket(Config.PORT);
    	DatagramPacket reclievePacket = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
		socket.setSoTimeout(1010);
		
	    socket.receive(reclievePacket);
	    System.out.println("Serwer otrzymał wiadomość");
	    int length = reclievePacket.getLength();
        String message =
        		new String(reclievePacket.getData(), 0, length, "utf8");
	    System.out.println(message+" odebrane");		        
	    System.out.println("Serwer nie odpowiedział, więc albo dostał wiadomość albo nie...");
	        
	} catch (Exception e) {
		e.printStackTrace();
	}
   
}
} 



public class Program {	
	
	public static void Hello(DatagramSocket socket) throws Exception{
    	String message = "Witam";
        InetAddress serverAddress = InetAddress.getByName("10.0.2.255");

        byte[] stringContents = message.getBytes("utf8"); //Pobranie strumienia bajtów z wiadomosci

        DatagramPacket sentPacket = new DatagramPacket(stringContents, stringContents.length);
        sentPacket.setAddress(serverAddress);
        sentPacket.setPort(Config.PORT);
        socket.send(sentPacket);
    }	

    public static void main(String[] args) throws Exception{
    DatagramSocket socket = new DatagramSocket(Config.PORT);
    socket.setSoTimeout(1010);
    
    
    ServerThread sth= new ServerThread();
    Recive recv= new Recive();
    Hello(socket);
    sth.run();
    recv.run();

    }
}

