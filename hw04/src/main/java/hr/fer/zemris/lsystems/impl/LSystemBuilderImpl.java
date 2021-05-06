package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Scanner;

import hr.fer.oprpp1.custom.collections.Dictionary;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;

/**
 * This class implements {@link LSystemBuilder} and is used to model objects which can be
 * configured and after that calls the method build which returns a concrete Lindermayer system
 * by the given configuration.
 * 
 * The configuration can be created in two ways: by calling setter methods or calling the
 * configureFromText() method.
 *
 * @author lukasunara
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/** Stores registered productions **/
	private Dictionary<Character, String> productions;
	
	/** Stores registered commands **/
	private Dictionary<Character, Command> commands;
	
	/** Represents the unit length of steps with which the turtle draws **/
	private double unitLength;
	
	/** Represents the unit length degree scaler of this turtle **/
	private double unitLengthDegreeScaler;
	
	/** Represents the starting point from which the turtle draws **/
	private Vector2D origin;
	
	/** Represents the direction in which the turtle should draw **/
	private double angle;

	/** Represents the initial sequence which the turtle should draw **/
	private String axiom;
	
	/**
	 * Default constructor creates a new LSystemBuilderImpl and intializes all fields.
	 */
	public LSystemBuilderImpl() {
		super();
		this.productions = new Dictionary<>();
		this.commands = new Dictionary<>();
		this.unitLength = 0.1;
		this.unitLengthDegreeScaler = 1;
		this.origin = new Vector2D(0, 0);
		this.angle = 0;
		this.axiom = "";
	}
	
	/**
	 * Creates a new instance of {@link LSystem} and returns it.
	 * 
	 * @return a new instance of {@link LSystem}
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}
	
	/**
	 * Parses through the given String array of arguments and returnes this LSystemBuilder.
	 * 
	 * @param arg0 array of strings which contain commands
	 * @return a new instance of {@link LSystemBuilder}
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		for(String str : arg0) {
			Scanner sc = new Scanner(str);
			while(sc.hasNext()) {
				// We assume that the user knows the correct input for each case
				switch(sc.next()) {
					case "origin": setOrigin(sc.nextDouble(), sc.nextDouble()); break;
					case "angle": setAngle(sc.nextDouble()); break;
					case "unitLength": setUnitLength(sc.nextDouble()); break;
					case "unitLengthDegreeScaler": {
						double number = sc.nextDouble();
						sc.skip("\\s*/");
						if(sc.hasNextDouble()) number /= sc.nextDouble();
						
						setUnitLengthDegreeScaler(number);
						break;
					}
					case "command": {
						char c = sc.next().charAt(0);
						String command = sc.next();
						if(sc.hasNext()) command += " "+sc.next();
						
						registerCommand(c, command);
						break;
					}
					case "axiom": setAxiom(sc.next()); break;
					case "production": registerProduction(sc.next().charAt(0), sc.next()); break;
					default: throw new IllegalArgumentException("Invalid String for configuration!");
				}
			}
			sc.close();
		}
		return this;
	}
	
	/**
	 * Puts the given Command to the commands dictionary and returnes this LSystemBuilder.
	 *
	 * @param arg0 char which represents a {@link Command}
	 * @param arg1 String which describes the {@link Command}
	 * @return this LSystemBuilder
	 * */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		Command command = null;
		String[] commandString = arg1.split(" ");
		switch(commandString[0]) {
			case "draw": command = new DrawCommand(Double.parseDouble(commandString[1])); break;
			case "skip": command = new SkipCommand(Double.parseDouble(commandString[1])); break;
			case "rotate": {
				 // cast deegrees to radians
				command = new RotateCommand(Math.toRadians(Double.parseDouble(commandString[1])));
				break;
			}
			case "push": command = new PushCommand(); break;
			case "pop": command = new PopCommand(); break;
			case "color": command = new ColorCommand(new Color(Integer.parseInt(commandString[1], 16))); break;
			case "scale": command = new ScaleCommand(Double.parseDouble(commandString[1])); break;
			default: throw new IllegalArgumentException("Invalid String for configuration!");
		}
		this.commands.put(arg0, command);
		
		return this;
	}
	
	/**
	 * Puts the given production to the productions dictionary and returnes this LSystemBuilder.
	 * 
	 * @param arg0 char which represents a {@link Command}
	 * @param arg1 String which describes the {@link Command}
	 * @return this LSystemBuilder
	 */
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		if(this.productions.put(arg0, arg1) != null)
			throw new IllegalArgumentException("Production for this symbol already exists!");
		return this;
	}
	
	/**
	 * Sets the angle (cast from degrees to radians) which represents the direction in which
	 * the turtle should draw and returnes this LSystemBuilder.
	 * 
	 * @param arg0 double value which represents the angle in degrees
	 * @return this LSystemBuilder
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		this.angle = Math.toRadians(arg0);
		return this;
	}
	/**
	 * Axiom represents the initial sequence (it can be only one symbol or a String) and
	 * returnes this LSystemBuilder.
	 * 
	 * @param arg0 String value which represents the axiom
	 * @return this LSystemBuilder
	 */
	@Override
	public LSystemBuilder setAxiom(String arg0) {
		this.axiom = arg0;
		return this;
	}
	/**
	 * Sets the origin (creates a new {@link Vector2D} with given coordinates) from which the
	 * trutle starts drawing and returns this LSystemBuilder.
	 * 
	 * @param arg0 double value which represents the x-coordinate of Vector2D
	 * @param arg1 double value which represents the y-coordinate of Vector2D
	 * @return this LSystemBuilder
	 */
	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		this.origin = new Vector2D(arg0, arg1);
		return this;
	}
	
	/**
	 * Sets the unit length of turtles steps and returns this LSystemBuilder.
	 * 
	 * @param arg0 double value which represents the unit length of turtle's steps
	 * @return this LSystemBuilder
	 */
	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		this.unitLength = arg0;
		return this;
	}
	/**
	 * Sets the unit length degree scaler for the turtle and returns this LSystemBuilder.
	 * 
	 * @param arg0 double value which represents the setUnitLengthDegreeScaler
	 * @return this LSystemBuilder
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		this.unitLengthDegreeScaler = arg0;
		return this;
	}
	
	/**
	 * Instances of this class create a new {@link Context}, a new {@link TurtleState}, pushes it on
	 * stack of context, generates a String for the given level and for each Character of the that String
	 * calls the Command it represents.
	 * 
	 * @author lukaunara
	 *
	 */
	class LSystemImpl implements LSystem {

		/**
		 * Draws the result fractal using {@link Painter} and {@link Context}.
		 * 
		 * @param arg0 int which represents the level which is going to be generated
		 */
		@Override
		public void draw(int arg0, Painter arg1) {
			Context ctx = new Context();

			// Dimensions of fractals should remain constants => shift = unitLength * (unitLengthDegreeScaler^d)
			TurtleState newState = new TurtleState(origin, Vector2D.UNIT_VECTOR.rotated(angle),
					Color.BLACK, unitLength*(Math.pow(unitLengthDegreeScaler, arg0)));
			ctx.pushState(newState);

			String generatedString = generate(arg0);

			// System.out.println(generatedString);
			for(char c : generatedString.toCharArray()) {
				Command command = commands.get(c);
				if(command != null) command.execute(ctx, arg1);
			}
		}

		/**
		 * Recieves the level which represents how many times are the productions going to be
		 * called and generates a result String. 
		 * 
		 * @param arg0 int which represents the level which is going to be generated
		 * @return the result String which was generated
		 */
		@Override
		public String generate(int arg0) {
			if(arg0 == 0) return axiom;
			
			String result = "";
			for(char c : generate(arg0-1).toCharArray()) {
				if(productions.get(c) != null) result += productions.get(c);
				else result += c;
			}
			return result;
		}

	}

}
