package logic;

import java.util.Set;

public class Negation{
	private AbstractStatement interior;
	public boolean evaluate(Set<String> context){
		return !interior.evaluate(context);
	}
	public boolean equals(Object o){
		return (o instanceof Negation) && interior.equals(((Negation)o ).interior);
	}
}