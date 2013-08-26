package germ.logging;

import germ.configuration.ConfigurationManager;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogManager {
	private static Logger logger = Logger.getLogger("germ");

	static {

		try {
			// necemo da nad roditelj ( u nasem slucaju root logger) ispisuje sve na konzolu
			// to sami radimo u zavisnosti od podesavanja u ConfigurationManager-u
			logger.setUseParentHandlers(false);
			logger.setLevel(Level.ALL);
			ConfigurationManager cm = ConfigurationManager.getInstance();
			LogFormater defaultLogFormater = new LogFormater();
			if (cm.getConfigParameter("logToFile").equals("true")) {
				// za logovanje u fajl...
				// rotira se na svaki 1 MB i cuva poslenja 3 log-fajla
				FileHandler fh = new FileHandler((String)cm.getConfigParameter("logFileName"), 1000000, 3, true);
				fh.setFormatter(defaultLogFormater);
				// System.out.println(logger.getHandlers());
				logger.addHandler(fh);
			}
			
			if ((Boolean)cm.getConfigParameter("logToConsole").equals("true")) {
				ConsoleHandler ch = new ConsoleHandler();
			ch.setFormatter(defaultLogFormater);
				logger.addHandler(ch);
			}

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Logger getLogger() {
		return logger;
	}
}