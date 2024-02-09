package javax.swing;

public class UIManager {
  private final static LookAndFeel lookAndFeel = new LookAndFeel();

  public static LookAndFeel getLookAndFeel() {
    return lookAndFeel;
  }
}
