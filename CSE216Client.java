/** <p>Title: P2P Project</p>
 *
 * <p>Description: This class is the client for the 
 *    CSE216 System.       
 * </p>
 *
 * <p>Copyright: none</p>
 *
 * <p>Company: Lehigh University</p>
 *
 * @author Bill Phillips 
 *
 */
import java.io.*;
import java.util.*;

//import sun.tools.asm.CatchData;

public class CSE216Client implements Runnable 
{

   private final ClientTransactionLogger ctl
           =  ClientTransactionLogger.Instance();
   private final ConfigFile cf
           =               ConfigFile.Instance();
   private final RunTimeVars rtv
           =               RunTimeVars.Instance();
   private final ClientServices        cservices;

   private CSE216ClientCommandLineInterface  cli;
   private String                       hostName;

   private final int mainport =  
                             rtv.getServerPort();
   private final String MAINPORT = 
                        String.valueOf(mainport);
   private final boolean auth =            true;

   public CSE216Client(String hName) 
   {
      hostName = hName;

      //
      // Create a client services object.
      //
      cservices = new ClientServices();

      ctl.writeToLogger("Client Created");
   }

   public final void run() {
         int index  = 0;
         String [] acsList = new String [100];
         String currentContent = null;
         Scanner sc = new Scanner (System.in);
         Scanner rsc = null;
         try {
            rsc = new Scanner (new File ("acsList.txt"));
         }
         catch (IOException io){
            System.out.println("File acsList.txt not found in the current directory!");
         }
         // skip the first row 
         rsc.nextLine();
         boolean auth = false;
         System.out.println("Please enter your username:");
         String username = sc.nextLine();
         System.out.println("Please enter your password:");
         String password = sc.nextLine();
         System.out.println("Please enter your role:");
         String role = sc.nextLine();
         String userInput = username + " " + password + " " + role;
         // read existing file (acsList.txt)
         while (rsc.hasNext()){
            acsList [index] = rsc.nextLine();
            index ++;
         }
         for (int i =0; i<acsList.length; i++) {
            if (userInput.equals(acsList [i])){
               auth = true;
               break;
            }
         }
         // authenticate successfully
         if (auth){
            System.out.println("\nAuthentication succeeded!");
            ctl.writeToLogger("Client Started");
            ctl.writeToLogger("Auth code = ", 0);
            //cli instantiates on host/ server IP 
            cli = new CSE216ClientCommandLineInterface (hostName, MAINPORT, role);
            // hostName changed to the client IP
            hostName = ConfigFile.Instance().getFirstIP();
            // After authentication asking for user to input message 
            do {
               CState cs = cli.getUserSelection();
               boolean bf = cservices.Connect (hostName, mainport);
               if (bf && cs.getV() ==1){
                  cservices.send(cs);
                  ctl.writeToLogger(cs.getMessage());
                  cservices.Disconnect();
                  ctl.writeToLogger("Client disconnected");
               }
               if (bf && cs.getV() ==2){
                  try ( FileWriter fw = new FileWriter(new File("acsList.txt"),true);){
                     fw.append(cs.getMessage());
                     ctl.writeToLogger("New account has been created.");
                  }catch(IOException io){
                     System.out.println("Unable to open acsList.txt and system exited");
                     System.exit(0);
                  }
               }
            } while (true);
         }
         // exit program whenever authentication fail
         else {
            System.out.println("Unable to authenticate, program exited!\n\n");
            ctl.writeToLogger("Authentication failed due to incorrect username/password/role!\nAuth code = 1");
            System.exit(0);
         }


   }

}

