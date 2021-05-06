package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Modifies the turtle's direction vector by rotating it for the given angle.
 * 
 * @author lukasunara
 *
 */
public class RotateCommand implements Command {

	/** double value for which the vector is rotated (in radians) **/
	private double angle;
	
	/**
	 * Constructor creates a new RotateCommand with the given angle.
	 * 
	 * @param angle double value for which the vector is rotated
	 */
	public RotateCommand(double angle) {
		super();
		this.angle = angle;
	}
	
	/** Modifies the turtle's direction vector by rotating it by a given angle. **/
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getDirection().rotate(angle);
	}

}
