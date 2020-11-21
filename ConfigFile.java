import java.util.*;
import java.io.*;
import java.net.InetAddress;
/* <p>Title: P2P Project</p>
 *
 * <p>Description: This class retrieves config data
 .                 stores it and makes it available.       
 * </p>
 *
 * <p>Copyright: none</p>
 *
 * <p>Company: Lehigh University</p>
 *
 * @author Bill Phillips 
 *
 */
public class ConfigFile 
{

   private final String CONFIGFILE = "ID.config";
   private static ConfigFile cf = null;
   private String sysid = null;
   private ConfigRecord myconfigrecord = null;
   private String hostname;
   private String hostaddr;
    //
   // The Singleton Design Pattern.
   //

   public static ConfigFile Instance() 
   {
      if (cf == null) 
      {
         cf = new ConfigFile();
      }
      return cf;
   }

   public class ConfigRecord implements 
                                      Serializable 
   {
      // client IP
      private final String IPAddress;
      // key word
      private final String sysID;
      // host IP
      private final String acsIP;
      private final int acsPort;

      public ConfigRecord(String IP, String sID, 
                             String acsip, int ap) 
      {
         IPAddress = IP;
         sysID = sID;
         acsIP = acsip;
         acsPort = ap;
      }

      public final String getIP() 
      {
         return IPAddress;
      }

      public final String getSysId() 
      {
         return sysID;
      }

      public final String getACSIP() 
      {
         return acsIP;
      }

      public final int getACSPort() 
      {
         return acsPort;
      }
   }

   private ConfigFile() {
        //
      // Read the config file.
      //
      if (this.readConfigFile(CONFIGFILE) == 0) 
      {
         System.out.println(
                 "\nProblem reading config file: "
                 + CONFIGFILE
                 + "*****");
         System.exit(-1);
      }

        //
      // Get this host's ID.
      //
      try 
      {
         hostaddr = InetAddress.getLocalHost().
                                 getHostAddress();
         hostname = InetAddress.getLocalHost().
                                    getHostName();

      } catch (Exception ex) {
      }

   }

   private synchronized int readConfigFile
        (final String fileName) 
   {
      Scanner input = null;
      try 
      {
         input = new Scanner(new File(fileName));

         myconfigrecord = new ConfigRecord(
                 input.next(), input.next(),
                 input.next(), input.nextInt());
      } catch (Exception e) {
         System.out.println
            ("\nProblem reading config file: "
                 + CONFIGFILE
                 + "*****");
         System.exit(-1);

      }

      return 1;
   }

   public final String getFirstIP() 
   {
      return myconfigrecord.getIP();
   }

   public final String getSysID() 
   {
      return myconfigrecord.getSysId();
   }

   public final String getACSIP() 
   {
      return myconfigrecord.getACSIP();
   }

   public final int getACSPORT() 
   {
      return myconfigrecord.getACSPort();
   }

   synchronized final public ConfigRecord 
                                 getConfigRecord() 
        {
      return myconfigrecord;
   }

   public final String getMyHostName() 
   {
      return hostname;
   }

   public final String getMyHostAddr() 
   {
      return hostaddr;
   }

   synchronized final public ConfigRecord 
                               getMyConfigRecord()  
   {
      return myconfigrecord;
   }

   synchronized final public String 
                               getIP(final int ID) 
   {
      return myconfigrecord.getIP();
   }

}
