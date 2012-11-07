package remoteServer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class rsLogFormatter extends Formatter{

	
	
	public String setTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Date now = new Date();
		dateFormat.format(now);
		String day = new String("["+dateFormat.format(now)+"]  "+timeFormat.format(now)+":  ");
		return day;
	}
	
	public rsLogFormatter() {
		super();
	}
	
	public String format(LogRecord record) {
		if (record.getLevel().getName() == "WARNING" | record.getLevel().getName() == "SEVERE") {
			record.setMessage(record.getMessage().toUpperCase());
		}
		StringBuffer sb = new StringBuffer(50);
		sb.append("["+record.getLevel().getName()+"]  ");
		sb.append(setTime());
		sb.append(formatMessage(record));
		String line = System.getProperty("line.separator");
		sb.append(line);
		return sb.toString();		
	}

	
	

}
