package germ.actions;

import germ.i18n.Messages;
import germ.util.URLOpener;

import java.awt.event.ActionEvent;
import java.io.File;

@SuppressWarnings("serial")
public class HelpAction extends AbstractGERMAction {

    public HelpAction() {
        putValue(NAME, Messages.getString("MainWindow.16"));  
    }
    
	public void actionPerformed(ActionEvent arg0) {
		URLOpener.openURL("doc" + File.separator + "index.html");
	}

}
