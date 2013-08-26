package germ.actions;

import germ.configuration.ConfigurationManager;
import germ.i18n.Messages;

import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Abstraktna klasa koju nasledjuju sve akcije u GERM aplikaciji.
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractGERMAction extends AbstractAction {


	/**
	 * Kreira ikonu na osnovu naziva fajla zadatog relativno u odnosu na ovu klasu
	 * @param fileName Naziv fajla slike sa relativnom putanjom
	 * @return
	 */
	public Icon loadIcon(String fileName) {
		URL imageURL = getClass().getResource("images/" +(String)ConfigurationManager.getInstance().getConfigParameter("theme")+fileName); //$NON-NLS-1$ //$NON-NLS-2$
		Icon icon = null;
		
		if (imageURL != null) {                      
	        icon = new ImageIcon(imageURL);
	    } else {                                     
	        System.err.println(Messages.getString("AbstractGERMAction.2") + fileName); //$NON-NLS-1$
	    }

		return icon;
	}
	
	
}
