package test.pavka.module5.loader.impl;

import by.pavka.module5.loader.TextLoader;
import by.pavka.module5.loader.impl.ConsoleTextLoaderImpl;
import by.pavka.module5.loader.impl.FileTextLoaderImpl;
import org.testng.annotations.Test;

import java.io.*;

import static org.testng.Assert.assertEquals;

public class TextLoaderTest {
  private String text = "Однажды в \"студеную\" зимнюю: пору\nI went from a forest, so-called\n\n\n"
          + "\"DeepForest!!!\" - страшное место... Кто-нибудь хотел; бы там побывать?";

  @Test
  public void loadTextFromFileTest() {
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Test.txt"))) {
      bufferedWriter.write(text);
    } catch (IOException e) {
      e.printStackTrace();
    }
    TextLoader textLoader = new FileTextLoaderImpl("Test.txt");
    String actual = textLoader.loadText().trim();
    assertEquals(actual, text);
  }

  @Test
  public void loadTextFromConsoleTest() {
    String consoleText = text + "\nESC";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(consoleText.getBytes());
    System.setIn(inputStream);
    TextLoader textLoader = new ConsoleTextLoaderImpl();
    String actual = textLoader.loadText().trim();
    System.setIn(System.in);
    assertEquals(actual, text);
  }


}
