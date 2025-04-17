package java.lang;

public class Runtime {
  private static Runtime runtime = new Runtime();

  public static Runtime getRuntime() {
    return runtime;
  }

  private Runtime() {}

  public Process exec(String[] program) {
    return new Process();
  }
}
