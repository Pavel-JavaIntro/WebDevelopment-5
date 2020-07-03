package by.pavka.module5.editor.impl;

import by.pavka.module5.editor.TextEditor;
import by.pavka.module5.loader.TextLoader;

public class StringTextEditorImpl implements TextEditor {
  public static final String BASIC_DELIMITER = "(\\p{Punct}+\\s+|\"|\\p{Punct}+$)";
  public static final String STUCK_DELIMITER = "[.,;:!?]";
  public static final String CONSONANTS = "^[^AEIOUYaeiouyАЕИОУЫЭЮЯаеиоуыэюя0-9].*";

  private TextLoader textLoader;
  private String text;
  /*
   this field value is to be set 'true' if e.g 'black,white' (without space after comma)
   considered to be two separate words;'false' otherwise
  */
  private boolean processStuckPunct;

  public StringTextEditorImpl(TextLoader textLoader) {
    this.textLoader = textLoader;
  }

  public StringTextEditorImpl(TextLoader textLoader, boolean processStuckPunct) {
    this.textLoader = textLoader;
    this.processStuckPunct = processStuckPunct;
  }

  public TextLoader getTextLoader() {
    return textLoader;
  }

  public void setTextLoader(TextLoader textLoader) {
    this.textLoader = textLoader;
  }

  public boolean isProcessStuckPunct() {
    return processStuckPunct;
  }

  public void setProcessStuckPunct(boolean processStuckPunct) {
    this.processStuckPunct = processStuckPunct;
  }

  @Override
  public String getText() {
    return text;
  }

  @Override
  public void loadText() {
    if (textLoader != null) {
      text = textLoader.loadText();
    }
  }

  @Override
  public void replaceCharacter(int index, char replacement) {
    if (text != null && index >= 0) {
      String[] words = normalizeText(text).split(" ");
      for (String target : words) {
        if (target.length() > index) {
          String replacementString =
              target.substring(0, index) + replacement + target.substring(index + 1);
          text = text.replace(target, replacementString);
        }
      }
    }
  }

  @Override
  public void correctCharacter(char anchor, char wrong, char correct) {
    if (text != null) {
      String[] words = normalizeText(text).split(" ");
      String wrongString = new String(new char[] {anchor, wrong});
      String correctString = new String(new char[] {anchor, correct});
      for (String target : words) {
        int startIndex = 0;
        String replacement = target;
        while (replacement.substring(startIndex).contains(wrongString)) {
          int index = replacement.indexOf(wrongString, startIndex);
          startIndex = index + 2;
          replacement =
              replacement.substring(0, index) + correctString + replacement.substring(startIndex);
        }
        if (startIndex > 0) {
          text = text.replace(target, replacement);
        }
      }
    }
  }

  @Override
  public void replaceWord(int length, String word) {
    if (text != null && word !=null) {
      String[] words = normalizeText(text).split(" ");
      for (String target : words) {
        if (target.length() == length) {
          text = text.replace(target, word);
        }
      }
    }
  }

  @Override
  public void clearTextFromPunctuation() {
    text = normalizeText(text);
  }

  private String normalizeText(String input) {
    String output = null;
    if (input != null) {
      output = input.replaceAll(BASIC_DELIMITER, " ");
      if (isProcessStuckPunct()) {
        output = output.replaceAll(STUCK_DELIMITER, " ");
      }
      output = output.replaceAll("\\s+", " ").trim();
    }
    return output;
  }

  @Override
  public void removeConsonantWords(int length) {
    if (text != null) {
      String[] words = normalizeText(text).split(" ");
      for (String target : words) {
        if (target.length() == length && target.matches(CONSONANTS)) {
          text = text.replace(target, "");
        }
      }
    }
  }
}
