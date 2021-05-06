package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MenuBar;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Document;
import javax.swing.text.Element;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.*;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.*;

/**
 * This program represents a simple text file editor.
 * 
 * @author lukasunara
 *
 */
public class JNotepadPP extends JFrame {

	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;
	
	private static final String TITLE = "JNotepad++";
	
	/** {@link Map} of {@link Action}s used by this notepad **/
	private Map<String, Action> actions;
	
	/** {@link FormLocalizationProvider} used for localization **/
	private FormLocalizationProvider flp;

	/** {@link DefaultMultipleDocumentModel} used for document tabs **/
	private DefaultMultipleDocumentModel multipleModel;
	
	/** Text saved from previous cut or copy actions **/
	private String savedTextFromCutOrCopy;
	
	/** Default constructor initializes this frame. **/
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);
		setTitle(TITLE);
		
		savedTextFromCutOrCopy = "";
		multipleModel = new DefaultMultipleDocumentModel();
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		
		fillActions();
		initGUI();
		
		// default window listener when application closes
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				JNotepadPP.this.getActions().get("exit").actionPerformed(null);
			}
		});
		
		// change title if file path is updated
		SingleDocumentListener singleListener = new SingleDocumentListener() {
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				JNotepadPP.this.setTitle(model.getFilePath().toString() + " - " + TITLE);
			}
		};
		
		// change title if changed current tab
		multipleModel.addMultipleDocumentListener(new MultipleDocumentListener() {
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				model.removeSingleDocumentListener(singleListener);
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				model.addSingleDocumentListener(singleListener);
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if(currentModel == null) {
					JNotepadPP.this.setTitle(TITLE);
					return;
				}
				
				Path path = currentModel.getFilePath();
				String filePath = path != null ? path.toString() : DefaultMultipleDocumentModel.UNNAMED;
				
				JNotepadPP.this.setTitle(filePath + " - " + TITLE);
			}
		});
	}
	
	/** Fills the {@link #actions} map. **/
	private void fillActions() {
		actions = new HashMap<>();
		
		actions.put("new", new NewFileAction(this, "new"));
		actions.put("open", new OpenFileAction(this, "open"));
		actions.put("save", new SaveFileAction(this, "save"));
		actions.put("save_as", new SaveFileAsAction(this, "save_as"));
		actions.put("close", new CloseFileAction(this, "close"));
		actions.put("exit", new ExitAppAction(this, "exit"));
		actions.put("cut", new CutCopyPasteTextAction(this, "cut", KeyStroke.getKeyStroke("control X"), KeyEvent.VK_X));
		actions.put("copy", new CutCopyPasteTextAction(this, "copy", KeyStroke.getKeyStroke("control C"), KeyEvent.VK_C));
		actions.put("paste", new CutCopyPasteTextAction(this, "paste", KeyStroke.getKeyStroke("control V"), KeyEvent.VK_V));
		actions.put("statistics", new StatisticsAction(this, "statistics"));
		actions.put("to_lowercase", new CaseTextAction(this, "to_lowercase", KeyStroke.getKeyStroke("alt shift L"), KeyEvent.VK_U));
		actions.put("to_uppercase", new CaseTextAction(this, "to_uppercase", KeyStroke.getKeyStroke("alt shift U"), KeyEvent.VK_U));
		actions.put("invert_case", new CaseTextAction(this, "invert_case", KeyStroke.getKeyStroke("alt shift I"), KeyEvent.VK_I));
		actions.put("ascending", new SortAction(this, "ascending", true));
		actions.put("descending", new SortAction(this, "descending", false));
		actions.put("unique", new UniqueAction(this, "unique"));
	}

	/** Initializes GUI of this frame. **/
	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout());
		
		createMenus();
		createToolbars();
		createTabbedPane();
	}

	/** Creates the {@link MenuBar} and its {@link JMenu}s. **/
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = createMenu("file");
		menuBar.add(fileMenu);

		fileMenu.add(createJMenuItem("new"));
		fileMenu.add(createJMenuItem("open"));
		fileMenu.addSeparator();
		fileMenu.add(createJMenuItem("close"));
		fileMenu.addSeparator();
		fileMenu.add(createJMenuItem("save"));
		fileMenu.add(createJMenuItem("save_as"));
		fileMenu.addSeparator();
		fileMenu.add(createJMenuItem("exit"));
		
		JMenu editMenu = createMenu("edit");
		menuBar.add(editMenu);
		
		editMenu.add(createJMenuItem("cut"));
		editMenu.add(createJMenuItem("copy"));
		editMenu.add(createJMenuItem("paste"));
		
		JMenu toolsMenu = createMenu("tools");
		menuBar.add(toolsMenu);
		
		toolsMenu.add(createJMenuItem("statistics"));
		toolsMenu.addSeparator();
		
		JMenu caseTools = createMenu("change_case");
		toolsMenu.add(caseTools);
		
		caseTools.add(createJMenuItem("to_uppercase"));
		caseTools.add(createJMenuItem("to_lowercase"));
		caseTools.add(createJMenuItem("invert_case"));
		
		JMenu sortTools = createMenu("sort");
		toolsMenu.add(sortTools);
		
		sortTools.add(createJMenuItem("ascending"));
		sortTools.add(createJMenuItem("descending"));
		
		toolsMenu.addSeparator();
		toolsMenu.add(createJMenuItem("unique"));
		
		JMenu languagesMenu = createMenu("languages");
		menuBar.add(languagesMenu);
		
		languagesMenu.add(createJMenuLanguage("hr"));
		languagesMenu.add(createJMenuLanguage("en"));
		languagesMenu.add(createJMenuLanguage("de"));
		
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Creates a new {@link JMenu}.
	 * 
	 * @param text String key used for localization
	 * @return the created {@link JMenu}
	 */
	private JMenu createMenu(String text) {
		JMenu menu = new JMenu(flp.getString(text));
		
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				menu.setText(flp.getString(text));
			}
		});
		
		return menu;
	}

	/**
	 * Creates a new {@link JMenuItem} with {@link Action}.
	 * 
	 * @param text String key used for localization and
	 * {@link Action} specification
	 * @return the created {@link JMenuItem}
	 */
	private JMenuItem createJMenuItem(String text) {
		JMenuItem menuItem = new JMenuItem(actions.get(text));
		
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				menuItem.setText(flp.getString(text));
			}
		});
		
		return menuItem;
	}
	
	/**
	 * Creates a new {@link JMenuItem} used for language
	 * selection.
	 * 
	 * @param text String key used for localization and
	 * language selection
	 * @return the created {@link JMenuItem}
	 */
	private JMenuItem createJMenuLanguage(String language) {
		JMenuItem menuItem = new JMenuItem(flp.getString(language));
		
		menuItem.addActionListener(e -> {
			LocalizationProvider.getInstance().setLanguage(language);
		});
		
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				menuItem.setText(flp.getString(language));
			}
		});
		
		return menuItem;
	}
	
	/** Creates the {@link JToolBar} and its {@link JButton}s. **/
	private void createToolbars() {
		JToolBar toolBar = createToolBar("toolbar");
		toolBar.setFloatable(true);
		
		toolBar.add(createJButton("open"));
		toolBar.add(createJButton("save"));
		toolBar.addSeparator();
		toolBar.add(createJButton("to_uppercase"));
		toolBar.add(createJButton("to_lowercase"));
		toolBar.add(createJButton("invert_case"));
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	/**
	 * Creates a new floatable {@link JToolBar}.
	 * 
	 * @param text String key used for localization
	 * @return the created {@link JToolBar}
	 */
	private JToolBar createToolBar(String text) {
		JToolBar toolBar = new JToolBar(flp.getString(text));
		
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				toolBar.setToolTipText(flp.getString(text));
				toolBar.setName(flp.getString(text));
			}
		});
		
		return toolBar;
	}

	/**
	 * Creates a new {@link JButton} with {@link Action}.
	 * 
	 * @param text String key used for localization and
	 * {@link Action} specification
	 * @return the created {@link JButton}
	 */
	private JButton createJButton(String text) {
		JButton button = new JButton(actions.get(text));
		
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				button.setText(flp.getString(text));
			}
		});
		
		return button;
	}
	
	/** Creates a {@link JPanel} which contains the {@link #multipleModel}. **/
	private void createTabbedPane() {
		JPanel modelPanel = new JPanel(new BorderLayout());
		this.getContentPane().add(modelPanel, BorderLayout.CENTER);
		
		modelPanel.add(multipleModel, BorderLayout.CENTER);
		
		JPanel statusBar = createStatusBar();
		modelPanel.add(statusBar, BorderLayout.PAGE_END);
	}
	
	/** Creates a {@link JPanel} which contains the status bar. **/
	private JPanel createStatusBar() {
		JPanel statusBar = new JPanel(new GridLayout(1, 0));
		
		JPanel lengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		statusBar.add(lengthPanel);

		lengthPanel.add(createInfoField("length:"));
		

		JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		statusBar.add(infoPanel);
		
		infoPanel.add(createInfoField("Ln:"));
		infoPanel.add(createInfoField("Col:"));
		infoPanel.add(createInfoField("Sel:"));
		
		statusBar.add(new ClockComponent());
		
		return statusBar;
	}

	/**
	 * Creates a new {@link JPanel} used info display.
	 * 
	 * @param info String used for name of info
	 * @return the created {@link JPanel}
	 */
	private JPanel createInfoField(String info) {
		JPanel infoPanel = new JPanel();
		
		JLabel infoLabel = new JLabel(info);
		infoPanel.add(infoLabel);
		
		JLabel numberLabel = new JLabel("0");
		infoPanel.add(numberLabel);
		
		multipleModel.addMultipleDocumentListener(new MultipleDocumentListener() {

			private CaretListener listener;
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				if(info.equals("length:")) return;
				
				model.getTextComponent().removeCaretListener(listener);
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				if(info.equals("length:")) return;
				
				listener = new CaretListener() {
					@Override
					public void caretUpdate(CaretEvent e) {
						JTextArea editor = model.getTextComponent();
						
						int number = 0;
						switch(info) {
							case "Sel:": { // set length of selected text
								number =ActionUtils.getLength(editor.getCaret());
								break;
							}
							case "Ln:", "Col:": { // set current line
								Document doc = editor.getDocument();
								Element root = doc.getDefaultRootElement();
								
								int position = editor.getCaretPosition();
								int line = root.getElementIndex(position);
								
								if(info.equals("Ln:")) {
									number = line + 1; // lines start from index 1
									break;
								}
								number = position - root.getElement(line).getStartOffset() + 1; // columns start from index 1
								break;
							}
						}
						setLabelText(number);
					}
				};
				model.getTextComponent().addCaretListener(listener);
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if(!info.equals("length:")) return;
				
				int number = currentModel == null ? 0 : currentModel.getTextComponent().getText().length();
				setLabelText(number);
			}
			
			/** Sets the label text to the given number. **/
			private void setLabelText(int number) {
				numberLabel.setText(String.valueOf(number));
			}
		});
		
		return infoPanel;
	}
	
	/**
	 * Public getter method for {@link #multipleModel}.
	 * 
	 * @return the {@link DefaultMultipleDocumentModel} of
	 * this JFrame
	 */
	public DefaultMultipleDocumentModel getMultipleModel() {
		return multipleModel;
	}
	
	/**
	 * Public getter method for {@link #flp}.
	 * 
	 * @return the {@link FormLocalizationProvider} of
	 * this JFrame
	 */
	public FormLocalizationProvider getFlp() {
		return flp;
	}
	
	/**
	 * Public getter method for {@link #actions}.
	 * 
	 * @return the map of {@link Action}s of this frame
	 */
	public Map<String, Action> getActions() {
		return Collections.unmodifiableMap(actions);
	}

	/**
	 * Public getter method for {@link #savedTextFromCutOrCopy}.
	 * 
	 * @return the saved text from previous cut or copy action
	 */
	public String getSavedTextFromCutOrCopy() {
		return savedTextFromCutOrCopy;
	}
	
	/**
	 * Public setter method for {@link #savedTextFromCutOrCopy}.
	 * 
	 * @param newText represents new saved text from a cut or copy action
	 */
	public void setSavedTextFromCutOrCopy(String newText) {
		this.savedTextFromCutOrCopy = newText;
	}
	
	/** Main method doesn't expect any arguments **/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
				new JNotepadPP().setVisible(true);
		});
	}

}
