package org.example.user;

import org.example.files.FileManager;

import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    private UserInterface() {
    }

    private static Scanner in = new Scanner(System.in);

    private static String name;
    private static String password;
    private static String handle;
    private static String email;
    private static UserAccount user;

    private static String choice;

//----------------------------------------------------------------------------------------------------------------------

    public static void StartProgram() {
        System.out.println("\n\u001B[32mWelcome In My Note-Taking Application 📝\u001B[0m\n");

        makeABreak();

        user = login();

        if (user == null) return;

        System.out.println("\n🤍 Hello " + user.getName() + " 🤍\n");

        choice = showNoteMenu();
        executeNoteMenu(choice);
    }

//----------------------------------------------------------------------------------------------------------------------

    public static UserAccount login() {

        System.out.println("\n⬜ Enter");
        System.out.println("  ◻ 1 -> To Login");
        System.out.println("  ◻ 2 -> To Add New Account");
        System.out.println("  ◻ 0 -> To Exit");
        System.out.print("\nPlease Select One Operation : ");
        choice = in.next();
        System.out.println();
        switch (choice) {
            case "1":
                user = enter();
                break;

            case "2":
                user = creat();
                break;

            case "0":
                sayGoodBye();
                return null;

            default:
                sayInvalidChoice();
                login();
        }
        return user;
    }

//----------------------------------------------------------------------------------------------------------------------

    public static UserAccount enter() { // enter to exist account
        in.nextLine();

        System.out.println();

        handle = getHandle();
        password = getPassword();

        user = LoginManager.enter(handle, password);

        if (user == null) {
            System.out.println("\u001B[31m\n❌ Incorrect Handle OR Password\u001B[0m\n");
            makeABreak();

            choice = showReEnterMenu();

            executeReEnterMenu(choice);

        } else {
            user.setName(FileManager.getUserInformation(handle).get("name"));
            user.setEmail(FileManager.getUserInformation(handle).get("email"));
            System.out.println("\n\u001B[32m✔ Entered Successfully\u001B[0m");
            makeABreak();
        }

        return user;
    }

    private static String showReEnterMenu() {
        System.out.println("⬜ Enter : ");
        System.out.println("  ◻ 1 -> To ReEnter");
        System.out.println("  ◻ 2 -> To Creat New Account");
        System.out.println("  ◻ 0 -> To Exit");
        System.out.print("\nPlease Select One Operation : ");

        choice = in.next();
        return choice;
    }

    private static void executeReEnterMenu(String choice) {
        switch (choice) {
            case "1":
                enter();
                break;

            case "2":
                creat();
                break;

            case "0":
                sayGoodBye();
                return;

            default:
                sayInvalidChoice();
                choice = showReEnterMenu();
                executeReEnterMenu(choice);
        }
    }

//----------------------------------------------------------------------------------------------------------------------

    public static UserAccount creat() { // creat new user
        in.nextLine();
        name = getName();
        email = getEmail();
        password = getPassword();
        handle = getHandle();

        user = LoginManager.register(name, email, password, handle);

        while (user == null) {
            System.out.println("\n\u001B[31m❌ Sorry ! This Handle Already Taken , Please Select Another One\u001B[0m\n");
            makeABreak();
            user = reEnterHandle();
        }

        System.out.println("\n\u001B[32m✔ Created Successfully\u001B[0m");
        makeABreak();
        return user;
    }

//----------------------------------------------------------------------------------------------------------------------

    private static UserAccount reEnterHandle() {
        System.out.println("⬜ Enter : ");
        System.out.println("  ◻ 1 -> To Write Another Handle");
        System.out.println("  ◻ 0 -> To Exit");

        System.out.print("\nPlease Select One Operation : ");
        choice = in.next();
        System.out.println();

        switch (choice) {
            case "1":
                in.nextLine();
                handle = getHandle();
                user = LoginManager.register(name, email, password, handle);
                return user;

            case "0":
                sayGoodBye();
                break;

            default:
                sayInvalidChoice();
                reEnterHandle();
                break;
        }
        return user;
    }

