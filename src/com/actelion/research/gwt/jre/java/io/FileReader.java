package java.io;

import org.cheminfo.utils.JSException;

public class FileReader extends Reader {

  public FileReader(File file) throws FileNotFoundException {

  }

  public FileReader(String filename) throws FileNotFoundException {

  }

  @Override
  public void close() throws IOException {
    JSException.throwUnimplemented();
  }

  @Override
  public int read() throws IOException {
    JSException.throwUnimplemented();
    return 0;
  }

  @Override
  public int read(char[] cbuf, int off, int len) throws IOException {
    JSException.throwUnimplemented();
    return 0;
  }

}
