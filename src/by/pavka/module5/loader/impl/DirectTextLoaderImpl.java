package by.pavka.module5.loader.impl;

import by.pavka.module5.loader.TextLoader;

public class DirectTextLoaderImpl implements TextLoader {
  private String text;

  public DirectTextLoaderImpl() {}

  public DirectTextLoaderImpl(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public String loadText() {
    return getText();
  }
}
