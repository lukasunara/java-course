package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Instances of this class are a {@link ShellCommand} which represent the exit command
 * in a shell. 
 * 
 * @author lukasunara
 *
 */
public class ExitShellCommand implements ShellCommand {

	/** Represents the name of this command **/
	private String name;
	
	/** Represents the description of this command **/
	private List<String> description;
	
	/** Default constructor for {@link ExitShellCommand} command. **/
	public ExitShellCommand() {
		super();
		this.name = "exit";
		this.description = new LinkedList<>();
		this.description.add("Command " + name + " terminates the shell.");
	}
	
	/** Command terminates the environment, but mustn't receive any arguments. **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			String[] args = CommandsParser.parseNormalArguments(arguments);
			if(args.length > 0)
				throw new IllegalArgumentException("Command " + name + " mustn't receive any arguments!");
		} catch(IllegalArgumentException | NullPointerException ex) {
			env.writeln(ex.toString());
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
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
