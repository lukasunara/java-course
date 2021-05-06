package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Instances of this class are a {@link ShellCommand} which represent the hexdump
 * command in a shell. 
 * 
 * @author lukasunara
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	/** Represents the name of this command **/
	private String name;
	
	/** Represents the description of this command **/
	private List<String> description;
	
	/** Default constructor for {@link HexdumpShellCommand} command. **/
	public HexdumpShellCommand() {
		super();
		this.name = "hexdump";
		this.description = new LinkedList<>();
		this.description.add("Command " + name + " expects a single argument: file name, and "
				+ "produces hex-output.");
		this.description.add("Only a standard subset of characters is shown; for all other "
				+ "characters a '.' is printed instead.");
	}
	
	/**
	 * Command hexdump expects a single argument: file name, and produces hex-output.
	 * Only a standard subset of characters is shown; for all other characters a '.' is printed instead.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			String[] args = CommandsParser.parseArgumentsWithFiles(arguments);
			if(args.length != 1) {
				throw new IllegalArgumentException("Command " + name + " must receive one argument!");
			}
			
			Path path = Paths.get(args[0]);
			if(Files.isDirectory(path))
				throw new IllegalArgumentException("The argument must be a file, not a directory!");
			
			toHexDumpFormat(env, path);
		} catch(Exception ex) {
			env.writeln(ex.toString());
		}
		return ShellStatus.CONTINUE;
	}

	/** Writes file in hex dump format. **/
	private void toHexDumpFormat(Environment env, Path path) throws IOException {
		try(InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
			int i = 0;
			while (is.available() > 0) {
				StringBuilder sbHex = new StringBuilder();		// for hex output
				StringBuilder sbChar = new StringBuilder("| ");	// for char output
				
			    env.write(String.format("%08x: ", i*16));
			    for(int j=0; j < 16; j++) {
			    	if(is.available() > 0) {
			    		int value = is.read(); // read next byte of data
			    		
			    		sbHex.append(String.format("%02x", value)).append(j==7 ? "|" : " ");
			    		
			    		if (!Character.isISOControl(value)) sbChar.append((char)value);
			    		else sbChar.append(".");
			    	}
			    	else {
			    		for(; j < 16; j++) {
			    			sbHex.append("  ").append(j==7 ? "|" : " ");
			    		}
			    	}
			    }
			    env.write(sbHex.toString());
			    env.writeln(sbChar.toString());
			    i++;
			}
		} catch(IOException ex) {
			throw ex;
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
