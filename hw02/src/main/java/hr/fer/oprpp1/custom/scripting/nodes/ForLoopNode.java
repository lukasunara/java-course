package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * Class ForLoopNode is a {@link Node} representing a single for-loop construct.
 * Tag ForLoopNode can have three or four parameters (as specified by user):
 * first it must have one ElementVariable and after that two or three Elements of
 * type variable, number or string.
 * 
 * @author lukasunara
 *
 */
public class ForLoopNode extends Node {

	public static final int FOR_LOOP_NUMBER_OF_VARIABLES = 4;

	private static final Object END_TAG = "{$END$}";
	
	/** Read-only {@link ElementVariable} property: variable **/ 
	private ElementVariable variable;
	
	/**  Read-only {@link Element} property: startExpression **/
	private Element startExpression;
	
	/**  Read-only {@link Element} property: endExpression **/
	private Element endExpression;
	
	/**  Read-only {@link Element} property: stepExpression (can be <code>null</code> **/
	private Element stepExpression;

	/**
	 * Constructor initializes all the properties a ForLoopNode has.
	 * 
	 * @param variable ElementVariable which is a variable
	 * @param startExpression Element which is the start expression
	 * @param endExpression Element which is the end expression
	 * @param stepExpression Element which is the step expression
	 * @throws NullPointerException when variable or startExpression or stepExpression
	 * are <code>null</code>
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression,
			Element endExpression, Element stepExpression) {
		super();
		if(variable==null || startExpression==null || stepExpression==null)
			throw new NullPointerException("Argument cannot be null!");
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/** Public getter method for {@link ElementVariable} property: variable. **/
	public ElementVariable getVariable() {
		return variable;
	}

	/** Public getter method for {@link Element} property: startExpression. **/
	public Element getStartExpression() {
		return startExpression;
	}

	/** Public getter method for {@link Element} property: endExpression. **/
	public Element getEndExpression() {
		return endExpression;
	}

	/** Public getter method for {@link Element} property: stepExpression. **/
	public Element getStepExpression() {
		return stepExpression;
	}
	
	/** Creates a string which contains all 4 (or 3) of its elements. **/
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$ FOR ")
		  .append(variable.asText())
		  .append(startExpression.asText())
		  .append(endExpression.asText());
		if(stepExpression != null) sb.append(stepExpression.asText());
		sb.append("$}");
		
		for(int i = 0; i < numberOfChildren(); i++) {
			sb.append(this.getChild(i).toString());
		}
		sb.append(END_TAG);
		return sb.toString();
	}
	
	/** Checks if this ForLoopNode is the same as the given ForLoopNode. **/
	@Override
	public boolean equals(Object obj) {
		if(obj==null) return false;
		if(obj instanceof ForLoopNode) {
			ForLoopNode node = (ForLoopNode)obj;
			if(!node.getVariable().equals(this.getVariable())) return false;
			if(!node.getStartExpression().equals(this.getStartExpression())) return false;
			if(!node.getEndExpression().equals(this.getEndExpression())) return false;
			if(!node.getStepExpression().equals(this.getStepExpression())) return false;
		}
		return true;
	}
	
}
