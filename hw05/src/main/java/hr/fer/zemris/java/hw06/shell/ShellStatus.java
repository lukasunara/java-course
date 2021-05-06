package hr.fer.zemris.java.hw06.shell;

/**
 * Enumeration ShellStatus represents status of {@link MyShell}.
 * 
 * @author lukasunara
 *
 */
public enum ShellStatus {

	/** If an exception occures in a {@link ShellCommand} => shell continues working. **/
	CONTINUE,
	
	/** If an exception occures while writing or reading from shell => shell terminated. **/
	TERMINATE
	
}
