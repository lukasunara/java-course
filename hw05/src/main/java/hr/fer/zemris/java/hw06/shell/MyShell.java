package hr.fer.zemris.java.hw06.shell;

/**
 * Class MyShell starts the {@link ShellEnvironment} and uses it as a command-line program.
 * 
 * @author lukasunara
 *
 */
public class MyShell {

	/** The shell which represents the command-line environment **/
	private static ShellEnvironment shell;
	
	/**
	 * Method starts the {@link ShellEnvironment}, reads lines and calls commands with
	 * given arguments.
	 * 
	 * @param args any arguments are ignored
	 */
	public static void main(String[] args) {
		shell = new ShellEnvironment();
		
		shell.writeln("Welcome to MyShell v 1.0");
		ShellStatus status = null;
		do {
			shell.write(shell.getPromptSymbol() + " ");
			
			// read the whole command (can be in multiple lines)
			String line = "";
			while(true) {
				String currentLine = shell.readLine().strip();
				if(!currentLine.endsWith(shell.getMorelinesSymbol().toString())) {
					line += currentLine;
					break;
				}
				line += currentLine.substring(0, currentLine.length()-2) + " "; // skip morelines symbol
				shell.write(shell.getMultilineSymbol() + " ");
			}
			// if command is empty do nothing (CONTINUE)
			if(line.isBlank() && line.length()==0) continue;

			// parse the command and start executing it with given parameters
			try {
				String commandName = line.strip().split("\\s+")[0];
				
				ShellCommand command = shell.commands().get(commandName);
				if(command == null) {
					throw new IllegalArgumentException("Shell cannot recognize command " + commandName + "!");
				}
				String arguments = line.strip().substring(commandName.length());
				
				status = command.executeCommand(shell, arguments);
			} catch(IllegalArgumentException ex) {
				shell.writeln(ex.toString());
				status = ShellStatus.CONTINUE;
			} catch(ShellIOException ex) {		// if ShellIOException occures => TERMINATE shell
				shell.writeln(ex.toString());
				status = ShellStatus.TERMINATE;
			}
		} while(status != ShellStatus.TERMINATE);
	}

}
