package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This class is a main program for testing {@link BarChartComponent}.
 * 
 * @author lukasunara
 *
 */
public class BarChartDemo extends JFrame {

	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;
	
	/** Number of useful rows in the given file **/
	private final static int NUM_OF_ROWS_IN_FILE = 6;
	
	/** {@link Path} to the given file **/
	private Path path;

	/** Constructor initalizes {@link JFrame} **/
	public BarChartDemo(BarChartComponent barComp, Path path) {
		super();
		this.path = path;
		
		setTitle("Bar chart");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(200, 200);
		
		initGUI(barComp);
	}
	
	/** Initializes bar chart GUI. **/
	private void initGUI(BarChartComponent barComp) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		cp.add(new JLabel(path.toAbsolutePath().toString(), SwingConstants.CENTER), BorderLayout.PAGE_START);
		cp.add(barComp, BorderLayout.CENTER);
	}
	
	/**
	 * Method expects one argument - path to a file.
	 *
	 * @param args first parameter is a path to a file which contains info
	 * about the bar char
	 */
	public static void main(String[] args) {
		if(args.length != 1)	
			throw new IllegalArgumentException("Program expects a file path as an argument!");
		Path path = Paths.get(args[0]);

//		BarChart model = new BarChart(
//				Arrays.asList(
//						new XYValue(1,8), new XYValue(2,20), new XYValue(3,22),
//						new XYValue(4,10), new XYValue(5,4)
//						),
//				"Number of people in the car",
//				"Frequency",
//				0,
//				22,
//				2
//		);

		BarChart model = createBarChart(path);
		if(model == null) return;
		
		BarChartComponent barComp = new BarChartComponent(model);

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new BarChartDemo(barComp, path);
//			frame.pack();
			frame.setSize(500, 500);
			frame.setVisible(true);
		});
	}

	/**
	 * Creates a bar chart for the data in the given file.
	 * 
	 * @param path {@link Path} to the given file
	 * @return the created {@link BarChart}
	 */
	private static BarChart createBarChart(Path path) {
		String xText = "";
		String yText = "";
		List<XYValue> listOfXY = new LinkedList<>();
		int yMin = 0;
		int yMax = 0;
		int yStep = 0;
		
		try(BufferedReader br = Files.newBufferedReader(path)) {
			for(int i = 0; i < NUM_OF_ROWS_IN_FILE; i++) {
				String line = br.readLine();
				switch(i) {
					case 0: xText = line; break;
					case 1: yText = line; break;
					case 2: parseValues(listOfXY, line); break;
					case 3: yMin = Integer.parseInt(line);
					case 4: yMax = Integer.parseInt(line);
					case 5: yStep = Integer.parseInt(line);
				}
			}
		} catch (Exception exc) {
			System.out.println(exc.toString());
			return null;
		}
		
		return new BarChart(listOfXY, xText, yText, yMin, yMax, yStep);
	}

	/**
	 * Parses through given {@link XYValue}s.
	 * 
	 * @param listOfXY list we want to fill with values
	 * @param line {@link String} which contains all those values
	 */
	private static void parseValues(List<XYValue> listOfXY, String line) {
		String[] values = line.strip().split("\\s++");
		
		for(String value : values) {
			String[] numbers = value.split(",");
			if(numbers.length != 2)
				throw new IllegalArgumentException("Given value is not a XYValue!");
			
			int x = Integer.parseInt(numbers[0]);
			int y = Integer.parseInt(numbers[1]);
			
			listOfXY.add(new XYValue(x, y));
		}
	}
	
}
