package logic;

import java.util.Set;

public class Conditional extends AbstractStatement{
	private AbstractStatement consequent;
	private AbstractStatement antecedent;
	public boolean evaluate(Set<String> context){
		return !consequent.evaluate(context) || antecedent.evaluate(context);
	}
	public boolean equals(Object that){
		return (that instanceof Conditional) 
				&& (consequent.equals(((Conditional)that ).consequent) )
				&& (consequent.equals(((Conditional)that ).antecedent) );
	}
	public Conditional(AbstractStatement c, AbstractStatement a){
		this.consequent=c;
		this.antecedent=a;
	}
}