
public class Program {	

    public static void main(String[] args) throws Exception{
 
    ServerThread sth= new ServerThread();
    ClientThread clt= new ClientThread();

    clt.run();
    sth.run();

    }
}

