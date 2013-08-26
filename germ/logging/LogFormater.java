package germ.logging;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Class for formating message to log. <br/>
 * Displays <code>[time] -> logLevel -> message</code>.
 */
public class LogFormater extends Formatter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
	 */
	@Override
	public String format(LogRecord record) {
		return ("[" + new Date(record.getMillis())).toString() + " -> "
				+ record.getLevel() + "]\t"	+ record.getMessage() + "\n";
	}

}