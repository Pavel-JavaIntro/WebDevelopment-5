package test.pavka.module5.editor.impl;

import by.pavka.module5.editor.TextEditor;
import by.pavka.module5.editor.impl.RegexTextEditorImpl;
import by.pavka.module5.loader.TextLoader;
import by.pavka.module5.loader.impl.DirectTextLoaderImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class RegexTextEditorTest {

  private String text =
      "Однажды в \"студеную\" зимнюю: пору\nI went from a forest, "
          + "so-called\n\n\n"
          + "\"DeepForest!!!\" - страшное место... Кто-нибудь хотел; бы там побывать?";
  private String text2 = "At 11.25 o'clock late, \"late\" in the evening...       an end-of-time " +
          "event      happened!";
  private TextLoader textLoader = new DirectTextLoaderImpl(text);
  private TextEditor textEditor = new RegexTextEditorImpl(textLoader);

  @BeforeMethod
  public void onLoadText() {
    textEditor.loadText();
  }

  @Test
  public void replaceCharacterTest() {
    int index = 5;
    char replacement = 'Ы';
    textEditor.replaceCharacter(index, replacement);
    String actual = textEditor.getText();
    String expected =
        "ОднажЫы в \"студеЫую\" зимнюЫ: пору\nI went from a foresЫ, so-caЫled\n\n\n"
            + "\"DeepFЫrest!!!\" - страшЫое место... Кто-нЫбудь хотел; бы там побывЫть?";
    assertEquals(actual, expected);
  }

  @Test
  public void correctCharacterTest() {
    char anchor = 'r';
    char wrong = 'e';
    char correct = 'Ж';
    textEditor.correctCharacter(anchor, wrong, correct);
    String actual = textEditor.getText();
    String expected =
        "Однажды в \"студеную\" зимнюю: пору\nI went from a forЖst, so-called\n\n\n"
            + "\"DeepForЖst!!!\" - страшное место... Кто-нибудь хотел; бы там побывать?";
    assertEquals(actual, expected);
  }

  @Test
  public void replaceWordTest() {
    int length = 3;
    String word = "HAHAHA";
    textEditor.replaceWord(length, word);
    String actual = textEditor.getText();
    String expected =
        "Однажды в \"студеную\" зимнюю: пору\nI went from a forest, so-called\n\n\n"
            + "\"DeepForest!!!\" - страшное место... Кто-нибудь хотел; бы HAHAHA побывать?";
    assertEquals(actual, expected);
  }

  @Test
  public void clearTextFromPunctuationTest1() {
    textEditor.clearTextFromPunctuation();
    String actual = textEditor.getText().trim();
    String expected =
        "Однажды в студеную зимнюю пору I went from a forest so-called "
            + "DeepForest страшное место Кто-нибудь хотел бы там побывать";
    assertEquals(actual, expected);
  }

  @Test
  public void clearTextFromPunctuationTest2() {
    TextLoader textLoader2 = new DirectTextLoaderImpl(text2);
    TextEditor textEditor2 = new RegexTextEditorImpl(textLoader2);
    textEditor2.loadText();
    textEditor2.clearTextFromPunctuation();
    String actual = textEditor2.getText().trim();
    String expected = "At 11.25 o'clock late late in the evening an end-of-time event happened";
    assertEquals(actual, expected);
  }

  @Test
  public void testOnNull() {
    TextLoader textLoader3 = new DirectTextLoaderImpl(null);
    TextEditor textEditor3 = new RegexTextEditorImpl(textLoader3);
    textEditor3.loadText();
    textEditor3.clearTextFromPunctuation();
    String actual = textEditor3.getText();
    assertNull(actual);
  }

  @Test
  public void removeConsonantWords() {
    int length = 6;
    textEditor.removeConsonantWords(length);
    String actual = textEditor.getText().trim();
    String expected =
        "Однажды в \"студеную\" : пору\nI went from a , so-called\n\n\n"
            + "\"DeepForest!!!\" - страшное место... Кто-нибудь хотел; бы там побывать?";
    assertEquals(actual, expected);
  }
}
