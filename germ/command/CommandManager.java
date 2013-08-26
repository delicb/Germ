package germ.command;

import germ.actions.ActionManager;
import germ.app.Application;
import germ.configuration.ConfigurationManager;
import germ.model.GERMModel;

import java.util.Stack;

/**
 * Klasa za smestanje svih urađenih komadi.
 */
public class CommandManager {
	/**
	 * Stack na koji se smestaju sve komande koje mogu da se poniste *UNDO).
	 */
	private Stack<Command> undoStack = new Stack<Command>();

	/**
	 * Stack na koji se smestaju sve komande koje su ponistene da bi
	 * ponistavanje moglo da se ponisti :) (REDO)
	 */
	private Stack<Command> redoStack = new Stack<Command>();

	/**
	 * Maximalna velicina stacka
	 */
	private int maximumStackSize;

	/**
	 * Model na koji se ovaj command manager odnosi
	 */
	GERMModel model;

	/**
	 * Inicijalizuje velicinu stackova parametrom iz configuration managera
	 */
	public CommandManager(GERMModel model) {
		maximumStackSize = Integer.parseInt((String) ConfigurationManager
				.getInstance().getConfigParameter("undoRedoStackSize"));
		this.model = model;
	}

	/**
	 * Izvisava komandu koja je prosledjena i smesta je na undo stack
	 * 
	 * @param c
	 *            Komanda koja treba da se izvrsi
	 */
	public void doCommand(Command c) {
		c.doCommand();
		if (undoStack.size() == maximumStackSize)
			undoStack.remove(0);

		undoStack.push(c);
		redoStack.clear(); // na izvrsavanje nove akcije ne moze vise da se
		// uradi redo
		// prethodnih undo-ova (ovako se valjda ponasaju ostali programi)
		refreshUndoRedoButtons(Application.getInstance());
		this.model.commandExecuted();
	}

	/**
	 * Uzima poslednju izvrsenu komandu i poziva njenu undo metodu
	 */
	public void undoCommand() {
		if (undoStack.size() > 0) {
			Command c = undoStack.pop();
			c.undoCommand();
			if (redoStack.size() == maximumStackSize) {
				redoStack.remove(0);
			}
			redoStack.push(c);
			refreshUndoRedoButtons(Application.getInstance());
			this.model.commandExecuted();
		}
	}

	/**
	 * Uzima poslenju ponistenu komandu i poziva njenu do metodu.
	 */
	public void redoCommand() {
		if (redoStack.size() > 0) {
			Command c = redoStack.pop();
			c.doCommand();
			if (undoStack.size() == maximumStackSize) {
				undoStack.remove(0);
			}
			undoStack.push(c);
			refreshUndoRedoButtons(Application.getInstance());
			this.model.commandExecuted();
		}
	}

	/**
	 * Uklanja poslednju commandu sa undo stacka
	 */
	public void removeLastCommand() {
		undoStack.pop();
		refreshUndoRedoButtons(Application.getInstance());
	}

	/**
	 * Vraca broj izvrsenih komandu. Broj izvrsenih komandi je velicina
	 * undoStack-a, jer u izvrsene komande ne racunamo one koje su undo-ovane, a
	 * one se nalaze u redoStack-u.
	 * 
	 * @return Broj izvrsenih komandi
	 */
	public int getCommandsCount() {
		return undoStack.size();
	}

	public void refreshUndoRedoButtons(Application app) {
		ActionManager am = app.getActionManager();
		if (undoStack.size() == 0)
			am.getUndoAction().setEnabled(false);
		else
			am.getUndoAction().setEnabled(true);
		if (redoStack.size() == 0)
			am.getRedoAction().setEnabled(false);
		else
			am.getRedoAction().setEnabled(true);
	}
}
