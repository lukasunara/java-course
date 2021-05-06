package hr.fer.oprpp1.hw04.db;

/**
 * This interface defines a strategy which is responsible for obtaining a requested
 * field value from given {@link StudentRecord}.
 * 
 * @author lukasunara
 *
 */
@FunctionalInterface
public interface IFieldValueGetter {

	/**
	 * Method responsible for obtaining a requested field value from given {@link StudentRecord}
	 * 
	 * @param record the student record whose field value we are obtaining
	 * @return a requested field value
	 */
	public String get(StudentRecord record);
	
}
