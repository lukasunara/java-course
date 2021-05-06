package hr.fer.oprpp1.hw04.db;

/**
 * Instances of this interface represent a filter which accepts (or doesn't) the
 * given {@link StudentRecord}.
 * 
 * @author lukasunara
 *
 */
@FunctionalInterface
public interface IFilter {

	/** The object is accepted or not by the given filter. **/
	public boolean accepts(StudentRecord record);

}
