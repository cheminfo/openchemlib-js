// https://code.google.com/p/anhquan/source/browse/trunk/Quiz/docs/restlet-gwt/org.restlet/com/google/gwt/emul/java/io/BufferedReader.java?r=60
/**
 * Copyright 2005-2010 Noelios Technologies.
 * 
 * The contents of this file are subject to the terms of one of the following
 * open source licenses: LGPL 3.0 or LGPL 2.1 or CDDL 1.0 or EPL 1.0 (the
 * "Licenses"). You can select the license that you prefer but you may not use
 * this file except in compliance with one of these Licenses.
 * 
 * You can obtain a copy of the LGPL 3.0 license at
 * http://www.opensource.org/licenses/lgpl-3.0.html
 * 
 * You can obtain a copy of the LGPL 2.1 license at
 * http://www.opensource.org/licenses/lgpl-2.1.php
 * 
 * You can obtain a copy of the CDDL 1.0 license at
 * http://www.opensource.org/licenses/cddl1.php
 * 
 * You can obtain a copy of the EPL 1.0 license at
 * http://www.opensource.org/licenses/eclipse-1.0.php
 * 
 * See the Licenses for the specific language governing permissions and
 * limitations under the Licenses.
 * 
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly at
 * http://www.noelios.com/products/restlet-engine
 * 
 * Restlet is a registered trademark of Noelios Technologies.
 */

package java.io;

/**
 * Emulate the BufferedReader class, especially for the GWT module.
 * 
 * @author Jerome Louvel
 */
public class BufferedReader extends Reader {

  /** The next saved character. */
  private int savedNextChar;

  private Reader source;

  /**
   * Constructor.
   * 
   * @param source The source reader.
   */
  public BufferedReader(Reader source) {
    this.source = source;
    this.savedNextChar = -2;
  }

  /**
   * Constructor.
   * 
   * @param source The source reader.
   * @param size   The size of the buffer.
   */
  public BufferedReader(Reader source, int size) {
    this.source = source;
    this.savedNextChar = -2;
  }

  /**
   * 
   */
  public void close() throws IOException {

  }

  /**
   * Returns the source reader.
   * 
   * @return The source reader.
   */
  private Reader getSource() {
    return source;
  }

  /**
   * Returns the next character, either the saved one or the next one from the
   * source reader.
   * 
   * @return The next character.
   * @throws IOException
   */
  public int read() throws IOException {
    int result = -1;

    if (this.savedNextChar != -2) {
      result = this.savedNextChar;
      this.savedNextChar = -2;
    } else {
      result = getSource().read();
    }

    return result;
  }

  @Override
  public int read(char[] cbuf, int off, int len) throws IOException {
    return source.read(cbuf, off, len);
  }

  /**
   * Reads the next line of characters.
   * 
   * @return The next line.
   */
  public String readLine() throws IOException {

    int nextChar = read();
    if (nextChar == -1)
      return null;

    StringBuilder sb = new StringBuilder();
    boolean eol = false;

    while (!eol) {
      if (nextChar == 10) {
        eol = true;
      } else if (nextChar == 13) {
        eol = true;

        // Check if there is a immediate LF following the CR
        nextChar = read();
        if (nextChar != 10) {
          this.savedNextChar = nextChar;
        }
      }

      if (!eol) {
        if (nextChar == -1) {
          break;
        }
        sb.append((char) nextChar);
        nextChar = read();
      }

    }

    return sb.toString();
  }

  public boolean ready() {
    // TODO Auto-generated method stub
    return false;
  }
}