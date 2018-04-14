package logic;

import java.util.Set;

public class AtomicStatement extends AbstractStatement{
	
	public AtomicStatement(char c){
		
	}
	
	public boolean evaluate(Set<String> context){
		return context.contains(this.toString());
	}
	
	@Override
	public boolean equals(Object that){
		return (that instanceof AtomicStatement && that.toString() == this.toString());
	}
}
