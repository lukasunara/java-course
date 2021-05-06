package hr.fer.oprpp1.hw08.jnotepadpp.model;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.ActionUtils;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

/**
 * This class is an implementation of the {@link MultipleDocumentModel}
 * interface. It tracks which tab is currently shown to user and
 * updates the current {@link SingleDocumentModel}.
 * 
 * @author lukasunara
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	
	/** Used by serializable objects **/
	private static final long serialVersionUID = 1L;

	/** String shown when file has not yet been saved **/
	public static final String UNNAMED = "(unnamed)";
	
	/** Represents the red color of save icon **/
	private static final String RED = "red";
	
	/** Represents the green color of save icon **/
	private static final String GREEN = "green";
	
	/** Represents the red save icon **/
	private static final ImageIcon RED_ICON = loadIcon(RED);
	
	/** Represents the green save icon **/
	private static final ImageIcon GREEN_ICON = loadIcon(GREEN);
	
	/** List of all existing {@link SingleDocumentModel} **/
	private List<SingleDocumentModel> documents;
	
	/** Current {@link SingleDocumentModel} **/
	private SingleDocumentModel currentDocument;
	
	/** List of all registered {@link MultipleDocumentListener}s **/
	private List<MultipleDocumentListener> listeners;
	
	/**
	 * Default constructor initializes {@link #documents} and
	 * {@link #listeners}.
	 */
	public DefaultMultipleDocumentModel() {
		super();
		documents = new LinkedList<>();
		listeners = new ArrayList<>();

		// default MultipleDocumentListener
		this.addMultipleDocumentListener(new MultipleDocumentListener() {
			/** get DefaultMultipleDocumentModel.this instance **/
			private DefaultMultipleDocumentModel thisModel = DefaultMultipleDocumentModel.this;
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				int numOfTabs = getNumberOfDocuments();
				
				thisModel.setSelectedIndex(numOfTabs - 1); // switch to the last tab
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				int numOfTabs = getNumberOfDocuments();
				
				thisModel.setSelectedIndex(numOfTabs - 1); // switch to the last tab
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if(previousModel == null && currentModel == null)
					throw new NullPointerException("Both models (previous and current model) are null!");
				
				setCurrentDocument(currentModel);
			}
		});
		// change listener enables switching between tabs
		this.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int index = getSelectedIndex();
				
				setSelectedIndex(index);
				fireCurrentDocumentChanged(getCurrentDocument(), index >= 0 ? getDocument(index) : null);
			}
		});
		// if i want to create a new document on startup
		// setCurrentDocument(createNewDocument());
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	/** 
	 * Creates a new empty document and sets it as
	 * the {@link #currentDocument}.
	 */
	@Override
	public SingleDocumentModel createNewDocument() {
		return addNewTab(null, UNNAMED, "");
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if(path == null)
			throw new NullPointerException("Path of loaded file cannot be null!");
		
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
		} catch(Exception ex) {
			String message = LocalizationProvider.getInstance().getString("error_while_reading")
					+ path.toAbsolutePath() + "!";
			String title = LocalizationProvider.getInstance().getString("error");
			
			ActionUtils.showMessageDialog(this, JOptionPane.ERROR_MESSAGE, message, title);
			return null;
		}
		String loadedText = new String(bytes, StandardCharsets.UTF_8);
		
		return addNewTab(path, path.getFileName().toString(), loadedText);
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		byte[] bytes = model.getTextComponent()
							.getText()
							.getBytes(StandardCharsets.UTF_8);
		try {
			if(newPath != null) model.setFilePath(newPath);
			Files.write(model.getFilePath(), bytes);
		} catch(IOException exc) {
			String message = String.format(
					LocalizationProvider.getInstance().getString("file_error_save") , newPath
			);
			String title = LocalizationProvider.getInstance().getString("error");
			
			ActionUtils.showMessageDialog(this, JOptionPane.ERROR_MESSAGE, message, title);
			return;
		}
		model.setModified(false); // document is up to date

		String message = LocalizationProvider.getInstance().getString("file_success_save");
		String title = LocalizationProvider.getInstance().getString("information");
		
		ActionUtils.showMessageDialog(this, JOptionPane.INFORMATION_MESSAGE, message, title);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		if(model == null) return;
		
		documents.remove(model); // remove given document from documents
		this.remove(this.getSelectedIndex()); // remove current tab
		
		fireDocumentRemoved(model);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}
	
	/**
	 * Private setter for {@link #currentDocument}
	 * 
	 * @param newDocument {@link SingleDocumentModel} which
	 * is the new {@link #currentDocument}
	 */
	private void setCurrentDocument(SingleDocumentModel newDocument) {
		this.currentDocument = newDocument;
	}
	
	/**
	 * Loads the save icon in the given color.
	 * Color must be green or red.
	 * 
	 * @param color String which can only be {@value #RED_ICON}
	 * or {@value #GREEN_ICON}
	 * @return {@link ImageIcon} which represents the save icon
	 * in the given color
	 */
	private static ImageIcon loadIcon(String color) {
		String iconPath = "";
		if(color.equals(RED)) iconPath = "../icons/save-red-8px.png";
		else if(color.equals(GREEN)) iconPath = "../icons/save-green-8px.png";
		
		byte[] bytes;
		try(InputStream is = DefaultMultipleDocumentModel.class.getResourceAsStream(iconPath)) {
			if(is == null)
				throw new NullPointerException("No resource with the given name was found!");
			bytes = is.readAllBytes();
		} catch(IOException | NullPointerException exc) {
			System.out.println(exc.toString());
			return null;
		}
		return new ImageIcon(bytes);
	}
	
	/**
	 * Adds a new tab to this {@link DefaultMultipleDocumentModel}.
	 * 
	 * @param path {@link Path} to the file of document
	 * @param fileName name of the document
	 * @return {@link DefaultSingleDocumentModel} which is added
	 * to this {@link JTabbedPane}
	 */
	private DefaultSingleDocumentModel addNewTab(Path path, String fileName, String textToDisplay) {
		DefaultSingleDocumentModel newDocument = new DefaultSingleDocumentModel(path, textToDisplay);
		
		JScrollPane scrollPane = new JScrollPane(newDocument.getTextComponent());
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(scrollPane, BorderLayout.CENTER);
		
		String tooltip;
		ImageIcon icon;
		if(path != null) {
			tooltip = path.toString();
			icon = GREEN_ICON;
		} else {
			tooltip = UNNAMED;
			icon = RED_ICON;
		}

		documents.add(newDocument);
		this.addTab(fileName, icon, panel, tooltip);
		fireDocumentAdded(newDocument);

		// define listener for modification change and file path update
		newDocument.addSingleDocumentListener(new SingleDocumentListener() {
			
			private DefaultMultipleDocumentModel thisModel = DefaultMultipleDocumentModel.this;
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				thisModel.setIconAt(
						thisModel.getSelectedIndex(),
						thisModel.getCurrentDocument().isModified() ? RED_ICON : GREEN_ICON
				);
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				Path path = model.getFilePath();
				int selectedIndex = thisModel.getSelectedIndex();
				
				thisModel.setTitleAt(selectedIndex, path.getFileName().toString());
				thisModel.setToolTipTextAt(selectedIndex, path.toString());
				
				model.setModified(false);
			}
		});
		
		return newDocument;
	}
	
	/** Notifies all listeners about removed document. **/
	private void fireDocumentRemoved(SingleDocumentModel model) {
		listeners.forEach(l -> {
			l.documentRemoved(model);
		});	
	}
	
	/** Notifies all listeners about added document. **/
	private void fireDocumentAdded(SingleDocumentModel model) {
		listeners.forEach(l -> {
			l.documentAdded(model);;
		});	
	}
	
	/** Notifies all listeners about change of current document. **/
	private void fireCurrentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		listeners.forEach(l -> {
			l.currentDocumentChanged(previousModel, currentModel);
		});	
	}
	
}
