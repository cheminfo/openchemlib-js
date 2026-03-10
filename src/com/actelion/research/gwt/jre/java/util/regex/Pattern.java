package java.util.regex;

import org.cheminfo.utils.JSException;
import org.cheminfo.utils.JSRegexMatcher;
import org.cheminfo.utils.JSRegexPattern;

public class Pattern {
  private JSRegexPattern jsPattern;

  private Pattern(JSRegexPattern jsPattern) {
    this.jsPattern = jsPattern;
  }

  public static Pattern compile(String p) throws PatternSyntaxException {
    JSRegexPattern jsPattern = JSRegexPattern.compile(p);
    if (jsPattern == null) {
      throw new PatternSyntaxException("invalid JS RegExp: " + p);
    } else {
      return new Pattern(jsPattern);
    }
  }

  public Matcher matcher(String input) {
    JSRegexMatcher jsMatcher = this.jsPattern.getMatcher(input);
    return new Matcher(jsMatcher);
  }
}
