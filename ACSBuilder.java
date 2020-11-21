/** <p>Title: P2P Project</p>
 *
 * <p>Description: This class builds the Access Control
 *    System and sets it into the ProcessingFacade object.       
 * </p>
 *
 * <p>Copyright: none</p>
 *
 * <p>Company: Lehigh University</p>
 *
 * @author Bill Phillips 
 *
 */
public class ACSBuilder extends P2PBuilder 
{
   @Override
   protected void build() 
   {
      ACSFacade a = new ACSFacade();
      pfb.setACSFacade(a);
   }
}
