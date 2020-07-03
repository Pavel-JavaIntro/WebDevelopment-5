package by.pavka.module5.loader.impl;

import by.pavka.module5.loader.TextLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileTextLoaderImpl implements TextLoader {
  private String fileName;

  public FileTextLoaderImpl(String fileName) {
    this.fileName = fileName;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public String loadText() {
    StringBuilder stringBuilder = new StringBuilder();
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
      while (bufferedReader.ready()) {
        stringBuilder.append(bufferedReader.readLine());
        stringBuilder.append('\n');
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stringBuilder.toString();
  }
}
