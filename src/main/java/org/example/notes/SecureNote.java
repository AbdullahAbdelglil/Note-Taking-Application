package org.example.notes;

public class SecureNote extends Note {
    private String password;

    public SecureNote(String title, String body, String password) {
        super(title, body);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
