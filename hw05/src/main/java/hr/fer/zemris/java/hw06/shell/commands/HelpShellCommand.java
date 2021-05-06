package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Instances of this class are a {@link ShellCommand} which represent the help command
 * in a shell. 
 * 
 * @author lukasunara
 *
 */
public class HelpShellCommand implements ShellCommand {

	/** Represents the name of this command **/
	private String name;
	
	/** Represents the description of this command **/
	private List<String> description;

	/** Default constructor for {@link HelpShellCommand} command. **/
	public HelpShellCommand() {
		super();
		this.name = "help";
		this.description = new LinkedList<>();
		this.description.add("Command " + name + " explains the usage of the selected command.");
		this.description.add("If started with no arguments, method lists names of all supported commands in this shell.");
		this.description.add("If started with a single argument, method prints the name and the description of selected "
				+ "command (or prints appropriate error message if no such command exists).");
	}
	
	/**
	 * If started with no arguments, method lists names of all supported commands.
	 * If started with a single argument, method prints the name and the description of selected
	 * command (or prints appropriate error message if no such command exists).
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			String[] args = CommandsParser.parseNormalArguments(arguments);
			if(args.length > 1)
				throw new IllegalArgumentException("Command " + name + " can receive only one optional argument!");
			if(args.length == 0) {
				env.writeln("This shell supports following commands:");
				env.commands().keySet().forEach(c -> env.writeln(c.indent(3)));
				return ShellStatus.CONTINUE;
			}
			
			ShellCommand command = env.commands().get(args[0]);
			if(command != null) {
				env.writeln("Command name: " + command.getCommandName() + "\n");
				command.getCommandDescription().forEach(env::writeln);
			} else {
				throw new IllegalArgumentException("Command \"" + args[0] + "\" is not supported by this shell!");
			}
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
