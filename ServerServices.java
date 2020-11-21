
import java.net.*;
import java.io.*;

/**
 * <p>
 * Title: CS656 Project</p>
 *
 * <p>
 * Description: IO services for the server</p>
 *
 * <p>
 * Copyright: none</p>
 *
 * <p>
 * Company: Lehigh University</p>
 *
 * @author Bill Phillips
 *
 */
public class ServerServices 
{

   private ObjectInputStream input;
   private ObjectOutputStream output;
   private Socket socket = new Socket();
   private final ServerTransactionLogger stl;
   private int port;

   public Socket GetSocket() 
   {
      return socket;
   }

   public ServerServices(final Socket S) 
   {
      stl = ServerTransactionLogger.Instance();
      socket = S;
   }

   public void getStreams() {
      try 
      {
         output= new ObjectOutputStream
                       (socket.getOutputStream());
         output.flush();
         input= new ObjectInputStream
                        (socket.getInputStream());
      } 
      catch (Exception e) 
      {
         stl.writeToLogger
          (" Error getting i/o streams (Server)");
      }
      stl.writeToLogger
            ("Server thread connected to Client");

   }

   public Object get() 
   {
      Object O = null;
      
      try 
      {
         O = null;
         O = input.readObject();

         if (O == null) 
         {
            stl.writeToLogger("Error reading " + 
                               "object (Server)");
         }

      } 
      catch (Exception e) 
      {
         stl.writeToLogger("Error reading " + 
                               "object (Server)");
      }
      return O;

   }

   public void send(final Object c) 
   {
      try 
      {
         output.reset();
         output.writeObject(c);
         output.flush();
      } 
      catch (Exception e) 
      {
         stl.writeToLogger
                ("Error sending object (Server)");
      }

   }

   public void Disconnect() {
      try {
         input.close();
         output.close();
         socket.close();
      } 
      catch (Exception e) 
      {
         stl.writeToLogger
                ("Error closing socket (Server)");
      }

      stl.writeToLogger("Server Disconnected"
            + System.getProperty("line.separator")
                                       + "*****");
   }

}
