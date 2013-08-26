package germ.command;

/**
 * Svaka komand implementira ovaj intefrace i 
 * njegove instance se čuvaju na undo-redo stacku.
 * Svaka komanda zna sebe da uradi u da se "un-uradi"
 */
public interface Command {
	void doCommand();
	void undoCommand();
}
