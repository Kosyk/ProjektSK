import java.util.ArrayList;

import javax.swing.JFileChooser;

public class Program {
	
	
	static ArrayList<User> usersList =new ArrayList<User>(); //lista użytkowników
	static ArrayList<String> pathList =new ArrayList<String>(); 
	
    public static void main(String[] args) throws Exception{
 
    	while (true){
    	JFileChooser chooser = new JFileChooser();
    	chooser.setDialogTitle("Wybierz udostępnione pliki");

    	if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
    		Program.pathList.add(chooser.getSelectedFile().getAbsolutePath());
    	} else 
    		break;	
    	}
    	System.out.println("Lista udostępnionych katalogów:");
    	for (int i=0; i<Program.pathList.size();i++){
    		System.out.println(Program.pathList.get(i));
    	}
    	
    	
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

