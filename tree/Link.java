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
	
	public static boolean createLink(Rule rule, Step premise, Step conclusion){
		Branch conBranch = conclusion.getBranch();
		Branch premBranch = premise.getBranch();
		if(conBranch == premBranch && conBranch.isBefore(conclusion, premise)) return false;
		while(conBranch != null && conBranch != premBranch) conBranch = conBranch.getParent();
		if(conBranch == null) return false;
		
		Link link = new Link(rule, premise, conclusion);
		premise.addConclusionLink(link);
		conclusion.addPremiseLink(link);
		return true;
	}
}
