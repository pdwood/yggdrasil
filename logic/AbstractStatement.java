package logic;

import java.util.Set;

public abstract class AbstractStatement{
	abstract boolean evaluate(Set<String> context); //Currently takes a single set as context. May want to have positive and negative context in the future.
//	protected AbstractStatement(String s){
//		this.string = s;
//		
//	}
	private String string;

	public static AbstractStatement createFromString(String s){
		//Parse text...
		return null; //TODO
	}

	@Override
	public String toString() {return string;}

}