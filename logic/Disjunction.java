package logic;

import java.util.Set;

public class Disjunction extends AbstractStatement{
	public Disjunction(Set<AbstractStatement> disjuncts){
		this.disjuncts = disjuncts;
		for(AbstractStatement as : disjuncts) this.hash += as.hashCode();
		hash *= 41;
	}
	
	private Set<AbstractStatement> disjuncts;

	public boolean equals(Object o){
		return (o instanceof Disjunction) && disjuncts.equals(((Disjunction)o ).disjuncts);
	}

	public boolean evaluate(Set<String> context){
		for(AbstractStatement s : disjuncts) if (s.evaluate(context)) return true;
		return false;
	}
	
	public Set<AbstractStatement> getDisjuncts(){ return disjuncts; }
}