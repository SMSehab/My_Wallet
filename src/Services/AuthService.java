package Services;

import java.io.*;

import Models.User;

public class AuthService {

    private static final String USER_FILE = "src/Database/user.txt";

    public boolean authenticate(User currentUser) {
        String userRecord = readSingleLine(USER_FILE);
        if (userRecord != null) {
            String[] parts = userRecord.split(",");
            return parts.length == 2 && parts[0].equals(currentUser.getUserName()) && parts[1].equals(currentUser.getPin());
        }
        return false;
    }

    public boolean register(User currentUser) throws Exception {
        String userRecord = readSingleLine(USER_FILE);
        if (userRecord != null) {
            throw new Exception("User already exists.");
        }
        writeSingleLine(USER_FILE, currentUser.getUserName() + "," + currentUser.getPin());
        return true;
    }

    public boolean isAnyUserRegistered() {
        return !(readSingleLine(USER_FILE) == null);
    }

    public User getCurrentUser() {
        String userRecord = readSingleLine(USER_FILE);
        if (userRecord != null) {
            String[] parts = userRecord.split(",");
            return new User(parts[0], parts[1]);
        }
        return null;
    }
    
    // Read the single line from the file
    private String readSingleLine(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return reader.readLine(); // Read only the first line
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Write a single line to the file, overwriting any existing content
    private void writeSingleLine(String fileName, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(data + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

