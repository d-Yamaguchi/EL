package el.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.script.ScriptContext;

import nez.main.CommandContext;
import nez.util.ConsoleUtils;
import nez.util.StringUtils;

public class Main {
	public final static void main(String[] args) {
		try {
			CommandContext c = new CommandContext();
			c.parseCommandOption(args, false/* nezCommand */);
			exec(c);
		} catch (IOException e) {
			ConsoleUtils.println(e);
			System.exit(1);
		}
	}

	public static ScriptContextError expected = ScriptContextError.NoError;

	public static void exec(CommandContext config) throws IOException {
		if (config.isUnspecifiedGrammarFilePath()) {
			config.setGrammarFilePath("EL.nez");
		}
		ScriptContext sc = new ScriptContext(config.newParser());
		if (config.hasInput()) {
			try {
				while (config.hasInput()) {
					sc.eval(config.nextInput());
				}
			} catch (AssertionError e) {
				e.printStackTrace();
				sc.found(ScriptContextError.AssertonError);
			} catch (RuntimeException e) {
				e.printStackTrace();
				sc.found(ScriptContextError.RuntimeError);
			}
			if (sc.getError() != expected) {
				ConsoleUtils.exit(1, "expected " + expected + " but " + sc.getError());
			}
		} else {
			show(config);
			shell(sc);
		}
	}

	public final static String ProgName = "EL";
	public final static String CodeName = "yokohama";
	public final static int MajorVersion = 4;
	public final static int MinerVersion = 0;
	public final static int PatchLevel = Revision.REV;
	public final static String Version = "" + MajorVersion + "." + MinerVersion + "-" + PatchLevel;
	public final static String Copyright = "Copyright (c) 2016, EL project authors";
	public final static String License = "BSD-License Open Source";

	private static void show(CommandContext config) {
		ConsoleUtils.bold();
		ConsoleUtils.println("Konoha " + Version + " U(" + config.newGrammar().getDesc() + ") on Nez " + nez.Version.Version);
		ConsoleUtils.end();
		ConsoleUtils.println(Copyright);
		ConsoleUtils.println("Copyright (c) 2016, Kimio Kuramitsu, Yokohama National University");
		ConsoleUtils.begin(37);
		ConsoleUtils.println(Message.Hint);
		ConsoleUtils.end();
	}

	public static void shell(ScriptContext sc) throws IOException {
		sc.setShellMode(true);
		ConsoleReader console = new ConsoleReader();
		console.setHistoryEnabled(true);
		console.setExpandEvents(false);
		int linenum = 1;
		String command = null;
		while ((command = readLine(console)) != null) {
			if (command.trim().equals("")) {
				continue;
			}
			if (hasUTF8(command)) {
				ConsoleUtils.begin(31);
				ConsoleUtils.println("(<stdio>:" + linenum + ") " + Message.DetectedUTF8);
				ConsoleUtils.end();
				command = filterUTF8(command);
			}

			try {
				ConsoleUtils.begin(32);
				Object result = sc.eval("<stdio>", linenum, command);
				ConsoleUtils.end();
				if (!(result instanceof EmptyResult)) {
					ConsoleUtils.begin(37);
					ConsoleUtils.println("<<<");
					ConsoleUtils.end();
					ConsoleUtils.bold();
					if (result instanceof String) {
						result = StringUtils.quoteString('"', (String) result, '"');
					}
					ConsoleUtils.println(result);
					ConsoleUtils.end();
				}
			} catch (ScriptRuntimeException e) {
				ConsoleUtils.begin(31);
				ConsoleUtils.println(e);
				e.printStackTrace();
				ConsoleUtils.end();
			} catch (RuntimeException e) {
				ConsoleUtils.begin(31);
				ConsoleUtils.println(e);
				e.printStackTrace();
				ConsoleUtils.end();
			}
			linenum += (command.split("\n").length);
		}
	}

	public final static String KonohaVersion = "4.0";

	private static String readLine(ConsoleReader console) throws IOException {
		ConsoleUtils.begin(31);
		ConsoleUtils.println(">>>");
		ConsoleUtils.end();
		List<Completer> completors = new LinkedList<Completer>();

		// completors.add(new AnsiStringsCompleter("\u001B[1mfoo\u001B[0m",
		// "bar", "\u001B[32mbaz\u001B[0m"));
		CandidateListCompletionHandler handler = new CandidateListCompletionHandler();
		handler.setStripAnsi(true);
		console.setCompletionHandler(handler);
		for (Completer c : completors) {
			console.addCompleter(c);
		}
		// History h = console.getHistory();
		// ("hoge\rhoge");
		StringBuilder sb = new StringBuilder();
		while (true) {
			String line = console.readLine();
			if (line == null) {
				return null;
			}
			if (line.equals("")) {
				return sb.toString();
			}
			sb.append(line);
			sb.append("\n");
		}
		// h = console.getHistory();
	}

	private static boolean hasUTF8(String command) {
		boolean skip = false;
		for (int i = 0; i < command.length(); i++) {
			char c = command.charAt(i);
			if (c == '"') {
				skip = !skip;
				continue;
			}
			if (c < 128 || skip) {
				continue;
			}
			return true;
		}
		return false;
	}

	static HashMap<Character, Character> charMap = null;

	static void initCharMap() {
		if (charMap == null) {
			charMap = new HashMap<>();
			charMap.put('　', ' ');
			charMap.put('（', '(');
			charMap.put('）', ')');
			charMap.put('［', '[');
			charMap.put('］', ']');
			charMap.put('｛', '{');
			charMap.put('｝', '}');
			charMap.put('”', '"');
			charMap.put('’', '\'');
			charMap.put('＜', '<');
			charMap.put('＞', '>');
			charMap.put('＋', '+');
			charMap.put('ー', '-');
			charMap.put('＊', '*');
			charMap.put('／', '/');
			charMap.put('✕', '*');
			charMap.put('÷', '/');
			charMap.put('＝', '=');
			charMap.put('％', '%');
			charMap.put('？', '?');
			charMap.put(':', ':');
			charMap.put('＆', '&');
			charMap.put('｜', '|');
			charMap.put('！', '!');
			charMap.put('、', ',');
			charMap.put('；', ';');
			charMap.put('。', '.');
			for (char c = 'A'; c <= 'Z'; c++) {
				charMap.put((char) ('Ａ' + (c - 'A')), c);
			}
			for (char c = 'a'; c <= 'z'; c++) {
				charMap.put((char) ('ａ' + (c - 'a')), c);
			}
			for (char c = '0'; c <= '9'; c++) {
				charMap.put((char) ('０' + (c - '0')), c);
			}
			for (char c = '1'; c <= '9'; c++) {
				charMap.put((char) ('一' + (c - '0')), c);
			}
		}
	}

	private static String filterUTF8(String command) {
		initCharMap();
		StringBuilder sb = new StringBuilder(command.length());
		boolean skip = false;
		for (int i = 0; i < command.length(); i++) {
			char c = command.charAt(i);
			if (c < 128 || skip) {
				if (c == '"') {
					skip = !skip;
				}
				sb.append(c);
				continue;
			}
			Character mapped = charMap.get(c);
			if (mapped != null) {
				sb.append(mapped);
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
