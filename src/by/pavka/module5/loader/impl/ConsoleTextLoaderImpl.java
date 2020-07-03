package by.pavka.module5.loader.impl;

import by.pavka.module5.loader.TextLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleTextLoaderImpl implements TextLoader {
  private static final String EXIT_LINE = "ESC";

  @Override
  public String loadText() {
    StringBuilder stringBuilder = new StringBuilder();
    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
      String line = bufferedReader.readLine();
      while (!line.equalsIgnoreCase(EXIT_LINE)) {
        stringBuilder.append(line);
        stringBuilder.append('\n');
        line = bufferedReader.readLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stringBuilder.toString();
  }
}
