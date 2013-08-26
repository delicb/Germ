package germ.model.workspace;

import germ.configuration.InternalConfiguration;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.event.UpdateListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Project {

	private ArrayList<GERMModel> diagrams = new ArrayList<GERMModel>();
	private String name;
	private File projectDir;

	public Project(String name) {
		this.name = name;
	}

	public Project(String name, File projectDir, UpdateListener treeListener) {
		this.name = name;
		this.projectDir = projectDir;
			for (String d : projectDir.list()) {
			File diagram;
			try {
				diagram = new File(projectDir.getCanonicalPath()
						+ File.separator + d);
				// samo nas fajlovi sa nasom extenzijom zanimaju
				if (!diagram.isDirectory()
						&& d.endsWith("." //$NON-NLS-1$
								+ InternalConfiguration.DIAGRAM_EXTENSION)
						&& d.split("\\.")[1] //$NON-NLS-1$
								.equals(InternalConfiguration.DIAGRAM_EXTENSION)) {
					GERMModel m = new GERMModel(d.split("\\.")[0], this); //$NON-NLS-1$
					m.load();
					addDiagram(m);
					m.addUpdateListener(treeListener);
				}
			} catch (IOException e) {
				System.err.println(Messages.getString("Project.3")); //$NON-NLS-1$
				e.printStackTrace();
			}
		}
		// ako projekat nema dijagrama, pravimo defaultni
		if (getDiagramCount() == 0) {
			GERMModel m = new GERMModel(Messages.getString("Project.4"), this); //$NON-NLS-1$
			m.save();
			addDiagram(m);
			m.addUpdateListener(treeListener);
		}
	}

	public Project(String name, File projectDir, ArrayList<GERMModel> diagrams) {
		this.name = name;
		this.projectDir = projectDir;
		this.diagrams = diagrams;
	}

	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void addDiagram(GERMModel diagram) {
		diagrams.add(diagram);
	}
	
	public void deleteDiagram(GERMModel diagram){
		diagrams.remove(diagram);
	}
	
	public void deleteDiagramIndex(int index){
		diagrams.remove(index);
	}

	public GERMModel getDiagram(int index) {
		return diagrams.get(index);
	}

	public int getDiagramIndex(GERMModel diagram) {
		return diagrams.indexOf(diagram);
	}

	public int getDiagramCount() {
		return diagrams.size();
	}

	public File getProjectDir() {
		return this.projectDir;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Menja ime projekta i menja ime direktorijuma u koji je projekat sacuvan
	 * 
	 * @param newName
	 *            novo Ime projekta
	 */
	public void setProjectDir(String newName) {
		try {
			String oldProjectDir = this.projectDir.getCanonicalPath();
			String newProjectDir = oldProjectDir.substring(0, oldProjectDir
					.lastIndexOf(File.separator))
					+ File.separator + newName;
			this.projectDir.renameTo(new File(newProjectDir));
			this.setName(newName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