//-------------------------------Private Methods To Make The Code More Efficient----------------------------------------

    private static String title, txt;

    private static String getName() {
        System.out.print("\n📍 Please Enter Your Name : ");

        name = in.nextLine();
        name = name.trim();

        while (name.isEmpty()) {
            sayEmptyInput();
            name = getName();
        }

        return checkInputLength(name);
    }

    private static String getEmail() {
        System.out.print("📍 Please Enter Your Email : ");

        email = in.nextLine();
        email = email.trim();

        while (email.isEmpty()) {
            sayEmptyInput();
            email = getEmail();
        }

        return email;
    }

    private static String getPassword() {
        System.out.print("📍 Please Enter A Password : ");

        password = in.nextLine();
        password = password.trim();

        while (password.isEmpty()) {
            sayEmptyInput();
            password = getPassword();
        }

        return checkInputLength(password);
    }


    public static String getHandle() {
        System.out.print("📍 Please Enter A Handle : ");

        handle = in.nextLine();
        handle = handle.trim();

        while (handle.isEmpty()) {
            sayEmptyInput();
            handle = getHandle();
        }

        while (!correctInput(handle)) {
            System.out.println("\n\u001B[31m❌ Sorry , Symbols ( : , / , \\ , { , } , - , + , < , > , * , = , % , . ) Are Not Allowed , Please Try Again\u001B[0m\n");
            makeABreak();
            handle = getHandle();
        }

        return checkInputLength(handle);
    }


    private static String getNoteTitle() {
        System.out.print("\n📍 Please Enter The Title : ");

        title = in.nextLine();
        title = title.trim();

        while (title.isEmpty()) {
            sayEmptyInput();
            title = getNoteTitle();
        }

        while (!correctInput(title)) {
            System.out.println("\n\u001B[31m❌ Sorry , Symbols ( : , / , \\ , { , } , - , + , < , > , * , = , % , .) Are Not Allowed , Please Try Again\u001B[0m\n");
            makeABreak();
            title = getNoteTitle();
        }

        return checkInputLength(title);
    }

    private static boolean correctInput(String input) {
        return (!input.contains(":") && !input.contains("/") && !input.contains("\\") &&
                !input.contains("}") && !input.contains("{") && !input.contains("-") &&
                !input.contains("+") && !input.contains("<") && !input.contains(">") &&
                !input.contains("*") && !input.contains("%") && !input.contains("=") &&
                !input.contains("."));
    }

    private static String checkInputLength(String input) {
        if (input.length() > 30) {
            input = input.substring(0, 30);
        }
        return input;
    }

    private static void sayEmptyInput() {
        System.out.println("\n\u001B[31m❌ Empty Inputs Not Allowed , Please Write Any Thing\u001B[0m\n");
        makeABreak();
    }
//----------------------------------------------------------------------------------------------------------------------

    private static String showNoteMenu() {

        System.out.println("\n⬜ Enter ");
        System.out.println("  ◻ 1 -> To Write New Note");
        System.out.println("  ◻ 2 -> To Write New Secure Note");
        System.out.println("  ◻ 3 -> To Edit Note");
        System.out.println("  ◻ 4 -> To Edit Secure Note");
        System.out.println("  ◻ 5 -> To Read Note");
        System.out.println("  ◻ 6 -> To Read Secure Note");
        System.out.println("  ◻ 0 -> To Close The Program\n");

        System.out.print("\nPlease Select One Operation : ");
        choice = in.next();
        return choice;
    }

//----------------------------------------------------------------------------------------------------------------------

    private static void executeNoteMenu(String choice) {

        switch (choice) {
            case "1":
                getNoteFromConsole("General");
                writeNote(title, txt);
                System.out.println("\n\u001B[32m✔ Successfully Stored\u001B[0m");
                makeABreak();
                continueApp();
                break;

            case "2":
                getNoteFromConsole("Secure");
                writeSecureNote(title, password, txt);
                System.out.println("\n\u001B[32m✔ Successfully Stored\u001B[0m");
                makeABreak();
                continueApp();
                break;

            case "3":
                in.nextLine();

                if (FileManager.getAllTitles("Users/DataBase/NotesData/General_Notes/" + user.getHandle() + "/Notes_Titles.txt").isEmpty()) {
                    sayWriteNote("Note");
                } else executeEditOperation("General");

                break;

            case "4":
                in.nextLine();

                if (FileManager.getAllTitles("Users/DataBase/NotesData/Secure_Notes/" + user.getHandle() + "/Notes_Titles.txt").isEmpty()) {
                    sayWriteNote("Secure Note");
                } else executeEditOperation("Secure");
                break;

            case "5":
                in.nextLine();
                if (FileManager.getAllTitles("Users/DataBase/NotesData/General_Notes/" + user.getHandle() + "/Notes_Titles.txt").isEmpty()) {
                    sayWriteNote("General Note");
                } else {
                    showNote(user.getHandle(), "General");
                    makeABreak();
                    continueApp();
                }
                break;

            case "6":
                in.nextLine();
                if (FileManager.getAllTitles("Users/DataBase/NotesData/Secure_Notes/" + user.getHandle() + "/Notes_Titles.txt").isEmpty()) {
                    sayWriteNote("Secure Note");
                } else {
                    showNote(user.getHandle(), "Secure");
                    makeABreak();
                    continueApp();
                }
                break;

            case "0":
                sayGoodBye();
                return;

            default:
                sayInvalidChoice();
                choice = showNoteMenu();
                executeNoteMenu(choice);
        }
    }

    private static void sayWriteNote(String type) {
        makeABreak();
        System.out.println("\n\u001B[31m❌ You Must Write Any " + type + " First\u001B[0m\n");
        makeABreak();
        choice = showNoteMenu();
        executeNoteMenu(choice);

    }
