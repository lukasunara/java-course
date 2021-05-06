package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Instances of this class represent a {@link ListModel} for
 * storing prime numbers.
 * 
 * @author lukasunara
 *
 * @param <E>
 */
public class PrimListModel implements ListModel<Integer> {

	/** For storing elements **/
	private List<Integer> elements = new ArrayList<>();
	
	/** For storing data listeners **/
	private List<ListDataListener> listeners = new ArrayList<>();
	
	/** Remembers the current prime number **/
	private Integer currentPrime;
	
	/** Constructor adds 1 to list of prime numbers. **/
	public PrimListModel(int startPrime) {
		super();
		currentPrime = startPrime;
		elements.add(currentPrime);
	}
	
	@Override
	public int getSize() {
		return elements.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return elements.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	/** Adds the given element to the list **/
	public void next() {
		int pos = elements.size();
		elements.add(getNextPrimeNumber());
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for(ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
	}
	
	/** returns the next prime number in this list **/
	private Integer getNextPrimeNumber() {
		while(true) {
			currentPrime++;
			if(isPrimeNumber()) break;
		}
	    return currentPrime;
	}
	
	/** Checks if the current number is a prime number **/
	private boolean isPrimeNumber() {
	    for(int i = 2; i <= Math.sqrt(currentPrime); i++) {
	        if(currentPrime % i == 0) {
	            return false;
	        }
	    }
	    return true;
	}
	
}
