package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface ShellCommand contains a list of methods every shell command should have.
 * 
 * @author lukasunara
 *
 */
public interface ShellCommand {

	/**
	 * Executes this {@link ShellCommand} in the given {@link Environment} for the given arguments.
	 * 
	 * @param env {@link Environment} in which the command has to be executed
	 * @param arguments single String which represents everything that user enters after the
	 * 		  command name
	 * @return a {@link ShellStatus} which defines if the shell should be terminated or
	 * 		   continue working
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Public getter method for command's name.
	 * 
	 * @return the command's name
	 */
	String getCommandName();
	
	/**
	 * Returnes a description of this ShellCommand (usage instructions for this command).
	 * 
	 * 
	 * @return read-only {@link List} of Strings which represent the description
	 */
	List<String> getCommandDescription();
	
}
