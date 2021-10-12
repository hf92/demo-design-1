
import java.util.HashMap;

public class Test extends Survey{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5446517942374969778L;
	private HashMap<Question,Answer> answers;
	
	public Test(String testName) {
		super(testName);
		answers = new HashMap<Question,Answer>();
	}
	
	public void addAnswer(Question q, Answer a) {
		answers.put(q, a);
	}
	
	public void removeAnswer(Question q){
		answers.remove(q);
	}
	
	public Answer getAnswer(Question q){
		return answers.get(q);
	}
}
