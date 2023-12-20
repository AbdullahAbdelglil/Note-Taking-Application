package org.example.user;

import org.example.files.FileManager;

import java.util.HashMap;
import java.util.Set;

public class UserAccountManager{

    private UserAccountManager(){
    }

    private static Set<String> handles;

//----------------------------------------------------------------------------------------------------------------------

    public static boolean addNewUser(UserAccount account) {
        handles = FileManager.getAllHandles();
        if (!isExistHandle(account.getHandle())) {
            FileManager.creatUserFolder(account.getHandle());
            FileManager.saveUserInformation(account);
            return true;
        }
        return false;
    }

//----------------------------------------------------------------------------------------------------------------------

    public static boolean isExistHandle(String handle){
        handles = FileManager.getAllHandles();
        if(handles ==null)
            return false;
        else
            return handles.contains(handle);
    }

//----------------------------------------------------------------------------------------------------------------------

}
