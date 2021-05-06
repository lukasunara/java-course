package hr.fer.oprpp1.custom.collections;

/**
 * The Processor is a model of an object capable of performing some operation on
 * the passed object. For this reason, each Processor must have the
 * <code>void process(Object value)</code> method.
 * 
 * Class Processor represents an conceptual contract between clients which
 * will have objects to be processed, and each specific Processor which knows how
 * to perform the selected operation. Each specific Processor will be defined as
 * a new class which inherits from the class Processor.
 *  
 * @author lukasunara
 *
 */
public class Processor {

	/**
	 * The Processor is a model of an object capable of performing some operation
	 * on the passed object. This method has an empty body, because each specific
	 * Processor will specify it's own specific operation.
	 * 
	 * @param value the object on which the Processor performs some operation
	 */
	public void process(Object value) { }
	
}
