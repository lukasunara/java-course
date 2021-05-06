package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Instances of this class are a {@link ShellCommand} which represent the mkdir
 * command in a shell. 
 * 
 * @author lukasunara
 *
 */
public class MkdirShellCommand implements ShellCommand {

	/** Represents the name of this command **/
	private String name;
	
	/** Represents the description of this command **/
	private List<String> description;
	
	/** Default constructor for {@link MkdirShellCommand} command. **/
	public MkdirShellCommand() {
		super();
		this.name = "mkdir";
		this.description = new LinkedList<>();
		this.description.add("Command " + name + " takes a single argument: directory name, and "
				+ "creates the appropriate directory structure.");
	}
	
	/** Command mkdir takes a single argument: directory name, and creates the appropriate directory structure. **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			String[] args = CommandsParser.parseArgumentsWithFiles(arguments);
			if(args.length != 1)
				throw new IllegalArgumentException("Command " + name + " must receive one argument!");

			Files.createDirectories(Paths.get(args[0].strip()));
		} catch(Exception ex) {
			env.writeln(ex.toString());
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
