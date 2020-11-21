import java.util.*;

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

public class TestMsgClientCommandLineInterface {
   
   private final RunTimeVars rtv = 
                           RunTimeVars.Instance();
   private final ConfigFile cf   =  
                            ConfigFile.Instance(); 
   private final String hostname = 
                               cf.getMyHostName();
   private final int portnumber  = 
                              rtv.getServerPort();
   


   private final String prompt;
   
   private final Scanner    sc;
   
   private int             sel;
   
   public TestMsgClientCommandLineInterface
              (final String hname, final String m) 
   {

      prompt = "Host: " + hostname
              +        "     Node ID: " + m + "\n"
              + "1) Data Analyst Log In " + 
                               "(not alertable)\n"
              + "2) Data Gathering Subsystem " + 
                 "Intrusion Attempt (alertable)\n"
              + "3) Audit and Alert Subsystem " + 
                             "Crash (alertable)\n"
              +     "   anything else: Leave\n\n";
      
      sc =                 new Scanner(System.in);
      
   }
   
   public CState getUserSelection() 
   {
      CState cs =  new CState();
      cs.mid    = MessageID.MSG;
      
      System.out.print (prompt);
      try {
             sel = sc.nextInt();
      } catch (Exception e) 
      {
         System.exit        (0);
      }
      
      if (sel == 1) 
      {
         cs.setV(0);
         cs.setMessage   ("Data Analyst Log In");
      } 
      else if (sel == 2) 
      {
         cs.setV(1);
         cs.setMessage
        ("Data Gathering Subsystem " +
                             "Intrusion Attempt");
         
      } else if (sel == 3) 
      {
         cs.setV(1);
         cs.setMessage("Audit and Alert " +
                               "Subsystem Crash");         
      } 
      else 
         System.exit(0);

      
      return cs;
      
   }
   
}
