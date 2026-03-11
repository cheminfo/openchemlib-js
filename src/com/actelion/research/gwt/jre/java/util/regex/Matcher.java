package java.util.regex;

import org.cheminfo.utils.JSException;
import org.cheminfo.utils.JSRegexMatcher;
import org.cheminfo.utils.JSRegexMatchResult;

public class Matcher {
  private JSRegexMatcher jsMatcher;
  // To simplify emulation for now, `find` can only be called once.
  private boolean wasCalled = false;
  private JSRegexMatchResult matchResult = null;

  public Matcher(JSRegexMatcher matcher) {
    this.jsMatcher = matcher;
  }

  public boolean find() {
    if (wasCalled) {
      JSException.throwError("find was already called on this matcher");
      return false;
    } else {
      wasCalled = true;
      matchResult = this.jsMatcher.find();
      return matchResult != null;
    }
  }

  public boolean find(int start) {
    JSException.throwUnimplemented();
    return false;
  }

  public MatchResult toMatchResult() {
    JSException.throwUnimplemented();
    return null;
  }

  public String group(int group) {
    return matchResult.group(group);
  }
}
