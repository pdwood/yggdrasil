import logic.*;

public class SomeTests {

	public static void main(String[] args) {
		printExpressionAsHierarchy(AbstractStatement.createFromString("P&(Q|!R)&!(P$Q)"),"");
	}

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
