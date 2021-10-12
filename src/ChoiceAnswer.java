import java.util.ArrayList;
import java.util.List;

public class ChoiceAnswer extends Answer {
    /**
     * 
     */
    private static final long serialVersionUID = -3753220630891989955L;
    private List<String> answer;

    public ChoiceAnswer() {
        super();
        answer = new ArrayList<String>();
    }

    public void addAnswer(String a) {
        answer.add(a);
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void print(UI ui) {
        for (int i = 0; i < answer.toArray().length; i++) {
            ui.display(String.format("%s  ", answer.get(i)));
        }
        ui.display("\n");
    }

    @Override
    public void newAnswer(UI ui, Question q, boolean isCorrect) {
        Choice multi = (Choice) q;

        if (multi.getIsSingleSelection()) {
            ui.display("Enter your answer: ");
            int i = ui.captureLetter(multi.getNumberOfChoices());

            addAnswer(String.format("%s) %s", String.valueOf((char) ('a' + i)), multi.getChoiceInt(i)));
        } else {
            ui.display("Number of answers: ");
            int i = ui.captureInt();

            for (int n = 0; n < i; n++) {
                ui.display("Enter an answer: ");
                int j = ui.captureLetter(multi.getNumberOfChoices());

                addAnswer(String.format("%s) %s", String.valueOf((char) ('a' + j)), multi.getChoiceInt(j)));
            }
        }
    }

    @Override
    public boolean equals(Answer otherAnswer) {
        boolean isCorrect = true;
        ChoiceAnswer choice = (ChoiceAnswer) otherAnswer;

        for (String c : answer) {
            if (!(choice.getAnswer().contains(c))) {
                isCorrect = false;
            }
        }

        for (String c : choice.getAnswer()) {
            if (!(answer.contains(c))) {
                isCorrect = false;
            }
        }

        return isCorrect;
    }
}
