package logic;

import java.util.Set;

public class Disjunction extends AbstractStatement{
	public Disjunction(Set<String> s){
		
	}
	
	private Set<AbstractStatement> disjuncts;

	public boolean equals(Object o){
		return (o instanceof Disjunction) && disjuncts.equals(((Disjunction)o ).disjuncts);
	}

	public boolean evaluate(Set<String> context){
		for(AbstractStatement s : disjuncts) if (s.evaluate(context)) return true;
		return false;
	}
}