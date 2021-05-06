package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Modifies the color in current {@link TurtleState} by the given color.
 * 
 * @author lukasunara
 *
 */
public class ColorCommand implements Command {

	/** double value for which the effective shift length is scaled **/
	private Color color;
	
	/**
	 * Constructor creates a new ColorCommand with the given color.
	 * 
	 * @param color the new Color in which the turtle draws
	 */
	public ColorCommand(Color color) {
		super();
		this.color = color;
	}
	
	/** Modifies the color in current {@link TurtleState} by given color. **/
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}
