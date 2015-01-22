package java.text;

public abstract class NumberFormat {
	
	protected NumberFormat() {}
	
	public abstract String format(double number);
	public abstract String format(float number);

}