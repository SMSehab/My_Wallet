package Services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {

    public List<String> readAllLines(String fileName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public void writeLine(String fileName, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(data + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeLineByValue(String fileName, String targetLine) {
        List<String> lines = readAllLines(fileName);
        lines.removeIf(line -> line.trim().equals(targetLine.trim()));
        overwriteFile(fileName, lines);
    }

    private void overwriteFile(String fileName, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


