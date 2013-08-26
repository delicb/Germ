package germ.i18n;

import germ.configuration.ConfigurationManager;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final ResourceBundle RESOURCE_BUNDLE;

	static {
		String languageShort = ConfigurationManager.getInstance().getString("language");
		String language = "english";
		if (languageShort.equals("ENG")) 
			language = "english";
		if (languageShort.equals("FRA")) 
			language = "french";
		if (languageShort.equals("GER")) 
			language = "german";
		if (languageShort.equals("HUN")) 
			language = "hungarian";
		if (languageShort.equals("ITA")) 
			language = "italian";
		if (languageShort.equals("SER")) 
			language = "serbian_lat";
		if (languageShort.equals("СРБ")) 
			language = "serbian_cyr";
		if (languageShort.equals("ESP")) 
			language = "spanish";
		
		RESOURCE_BUNDLE = ResourceBundle.getBundle("germ.i18n." + language);
	}
	
	private Messages() {
	}

	public static String getString(String key) {
		try {
			String value = RESOURCE_BUNDLE.getString(key);
			return new String (value.getBytes("ISO-8859-1"),"UTF-8");
		} catch (MissingResourceException e) {
			System.out.println(key);
			return '!' + key + '!';
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "!ERROR!";
	}
}
