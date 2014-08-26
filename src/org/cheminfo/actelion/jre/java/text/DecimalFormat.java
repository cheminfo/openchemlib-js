package java.text;

import com.google.gwt.i18n.client.NumberFormat;

public class DecimalFormat {
	
	NumberFormat formatter;
	
	public DecimalFormat(String pattern, DecimalFormatSymbols symbols) {
		formatter = NumberFormat.getFormat(pattern);
	}
	
	public String format(double number) {
		return formatter.format(number);
	}

}
