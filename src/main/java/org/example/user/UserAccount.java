package org.example.user;

import org.example.files.FileManager;
import org.example.notes.Note;
import org.example.notes.SecureNote;

import java.util.HashMap;

public class UserAccount {

    private String name;
    private String handle; // unique value added by user
    private String email;
    private String password;

    // for enter new account
    public UserAccount(String name, String email, String password, String handle) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.handle = handle;
    }

    // for login
    public UserAccount(String handle) {
        this.handle = handle;
        this.name = FileManager.getUserInformation(handle).get("name");
        this.email = FileManager.getUserInformation(handle).get("email");
        this.password = FileManager.getUserInformation(handle).get("password");
    }

//----------------------------------------------------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    @Override
    public String toString() {
        return this.getName() + " Information \n" +
                "-----------------------------------\n" +
                "Email Address : " + this.getEmail() +
                "\nAccount Password : " + this.getPassword() +
                "\nAccount Handle : " + this.getHandle() + "" +
                "-----------------------------------\n";
    }

//----------------------------------------------------------------------------------------------------------------------

    private Note note;

    public boolean writeNote(String title, String body) {
        note = new Note(title, body);
        return FileManager.storeGeneralNote(this.getHandle(), note, true);
    }

    private SecureNote secureNote;

    public void writeSecureNote(String title, String body, String password) {
        secureNote = new SecureNote(title, body, password);
        FileManager.storeSecureNote(this.getHandle(), secureNote, true);
    }

//----------------------------------------------------------------------------------------------------------------------

    public void editNote(String title, String newText, boolean append) {
        FileManager.editGeneralNote(this.getHandle(), title, newText, append);
    }

    public boolean editSecureNote(String title, String newText, String password, boolean append) {
        if (isCorrectPassword(title, password)) {
            FileManager.editSecureNote(this.getHandle(), title, newText, append);
            return true;
        }
        return false;
    }

//----------------------------------------------------------------------------------------------------------------------

    private HashMap<String, String> allPasswords;

    protected boolean isCorrectPassword(String title, String pass) {
        allPasswords = FileManager.getAllPasswords("Users/DataBase/NotesData/Secure_Notes/" + this.getHandle() + "/Notes_Passwords.txt");
        return allPasswords.get(title).equals(pass);
    }

    public boolean inValidTitle(String title,String noteType){
        return FileManager.isExistTitle(title,"Users/DataBase/NotesData/"+noteType+"_Notes/" + this.getHandle() + "/Notes_Titles.txt");
    }

//----------------------------------------------------------------------------------------------------------------------

}
