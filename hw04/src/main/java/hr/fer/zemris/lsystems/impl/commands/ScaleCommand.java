package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Modifies the current effective shift length in current {@link TurtleState} by scaling it
 * for the given factor.
 * 
 * @author lukasunara
 *
 */
public class ScaleCommand implements Command {

	/** double value for which the effective shift length is scaled **/
	private double factor;
	
	/**
	 * Constructor creates a new ScaleCommand with the given factor.
	 * 
	 * @param factor double value for which the effective shift length is scaled
	 */
	public ScaleCommand(double factor) {
		super();
		this.factor = factor;
	}
	
	/**
	 * Modifies the current effective shift length in current {@link TurtleState} by scaling
	 * it with the given factor.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setShift(ctx.getCurrentState().getShift() * factor);
	}

}
