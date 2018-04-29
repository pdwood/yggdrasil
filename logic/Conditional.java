package logic;

import java.util.Set;

public class Conditional extends AbstractStatement{
	public final AbstractStatement consequent;
	public final AbstractStatement antecedent;
	public boolean evaluate(Set<String> context){
		return !antecedent.evaluate(context) || consequent.evaluate(context);
	}
	public boolean equals(Object that){
		return (that instanceof Conditional) 
				&& (consequent.equals(((Conditional)that ).consequent) )
				&& (antecedent.equals(((Conditional)that ).antecedent) );
	}
	public Conditional(AbstractStatement a, AbstractStatement c){
		this.antecedent=a;
		this.consequent=c;
	}
}