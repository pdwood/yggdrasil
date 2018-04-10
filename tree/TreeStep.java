package tree;

import java.util.Set;
import logic.AbstractStatement;
import gui.TreeStepView;

public class TreeStep {

	private AbstractStatement statement;
	private Set<TreeStep> inlinks;
	private Set<TreeStep> outlinks;
	private TreeStepView view;

}