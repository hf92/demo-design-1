import java.io.Serializable;
import java.util.ArrayList;

public class Survey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7280239886718438522L;
	private ArrayList<Question> questions;
	private String name;
	
	public Survey(String surveyName) {
		name = surveyName;
		questions = new ArrayList<Question>();
	}
	
	public void addQuestion(Question q) {
		questions.add(q);
	}
	
	public void removeQuestion(Question q){
		questions.remove(q);
	}
	
	public ArrayList<Question> getQuestions(){
		return questions;
	}

	public String getName(){
		return name;
	}
	
	public void setName(String surveyName){
		name = surveyName;
	}
}
