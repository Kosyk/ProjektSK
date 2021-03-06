import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

public class ClientThread  implements Runnable{
	
	public void run() {
		while (true){
		    try {
		    	//Wysyłanie zapytania na broadcast
		    	List<InterfaceAddress> interfaceAddress;
		    	InetAddress broadcast = InetAddress.getLocalHost();
				try {
					interfaceAddress = NetworkInterface.getNetworkInterfaces().nextElement().getInterfaceAddresses();
					broadcast = interfaceAddress.get(1).getBroadcast(); 
				} catch (Exception e) {
					System.out.println("Wystąpił problem z pobraniem adresu broadcast");
				}
		    		
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
					    Program.usersList.clear();
					    //Dodawanie do listy nowych użytkowników
					    if (!name.equals(System.getProperty("user.name"))){
				    		Program.usersList.add(new User(name, recv.getAddress()));
				    	}				    		
					    
					}catch (SocketTimeoutException ste){
						break;
					}
				}	
					
				System.out.println("\nPrzeszukano sieć");	
				
				if (!Program.usersList.isEmpty()){
					
					//Wypisanie listy użytkowników
					System.out.println("\nUżytkownicy na liście");
					for (int j=0; j<Program.usersList.size(); j++){
						System.out.println("Nr "+(j));
						Program.usersList.get(j).printUser();
					}
					
					//Wybór konkretnego użytkownika
					System.out.println("\nWybierz numer użytkownika, od którego chcesz pobrać plik:");
					int choice;
					Scanner in=new Scanner(System.in);
					choice=in.nextInt();
									
					//Prośba o listę katalogów/plików
					DatagramSocket fileSocket = new DatagramSocket();
					fileSocket.setSoTimeout(1010);
					byte[] request = "Lista".getBytes("utf8"); 

					DatagramPacket fileRequest = new DatagramPacket(
							request, request.length, Program.usersList.get(choice).getUserAddress(), Config.FILEPORT);
					fileSocket.send(fileRequest);
					
					DatagramPacket rec = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
					
					//oczekiwanie na listę katalogów
					System.out.println("\nUdostępnione ścieżki przez "+Program.usersList.get(choice).getUserNname()+":");
					int i=0;
					while(true){
						try{
							
							fileSocket.receive(rec);
							int length = rec.getLength();
						    String path =
				                    new String(rec.getData(), 0, length, "utf8");					 
						    System.out.println("Ścieżka nr "+i+": "+path);
						    i++;
						}catch (SocketTimeoutException ste){
							i=0;
							break;
						}
					}
					
					//wybór katalogu/pliku
					System.out.println("\nWybierz numer ścieżki pliku, który chcesz pobrać");
					i=in.nextInt();
					request=Integer.toString(i).getBytes("utf8");
					fileRequest = new DatagramPacket(
							request, request.length, Program.usersList.get(choice).getUserAddress(), Config.FILEPORT);
					fileSocket.send(fileRequest);
					
					//odbiór pliku	
					saveFile(fileSocket, rec);
				}
				else {
					System.out.println("\nBrak podłączonych użytkowników");
				}		
					
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	void saveFile(DatagramSocket datagramSocket, DatagramPacket datagramPacket){
        try {
        	String path=null;
        	UIManager.put("FileChooser.cancelButtonText", "Anuluj");
			JFileChooser chooser = new JFileChooser();
			chooser.setApproveButtonText("Zapisz");
	    	chooser.setDialogTitle("Podaj ścieżkę, pod którą chcesz zapisać plik:");
	    	if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	    		path=chooser.getSelectedFile().getAbsolutePath();
	    		FileOutputStream fileOutputStream=new FileOutputStream(path);
				
				while(true) {
					try {
	             	   datagramSocket.receive(datagramPacket);
	             	   fileOutputStream.write(datagramPacket.getData(), 0, datagramPacket.getLength()); 
	             	   fileOutputStream.flush();
	             	   fileOutputStream.close();
	             	   System.out.println("Plik zapisany");
	             	   break;
					} catch (SocketTimeoutException e) {
						break;
					}                  	
		        }
	    	}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
} 