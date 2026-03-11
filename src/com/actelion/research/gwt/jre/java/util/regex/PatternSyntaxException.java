package java.util.regex;

import java.lang.IllegalArgumentException;

public class PatternSyntaxException extends IllegalArgumentException {
  public PatternSyntaxException(String desc) {
    super(desc);
  }
}
