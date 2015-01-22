package java.text;

import com.google.gwt.i18n.client.NumberFormat;

public class DecimalFormat extends java.text.NumberFormat {
	
	NumberFormat formatter;
	
	public DecimalFormat(String pattern, DecimalFormatSymbols symbols) {
		this(pattern);
	}
	
	public DecimalFormat(String pattern) {
		formatter = NumberFormat.getFormat(pattern);
	}
	
	public String format(double number) {
		return formatter.format(number);
	}

	public String format(float number) {
		return formatter.format(number);
	}

}
