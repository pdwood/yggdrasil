package tree;

import java.util.ArrayList;

import logic.*;

public class Branch {

	private Branch parent;
	private ArrayList<Step> steps;
	private ArrayList<Branch> branches;
	
	public Branch(){
		steps = new ArrayList<Step>();
		branches = new ArrayList<Branch>();
	}
	
	public Branch addBranch(){
		Branch that = new Branch();
		that.parent = this;
		branches.add(that);
		return that;
	}
	public Branch getParent(){ return parent; }
	public void addStep(Step step){
		steps.add(step);
	}
	public void removeStep(Step step){
		steps.remove(step);
	}
	public boolean isBefore(Step a, Step b){
		for(Step s : steps){
			if(s==a) return true;
			if(s==b) return false;
		}
		return false;
	}
	
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
		for(Branch b : branches) if (!b.isFinished()) return false;
		return true;
	}

	public ArrayList<Branch> getBranches(){ return branches; }

}