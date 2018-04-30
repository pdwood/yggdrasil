package tree;

public abstract class Rule {
	protected boolean autoCheck; // true if using this rule automatically checks off the source step
	public final String shortName;
	protected Rule(String shortName){
		this.shortName = shortName;
	}
	public static final Rule[] DECMP_RULES = {};
	public static final String[] DECMP_NAMES = { "∧","¬∧","∨","¬∨","→","¬→","↔","¬↔","¬¬" };
}
