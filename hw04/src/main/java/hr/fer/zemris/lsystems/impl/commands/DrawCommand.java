package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Calculates where does the turtle need to go and draws a line from current to calculated
 * position. Also, saves the new position in current {@link TurtleState}.
 * 
 * @author lukasunara
 *
 */
public class DrawCommand implements Command {

	/** double value for which the turtle moves in its direction **/
	private double step;
	
	/**
	 * Constructor creates a new DrawCommand with the given step.
	 * 
	 * @param step double value for which the turtle moves in its direction
	 */
	public DrawCommand(double step) {
		super();
		this.step = step;
	}
	
	/**
	 * Calculates where does the turtle need to go and draws a line from current to
	 * calculated position. Also, saves the new position in current {@link TurtleState}.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		
		Vector2D currentPosition = currentState.getCurrentPosition();
		Vector2D currentDirection = currentState.getDirection();
		
		double distanceToDraw = currentState.getShift() * step;
		
		double angle = Math.atan2(currentDirection.getY(), currentDirection.getX());
		
		double newX = currentPosition.getX() + distanceToDraw*Math.cos(angle);
		double newY = currentPosition.getY() + distanceToDraw*Math.sin(angle);
		
		painter.drawLine(currentPosition.getX(), currentPosition.getY(), newX,
				newY, currentState.getColor(), 1f);
		
		Vector2D newPosition = new Vector2D(newX, newY);
		currentState.setCurrentPosition(newPosition);
	}

}
