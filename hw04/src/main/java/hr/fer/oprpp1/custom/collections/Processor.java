package hr.fer.oprpp1.custom.collections;

/**
 * The Processor is a model of an object capable of performing some operation on
 * the passed object. For this reason, each Processor must have the
 * <code>void process(Object value)</code> method.
 * 
 * Interface Processor here represents an conceptual contract between clients which
 * will have objects to be processed, and each specific Processor which knows how
 * to perform the selected operation.
 *  
 * @author lukasunara
 *
 * @param <T> type of Object which this Processor can process
 */
@FunctionalInterface
public interface Processor<T> {

	/**
	 * Each specific processor will specify it's own specific process operation.
	 * 
	 * @param value the object on which the Processor performs some operation
	 */
	public void process(T value);
	
}
