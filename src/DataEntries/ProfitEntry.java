package DataEntries;


public class ProfitEntry {
	
	public ProfitEntry(int bRule, String bRuleText, String concern0, String concern1) {
		super();
		this.bRule = bRule;
		this.bRuleText = bRuleText;
		this.concern0 = concern0;
		this.concern1 = concern1;
	}
	int bRule;
	public int getbRule() {
		return bRule;
	}
	public void setbRule(int bRule) {
		this.bRule = bRule;
	}
	public String getbRuleText() {
		return bRuleText;
	}
	public void setbRuleText(String bRuleText) {
		this.bRuleText = bRuleText;
	}
	public String getConcern0() {
		return concern0;
	}
	public void setConcern0(String concern0) {
		this.concern0 = concern0;
	}
	public String getConcern1() {
		return concern1;
	}
	public void setConcern1(String concern1) {
		this.concern1 = concern1;
	}
	String bRuleText;
	String concern0;
	String concern1;
	
}
