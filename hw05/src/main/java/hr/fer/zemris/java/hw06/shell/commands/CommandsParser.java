package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.ShellCommand;

/**
 * This class contains methods used by instances of {@link ShellCommand} for parsing
 * through arguments.
 * 
 * @author lukasunara
 *
 */
public class CommandsParser {

	/**
	 * Parse for commands which use normal arguments without any file paths.
	 * 
	 * @param arguments String which represents concated arguments used by command
	 * @return String array containing all arguments
	 */
	public static String[] parseNormalArguments(String arguments) {
		if(arguments == null)
			throw new NullPointerException("Arguments is null!");
		if(arguments.isBlank())
			return new String[0];

		return arguments.strip().split("\\s+");
	}
	
	/**
	 * Parse for commands which use arguments with possible file paths.
	 * 
	 * @param arguments String which represents concated arguments used by command
	 * @return String array containing all arguments
	 */
	public static String[] parseArgumentsWithFiles(String arguments) {
		if(arguments == null)
			throw new NullPointerException("Arguments is null!");
		
		String[] splittedString = arguments.strip().split("\\s+");
		String[] args = new String[splittedString.length];
		
		int j = 0;
		for(int i=0; i < splittedString.length; i++, j++) {
			// argument can be a file path so we need to enable parsing paths with form "..."
			if(splittedString[i].startsWith("\"")) {
				int lastIndex = splittedString[i].substring(1).indexOf('\"');
				if(lastIndex != -1) {
					if(lastIndex != splittedString[i].length()-2) // because substring is one char shorter
						throw new IllegalArgumentException("Illegal path: After \" must be a whitespace!");
					
					args[j] = splittedString[i].substring(1, splittedString[i].length()-1); // skip  " ... "
					continue;
				}
				// path name must start and end with "
				args[j] = splittedString[i++];
				while(!splittedString[i].contains("\"")) {
					args[j] += " " + splittedString[i++];
					if(i >= splittedString.length)
						throw new IllegalArgumentException("Illegal name for path!");
				}
				if(splittedString[i].indexOf('\"') != splittedString[i].length()-1)
					throw new IllegalArgumentException("Illegal path: After \" must be a whitespace!");
				
				args[j] += " " + splittedString[i];
				args[j] = args[j].substring(1, args[j].length()-1); // skip  " ... "
			} else {
				args[j] = splittedString[i];
			}
		}
		return Arrays.copyOf(args, j); // return only non null elements
	}

}
