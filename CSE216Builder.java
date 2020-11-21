/** <p>Title: P2P Project</p>
 *
 * <p>Description: This class builds the Test
 *    Message Subsystem
 *    and sets it into the ProcessingFacade
 *    object.       
 * </p>
 *
 * <p>Copyright: none</p>
 *
 * <p>Company: Lehigh University</p>
 *
 * @author Bill Phillips 
 *
 */
public class CSE216Builder extends P2PBuilder
{
   @Override
   public void build()
   {
      CSE216Facade c = new CSE216Facade();
      pfb.setCSE216Facade(c);
   }
}
