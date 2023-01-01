package org.example.user;

public class LoginManager {

    private LoginManager() {
    }

    private static UserAccount newAccount;

    public static UserAccount enter(String handle, String password) {

        if (UserAccountManager.isExistHandle(handle)) {
            newAccount = new UserAccount(handle);
            if (password.equals(newAccount.getPassword())) return newAccount;

            else return null;
        }
        return null;
    }

//----------------------------------------------------------------------------------------------------------------------

    public static UserAccount register(String name, String email, String password, String handle) {
        newAccount = new UserAccount(name, email, password, handle);
        if (UserAccountManager.addNewUser(newAccount)) return newAccount;
        else return null;
    }

    public static UserAccount register(UserAccount account) {
        newAccount = account;
        if (UserAccountManager.addNewUser(newAccount)) return newAccount;
        else return null;
    }
}

//----------------------------------------------------------------------------------------------------------------------