//----------------------------------------------------------------------------------------------------------------------

    private static void writeNote(String title, String body) {
        user.writeNote(title, body);
    }

    private static void writeSecureNote(String title, String password, String body) {
        user.writeSecureNote(title, body, password);
    }

//----------------------------------------------------------------------------------------------------------------------

    private static void editNote(String title, String newTxt, boolean append) {
        user.editNote(title, newTxt, append);
    }

    private static void editSecureNote(String title, String newTxt, String password, boolean append) {
        user.editSecureNote(title, newTxt, password, append);
    }

//----------------------------------------------------------------------------------------------------------------------

    private static void getNoteFromConsole(String noteType) {
        in.nextLine();
        title = getNoteTitle();

        while (user.inValidTitle(title, noteType)) {
            System.out.println("\u001B[31m\nOOPS ! There Is Note With The Same Title , Please Select Another One\u001B[0m\n");
            makeABreak();
            title = getNoteTitle();
        }

        if (noteType.equals("Secure")) {
            password = getPassword();
        }

        System.out.println("\nEnjoy Writing 🥰\n");
        makeABreak();
        System.out.print("> ");
        txt = in.nextLine();
    }

//----------------------------------------------------------------------------------------------------------------------

    private static void showEditMenu(String noteType) {
        System.out.println("\n⬜ Enter");
        System.out.println("  ◻ 1 -> To Override Old Data");
        System.out.println("  ◻ 2 -> To Append New Text");
        System.out.println("  ◻ 0 -> To Go Back\n");
        System.out.print("\nPlease Select One Operation : ");

        choice = in.next();

        switch (choice) {
            case "1":
                if (noteType.equals("General"))
                    editNote(title, txt, false);
                else if (noteType.equals("Secure"))
                    editSecureNote(title, txt, password, false);

                System.out.println("\n\u001B[32m✔ Successfully Edited\u001B[0m\n");
                makeABreak();
                choice = showNoteMenu();
                executeNoteMenu(choice);
                break;

            case "2":
                if (noteType.equals("General"))
                    editNote(title, txt, true);
                else if (noteType.equals("Secure"))
                    editSecureNote(title, txt, password, true);

                System.out.println("\n\u001B[32m✔ Successfully Edited\u001B[0m\n");
                makeABreak();
                choice = showNoteMenu();
                executeNoteMenu(choice);
                break;

            case "0":
                makeABreak();
                choice = showNoteMenu();
                executeNoteMenu(choice);
                break;

            default:
                sayInvalidChoice();
                showEditMenu(noteType);
        }
    }

//----------------------------------------------------------------------------------------------------------------------

    private static void executeEditOperation(String noteType) {

        showNote(user.getHandle(), noteType);

        System.out.println("\nEnjoy Writing 🥰\n");
        makeABreak();

        System.out.print("> ");
        txt = in.nextLine();
        showEditMenu(noteType);
    }

    private static void showNote(String handle, String noteType) {
        title = getNoteTitle();
        while (!user.inValidTitle(title, noteType)) {
            System.out.println("\n\u001B[31m❌ There Isn't Any Note With This Name , Please Check Your Inputs And Try Again\u001B[0m\n");
            makeABreak();
            title = getNoteTitle();
        }

        if (noteType.equals("Secure")) {
            password = getPassword();
            while (!user.isCorrectPassword(title, password)) {
                System.out.println("\n\u001B[31m❌ Wrong Password , Please Check Your Inputs And Try Again\u001B[0m\n");
                makeABreak();
                password = getPassword();
            }
        }
        makeABreak();
        System.out.println("\n📝 Note Content : ");
        System.out.println("================\n");
        FileManager.readNote(handle, title, noteType);
        System.out.println();
    }
//----------------------------------------------------------------------------------------------------------------------

    private static void continueApp() {
        System.out.println("\n⬜ Enter\n  ◻  1 -> To Continue \n  ◻  0 -> To Exit");
        System.out.print("\nPlease Select One Operation : ");
        choice = in.next();

        switch (choice) {
            case "1":
                choice = showNoteMenu();
                executeNoteMenu(choice);
                break;

            case "0":
                sayGoodBye();
                return;

            default:
                sayInvalidChoice();
                continueApp();
        }

    }

//----------------------------------------------------------------------------------------------------------------------

    private static void makeABreak() {
        System.out.println("--------------------------------------------------------------------------------");
    }

    private static void sayInvalidChoice() {
        System.out.println("\n\u001B[31m❌ Invalid Choice , Please Read The Menu Carefully Then Try Again\u001B[0m\n");
        makeABreak();
    }

    private static void sayGoodBye() {
        System.out.println("\n\u001B[32mGood Bye 🥰\u001B[0m");
        makeABreak();
    }

//----------------------------------------------------------------------------------------------------------------------

}
