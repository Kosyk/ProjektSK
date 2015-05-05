import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Scanner;

public class ClientThread  implements Runnable{
	
	public void run() {
		
	    try {
	    	//Wysyłanie zapytania na broadcast
	    	List<InterfaceAddress> interfaceAddress = NetworkInterface.getNetworkInterfaces().nextElement().getInterfaceAddresses();
	    	InetAddress broadcast = interfaceAddress.get(1).getBroadcast(); 	
	    	DatagramSocket socket = new DatagramSocket();

	    	byte[] message = "Witam w Rumbie".getBytes("utf8");	        
	        
			DatagramPacket sent = new DatagramPacket(message, message.length, broadcast, Config.PORT);
			socket.send(sent);
			
			DatagramPacket recv = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
			socket.setSoTimeout(1010);
				
			//Nasłuchiwanie odpowiedzi innych użytkowników			
			while(true){
				try{
				    socket.receive(recv);
				    int length = recv.getLength();
				    String name =
		                    new String(recv.getData(), 0, length, "utf8");
				    
				    //Dodawanie do listy nowych użytkowników
				    if (!name.equals(System.getProperty("user.name"))){
			    		Program.usersList.add(new User(name, recv.getAddress()));
			    	}				    		
				    
				}catch (SocketTimeoutException ste){
					break;
				}
			}	
				
			System.out.println("Przeszukano sieć");	
			
			if (!Program.usersList.isEmpty()){
				
				//Wypisanie listy użytkowników
				System.out.println("Użytkownicy na liście");
				for (int j=0; j<Program.usersList.size(); j++){
					System.out.println("Nr "+(j));
					Program.usersList.get(j).printUser();
				}
				
				System.out.println("Wybierz numer użytkownika, od którego chcesz pobrać plik:");
				int choice;
				Scanner in=new Scanner(System.in);
				choice=in.nextInt();
								
				//prośba o listę katalogów/plików
				DatagramSocket fileSocket = new DatagramSocket();
				fileSocket.setSoTimeout(1010);
				byte[] request = "Lista".getBytes("utf8"); 

				DatagramPacket fileRequest = new DatagramPacket(
						request, request.length, Program.usersList.get(choice).getUserAddress(), Config.FILEPORT);
				fileSocket.send(fileRequest);
				
				DatagramPacket rec = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
				
				//oczekiwanie na listę katalogów
				while(true){
					try{					
						fileSocket.receive(rec);
						int length = rec.getLength();
					    String path =
			                    new String(rec.getData(), 0, length, "utf8");
					    System.out.println("Udostępnione ścieżki przez "+Program.usersList.get(choice).getUserNname());
					    System.out.println(path);
					}catch (SocketTimeoutException ste){
						break;
					}
				}
				
				//wybór katalogu/pliku
				
				//odbiór pliku
			}
			else {
				System.out.println("Brak podłączonych użytkowników");
			}		
				
		} catch (Exception e) {
			e.printStackTrace();
		}	   
	}
} 