package logic;

import java.util.Set;

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
	public AbstractStatement(String string){
		this.string = string;
	}
	public AbstractStatement(){}
	
	protected int hash;
	
	public int hashCode(){
		return hash;
	}
	
	public static final AbstractStatement FALSE = new AbstractStatement("⊥"){

		@Override
		boolean evaluate(Set<String> context) {
			return false;
		}
	};

	public static final AbstractStatement TRUE = new AbstractStatement(""){
		@Override
		boolean evaluate(Set<String> context) {
			return false;
		}		
	};

	static{
		FALSE.hash = 1;
	}
	
	static final HashSet<Character> OPERATORS = new HashSet<Character> ( Arrays.asList('∧','∨','→','%') );

	private static String stripOuterParens(String s){
		int outerParens = 0;
		while(s.charAt(outerParens)=='(' && s.charAt(s.length()-1-outerParens)==')')++outerParens;
		int parens = 0;
		int minParens = 0;
		for(int i=outerParens;i<s.length()-outerParens; ++i){
			if(s.charAt(i)=='(') ++parens;
			else if(s.charAt(i)==')') --parens;
			if(parens < minParens) minParens = parens;
		}
		outerParens += minParens; //don't strip parens that aren't actually exterior
		return s.substring(outerParens, s.length()-outerParens);
	}
	
	private static String enclosedParenString(String s, int start){
		if(s.charAt(start)!='(') return s.substring(start,start+1);
		int parens = 1;
		int length = 1;
		while(parens>0 && start + length < s.length()){
			char c = s.charAt(start + length);
			if(c == '(') ++parens;
			else if(c==')') --parens;
			++length;
		}
		return s.substring(start, start+length);
	}

	public static AbstractStatement createFromString(String s){
		System.out.println("Creating from: "+s);

		//Strip parentheses surrounding the entire expression.
		s = stripOuterParens(s);
		System.out.println("After parens stripped: "+s);
		if(s.length() == 1 && s.charAt(0)=='⊥') return FALSE;

		ArrayList<AbstractStatement> juncts = new ArrayList<AbstractStatement>(); 
		char operator = 0;
		for ( int i=0; i<s.length(); ++i ){
			char c = s.charAt(i);
			if (c == '('){
				String parenString = enclosedParenString(s,i);
				juncts.add(createFromString(parenString));
				i+=parenString.length()-1;
			}
			else if(c == '¬'){
				i+=1;
				String interiorString = enclosedParenString(s,i);//either 1 char or something enclosed by parens
				juncts.add(new Negation(createFromString(interiorString)));
				i+=interiorString.length();
			}else if(OPERATORS.contains(c)){
				if(operator == 0) operator = c;
				if(c != operator) return null; //or, throw new SyntaxException("Ambiguous form in statement "+s);					
			}else{ //atomic statement
				AbstractStatement as = new AtomicStatement(c+"");
				juncts.add(as);

			}
		}
		AbstractStatement out;
		switch(operator){
		case 0:
			out = juncts.get(0);
			break;
		case '∧':
			out = new Conjunction(new HashSet<AbstractStatement>(juncts));
			break;
		case '∨':
			out = new Disjunction(new HashSet<AbstractStatement>(juncts));
			break;
		case '→':
			if(juncts.size() == 2) out = new Conditional(juncts.get(0), juncts.get(1));
			else return null;
			break;
		default:
			out = null;	
		}
		if(out != null) out.string = s;
		return out;
	}
	
	/*public static AbstractStatement createFromString(String s){
		String newS = "";
		for(int i=0;i<s.length();++i){
			switch(s.charAt(i)){
			case '&': newS += '∧'; break;
			case '|': newS += '∨'; break;
			case '~': newS += '¬'; break;
			case '$': newS += '→'; break;
			case '%': newS += '↔'; break;
			case '^': newS += '⊥'; break;
			default: newS += s.charAt(i);
			}
		}
		return createFromTransformedString(newS);
	}*/

	@Override
	public String toString() {return string;}

	

	public static void printExpressionAsHierarchy(AbstractStatement as, String pad){
		pad += '\t';
		System.out.print(pad);
		if(as instanceof AtomicStatement) System.out.println(as);
		else if(as instanceof Conjunction){
			System.out.println("and");
			for(AbstractStatement as2 : ((Conjunction)as).getConjuncts()){
				printExpressionAsHierarchy(as2, pad);
			}
		}else if(as instanceof Disjunction){
			System.out.println("or");
			for(AbstractStatement as2 : ((Disjunction)as).getDisjuncts()){
				printExpressionAsHierarchy(as2, pad);
			}
		}else if(as instanceof Negation){
			System.out.println("not");
			printExpressionAsHierarchy(((Negation)as).getInterior(),pad);
		}else if(as instanceof Conditional){
			System.out.println("if");
			printExpressionAsHierarchy(((Conditional)as).antecedent,pad);
			printExpressionAsHierarchy(((Conditional)as).consequent,pad);
		}else if(as == AbstractStatement.FALSE) System.out.println("false");
		else if(as == AbstractStatement.TRUE) System.out.println("true");
	}
}