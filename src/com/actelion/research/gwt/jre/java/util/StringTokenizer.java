package java.util;

import com.google.gwt.core.client.JavaScriptObject;

public class StringTokenizer {
  private JavaScriptObject tokens;
  private int currentToken;
  private int totalTokens;

  public StringTokenizer(String str) {
    this(str, " \t\n\r\f", false);
  }

  public StringTokenizer(String str, String delim) {
    this(str, delim, false);
  }

  public StringTokenizer(String str, String delim, boolean returnDelims) {
    createTokens(str, delim, returnDelims);
  }

  private native JavaScriptObject createTokens(String str, String delim, boolean returnDelims) /*-{
    delim = delim.split('');
    // TODO: create the tokens.
    throw new Error('unimplemented');
  }-*/;

  public boolean hasMoreTokens() {
    return currentToken < totalTokens;
  }

  public int countTokens() {
    return totalTokens - currentToken;
  }

  public String nextToken() {
    String token = getToken(tokens, currentToken);
    currentToken++;
    return token;
  }

  private native String getToken(JavaScriptObject tokens, int index) /*-{
    return tokens[index];
  }-*/;
}
