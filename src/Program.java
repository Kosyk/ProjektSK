import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Program {

    public static void main(String[] args) throws Exception{
    Klient kl = new Klient();
    Serwer sr = new Serwer();
    DatagramSocket socket = new DatagramSocket(Config.PORT);
    socket.setSoTimeout(1010);
    kl.Hello(socket);
    while (true){
    sr.Wait(socket);
    kl.Recive(socket);
    }
    }
}

