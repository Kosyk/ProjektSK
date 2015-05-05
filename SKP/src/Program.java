import java.util.ArrayList;

public class Program {	
	static ArrayList<User> usersList =new ArrayList<User>();
    public static void main(String[] args) throws Exception{
 
    ServerThread sth= new ServerThread();
    ClientThread cth= new ClientThread();
    
    Thread t1 = new Thread(sth);
    Thread t2 = new Thread(cth);
    
    t1.start();
    t2.start();  
    //część pliku
    FileThread fth= new FileThread();
    Thread t3 = new Thread(fth);
    t3.start();
    
    }
}

