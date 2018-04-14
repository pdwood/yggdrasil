package logic;

import java.util.Set;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Operators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public abstract class AbstractStatement{
	abstract boolean evaluate(Set<String> context); //Currently takes a single set as context. May want to have positive and negative context in the future.
//	protected AbstractStatement(String s){
//		this.string = s;
//		
//	}
	private String string;

	public static final AbstractStatement FALSE = new AbstractStatement(){
		@Override
		boolean evaluate(Set<String> context) {
			return false;
		}		
	};

	public static final AbstractStatement TRUE = new AbstractStatement(){
		@Override
		boolean evaluate(Set<String> context) {
			return false;
		}		
	};
	
	static final HashSet<Character> OPERATORS = new HashSet<Character> ( Arrays.asList('&','|','$','%') );
	
	public static AbstractStatement createFromString(String s){
		
		//Strip parentheses surrounding the entire expression.
		int outerParens = 0;
		while(s.charAt(outerParens)=='(' && s.charAt(s.length()-1-outerParens)==')')++outerParens;
		int parens = 0;
		int minParens = 0;
		for(int i=0;i<s.length(); ++i){
			if(s.charAt(i)=='(') ++parens;
			else if(s.charAt(i)==')') --parens;
			if(parens < minParens) minParens = parens;
		}
		outerParens += minParens; //don't strip parens that aren't actually exterior
		if(outerParens > 0) s = s.substring(outerParens, s.length()-1-outerParens);
		//Parse text...
		
		parens = 0;
		int parenStart = 0;
		ArrayList<AbstractStatement> juncts = new ArrayList<AbstractStatement>(); 
		char operator = 0;
		for ( int i=0; i<s.length(); ++i ){
			char c = s.charAt(i);
			if (c == '('){
				if(parens == 0) parenStart = i;
				parens += 1;				
			}else if (c == ')'){
				parens -= 1;
				if(parens == 0) {
					juncts.add(createFromString(s.substring(parenStart+1, i)));
				}
			}
			else if(parens == 0){
				if(OPERATORS.contains(c)){
					if(operator == 0) operator = c;
					if(c != operator) return null; //or, throw new SyntaxException("Ambiguous form in statement "+s);					
				}else{ //atomic statement
					AbstractStatement as = new AtomicStatement(c);
					as.string = c+"";
					juncts.add(as);
					
				}
			}
		}
		AbstractStatement out;
		switch(operator){
		case 0:
			out = juncts.get(0);
			break;
		case '!':
			if(juncts.size() == 1) out = new Negation(juncts.get(0));
			else return null;
		case '&':
			out = new Conjunction(new HashSet<AbstractStatement>(juncts));
			break;
		case '|':
			out = new Disjunction(new HashSet<AbstractStatement>(juncts));
			break;
		case '$':
			if(juncts.size() == 2) out = new Conditional(juncts.get(0), juncts.get(1));
			else return null;
			break;
		default:
			out = null;	
		}
		if(out != null) out.string = s;
		return out;
	}

	@Override
	public String toString() {return string;}

}