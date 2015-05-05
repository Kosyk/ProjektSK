import java.net.InetAddress;

public class User {

	private String userNname;
	private InetAddress userAddress;

	public User(String username, InetAddress address) {
		userNname=username;
		userAddress = address;
	}
	
	public InetAddress getUserAddress() {
		return userAddress;
	}
	
	public String getUserNname() {
		return userNname;
	}
	
	public void printUser(){
		System.out.println("Nazwa: "+getUserNname());
		System.out.println("Adres IP: "+getUserAddress());

	}
}
