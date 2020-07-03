package by.pavka.module5.editor;

public interface TextEditor {
  String getText();
  void loadText();

  void replaceCharacter(int index, char replacement);
  void correctCharacter(char anchor, char wrong, char correct);
  void replaceWord(int length, String word);
  void clearTextFromPunctuation();
  void removeConsonantWords(int length);
}
