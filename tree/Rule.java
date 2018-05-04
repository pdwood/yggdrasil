package tree;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import logic.AbstractStatement;

public abstract class Rule {
	protected boolean autoCheck; // true if using this rule automatically checks off the source step
	public final String shortName;
	protected Rule(String shortName){
		this.shortName = shortName;
	}
	public static final Rule[] DECMP_RULES = {};
	public static final String[] DECMP_NAMES = { "∧","¬∧","∨","¬∨","→","¬→","↔","¬↔","¬¬" };
	
	public static final Rule TREE_DECOMP = new Rule("decmp"){};
	public static final Rule PREMISE = new Rule("premise"){};
	public static final Rule CONTRADICTION = new Rule("contra"){};
	
	
	//Returns set of juncts that are not decomposed
	public static Set<AbstractStatement> verifyDecompRule(Set<Step> conclusions, Branch branch, Set<AbstractStatement> conjuncts){
		Iterator<Step> it = conclusions.iterator();
		while(it.hasNext()){
			Step s = it.next();
			if(s.getBranch() == branch && conjuncts.contains(s.getStatement())){
				conjuncts.remove(s.getStatement());
				it.remove();
			}
		}
		//if(conjuncts.isEmpty() && conclusions.isEmpty());
		//else
		if(branch.getBranches().isEmpty()) return conjuncts; // return false; // some conjuncts remain, but no branches left
		
		Set<AbstractStatement> remainingJuncts = new HashSet<AbstractStatement>();

		for(Branch b : branch.getBranches()){
			remainingJuncts.addAll(verifyDecompRule(conclusions, b, new HashSet<AbstractStatement>(conjuncts)));
		}
		return remainingJuncts;
	}

	public static Set<AbstractStatement> verifySplitRule(Set<Step> conclusions, Branch branch, Set<AbstractStatement> disjuncts){
		return null;
	}
}
