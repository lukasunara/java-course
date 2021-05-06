package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Instances of this class are a {@link ShellCommand} which represent the copy
 * command in a shell. 
 * 
 * @author lukasunara
 *
 */
public class CopyShellCommand implements ShellCommand {

	/** Represents the name of this command **/
	private String name;
	
	/** Represents the description of this command **/
	private List<String> description;
	
	/** Default constructor for {@link CopyShellCommand} command. **/
	public CopyShellCommand() {
		super();
		this.name = "copy";
		this.description = new LinkedList<>();
		this.description.add("Command " + name + " expects two arguments: source file path "
				+ "and destination file path.");
		this.description.add("If destination file exists, it asks user is it allowed to overwrite it.");
		this.description.add("First argument cannot be a directory.");
		this.description.add("If the second argument is a directory, command assumes that user wants "
				+ "to copy the original file into that directory using the original file name.");
	}
	
	/**
	 * Command copy expects two arguments: source file path and destination file path.
	 * If destination file exists, it asks user is it allowed to overwrite it.
	 * First argument cannot be a directory. If the second argument is a directory, command assumes
	 * that user wants to copy the original file into that directory using the original file name.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		final int SIZE4KB = 4096; //Represents size of 4kb = 4096 bytes
		
		try {
			String[] args = CommandsParser.parseArgumentsWithFiles(arguments);
			if(args.length != 2) {
				throw new IllegalArgumentException("Command " + name + " must receive two arguments!");
			}
			
			Path inputFile = Paths.get(args[0]);
			Path outputFile = Paths.get(args[1]);
			if(Files.notExists(inputFile))
				throw new IllegalArgumentException("The given file does not exist!");
			if(Files.isDirectory(inputFile))
				throw new IllegalArgumentException("First argument cannot be a directory!");
			
			if(Files.exists(outputFile)) {
				while(true) {
					env.write("Overwrite " + outputFile.getFileName().toString() + "? (y/n): ");
					String yesOrNo = env.readLine().strip().toLowerCase();
	
					if(yesOrNo.equals("n")) return ShellStatus.CONTINUE;
					else if(yesOrNo.equals("y")) break;
					else continue;
				}
				
				if(Files.isDirectory(outputFile))
					outputFile = Paths.get(args[1] + inputFile.getFileName().toString());
			}
			try (InputStream is = new BufferedInputStream(Files.newInputStream(inputFile));
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(outputFile));
			) {
				byte[] buff = new byte[SIZE4KB]; // read the file by chunks of 4kb
				int sizeOfUpdate;
				while((sizeOfUpdate = is.read(buff)) != -1) {
					os.write(buff, 0, sizeOfUpdate);
				}
			} catch(IOException ex) {
				throw ex;
			}
		} catch(ShellIOException ex) {
			throw ex;
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
