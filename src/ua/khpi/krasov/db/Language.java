package ua.khpi.krasov.db;

import java.util.Locale;

public enum Language {
	
	DEFALT, EN, RU;
	
	public int getLanuageId() {
		return this.ordinal();
	}
	
	public static Language getLanguage(Locale locale) {
		String name = locale.getLanguage().toUpperCase();
		return Language.valueOf(name);
	}

}
