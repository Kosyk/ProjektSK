import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class User {

	private String userNname;
	private InetAddress userAddres;
	private DatagramSocket datagramSocket;
	String directoryList[];

	public User(String username, InetAddress address, int filePort) {
		userNname=username;
		userAddres = address;
		try {
			datagramSocket=new DatagramSocket(filePort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public DatagramSocket getDatagramSocket() {
		return datagramSocket;
	}
	
	public InetAddress getUserAddres() {
		return userAddres;
	}
	
	public String getUserNname() {
		return userNname;
	}
	
	public void printUser(){
		System.out.println("Nazwa: "+getUserNname());
		System.out.println("Adres IP: "+getUserAddres());
		System.out.println("Port, którym może przesyłać pliki: "+ datagramSocket.getPort());
	//System.out.println("Udostęþnione katalogi: "+directoryList);
	}
}
