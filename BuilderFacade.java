/** <p>Title: P2P Project</p>
 *
 * <p>Description: This class builds and installs the
 *    systems for this network element.       
 * </p>
 *
 * <p>Copyright: none</p>
 *
 * <p>Company: Lehigh University</p>
 *
 * @author Bill Phillips 
 *
 */
public class BuilderFacade 
{
   private final P2PBuilder p2pb;
   
    private static BuilderFacade bf   = null;
    //
    // The Singleton Design Pattern.
    //
    public static BuilderFacade Instance()
    {
        if ( bf == null ) bf = new BuilderFacade();
        return bf;
    }  
    
   private void construct()
   {
      //
      // Build ACS or CSE216
      //
      boolean isacs = RunTimeVars.Instance().isACS();
      boolean istmg = RunTimeVars.Instance().isTMG();
      boolean iscse = RunTimeVars.Instance().isCSE();
      
      if (isacs)
         p2pb.buildACS();
      else if (iscse)
         p2pb.buildCSE216();
      else if (istmg)
         p2pb.buildTestMsg();
      

   }
   
   public ProcessingFacade getProcessingFacade() 
   {
      ProcessingFacade pf = p2pb.getResult();
      return pf;
   }
   
   private BuilderFacade() 
   {
      p2pb = new P2PBuilder();
      construct();
   }
   
}
