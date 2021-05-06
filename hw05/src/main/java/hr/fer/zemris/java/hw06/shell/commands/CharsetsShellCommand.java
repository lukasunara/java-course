package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Instances of this class are a {@link ShellCommand} which represent the charsets
 * command in a shell. 
 * 
 * @author lukasunara
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	/** Represents the name of this command **/
	private String name;
	
	/** Represents the description of this command **/
	private List<String> description;
	
	/** Default constructor for {@link CharsetsShellCommand} command. **/
	public CharsetsShellCommand() {
		super();
		this.name = "charset";
		this.description = new LinkedList<>();
		this.description.add("Command " + name + " takes no arguments and lists names of "
				+ "supported charsets for this Java platform.");
	}
	
	/** Command takes no arguments and lists names of supported charsets for this Java platform. **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			String[] args = CommandsParser.parseNormalArguments(arguments);
			if(args.length > 0)
				throw new IllegalArgumentException("Command " + name + " mustn't receive any arguments!");
			
			Charset.availableCharsets().values().forEach(c -> env.writeln(c.displayName()));
		} catch(IllegalArgumentException | NullPointerException exc) {
			env.writeln(exc.toString());
		}
		return ShellStatus.CONTINUE;
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
