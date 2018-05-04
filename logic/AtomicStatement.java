package logic;

import java.util.Set;

public class AtomicStatement extends AbstractStatement{
	
	public AtomicStatement(String s){
		super(s);
		this.hash = s.hashCode();
	}
	
	public boolean evaluate(Set<String> context){
		return context.contains(this.toString());
	}
	
	@Override
	public boolean equals(Object that){
		return (that instanceof AtomicStatement && that.toString().equals(this.toString()));
	}
}
