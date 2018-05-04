package tree;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import logic.*;
import gui.StepView;
import javafx.util.Pair;

public class Step {

	private AbstractStatement statement;
	private Set<Step> premises;
	private Set<Step> conclusions;
	private StepView view;
	private Branch parent;
	private Rule origin;
	private Rule destination;
	
	public Step(StepView view, Branch parent){
		this.view = view;
		this.parent = parent;
		parent.addStep(this);
		premises = new HashSet<Step>();
		conclusions = new HashSet<Step>();
	}

	public void setStatement(String inputString){ this.statement = AbstractStatement.createFromString(inputString); System.out.println(statement); }
	public AbstractStatement getStatement(){ return statement; }
	
	public Branch getBranch(){ return parent; }
	
	public void removeFromBranch(){
		parent.removeStep(this);
	}
	
	public boolean isChecked(){
		if(statement instanceof logic.AtomicStatement || 
				(statement instanceof Negation && ((Negation)statement).interior instanceof AtomicStatement)) return true;
		//return isCheckedInBranch(parent);
		return false;
	}
	
	public Set<Step> getPremises() { return premises; }
	public Set<Step> getConclusions() { return conclusions; }
		
	public StepView getView(){ return view; }

	public Rule getOriginRule(){ return origin; }
	public Rule getDestinationRule(){ return destination; }
	
	public static boolean createLink(Rule rule, Step premise, Step conclusion){		
		Branch conBranch = conclusion.getBranch();
		Branch premBranch = premise.getBranch();
		
		boolean invert = false;
		
		if(conBranch == premBranch && conBranch.isBefore(conclusion, premise)) invert = true;
		while(conBranch != null && conBranch != premBranch) conBranch = conBranch.getParent();
		if(conBranch == null) invert = true;
		
		if(invert){
			Step temp = conclusion;
			conclusion = premise;
			premise = temp;
		}
		
		//Link link = new Link(rule, premise, conclusion);
		premise.conclusions.add(conclusion);
		conclusion.premises.add(premise);
		return !invert;
	}
	public static void removeLink(Step a, Step b){
		a.conclusions.remove(b);
		b.premises.remove(a);
		a.premises.remove(b);
		b.conclusions.remove(a);
	}


	public Pair<Set<AbstractStatement>,Set<Step>> verify(){
		AbstractStatement.printExpressionAsHierarchy(statement,"");
		System.out.println("Statement type: "+statement.getClass());
				
		if(statement instanceof logic.AtomicStatement || (statement instanceof Negation && ((Negation)statement).interior instanceof AtomicStatement)){
			return new Pair<Set<AbstractStatement>,Set<Step>> (new HashSet<AbstractStatement>(), new HashSet<Step>());
		}
		
		
		Set<AbstractStatement> remainingJuncts = null;
		Set<Step> extraneousSteps = new HashSet<Step>(conclusions);
		
		if(statement instanceof Conjunction){
			remainingJuncts = Rule.verifyDecompRule(extraneousSteps, parent,
					new HashSet<AbstractStatement>(((Conjunction)statement).getConjuncts())); 
		} else if(statement instanceof Disjunction){
			remainingJuncts = Rule.verifySplitRule(extraneousSteps, parent,
					new HashSet<AbstractStatement>(((Disjunction)statement).getDisjuncts()));  
		} else if(statement instanceof Conditional){
			Conditional cond = (Conditional) statement;
			remainingJuncts = Rule.verifySplitRule(extraneousSteps, parent,
					new HashSet<AbstractStatement>(Arrays.asList(new Negation(cond.antecedent),cond.consequent))); 
		} else if(statement instanceof Negation) {
			AbstractStatement interior = ((Negation)statement).interior;
			if(interior instanceof Conjunction){
				Set<AbstractStatement> disjuncts = new HashSet<AbstractStatement>();
				for(AbstractStatement s : ((Conjunction)interior).getConjuncts()){
					disjuncts.add(new Negation(s));
				}
				remainingJuncts = Rule.verifySplitRule(extraneousSteps, getBranch(), disjuncts); 
			} else if(interior instanceof Disjunction){
				System.out.println("NOR...");
				Set<AbstractStatement> conjuncts = new HashSet<AbstractStatement>();
				for(AbstractStatement s : ((Disjunction)interior).getDisjuncts()){
					conjuncts.add(new Negation(s));
				}
				remainingJuncts = Rule.verifyDecompRule(extraneousSteps, getBranch(), conjuncts); 
			} else if(interior instanceof Conditional){
				Conditional cond = (Conditional) interior;
				remainingJuncts = Rule.verifyDecompRule(extraneousSteps, getBranch(),
						new HashSet<AbstractStatement>(Arrays.asList(cond.antecedent, new Negation(cond.consequent)))); 
			}
		}
		
		return new Pair<Set<AbstractStatement>,Set<Step>> (remainingJuncts, extraneousSteps);
	}
	
	public boolean hasPremise(Step premise) {
		return premises.contains(premise);
	}
}