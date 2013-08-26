package germ.gui.workspace;

import germ.model.GERMModel;
import germ.model.nodes.Argument;
import germ.model.nodes.ArgumentValue;
import germ.model.nodes.Assumption;
import germ.model.nodes.Decision;
import germ.model.nodes.DecisionValue;
import germ.model.nodes.Position;
import germ.model.nodes.Requirement;
import germ.model.nodes.Stakeholder;
import germ.model.nodes.Topic;
import germ.model.workspace.Project;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

@SuppressWarnings("serial")
public class CustomIconRenderer extends DefaultTreeCellRenderer {
	static ImageIcon projectIcon;
	static ImageIcon diagramIcon;
	static ImageIcon stakeholderIcon;
	static ImageIcon argumentNeutralIcon;
	static ImageIcon argumentAffirmativeIcon;
	static ImageIcon argumentNegativeIcon;
	static ImageIcon assumptionIcon;
	static ImageIcon decisionInternalIcon;
	static ImageIcon decisionFinalIcon;
	static ImageIcon positionIcon;
	static ImageIcon topicIcon;
	static ImageIcon requirementIcon;
	
	static {
		diagramIcon = new ImageIcon(CustomIconRenderer.class
				.getResource("/germ/gui/windows/images/project.png"));
		projectIcon = new ImageIcon(CustomIconRenderer.class
				.getResource("/germ/gui/windows/images/project.png"));
		stakeholderIcon = new ImageIcon(
				CustomIconRenderer.class
						.getResource("/germ/gui/windows/images/nodeIcons/stakeholder.png"));
		argumentNeutralIcon = new ImageIcon(
				CustomIconRenderer.class
						.getResource("/germ/gui/windows/images/nodeIcons/argument-neutral.png"));
		argumentAffirmativeIcon = new ImageIcon(
				CustomIconRenderer.class
						.getResource("/germ/gui/windows/images/nodeIcons/argument-affirmative.png"));
		argumentNegativeIcon = new ImageIcon(
				CustomIconRenderer.class
						.getResource("/germ/gui/windows/images/nodeIcons/argument-negative.png"));
		assumptionIcon = new ImageIcon(
				CustomIconRenderer.class
						.getResource("/germ/gui/windows/images/nodeIcons/assumption.png"));
		decisionInternalIcon = new ImageIcon(
				CustomIconRenderer.class
						.getResource("/germ/gui/windows/images/nodeIcons/decision-internal.png"));
		decisionFinalIcon = new ImageIcon(
				CustomIconRenderer.class
						.getResource("/germ/gui/windows/images/nodeIcons/decision-final.png"));
		positionIcon = new ImageIcon(CustomIconRenderer.class
				.getResource("/germ/gui/windows/images/nodeIcons/position.png"));
		topicIcon = new ImageIcon(CustomIconRenderer.class
				.getResource("/germ/gui/windows/images/nodeIcons/topic.png"));
		requirementIcon = new ImageIcon(
				CustomIconRenderer.class
						.getResource("/germ/gui/windows/images/nodeIcons/requirement.png"));
	}

	public CustomIconRenderer() {

	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		setIcon(diagramIcon);
		if (value instanceof GERMModel) {
			setIcon(diagramIcon);
		}
		if (value instanceof Project) {
			setIcon(projectIcon);
		}

		if (value instanceof Argument) {
			Argument a = (Argument) value;
			setIcon(argumentNeutralIcon);
			if (a.getValue() == ArgumentValue.ARG_NEUTRAL)
				setIcon(argumentNeutralIcon);
			if (a.getValue() == ArgumentValue.ARG_NEGATIVE)
				setIcon(argumentNegativeIcon);
			if (a.getValue() == ArgumentValue.ARG_AFFIRMATIVE)
				setIcon(argumentAffirmativeIcon);
		}

		if (value instanceof Assumption) {
			setIcon(assumptionIcon);
		}

		if (value instanceof Decision) {
			Decision d = (Decision) value;
			setIcon(decisionInternalIcon);
			if (d.getValue() == DecisionValue.FINAL_DECISION) 
				setIcon(decisionFinalIcon);
			if (d.getValue() == DecisionValue.INTERNAL_DECISION)
				setIcon(decisionInternalIcon);
		}

		if (value instanceof Position) {
			setIcon(positionIcon);
		}

		if (value instanceof Topic) {
			setIcon(topicIcon);
		}

		if (value instanceof Requirement) {
			setIcon(requirementIcon);
		}

		if (value instanceof Stakeholder) {
			setIcon(stakeholderIcon);
		}
		return this;
	}
}
