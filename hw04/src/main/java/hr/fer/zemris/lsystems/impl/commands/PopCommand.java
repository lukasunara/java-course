package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Removes one {@link TurtleState} from context. 
 * 
 * @author lukasunara
 *
 */
public class PopCommand implements Command {

	/** Removes one {@link TurtleState} from context. **/
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
