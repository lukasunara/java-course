package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Dimension;

/**
 * Implementations of this interface are used
 * for calculating one {@link Component} {@link Dimension}.
 * 
 * @author lukasunara
 *
 */
public interface SizeCounter {

	/**
	 * Counts the {@link Component} {@link Dimension}.
	 * 
	 * @param comp {@link Component} for which the size is calculated
	 * @return the calculated {@link Dimension}
	 */
	Dimension count(Component comp);
	
}
