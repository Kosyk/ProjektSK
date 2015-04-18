import java.util.ArrayList;

public class Program {	
	static ArrayList<User> usersList =new ArrayList<User>();
    public static void main(String[] args) throws Exception{
 
    ServerThread sth= new ServerThread();
    ClientThread clt= new ClientThread();
    Thread t1 = new Thread(sth);
    Thread t2 = new Thread(clt);
    t1.start();
    t2.start();

    }
}

