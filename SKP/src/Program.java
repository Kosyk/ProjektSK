import java.util.ArrayList;

public class Program {
	
	static ArrayList<User> usersList =new ArrayList<User>(); //lista użytkowników
	
    public static void main(String[] args) throws Exception{
 
	    ServerThread sth= new ServerThread();
	    ClientThread cth= new ClientThread();
	    FileThread fth= new FileThread();
	    
	    Thread t1 = new Thread(sth);
	    Thread t2 = new Thread(cth);
	    Thread t3 = new Thread(fth);
	    
	    t1.start();
	    t2.start();  
	    t3.start();   
    }
}

