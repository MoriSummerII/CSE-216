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

public class TestMsgClient implements Runnable 
{

   private final ClientTransactionLogger ctl
           =  ClientTransactionLogger.Instance();
   private final ConfigFile cf
           =               ConfigFile.Instance();
   private final RunTimeVars rtv
           =               RunTimeVars.Instance();
   private final ClientServices        cservices;

   private TestMsgClientCommandLineInterface  cli;
   private String                       hostName;

   private final int mainport =  
                             rtv.getServerPort();
   private final String MAINPORT = 
                        String.valueOf(mainport);
   private final boolean auth =            true;

   public TestMsgClient(String hName) 
   {
      hostName = hName;

      //
      // Create a client services object.
      //
      cservices = new ClientServices();

      ctl.writeToLogger("Client Created");
   }

   public final void run() {

      ctl.writeToLogger("Client Started");
      if (rtv.isAUTH())
      {
         int ACSPort    = cf.getACSPORT();
         String acshost =   cf.getACSIP();

         if (cservices.Connect(acshost, ACSPort)) 
         {
            CState cx = new CState();
            cx.setMessage
                       ("username password role");
            cservices.send(cx);
            cx = (CState)cservices.get();
            int v = cx.getV();
            ctl.writeToLogger ("Auth code = ", v);
            cservices.Disconnect();
         }
      }

      cli = new TestMsgClientCommandLineInterface
                            (hostName, MAINPORT);
      hostName =  
              ConfigFile.Instance().getFirstIP();
      
      do 
      {

         //
         // Get the command from the
         // user input panel.
         //
         CState cs =       cli.getUserSelection();

         //
         // Connect to the server.
         //
         boolean bf = cservices.Connect
                             (hostName, mainport);
         if (bf) 
         {

            //
            // Send request.
            //
            cservices.send(cs);

            cservices.Disconnect();
         }

      } while (true);
   }

}
