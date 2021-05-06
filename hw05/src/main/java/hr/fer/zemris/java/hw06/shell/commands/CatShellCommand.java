package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Instances of this class are a {@link ShellCommand} which represent the cat
 * command in a shell. 
 * 
 * @author lukasunara
 *
 */
public class CatShellCommand implements ShellCommand {

	/** Represents the name of this command **/
	private String name;
	
	/** Represents the description of this command **/
	private List<String> description;
	
	/** Default constructor for {@link CatShellCommand} command. **/
	public CatShellCommand() {
		super();
		this.name = "cat";
		this.description = new LinkedList<>();
		this.description.add("Command " + name + " takes one or two arguments.");
		this.description.add("The first argument is path to some file and is mandatory.");
		this.description.add("The second argument is charset name that should be used to interpret "
				+ "chars from bytes. If not provided, a default platform charset is used.");
		this.description.add("This command opens given file and writes its content to console.");
	}
	
	/**
	 * Command cat takes one or two arguments. The first argument is path to some file and
	 * is mandatory. The second argument is charset name that should be used to interpret chars
	 * from bytes. If not provided, a default platform charset is used.
	 * This command opens given file and writes its content to console.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		final int SIZE4KB = 4096; //Represents size of 4kb = 4096 bytes
		try {
			String[] args = CommandsParser.parseArgumentsWithFiles(arguments);
			if(args.length < 1 || args.length > 2) {
				throw new IllegalArgumentException("Command " + name + " must receive one or two arguments!");
			}
			
			Charset charset = null;
			if(args.length == 2) charset = Charset.forName(args[1]);
			else charset = Charset.defaultCharset();
			
			try (InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(args[0])))) {
				byte buff[] = new byte[SIZE4KB];
				int sizeOfUpdate;
				while ((sizeOfUpdate = is.read(buff)) != -1) {
					env.write(new String(buff, 0, sizeOfUpdate, charset.displayName()));
				}
				env.writeln("");
			} catch(IOException ex) {
				throw ex;
			}
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
