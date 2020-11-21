/**
 * <p>
 * Title: CS656 Project</p>
 *
 * <p>
 * Description: Singleton used to hold run time variables
 * for the Project.</p>
 *
 * <p>
 * Copyright: none</p>
 *
 * <p>
 * Company: NJIT Fall 2009</p>
 *
 * @author Bill Phillips (214-36-930)
 * @version 1.0
 */
public class RunTimeVars {

    //
   // This singleton object stores run 
   // time values.
   // Since it's a singleton, all objects 
   // have access
   // without having to pass a pointer around.
   //
   private static RunTimeVars rtvars = null;

   private boolean isguiinterface = false;
   private int serverport = 0;
   private ConfigFile.ConfigRecord prevneighbor
                                           = null;
   private ConfigFile.ConfigRecord nextneighbor 
                                           = null;
   private IOInterface cip = null;
   private boolean isACS = false;
   private boolean isTMG = false;
   private boolean isCSE = false;
   private boolean auth  = false;

    //
   // The Singleton Design Pattern.
   //
   public static RunTimeVars Instance() 
   {
      if (rtvars == null) 
      {
         rtvars = new RunTimeVars();
      }
      return rtvars;
   }

   private RunTimeVars() 
   {
   }

   public final void setServerPort(final int s) 
   {
      serverport = s;
   }

   public final int getServerPort() 
   {
      return serverport;
   }

   public final void setIsGUIInterface
                                 (final boolean f) 
   {
      isguiinterface = f;
   }

   public final boolean isGUIInterface() 
   {
      return isguiinterface;
   }

   public final void setACS(final boolean f) 
   {
      isACS = f;
   }

   public final boolean isACS() 
   {
      return isACS;
   }

   public final void setTMG(final boolean f) 
   {
      isTMG = f;
   }

   public final boolean isTMG() 
   {
      return isTMG;
   }

   public final void setCSE(final boolean f) 
   {
      isCSE = f;
   }

   public final boolean isCSE() 
   {
      return isCSE;
   }
    public final void setAUTH(final boolean f) 
   {
      auth = f;
   }

   public final boolean isAUTH() 
   {
      return auth;
   }  
}
