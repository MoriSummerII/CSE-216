import java.util.*;
import java.io.*;

/** <p>Title: P2P Project</p>
 *
 * <p>Description: This class gets a test message.       
 * </p>
 *
 * <p>Copyright: none</p>
 *
 * <p>Company: Lehigh University</p>
 *
 * @author Bill Phillips 
 *
 */

public class CSE216ClientCommandLineInterface {
   
   private final RunTimeVars rtv = 
                           RunTimeVars.Instance();
   private final ConfigFile cf   =  
                            ConfigFile.Instance(); 
   private final String hostname = 
                               cf.getMyHostName();
   private final int portnumber  = 
                              rtv.getServerPort();

   private final String fileName = "acsList.txt";
   private String username = null;
   private String password = null;
   private File acsFile = null;

   private final String prompt;
   
   private Scanner    sc;
   private Scanner rsc = null;
   private String role = null;

   
   private int             sel;
   
   public CSE216ClientCommandLineInterface 
              (final String hname, final String m, String role) 
   {
      if (role.equals("Ad")){
         prompt = "\nHost: " + hostname
         +        "     Node ID: " + m + "\n" +  "********Admin MENU*********\n"
         + "1 - Send message (encrytion and decrytion will be written to loggers).\n"+
         "2 - Create new accounts (UserName, Password, Role). \n"
         +     "Anything else - Leave \n"+"******END MENU******";
      }else{
         prompt = "\nHost: " + hostname
         +        "     Node ID: " + m + "\n" +  "********Accessing MENU*********\n"
         + "1 - Send message (encrytion and decrytion will be written to loggers).\n"
         +     "Anything else - Leave \n"+"******END MENU******";
      }

      acsFile = new File(fileName);
      sc =                 new Scanner(System.in);
      try {
         rsc = new Scanner (acsFile);
      }
      catch(IOException io){
         System.out.println("File acsList.txt not found in the current directory!");
      }
      this.role = role;
   }
   
   public CState getUserSelection()
   {  
      String [] acsList = new String [5];
      int index = 0;
      String currentContent = null;
      // skip the first row
      rsc.nextLine();

      CState cs =  new CState();
      cs.mid    = MessageID.MSG;
      
      System.out.println(prompt);
      try {
             sel = sc.nextInt();
      } catch (Exception e) 
      {
         System.exit        (0);
      }

      if (sel == 1){
         System.out.println("Enter the message you want to send: ");
         sc = new Scanner (System.in);
         String usrInput = sc.nextLine();
         cs.setMessage(usrInput);
         // when CState object's value is 1 => sending message
         cs.setV(1);
         cs.reverse();
      }
      else if (sel ==2 && role.equals("Ad")){
         sc = new Scanner (System.in);
            System.out.println("Enter new username: ");
            String usrName = sc.nextLine();
            System.out.println("Enter new password: ");
            String password = sc.nextLine();
            System.out.println("Enter new role: ");
            String role = sc.nextLine();
            String newAcct = usrName + " " + password + " " + role+"\n";
            cs.setMessage(newAcct);
            // CState value is 2 when create new account
            cs.setV(2);
      } 
      else 
         System.exit(0);

      
      return cs;
      
   }
   
}
