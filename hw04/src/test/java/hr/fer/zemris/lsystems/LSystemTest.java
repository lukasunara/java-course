package hr.fer.zemris.lsystems;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class LSystemTest {
	
	@Test
	public void simpleLSystemTest() {
		LSystemBuilderImpl systemBuilder = new LSystemBuilderImpl();
		
		LSystem system = systemBuilder.registerCommand('F', "draw 1")
				.registerCommand('+', "rotate 60")
				.registerCommand('-', "rotate -60")
				.setOrigin(0.05, 0.4)
				.setAngle(0)
				.setUnitLength(0.9)
				.setUnitLengthDegreeScaler(1.0/3.0)
				.registerProduction('F', "F+F--F+F")
				.setAxiom("F")
				.build();
		
		assertEquals("F", system.generate(0));
		assertEquals("F+F--F+F", system.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", system.generate(2));
	}

}
