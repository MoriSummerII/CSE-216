import javax.swing.JFrame;
import java.net.InetAddress;
import java.io.File;
/**
 * <p>
 * Title: P2P Project</p>
 *
 * <p>
 * Description: TransactionLogger outputs messages to the
 * Transaction Output Panel.
 * </p>
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

public class ClientTransactionLogger {

   private String localhost;
   private String filename;

   public final static int XLOC = 240;
   public final static int YLOC = 350;
   public final static int WIDTH = 600;
   public final static int HEIGHT = 200;

   private boolean isguiinterface
        = RunTimeVars.Instance().isGUIInterface();

   private static TransactionOutputPanel  topanel;
   private static ClientTransactionLogger 
                                  ctolgger = null;

    //
   // The Singleton Design Pattern.
   //
   public static ClientTransactionLogger Instance() 
   {
      if (ctolgger == null) 
      {
         ctolgger = new ClientTransactionLogger();
      }
      return ctolgger;
   }

   public void writeToLogger(final String S) 
   {
      if (isguiinterface) {
         topanel.setText(S);
         topanel.repaint();
      }
      SimpleLog.write(filename, S);
   }

   public void writeToLogger
                     (final String S, final int i) 
   {
      writeToLogger(String.format("%s %d", S, i));
   }

   private ClientTransactionLogger() {
      try {
         localhost = InetAddress.
                     getLocalHost().getHostName();
      } 
      catch (Exception e) 
      {
      }

      if (isguiinterface) {
         topanel = new TransactionOutputPanel(
                "Client Transactions for host: " + 
                               localhost, 20, 80);
         topanel.setDefaultCloseOperation
                     (JFrame.DO_NOTHING_ON_CLOSE);
         topanel.setSize(WIDTH, HEIGHT);
         topanel.setLocation(XLOC, YLOC);
         topanel.setResizable(true);
         topanel.setVisible(true);
      }

        //
      // Create the log file name.
      // If a log file exists, delete it.
      //
      filename = "ClientOutputLog"
              + "-From-"
              + localhost
              + ".txt";
      final File f = new File(filename);
      if (f.exists()) 
      {
         f.delete();
      }

   }

}
