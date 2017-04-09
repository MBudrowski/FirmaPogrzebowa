package mbud.hibernate.util;

import org.hibernate.dialect.MySQLDialect;

public class CustomDialect extends MySQLDialect {
	@Override
	public String getTableTypeString() {
		return " DEFAULT CHARSET=utf8";
	}
}
