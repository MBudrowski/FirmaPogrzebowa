package mbud.util;

import java.util.regex.Pattern;

public class Validator {
	
	private static Pattern emailPattern = Pattern.compile(
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	private static Pattern pwdPattern = Pattern.compile("^.{6,}$");
	private static Pattern phonePattern = Pattern.compile("^[0-9]{9,13}$");
	private static Pattern datePattern = Pattern.compile("^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$");
	private static Pattern dateTimePattern = Pattern.compile("^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) ([01]?[0-9]|2[0-3]):[0-5][0-9]$");

	public static boolean validateEmail(String str) {
		return emailPattern.matcher(str).matches();
	}
	
	public static boolean validatePassword(String str) {
		return pwdPattern.matcher(str).matches();
	}
	
	public static boolean validatePhone(String str) {
		return phonePattern.matcher(str).matches();
	}
	
	public static boolean validateDate(String str) {
		return datePattern.matcher(str).matches();
	}
	
	public static boolean validateDateTime(String str) {
		return dateTimePattern.matcher(str).matches();
	}
	
}
