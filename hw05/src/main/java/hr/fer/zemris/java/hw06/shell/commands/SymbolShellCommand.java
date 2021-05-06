package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Instances of this class are a {@link ShellCommand} which represent the symbol
 * command in a shell. 
 * 
 * @author lukasunara
 *
 */
public class SymbolShellCommand implements ShellCommand {

	/** Represents the name of this command **/
	private String name;
	
	/** Represents the description of this command **/
	private List<String> description;
	
	/** Default constructor for {@link SymbolShellCommand} command. **/
	public SymbolShellCommand() {
		super();
		this.name = "symbol";
		this.description = new LinkedList<>();
		this.description.add("Command " + name + " prints or changes the selected symbol of the shell.");
		this.description.add("It must receive one or two arguments: [TYPE] and [value] (value is optional).");
		this.description.add("[TYPE] can be: \"PROMPT\", \"MORELINES\" or \"MULTILINE\"");
		this.description.add("[VALUE] must be a java.lang.Character!");
	}
	
	/**
	 * Command changes prints or changes the selected symbol of the shell and must receive one or two arguments:
	 * [TYPE] and [value] (value is optional). [TYPE] can be: "PROMPT", "MORELINES" or "MULTILINE".
	 * [VALUE] must be a {@link Character}.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			String[] args = CommandsParser.parseNormalArguments(arguments);
			if(args.length < 1 || args.length > 2) {
				throw new IllegalArgumentException("Command " + name + " must receive one or two arguments!");
			}
			
			if(args.length == 1) {
				env.writeln(String.format("Symbol for %s is '%c'", args[0], getSymbol(env, args[0])));
			} else {
				if(args[1].length() != 1) {
					throw new IllegalArgumentException("Command " + name + " must receive a java.lang.Character as the second argument!");
				}
				env.writeln(String.format("Symbol for %s changed from '%c' to '%c'", args[0], getSymbol(env, args[0]), setSymbol(env, args[0], args[1])));
			}
		} catch(IllegalArgumentException | NullPointerException exc) {
			env.writeln(exc.toString());
		}
		return ShellStatus.CONTINUE;
	}

	/** Returns the selected shell symbol. **/
	private Character getSymbol(Environment env, String symbol) {
		switch(symbol) {
			case "PROMPT": return env.getPromptSymbol();
			case "MORELINES": return env.getMorelinesSymbol();
			case "MULTILINE": return env.getMultilineSymbol();
			default: {
				throw new IllegalArgumentException("First argument for command " + name + " must be: "
						+ "\"PROMPT\", \"MORELINES\" or \"MULTILINE\"");
			}
		}
	}

	/** Sets the selected new symbol **/
	private Character setSymbol(Environment env, String symbol, String newSymbol) {
		switch(symbol) {
			case "PROMPT": {
				env.setPromptSymbol(newSymbol.charAt(0));
				return env.getPromptSymbol();
			}
			case "MORELINES": {
				env.setMorelinesSymbol(newSymbol.charAt(0));
				return env.getMorelinesSymbol();
			}
			case "MULTILINE": {
				env.setMultilineSymbol(newSymbol.charAt(0));
				return env.getMultilineSymbol();
			}
			default: {
				throw new IllegalArgumentException("First argument for command " + name + " must be: "
						+ "\"PROMPT\", \"MORELINES\" or \"MULTILINE\"");
			}
		}
	}
	
	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
