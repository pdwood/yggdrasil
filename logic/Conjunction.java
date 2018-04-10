package logic;

import java.util.Set;

public class Conjunction{
	private Set<AbstractStatement> conjuncts;

	public boolean equals(Object o){
		return (o instanceof Conjunction) && conjuncts.equals(((Conjunction)o ).conjuncts);
	}

	public boolean evaluate(Set<String> context){
		for(AbstractStatement s : conjuncts) if (!s.evaluate(context)) return false;
		return true;
	}
}