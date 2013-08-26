package germ.gui.windows;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.workspace.Project;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JLabel;



/**
 * Klasa predstavlja prozor za kreiranje novog dijagrama
 */
@SuppressWarnings("serial")
public class CreateNewDiagramWindow extends NewPodWindow {

	private JComboBox cbProjects = new JComboBox();
	private JLabel lblDiagram = new JLabel(Messages.getString("CreateNewDiagramWindow.0")); //$NON-NLS-1$
	
	public CreateNewDiagramWindow(){
		super(1);
		
		try{
			setIconImage(ImageIO.read(new File("germ/gui/windows/images/programIcon.png")));
			}catch(Exception ex){}
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getCenterPoint();
		
		initializeProjects();
		
		Container container = getContentPane();

		GridBagConstraints c00 = new GridBagConstraints();
		c00.gridx = 0;
		c00.gridy = 0;
		c00.anchor = GridBagConstraints.EAST;
		c00.insets = new Insets(20, 20, 0, 0);

		GridBagConstraints c10 = new GridBagConstraints();
		c10.gridx = 1;
		c10.gridy = 0;
		c10.weightx = 1;
		c10.fill = GridBagConstraints.HORIZONTAL;
		c10.anchor = GridBagConstraints.WEST;
		c10.insets = new Insets(20, 20, 0, 20);

		GridBagConstraints c01 = new GridBagConstraints();
		c01.gridx = 0;
		c01.gridy = 1;
		c01.anchor = GridBagConstraints.EAST;
		c01.insets = new Insets(20, 20, 0, 0);
		
		GridBagConstraints c11 = new GridBagConstraints();
		c11.gridx = 1;
		c11.gridy = 1;
		c11.weightx = 1;
		c11.fill = GridBagConstraints.HORIZONTAL;
		c11.anchor = GridBagConstraints.WEST;
		c11.insets = new Insets(20, 20, 0, 20);
		
		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 0;
		c2.gridy = 2;
		c2.gridwidth = 2;
		c2.anchor = GridBagConstraints.CENTER;
		c2.insets = new Insets(35, 0, 10, 0);

		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyEventDispatcher() {
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					escapePressed();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					enterPressed();
				}
				return false;
			}
		});

		container.add(lblNew, c00);
		container.add(cbProjects, c10);
		container.add(lblDiagram, c01);
		container.add(tfNew,c11);
		container.add(okCancelBox, c2);
		
		this.pack();
		setLocation(center.x - getSize().width / 2, center.y - getSize().height / 2);
		
	}
	
	/**
	 * Funkcija vrsi inicijalizaciju sadrzaja comboBoxa sa imenima svih postojecih projekata u trenutnom workspace-u
	 */
	public void initializeProjects(){
		int projectsCount = Application.getInstance().getProjectsCount();
		List<Project> projects = Application.getInstance().getProjects(); 		
		for(int i = 0; i != projectsCount; i++){
			cbProjects.addItem(projects.get(i));
		}
	}

	public String getProject() {
		return cbProjects.getSelectedItem().toString();
	}

	public void setCbProjects(String project) {
		cbProjects.setEditable(true);
		cbProjects.setSelectedItem(project);
		cbProjects.setEditable(false);
	}
	
	/**
	 * Funkcija onemogucava menjanje sadrzaja comboBoxa sa projektima.
	 */
	public void disableCbProjects(){
		cbProjects.setEnabled(false);
	}
	

}
