package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Copies the {@link TurtleState} from top of context stack and pushes it on top of stack. 
 * 
 * @author lukasunara
 *
 */
public class PushCommand implements Command {

	/** Copies the {@link TurtleState} from top of context stack and pushes it on top of stack. **/
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}

}
