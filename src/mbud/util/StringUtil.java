package mbud.util;

import java.text.DecimalFormat;

public class StringUtil {
	
	private static DecimalFormat format = new DecimalFormat("0.00");
	
	public static String formatNormalNumber(String str) {
		String ret = str.substring(0, 2) + " " + str.substring(2, 5) + " " + str.substring(5, 7) + " " + str.substring(7, 9);
		return ret;
	}
	
	public static String formatPrice(double price) {
		return format.format(price);
	}

}
