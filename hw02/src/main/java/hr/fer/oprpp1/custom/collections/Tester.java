package hr.fer.oprpp1.custom.collections;

/**
 * Interface Tester is used to model Objects which tests if they are acceptable or not. 
 * 
 * @author lukasunara
 *
 */
public interface Tester {

	/**
	 * Method defines what test is used to check if the given Object is acceptable or not.
	 * 
	 * @param obj the Object which is being tested
	 * @return true if acceptable, false otherwise
	 */
	boolean test(Object obj);
	
}
