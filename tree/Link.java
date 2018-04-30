package tree;

public class Link {
	private Rule rule;
	private Step premise;
	private Step conclusion;
	
	private Link(Rule rule, Step premise, Step conclusion){
		this.rule = rule;
		this.premise = premise;
		this.conclusion = conclusion;
	}
	public Step getPremise(){ return premise; }
	public Step getConclusion(){ return conclusion; }
	public Rule getRule() { return rule; }
	
	public static void createLink(Rule rule, Step premise, Step conclusion){
		Link link = new Link(rule, premise, conclusion);
		premise.addConclusionLink(link);
		conclusion.addPremiseLink(link);
	}
}
