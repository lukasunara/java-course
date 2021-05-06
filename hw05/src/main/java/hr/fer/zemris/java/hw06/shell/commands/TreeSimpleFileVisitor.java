package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import hr.fer.zemris.java.hw06.shell.Environment;

/**
 * A {@link SimpleFileVisitor} used by {@link TreeShellCommand}.
 * 
 * @author lukasunara
 *
 */
public class TreeSimpleFileVisitor extends SimpleFileVisitor<Path> {

	/** Represents level of the current files (used for indentation) **/
	private int level;
	
	/** The {@link Environment} in which the results are being written **/
	private Environment env;
	
	/** Constructor for the file visitor. **/
	public TreeSimpleFileVisitor(Environment env) {
		super();
		this.env = env;
		this.level = -1;
	}
	
	/** If it's time to visit a directory => level++ **/
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		env.write(dir.getFileName().toString().indent(level*2));
		level++;
		
		return FileVisitResult.CONTINUE;
	}

	/** If it's time to visit a file => print its name **/
	@Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
    	env.write(file.getFileName().toString().indent(level*2));
    	
    	return FileVisitResult.CONTINUE;
    }

	/** If it's time to leave a directory => level-- **/
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        level--;
        
        return FileVisitResult.CONTINUE;
    }
    
}
