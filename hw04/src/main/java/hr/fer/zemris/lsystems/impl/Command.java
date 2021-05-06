package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Command is a model capable of performing some operation on the passed {@link Context} and
 * {@link Painter}. For this reason, each Command must have the
 * <code>void execute(Context ctx, Painter painter)</code> method.
 * 
 * @author lukasunara
 *
 */
@FunctionalInterface
public interface Command {

	/**
	 * Each Command will specify it's own specific execute operation.
	 * 
	 * @param ctx Context which contains turtle states
	 * @param painter Painter used to draw lines
	 */
	void execute(Context ctx, Painter painter);
	
}
