/** <p>Title: P2P Project</p>
 *
 * <p>Description: This class handles processing
 *    of the message sent to the Access Control 
 *    SubSystem.       
 * </p>
 *
 * <p>Copyright: none</p>
 *
 * <p>Company: Lehigh University</p>
 *
 * @author Bill Phillips 
 *
 */

public class ACSFacade extends 
                             ProcessingFacadeBase 
{
   ServerTransactionLogger stl = 
               ServerTransactionLogger.Instance();
   
   @Override
   synchronized public void processMsg (Object o) 
   {
      CState cs = getMsg(o);
      String m = cs.getMessage();
      stl.writeToLogger
        ("Authorization message received: "  + m);
      String[] parts = m.split(" ");
      int response = AccessControlList.Instance().authenticate(parts[0], parts[1], parts[2]);
      cs.setV(response);
      stl.writeToLogger("Auth response: ",  response);
      sendMsg(cs, o);
   }

}
