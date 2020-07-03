package by.pavka.module5.editor.impl;

import by.pavka.module5.editor.TextEditor;
import by.pavka.module5.loader.TextLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTextEditorImpl implements TextEditor {
  public static final String CONSONANTS = "^[^AEIOUYaeiouyАЕИОУЫЭЮЯаеиоуыэюя0-9].{%d}$";
  public static final String WORD =
      "([\\wа-яА-Я]+(-[\\wа-яА-Я]+)*('[\\wа-яА-Я]+)?|\\d+(.\\d+)?)"
          + "(?=\\p{Punct}+\\s+|\"|\\s+|\\p{Punct}+$)";
  public static final String REPLACER = "(?<=^[\\wа-яА-Я\\-']{%d})[\\wа-яА-Я\\-']" +
          "(?=[\\wа-яА-Я\\-']*)";
  public static final String CORRECTOR = "(?<=^[\\wа-яА-Я\\-']{0,30})%s(?=[\\wа-яА-Я\\-']*)";

  private TextLoader textLoader;
  private String text;

  public RegexTextEditorImpl(TextLoader textLoader) {
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
    text = textLoader.loadText();
  }

  @Override
  public void replaceCharacter(int index, char replacement) {
    List<String> words = collectWords(text);
    if (words != null && !words.isEmpty()) {
      String replace = String.valueOf(replacement);
      String regex = String.format(REPLACER, index);
      replaceInText(words, regex, replace);
    }
  }

  @Override
  public void correctCharacter(char anchor, char wrong, char correct) {
    List<String> words = collectWords(text);
    if (words != null && !words.isEmpty()) {
      String wrongString = new String(new char[] {anchor, wrong});
      String correctString = new String(new char[] {anchor, correct});
      String regex = String.format(CORRECTOR, wrongString);
      replaceInText(words, regex, correctString);
    }
  }

  @Override
  public void replaceWord(int length, String word) {
    List<String> words = collectWords(text);
    if (words != null && !words.isEmpty()) {
      String regex = "^.{" + length + "}$";
      replaceInText(words, regex, word);
    }
  }

  private void replaceInText(List<String> words, String regex, String replace) {
    Pattern pattern = Pattern.compile(regex);
    for (int i = 0; i < words.size(); i++) {
      Matcher matcher = pattern.matcher(words.get(i));
      String s = matcher.replaceAll(replace);
      text = text.replace(words.get(i), s);
    }
  }

  @Override
  public void clearTextFromPunctuation() {
    List<String> words = collectWords(text);
    if (words != null && !words.isEmpty()) {
      StringBuilder stringBuilder = new StringBuilder(words.get(0));
      for (int i = 1; i < words.size(); i++) {
        stringBuilder.append(" ");
        stringBuilder.append(words.get(i));
      }
      text = stringBuilder.toString();
    }
  }

  private List<String> collectWords(String input) {
    List<String> words = null;
    if (input != null) {
      words = new ArrayList<>();
      Pattern pattern = Pattern.compile(WORD);
      Matcher matcher = pattern.matcher(input);
      while (matcher.find()) {
        words.add(matcher.group());
      }
    }
    return words;
  }

  @Override
  public void removeConsonantWords(int length) {
    List<String> words = collectWords(text);
    if (words != null && !words.isEmpty()) {
      String consonantWord = String.format(CONSONANTS, length - 1);
      replaceInText(words, consonantWord, "");
    }
  }
}
