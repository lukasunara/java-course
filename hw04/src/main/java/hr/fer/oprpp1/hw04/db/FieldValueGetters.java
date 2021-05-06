package hr.fer.oprpp1.hw04.db;

/**
 * Instance of this class defines three concrete strategies: one for each String
 * field of {@link StudentRecord} class (i.e. one that returns student's first name,
 * one that returns student's last name, and one that returns student's jmbag).
 * 
 * @author lukasunara
 *
 */
public class FieldValueGetters {

	/** Gets student's first name from StudentRecord **/
	public static final IFieldValueGetter FIRST_NAME = StudentRecord::getFirstName;
	
	/** Gets student's last name from StudentRecord **/
	public static final IFieldValueGetter LAST_NAME = StudentRecord::getLastName;
	
	/** Gets student's jmbag from StudentRecord **/
	public static final IFieldValueGetter JMBAG = StudentRecord::getJmbag;
	
}
