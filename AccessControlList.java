import java.util.ArrayList;
import java.io.File;  
import java.io.FileNotFoundException; 
import java.util.Scanner; 

public class AccessControlList {

    // the Singleton instance of this class
    private static AccessControlList acList = null;

    ServerTransactionLogger stl = ServerTransactionLogger.Instance();

    // name of the file containing the <user password role> entries
    private static String listFileName = "ACList.txt";

    // data structure that holds the <user password role> entries
    private static ArrayList<ArrayList<String>> userEntries;

    // response codes that the authenticate() method will return
    private final static int ResponseCode_success = 0;
    private final static int ResponseCode_userNotFound = 1;
    private final static int ResponseCode_passwordNotMatched = 2;
    private final static int ResponseCode_roleNotMatched = 3;

    // Singleton design pattern
    public static AccessControlList Instance() {
        if (acList == null) {
            acList = new AccessControlList();
        }
        return acList;
    }

    private AccessControlList() {
        userEntries = readACLFile();
    }

    // read in entries from the file and return an ArrayList of them
    private ArrayList<ArrayList<String>> readACLFile() {
        // list of <user password role> entries read from the file
        ArrayList<ArrayList<String>> fileEntries = new ArrayList<ArrayList<String>>();
        try {
            File listFile = new File(listFileName);
            Scanner scanner = new Scanner(listFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");

                ArrayList<String> entry = new ArrayList<String>();
                entry.add(parts[0]);
                entry.add(parts[1]);
                entry.add(parts[2]);
                
                fileEntries.add(entry);
            }
            scanner.close();
        } catch(Exception e) {
            stl.writeToLogger("An error occurred while reading the ACS list file:");
            stl.writeToLogger(e.getMessage());
        }
        return fileEntries;
    }

    // authentication process
    public int authenticate(String user, String password, String role) {
    
        boolean userFound = false;
        boolean passwordMatched = false;
        boolean roleMatched = false;

        // iterate through the ACS entries
        for(ArrayList<String> entry : userEntries) {
            if(entry.get(0).equals(user)) {
                userFound = true;
                if(entry.get(1).equals(password)) {
                    passwordMatched = true;
                    if(entry.get(2).equals(role)) {
                        roleMatched = true;
                    }
                }
            } 
        }

        // return the corresponding response code
        if(roleMatched) {
            return ResponseCode_success;
        } else if(passwordMatched) {
            return ResponseCode_roleNotMatched;
        } else if(userFound) {
            return ResponseCode_passwordNotMatched;
        } else {
            return ResponseCode_userNotFound;
        }
    }
}