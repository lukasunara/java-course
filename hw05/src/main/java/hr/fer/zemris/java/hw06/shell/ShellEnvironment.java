package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 * Instances of this class represent a command-line program and implement the 
 * {@link Environment} interface.
 * 
 * @author lukasunara
 *
 */
public class ShellEnvironment implements Environment {

	/** Default {@link Character} for the prompt symbol of {@link ShellEnvironment} **/
	private final static Character DEFAULT_PROMPT_SYMBOL = '>';

	/** Default {@link Character} for the morelines symbol of {@link ShellEnvironment} **/
	private final static Character DEFAULT_MORELINES_SYMBOL = '\\';

	/** Default {@link Character} for the multiline symbol of {@link ShellEnvironment} **/
	private final static Character DEFAULT_MULTILINE_SYMBOL = '|';
	
	/** Represents the prompt symbol in shell **/
	private Character promptSymbol;
	
	/** Represents the symbol in shell for commands in which more lines are used **/
	private Character morelinesSymbol;
	
	/** Represents the prompt symbol in shell for commands in which multiple lines are used **/
	private Character multilineSymbol;

	/** Represents a list of all commands in this shell **/
	private SortedMap<String, ShellCommand> commands;
	
	/** Represents the {@link Scanner} used for interaction with user **/
	private Scanner sc;
	
	/** Default constructor for {@link ShellEnvironment}. **/
	public ShellEnvironment() {
		super();
		this.promptSymbol = DEFAULT_PROMPT_SYMBOL;
		this.morelinesSymbol = DEFAULT_MORELINES_SYMBOL;
		this.multilineSymbol = DEFAULT_MULTILINE_SYMBOL;
		this.buildCommandsMap();
		this.sc = new Scanner(System.in);
	}
	
	@Override
	public String readLine() throws ShellIOException {
		try {
			return sc.nextLine();			
		} catch (Exception ex) {
			sc.close();
			throw new ShellIOException("Exception occured while reading!");
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(this.commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return this.multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		if(symbol == null)
			throw new IllegalArgumentException("Multiline symbol mustn't be null!");
		
		this.multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return this.promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		if(symbol == null)
			throw new IllegalArgumentException("Prompt symbol mustn't be null!");
		
		this.promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return this.morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		if(symbol == null)
			throw new IllegalArgumentException("Morelines symbol mustn't be null!");
		
		this.morelinesSymbol = symbol;
	}
	
	/** Method fills the commands {@link SortedMap} with all commands which this shell supports. **/
	public void buildCommandsMap() {
		commands = new TreeMap<>();
		
		commands.put("ls", new LsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
	}
	
}
