package javax.swing;

public class UIManager {

  private static final LookAndFeel lookAndFeel = new LookAndFeel();

  public static LookAndFeel getLookAndFeel() {
    return lookAndFeel;
  }
}
