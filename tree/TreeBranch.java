package tree;

import java.util.ArrayList;

import logic.*;

public class TreeBranch {

	private ArrayList<Step> steps;
	private ArrayList<TreeBranch> branches;
	
	public boolean isFinished(){
		boolean hasContradiction = false;
		boolean shouldHaveContradiction = false;
		for(Step step : steps){
			if(!step.isChecked()) return false;
			AbstractStatement s = step.getStatement();
			if(s instanceof Negation){
				for(Step step2 : steps){
					if(((Negation) s).contradicts(step2.getStatement())){
						shouldHaveContradiction = true;
					}
				}
			} else if(s == AbstractStatement.FALSE){
				hasContradiction = true;
			}
		}
		if(hasContradiction != shouldHaveContradiction) return false;
		for(TreeBranch b : branches) if (!b.isFinished()) return false;
		return true;
	}

}