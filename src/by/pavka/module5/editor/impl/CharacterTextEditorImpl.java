package by.pavka.module5.editor.impl;

import by.pavka.module5.editor.TextEditor;
import by.pavka.module5.loader.TextLoader;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class CharacterTextEditorImpl implements TextEditor {
  public static final String VOWELS = "AEIOUYaeiouyАЕИОУЫЭЮЯаеиоуыэюя";

  private TextLoader textLoader;
  private String text;

  public CharacterTextEditorImpl(TextLoader textLoader) {
    this.textLoader = textLoader;
  }

  public TextLoader getTextLoader() {
    return textLoader;
  }

  public void setTextLoader(TextLoader textLoader) {
    this.textLoader = textLoader;
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
    if (text != null && index>= 0) {
      char[] textChars = text.toCharArray();
      int wordIndex = -1;
      for (int i = 0; i < textChars.length; i++) {
        char c = textChars[i];
        if (Character.isLetterOrDigit(c)
            || (i > 0 && Character.isLetterOrDigit(textChars[i - 1]) && isInWordPunct(c))
            || (i > 0 && Character.isDigit(textChars[i - 1]) && c == '.')) {
          wordIndex++;
        } else {
          wordIndex = -1;
        }
        if (wordIndex == index) {
          textChars[i] = replacement;
        }
      }
      text = new String(textChars);
    }
  }

  @Override
  public void correctCharacter(char anchor, char wrong, char correct) {
    if (text != null) {
      char[] textChars = text.toCharArray();
      for (int i = 0; i < textChars.length; i++) {
        char c = textChars[i];
        if (Character.isLetterOrDigit(c)
            || (i > 0 && Character.isLetterOrDigit(textChars[i - 1]) && isInWordPunct(c))
            || (i > 0 && Character.isDigit(textChars[i - 1]) && c == '.')) {
          if (c == wrong && (i > 0 && textChars[i - 1] == anchor)) {
            textChars[i] = correct;
          }
        }
      }
      text = new String(textChars);
    }
  }

  @Override
  public void replaceWord(int length, String word) {
    if (text != null && word != null && length > 0) {
      char[] textChars = text.toCharArray();
      char[] wordChars = word.toCharArray();
      Deque<Character> letters = new ArrayDeque<>();
      int wordLength = 0;
      for (int i = 0; i < textChars.length; i++) {
        char c = textChars[i];
        if (Character.isLetterOrDigit(c)
            || (i > 0 && Character.isLetterOrDigit(textChars[i - 1]) && isInWordPunct(c))
            || (i > 0 && Character.isDigit(textChars[i - 1]) && c == '.')) {
          wordLength++;
          letters.addLast(c);
        } else {
          if (wordLength == length) {
            for (int j = 0; j < length; j++) {
              letters.pollLast();
            }
            for (int j = 0; j < wordChars.length; j++) {
              letters.addLast(wordChars[j]);
            }
          }
          wordLength = 0;
          letters.addLast(c);
        }
      }
      text = letters.stream().map(String::valueOf).collect(Collectors.joining());
    }
  }

  @Override
  public void clearTextFromPunctuation() {
    text = normalizeText(text);
  }

  @Override
  public void removeConsonantWords(int length) {
    if (text != null && length > 0) {
      char[] textChars = text.toCharArray();
      boolean startsWithConsonant = false;
      Deque<Character> letters = new ArrayDeque<>();
      int wordLength = 0;
      for (int i = 0; i < textChars.length; i++) {
        char c = textChars[i];
        if (Character.isLetterOrDigit(c)
            || (i > 0 && Character.isLetterOrDigit(textChars[i - 1]) && isInWordPunct(c))
            || (i > 0 && Character.isDigit(textChars[i - 1]) && c == '.')) {
          wordLength++;
          letters.addLast(c);
          if (wordLength == 1 && !VOWELS.contains(String.valueOf(c))) {
            startsWithConsonant = true;
          }
        } else {
          if (wordLength == length && startsWithConsonant) {
            System.out.println(letters.stream().map(String::valueOf).collect(Collectors.joining()));
            for (int j = 0; j < length; j++) {
              letters.pollLast();
            }
          }
          startsWithConsonant = false;
          wordLength = 0;
          letters.addLast(c);
        }
      }
      text = letters.stream().map(String::valueOf).collect(Collectors.joining());
    }
  }

  private boolean isInWordPunct(char c) {
    return c == '-' || c == '\'';
  }

  private String normalizeText(String input) {
    String output = null;
    if (text != null) {
      char[] textChars = input.toCharArray();
      List<Character> letters = new ArrayList<>();
      char singleSpace = ' ';
      for (int i = 0; i < textChars.length; i++) {
        char c = textChars[i];
        if (Character.isLetterOrDigit(c)
            || (i > 0 && Character.isLetterOrDigit(textChars[i - 1]) && isInWordPunct(c))
            || (i > 0 && Character.isDigit(textChars[i - 1]) && c == '.')) {
          letters.add(c);
        } else if (i > 0 && Character.isLetterOrDigit(textChars[i - 1])) {
          letters.add(singleSpace);
        }
      }
      output = letters.stream().map(String::valueOf).collect(Collectors.joining());
    }
    return output;
  }
}
