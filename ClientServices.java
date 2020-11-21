import java.net.*;
import java.io.*;

/** <p>Title: P2P Project</p>
 *
 * <p>Description: This class defines the
 *    ClientServices object. ClientServices 
 *    object is the interface between
 *    the Application layer and the Transport 
 *    Layer needed by the client.       
 * </p>
 *
 * <p>Copyright: none</p>
 *
 * <p>Company: Lehigh University</p>
 *
 * @author Bill Phillips 
 *
 */
public class ClientServices {

   private Socket connection;

   private ObjectInputStream input;
   private ObjectOutputStream output;

   private final ClientTransactionLogger ctl
             = ClientTransactionLogger.Instance();


   public final Socket GetSocket() {
      return connection;
   }

   public final boolean Connect
                     (final String h, final int p) 
   {
      boolean connected = true;
        //
      // Get the socket, create the sender and receiver.
      //
      try 
      {
         connection= new Socket
                    (InetAddress.getByName(h), p);
      } catch (Exception e) 
      {
         ctl.writeToLogger("Error establishing " + 
                          "connections (Client)");
         connected = false;
      }

      try 
      {
         input  = new ObjectInputStream
                    (connection.getInputStream());
         output = new ObjectOutputStream
                   (connection.getOutputStream());
         output.flush();
      } 
      catch (Exception e) 
      {
         ctl.writeToLogger
            ("Error getting I/O stream (Client)");
         ctl.writeToLogger("Client UNABLE TO " + 
               "CONNECT. Bad host name or port?");
         connected = false;
      }

      if (connected) 
      {
         ctl.writeToLogger
                 ("Client Connected to port:", p);
      }

      return connected;

   }

   public final void send(final Object c) 
   {
      try 
      {
         output.reset();
         output.writeObject(c);
         output.flush();
      } 
      catch (Exception e) 
      {
         ctl.writeToLogger
                ("Error sending object (Client)");        
      }

   }

   public final Object get() 
   {
      Object O = null;

      try 
      {
         O = input.readObject();
      } 
      catch (Exception e) 
      {
         ctl.writeToLogger
                ("Error reading object (Client)");
      }

      return O;

   }

   public final void Disconnect() 
   {
      try 
      {
         input.close();
         output.close();
         connection.close();
      } 
      catch (Exception e) 
      {
         ctl.writeToLogger
                ("Error closing socket (Client)");
      }

      ctl.writeToLogger("Client Disconnected"
            + System.getProperty("line.separator")
            + "*****");

   }

}
