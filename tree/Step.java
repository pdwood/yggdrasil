package tree;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import logic.*;
import gui.StepView;

public class Step {

	private AbstractStatement statement;
	private Set<Link> premises;
	private Set<Link> conclusions;
	private StepView view;
	private Branch parent;
	private Rule origin;
	
	public Step(StepView view, Branch parent){
		this.view = view;
		this.parent = parent;
		parent.addStep(this);
		premises = new HashSet<Link>();
		conclusions = new HashSet<Link>();
	}

	public void setStatement(String inputString){ this.statement = AbstractStatement.createFromString(inputString); }
	public AbstractStatement getStatement(){ return statement; }
	
	public Branch getBranch(){ return parent; }
	
	public void removeFromBranch(){
		parent.removeStep(this);
	}
	
	public boolean isChecked(){
		if(statement instanceof logic.AtomicStatement || 
				(statement instanceof Negation && ((Negation)statement).interior instanceof AtomicStatement)) return true;
		return isCheckedInBranch(parent);
	}
	
	public Set<Link> getPremises() { return premises; }
	public Set<Link> getConclusions() { return conclusions; }
	
	public void addPremiseLink(Link link){ premises.add(link); }
	public void addConclusionLink(Link link){ conclusions.add(link); }
	
	public StepView getView(){ return view; }

	public Rule getOriginRule(){ return origin; }
	
	public boolean isCheckedInBranch(Branch branch){
		// For reasons of time constraints, we are only going to have the standard truth tree rules.
		// Yggdrasil will be extensible with more rules, after this function and a few others are rewritten.
		if(statement instanceof Conjunction){
			Set<AbstractStatement> conjuncts = new HashSet<AbstractStatement>(((Conjunction)statement).getConjuncts());
			return decompRuleCheckedInBranch(branch, conjuncts); 
		} else if(statement instanceof Disjunction){
			Set<AbstractStatement> disjuncts = new HashSet<AbstractStatement>(((Disjunction)statement).getDisjuncts());
			return splitRuleCheckedInBranch(branch, disjuncts); 
		} else if(statement instanceof Conditional){
			Conditional cond = (Conditional) statement;
			Set<AbstractStatement> disjuncts = new HashSet<AbstractStatement>(Arrays.asList(new Negation(cond.antecedent),cond.consequent));
			return splitRuleCheckedInBranch(branch, disjuncts); 
		} else if(statement instanceof Negation) {
			AbstractStatement interior = ((Negation)statement).interior;
			if(interior instanceof Conjunction){
				Set<AbstractStatement> disjuncts = new HashSet<AbstractStatement>();
				for(AbstractStatement s : ((Conjunction)interior).getConjuncts()){
					disjuncts.add(new Negation(s));
				}
				return splitRuleCheckedInBranch(branch, disjuncts); 
			} else if(interior instanceof Disjunction){
				Set<AbstractStatement> conjuncts = new HashSet<AbstractStatement>();
				for(AbstractStatement s : ((Disjunction)interior).getDisjuncts()){
					conjuncts.add(new Negation(s));
				}
				return decompRuleCheckedInBranch(branch, conjuncts); 
			} else if(interior instanceof Conditional){
				Conditional cond = (Conditional) interior;
				Set<AbstractStatement> conjuncts = new HashSet<AbstractStatement>(Arrays.asList(cond.antecedent, new Negation(cond.consequent)));
				return decompRuleCheckedInBranch(branch, conjuncts); 
			}
		}
		//none of the above?
		return true;// ? or false?
	}
	
	public boolean splitRuleCheckedInBranch(Branch branch, Set<AbstractStatement> disjuncts){
		return false; //TODO
	}
	
	public boolean decompRuleCheckedInBranch(Branch branch, Set<AbstractStatement> conjuncts){
		for(Link l : conclusions){
			if(l.getConclusion().parent == branch && conjuncts.contains(l.getConclusion().statement)){
				conjuncts.remove(l.getConclusion().statement);
			}
		}
		if(conjuncts.isEmpty()) return true;
		else if(branch.getBranches().isEmpty()) return false; // some conjuncts remain, but no branches left
		
		for(Branch b : branch.getBranches()){
			if(!decompRuleCheckedInBranch(b, new HashSet<AbstractStatement>(conjuncts))) return false;
		}
		return true;
	}
}