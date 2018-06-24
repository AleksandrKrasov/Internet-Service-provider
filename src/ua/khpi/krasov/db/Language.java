package ua.khpi.krasov.db;

import java.util.Locale;

/**
 * Language sets enumeration for languages used in application.
 * Method helps to identify the locale language. Enum values: en, ru.
 * 
 *  @author A.Krasov
 *  @version 1.0
 */
public enum Language {
	
	EN, RU;
	
	public int getLanuageId() {
		return this.ordinal();
	}
	
	public static Language getLanguage(Locale locale) {
		String name = locale.getLanguage().toUpperCase();
		return Language.valueOf(name);
	}

}
