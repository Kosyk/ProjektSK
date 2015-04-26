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
		

			DatagramPacket recv = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
			socket.setSoTimeout(1010);
			//Słuchanie serwerów
			int i=0;
			while(true){
				try{
				    socket.receive(recv);
				    int length = recv.getLength();
				    String name =
		                    new String(recv.getData(), 0, length, "utf8");
				    System.out.println("Serwer otrzymał wiadomość i nazywa się:"+name);
				    System.out.println("Jego adres: "+recv.getAddress());

					Program.usersList.add(new User(name, recv.getAddress(), 8500+i));
				    i++;
				}catch (SocketTimeoutException ste){
					break;
				}
			}
			i=0;
			System.out.println("Wszystkich użytkowników już mam, wychodzę z while'a...");
			System.out.println("Użytkownicy na liście");
			for (int j=0; j<Program.usersList.size(); j++){
				System.out.println("Nr"+(j+1));
				Program.usersList.get(j).printUser();
			}
			//System.out.println("Wybierz użytkownika, oraz podaj ścieżkę do pliku, który chcesz pobrać:");
	} catch (Exception e) {
		e.printStackTrace();
	}
   
}
} 