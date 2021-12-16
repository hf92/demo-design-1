
import java.util.HashMap;

public class MatchingAnswer extends Answer {

    /**
     * 
     */
    private static final long serialVersionUID = -8014221085180630379L;
    private HashMap<String, String> answer;

    public MatchingAnswer() {
        super();
        answer = new HashMap<String, String>();
    }

    public void setAnswer(String left, String right) {
        answer.put(left, right);
    }

    public HashMap<String, String> getAnswer() {
        return answer;
    }

    public void print(UI ui) {
        for (String key : answer.keySet()) {
            ui.display(String.format("%s : %s\n", key, answer.get(key)));
        }
    }

    @Override
    public void newAnswer(UI ui, Question q, boolean isCorrect) {
        Match match = (Match) q;

        for (int n = 0; n < match.getNumberOfChoices(); n++) {
            ui.display(String.format("Enter the match for %s: ", String.valueOf((char) ('a' + n))));
            int j = ui.captureLetter(match.getNumberOfChoices());

            String l = String.format("%s) %s", String.valueOf((char) ('a' + n)), match.getLeftChoiceInt(n));
            String r = String.format("%s) %s", String.valueOf((char) ('a' + j)), match.getRightChoiceInt(j));

            setAnswer(l, r);
        }
    }

    @Override
    public boolean equals(Answer otherAnswer) {
        boolean isCorrect = true;
        MatchingAnswer match = (MatchingAnswer) otherAnswer;

        for (String c : answer.keySet()) {
            if (match.getAnswer().containsKey(c)) {
                if (!(match.getAnswer().get(c).equalsIgnoreCase(answer.get(c)))) {
                    isCorrect = false;
                }
            } else {
                isCorrect = false;
            }
        }

        return isCorrect;
    }

    @Override
    public void updateAnswerCounter(Question q, HashMap<String, Integer> counterMap) {
        for (String left : this.answer.keySet()) {
            String right = this.answer.get(left);
            String match = left + '-' + right;
            Integer count = counterMap.get(match);
            if (count != null) {
                counterMap.put(match, count++);
            } else {
                counterMap.put(match, 1);
            }
        }
    }
}