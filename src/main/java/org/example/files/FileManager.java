package org.example.files;

import org.example.notes.Note;
import org.example.notes.SecureNote;
import org.example.user.UserAccount;

import java.io.*;
import java.util.*;

public class FileManager {

    private FileManager() {
    }

    //Readers,Writers, and streams

    private static File file;
    private static FileOutputStream fos;
    private static PrintWriter writer;
    private static BufferedReader reader;

//----------------------------------------------------------------------------------------------------------------------

    private static Set<String> usersHandles;

    public static void creatUserFolder(String handle) {

        file = new File("Users/" + handle);
        file.mkdir();

        file = new File("Users/" + handle + "/Notes");
        file.mkdir();

        file = new File("Users/" + handle + "/Notes/General_Notes");
        file.mkdir();

        file = new File("Users/" + handle + "/Notes/Secure_Notes");
        file.mkdir();

        file = new File("Users/DataBase/NotesData/General_Notes/" + handle);
        file.mkdir();

        file = new File("Users/DataBase/NotesData/Secure_Notes/" + handle);
        file.mkdir();

        try {

            file = new File("Users/DataBase/NotesData/General_Notes/" + handle + "/Notes_Titles.txt");
            file.createNewFile();

            file = new File("Users/DataBase/NotesData/Secure_Notes/" + handle + "/Notes_Titles.txt");
            file.createNewFile();

            file = new File("Users/DataBase/NotesData/Secure_Notes/" + handle + "/Notes_Passwords.txt");
            file.createNewFile();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//----------------------------------------------------------------------------------------------------------------------

    public static void saveUserInformation(UserAccount account) {
        try {

            fos = new FileOutputStream("Users/" + account.getHandle() + "/information.txt");
            writer = new PrintWriter(fos);
            writer.println("name:" + account.getName());
            writer.println("email:" + account.getEmail());
            writer.println("password:" + account.getPassword());
            writer.println("handle:" +account.getHandle());

            writer.flush();
            writer.close();
            storeHandle(account.getHandle());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static HashMap<String, String> userInformation;
    private static String key;
    private static String value;
    private static String line;

    public static HashMap<String, String> getUserInformation(String handle) {
        userInformation = new HashMap<>();
        try {
            reader = new BufferedReader(new FileReader("Users/" + handle + "/information.txt"));
            line = reader.readLine();
            while (line != null) {
                key = line.substring(0, line.indexOf(':'));
                value = line.substring(line.indexOf(':') + 1);
                userInformation.put(key, value);
                line = reader.readLine();
            }
            reader.close();
            return userInformation;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//----------------------------------------------------------------------------------------------------------------------

    public static void storeHandle(String key) {
        getAllHandles();
        if (!usersHandles.contains(key)) {
            try {
                fos = new FileOutputStream("Users/DataBase/Users_Handles/handles.txt", true);
                writer = new PrintWriter(fos);
                writer.println(key);
                writer.flush();
                writer.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Set<String> getAllHandles() {

        usersHandles = new HashSet<>();
        try {
            reader = new BufferedReader(new FileReader("Users/DataBase/Users_Handles/handles.txt"));
            String line = reader.readLine();

            while (line != null) {
                usersHandles.add(line);
                line = reader.readLine();
            }
            reader.close();
            return usersHandles;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//----------------------------------------------------------------------------------------------------------------------

    public static boolean storeGeneralNote(String handle, Note note, boolean append) {
        if (isExistTitle(note.getTitle(), "Users/DataBase/NotesData/General_Notes/" + handle + "/Notes_Titles.txt"))
            return false;
        try {
            fos = new FileOutputStream("Users/" + handle + "/Notes/General_Notes/" + note.getTitle() + ".txt", append);
            writer = new PrintWriter(fos);
            writer.println(note.getBody());
            writer.flush();
            writer.close();
            storeTitle("Users/DataBase/NotesData/General_Notes/" + handle + "/Notes_Titles.txt", note.getTitle());
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void editGeneralNote(String handle, String title, String newTxt, boolean append) {
        try {
            fos = new FileOutputStream("Users/" + handle + "/Notes/General_Notes/" + title + ".txt", append);
            writer = new PrintWriter(fos);
            writer.println(newTxt);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readNote(String handle, String title, String noteType){
        if (isExistTitle(title, "Users/DataBase/NotesData/"+noteType+"_Notes/" + handle + "/Notes_Titles.txt")){
            try{
                reader=new BufferedReader(new FileReader("Users/"+ handle + "/Notes/"+noteType+"_Notes/" +title+ ".txt"));

                line=reader.readLine();

                while (line!=null){
                    System.out.println("\u001B[32m"+line+"\u001B[0m");
                    line=reader.readLine();
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
//----------------------------------------------------------------------------------------------------------------------

    public static boolean storeSecureNote(String handle, SecureNote note, boolean append) {
        if (isExistTitle(note.getTitle(), "Users/DataBase/NotesData/Secure_Notes/" + handle + "/Notes_Titles.txt"))
            return false;

        try {
            fos = new FileOutputStream("Users/" + handle + "/Notes/Secure_Notes/" + note.getTitle() + ".txt", append);
            writer = new PrintWriter(fos);
            writer.println(note.getBody());
            writer.flush();
            writer.close();
            storePassword(handle, note.getTitle(), note.getPassword());
            storeTitle("Users/DataBase/NotesData/Secure_Notes/" + handle + "/Notes_Titles.txt", note.getTitle());

            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void editSecureNote(String handle, String title, String newTxt, boolean append) {
        try {
            fos = new FileOutputStream("Users/" + handle + "/Notes/Secure_Notes/" + title + ".txt", append);
            writer = new PrintWriter(fos);
            writer.println(newTxt);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
//----------------------------------------------------------------------------------------------------------------------

    private static void storeTitle(String path, String title) {
        try {
            fos = new FileOutputStream(path, true);
            writer = new PrintWriter(fos);
            writer.println(title);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> allTitles;

    public static List<String> getAllTitles(String path) {
        allTitles = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null) {
                allTitles.add(line);
                line = reader.readLine();
            }
            reader.close();
            return allTitles;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isExistTitle(String title, String path) {
        allTitles = getAllTitles(path);
        for (var t : allTitles) {
            if (title.equals(t)) return true;
        }
        return false;
    }

//----------------------------------------------------------------------------------------------------------------------

    public static void storePassword(String handle, String title, String password) {
        try {
            fos = new FileOutputStream("Users/DataBase/NotesData/Secure_Notes/" + handle + "/Notes_Passwords.txt", true);
            writer = new PrintWriter(fos);
            writer.println(title + "->" + password);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static HashMap<String, String> allPasswords;

    public static HashMap<String, String> getAllPasswords(String path) {
        allPasswords = new HashMap<>();
        try {
            reader = new BufferedReader(new FileReader(path));
            line = reader.readLine();
            while (line != null) {
                key = line.substring(0, line.indexOf('-'));
                value = line.substring(line.indexOf('>') + 1);
                allPasswords.put(key, value);
                line = reader.readLine();
            }
            reader.close();
            return allPasswords;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

//----------------------------------------------------------------------------------------------------------------------

}