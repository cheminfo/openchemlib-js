package javax.swing;

public class UIManager {
  private final static LookAndFeel lookAndFeel = new LookAndFeel();

  static LookAndFeel getLookAndFeel() {
    return lookAndFeel;
  }
}
