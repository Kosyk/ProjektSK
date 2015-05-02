import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

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
		

			DatagramPacket recv = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
			socket.setSoTimeout(1010);
			//Słuchanie serwerów			
			while(true){
				try{
				    socket.receive(recv);
				    int length = recv.getLength();
				    String name =
		                    new String(recv.getData(), 0, length, "utf8");
				    	if (!name.equals(System.getProperty("user.name"))){
				    		System.out.println("Serwer otrzymał wiadomość i nazywa się:"+name);
						    System.out.println("Jego adres: "+recv.getAddress());
				    		Program.usersList.add(new User(name, recv.getAddress()));
				    	}
				    		
				    
				}catch (SocketTimeoutException ste){
					break;
				}
			}
			
			System.out.println("Wszystkich użytkowników już mam, wychodzę z while'a...");
			System.out.println("Użytkownicy na liście");
			for (int j=0; j<Program.usersList.size(); j++){
				System.out.println("Nr"+(j+1));
				Program.usersList.get(j).printUser();
			}
			
			System.out.println("Wybierz użytkownika:");
			int choice;
			Scanner in=new Scanner(System.in);
			choice=in.nextInt()-1;
			
			
			//prośba o listę katalogów/plików
			DatagramSocket fileSocket = new DatagramSocket(Config.FILEPORT);
			socket.setSoTimeout(1010);
			byte[] request = "Lista".getBytes("utf8"); 

			DatagramPacket fileRequest = new DatagramPacket(
					request, request.length, Program.usersList.get(choice).getUserAddres(), Config.FILEPORT);
			fileSocket.send(fileRequest);
			
			//oczekiwanie na listę katalogów
			while(true){
				try{
					fileSocket.receive(recv);
				}catch (SocketTimeoutException ste){
					break;
				}
			}
			
			//wybór katalogu/pliku
			
			//odbiór pliku
			
	} catch (Exception e) {
		e.printStackTrace();
	}
   
}
} 