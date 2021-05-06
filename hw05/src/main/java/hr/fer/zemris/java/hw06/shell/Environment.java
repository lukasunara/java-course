package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Interface Environments contains a list of methods every environment should have.
 * 
 * @author lukasunara
 *
 */
public interface Environment {

	/**
	 * Reads the rest of next line.
	 * 
	 * @return String which represents the rest of next line
	 * @throws ShellIOException if there is an error while reading from shell
	 */
	 String readLine() throws ShellIOException;
	 
	 /**
	  * Writes the given String on shell.
	  * 
	  * @param text String to be written on shell
	  * @throws ShellIOException if there is an error whilewriting on shell
	  */
	 void write(String text) throws ShellIOException;
	 
	 /**
	  * Writes the given String on shell and adds a new line in the end.
	  * 
	  * @param text String to be written on shell
	  * @throws ShellIOException if there is an error whilewriting on shell
	  */
	 void writeln(String text) throws ShellIOException;
	 
	 /**
	  * Creates and returns a unmodifiable map of all commands the environment supports.
	  * 
	  * @return the created unmodifiable map of commands
	  */
	 SortedMap<String, ShellCommand> commands();
	 
	 /**
	  * Getter for multiline symbol.
	  * 
	  * @return the multiline symbol
	  */
	 Character getMultilineSymbol();
	 
	 /**
	  * Setter for multiline symbol.
	  * 
	  * @param symbol the new multiline symbol
	  */
	 void setMultilineSymbol(Character symbol);
	 
	 /**
	  * Getter for prompt symbol.
	  * 
	  * @return the prompt symbol
	  */
	 Character getPromptSymbol();
	 
	 /**
	  * Setter for prompt symbol.
	  * 
	  * @param symbol the new prompt symbol
	  */
	 void setPromptSymbol(Character symbol);
	 
	 /**
	  * Getter for morelines symbol.
	  * 
	  * @return the morelines symbol
	  */
	 Character getMorelinesSymbol();
	 
	 /**
	  * Setter for morelines symbol.
	  * 
	  * @param symbol the new morelines symbol
	  */
	 void setMorelinesSymbol(Character symbol);

}
