import java.net.InetAddress;


public class User {

	private String userNname;
	private InetAddress userAddres;


	public User(String username, InetAddress address) {
		userNname=username;
		userAddres = address;

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

	}
}
