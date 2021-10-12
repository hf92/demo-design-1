
public class AnswerSheet extends Test {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1252020563467408945L;
	private String takerID;
	
	public AnswerSheet(String testName, String takerName) {
		super(testName);
		takerID = takerName;
	}
	
	public String getTakerName() {
		return takerID;
	}
}
