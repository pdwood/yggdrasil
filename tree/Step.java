package tree;

import java.util.Set;
import logic.AbstractStatement;
import gui.TreeStepView;

public class Step {

	private AbstractStatement statement;
	private Set<Step> inlinks;
	private Set<Step> outlinks;
	private TreeStepView view;
	private TreeBranch parent;

	public AbstractStatement getStatement(){ return statement; }
	
	public boolean isChecked(){
		if(statement instanceof logic.AtomicStatement) return true;
		else if(statement instanceof logic.Conditional){
			
		} else if(statement instanceof logic.Conjunction){
			//Check if any of its outlink rules autocheck
			//Check if it has an outlink for each conjunct with the Conjunction Decomposition rule cited
			//etc
		} else if(statement instanceof logic.Negation){
			
		}
		return false;
	}
}