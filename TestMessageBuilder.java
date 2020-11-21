/** <p>Title: P2P Project</p>
 *
 * <p>Description: This class builds the CSE216
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
public class TestMessageBuilder extends P2PBuilder
{
   @Override
   public void build()
   {
      TestMsgFacade t = new TestMsgFacade();
      pfb.setTestMessageFacade(t);
   }
}
