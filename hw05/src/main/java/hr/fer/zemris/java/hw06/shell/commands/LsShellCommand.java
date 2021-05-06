package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Instances of this class are a {@link ShellCommand} which represent the ls
 * command in a shell. 
 * 
 * @author lukasunara
 *
 */
public class LsShellCommand implements ShellCommand {

	/** Represents the name of this command **/
	private String name;
	
	/** Represents the description of this command **/
	private List<String> description;
	
	/** Default constructor for {@link LsShellCommand} command. **/
	public LsShellCommand() {
		super();
		this.name = "ls";
		this.description = new LinkedList<>();
		this.description.add("Command " + name + " takes a single argument – directory – and writes a directory "
				+ "listing (not recursive).");
		this.description.add("The output consists of 4 columns:");
		this.description.add("\tFirst column indicates if current object is directory (d), readable (r), "
				+ "writable (w) and executable (x).");
		this.description.add("\tSecond column contains object size in bytes.");
		this.description.add("\tThird column contains file creation date/time.");
		this.description.add("\tFourth column contains file name.");
	}
	
	/** Command ls takes a single argument – directory – and writes a directory listing (not recursive). **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			String[] args = CommandsParser.parseArgumentsWithFiles(arguments);
			if(args.length != 1) {
				throw new IllegalArgumentException("Command " + name + " must receive one argument!");
			}
			
			Path dir = Paths.get(args[0]);
			if(Files.notExists(dir))
				throw new IllegalArgumentException("The given file does not exist!");
			if(!Files.isDirectory(dir))
				throw new IllegalArgumentException("The given path must be a directory!");
			
			try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
				for(Path p : stream) {
						env.writeln(basicAttributes(p));
				}
			} catch(IOException ex) {
				throw ex;
			}
		} catch(Exception ex) {
			env.writeln(ex.toString());
		}
		return ShellStatus.CONTINUE;
	}

	/** Connects all columns into one String. **/
	private String basicAttributes(Path p) throws IOException {
		String attributes = "";
		
		attributes += firstColumn(p) + " ";
		attributes += secondColumn(p) + " ";
		attributes += thirdColumn(p) + " ";
		attributes += fourthColumn(p);

		return attributes;
	}

	/** Creates the first column. **/
	private String firstColumn(Path p) {
		return (Files.isDirectory(p) ? "d" : "-")
				+ (Files.isReadable(p) ? "r" : "-")
				+ (Files.isWritable(p) ? "w" : "-")
				+ (Files.isExecutable(p) ? "x" : "-");
	}
	
	/** Creates the second column. **/
	private String secondColumn(Path p) throws IOException {
		return String.format("%10d", Files.size(p));
	}
	
	/** Creates the third column. **/
	private String thirdColumn(Path p) throws IOException {
		BasicFileAttributeView faView = Files.getFileAttributeView(
				p, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
		);
		BasicFileAttributes attributes = faView.readAttributes();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		
		return formattedDateTime;
	}
	
	/** Creates the fourth column. **/
	private String fourthColumn(Path p) {
		return p.getFileName().toString();
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
