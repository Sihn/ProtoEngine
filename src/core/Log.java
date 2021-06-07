package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import core.tools.ExtensionFilter;

/** I tried the Logger class, once. Once. */
public class Log {
	private Log() {}

	private final static DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	private final static DateFormat time = new SimpleDateFormat("HH:mm:ss");
	private final static String logFolder = "logs/";
	private final static String logExt = ".log";
	private final static String logFile = "%1$s" + logExt;
	private static boolean canSaveOnFile = true;

	public final static int error = 10;
	public final static int info = 20;
	public final static int debug = 30;
	public static int errorLevel = error;
	public static int cmdLevel = info;
	public static int fileLevel = debug;

	public static void error(Object message) {
		log(error, message, "ERROR");
	}

	public static void info(Object message) {
		log(info, message, "INFO");
	}

	public static void debug(Object message) {
		log(debug, message, "DEBUG");
	}

	public static void log(int level, Object message, String tag) {
		PrintStream stream = level <= errorLevel ? System.err : System.out;
		log(level, message, tag, stream);
	}

	public static void log(int level, Object message, String tag, PrintStream stream) {
		String text = message.toString();
		text = time.format(new Date()) + " : " + text + "\n";
		if (tag != null && !tag.isEmpty())
			text = "[" + tag + "] " + text;
		if (level <= cmdLevel)
			stream.print(text);
		if (!canSaveOnFile || level > fileLevel)
			return;
		String filename = getLogFilename();
		try (FileWriter fw = new FileWriter(filename, true); BufferedWriter buffer = new BufferedWriter(fw)) {
			buffer.write(text);
			buffer.flush();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			canSaveOnFile = false;
		}
	}

	private static String getLogFilename() {
		return String.format(logFolder + logFile, date.format(new Date()));
	}

	public static void main(String[] args) {
		System.out.println("Voulez-vous vraiment supprimer tous les logs ? (Y : oui | N : non)");
		try {
			String answer = String.valueOf(Character.toChars(System.in.read())).toUpperCase();
			if (answer.startsWith("Y")) {
				File path = new File(logFolder);
				if (path.exists()) {
					for (File file : path.listFiles(new ExtensionFilter(logExt))) {
						System.out.println("Delete : " + file.getAbsolutePath());
						file.delete();
					}
					System.out.println("Logs deleted.");
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
