import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;


public class FileThread implements Runnable{
	
	@Override
	public void run() {
			try {
				System.out.println("Wybierz użytkownika:");
				int choice;
				Scanner in=new Scanner(System.in);
				choice=in.nextInt();
				
				DatagramSocket fileSocket = new DatagramSocket(Config.FILEPORT);
				byte[] request = "Plik".getBytes("utf8");

				DatagramPacket fileRequest = new DatagramPacket(
						request, request.length, Program.usersList.get(choice).getUserAddres(), Config.FILEPORT);
			
			fileSocket.send(fileRequest);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
