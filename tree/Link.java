package tree;

public class Link {
	private Rule rule;
	private Step premise;
	private Step conclusion;
	
	public Link(Rule rule, Step premise, Step conclusion){
		this.rule = rule;
		this.premise = premise;
		this.conclusion = conclusion;
	}
	public Step getPremise(){ return premise; }
	public Step getConclusion(){ return conclusion; }
	public Rule getRule() { return rule; }
}
