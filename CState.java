
import java.io.*;
import java.util.*;
import java.lang.*;

/* <p>Title: P2P Project</p>
 *
 * <p>Description: This is the class defining
 *    the object that is passed from the client
 *    to the server.       
 * </p>
 *
 * <p>Copyright: none</p>
 *
 * <p>Company: Lehigh University</p>
 *
 * @author Bill Phillips 
 *
 */

public class CState implements Serializable 
{

   private String hostname = null;
   private String filename = null;
   private String filetype = null;
   private String message  = null;
   
   private int  v     = 0;
   private long fsize = 0;

   public String[] files;
   public String[] types;
   public boolean[] flags;
   public int[] vals;

   public CState() {
   }

   public final void setV(final int x) 
   {
      v = x;
   }

   public final int getV() 
   {
      return v;
   }

   public final void setFilesize(final long x) 
   {
      fsize = x;
   }

   public final long getFilesize() 
   {
      return fsize;
   }

   public final String getMessage() 
   {
      return message;
   }

   public final void setMessage(final String M) 
   {
      message = M;
   }

   public final String getFilename() 
   {
      return filename;
   }

   public final void setFilename(final String S) 
   {
      filename = S;
   }

   public final String getFiletype() 
   {
      return filetype;
   }

   public final void setFiletype(final String S) 
   {
      filetype = S;
   }

   public final String getHostname() 
   {
      return hostname;
   }

   public final void setHostname(final String S) 
   {
      hostname = S;
   }

   public final void reverse(){
      byte[] byteList = message.getBytes();
      byte [] reverseList = new byte [byteList.length];
      for (int i=0; i<reverseList.length; i++){
         reverseList [i] = byteList[byteList.length -1 -i];
      }
      message = new String (reverseList);
   }

   public MessageID mid;

}
