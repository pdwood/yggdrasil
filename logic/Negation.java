package logic;

import java.util.Set;

public class Negation extends AbstractStatement{
	private AbstractStatement interior;
	
	public Negation(AbstractStatement interior){
		this.interior = interior;
	}
	
	public boolean evaluate(Set<String> context){
		return !interior.evaluate(context);
	}
	public boolean equals(Object o){
		return (o instanceof Negation) && interior.equals(((Negation)o ).interior);
	}
	
	public AbstractStatement getInterior(){return interior;}
	
	public boolean contradicts(AbstractStatement that){
		return that.equals(interior);
	}
}