package puzzle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CleanData {
  public static void main(String[] args) {
    Path outputPath = Paths.get("C:\\Users\\limin\\Downloads", "Vocab5.txt"); // Declare Path outside try
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(
      CleanData.class.getClassLoader().getResourceAsStream("vocab4.txt")));
         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(outputPath.toFile())))) {
      
      String line;
      while ((line = reader.readLine()) != null) {
          // Check if the line's length is greater than 2
          if (line.trim().length() > 2) {
              writer.write(line);
              writer.newLine();
          }
          // Lines with length less than or equal to 2 are not written, effectively deleting them
      }

      System.out.println("Cleaned data saved to: " + outputPath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
