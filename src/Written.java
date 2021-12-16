import java.util.HashMap;
import java.util.List;

public class Written extends Question {
    /**
     * 
     */
    private static final long serialVersionUID = 808909228645285150L;
    private int length;

    public Written(int l) {
        super();
        length = l;
    }

    public int getLength() {
        return length;
    }

    @Override
    public void newChoices(UI ui) {
    }

    @Override
    public void modifyChoices(UI ui) {
    }

    @Override
    public void tabulateAnswers(UI ui, List<Answer> answers) {
        ui.display("\n");
        print(ui);
        HashMap<String, Integer> counterMap = new HashMap<>();

        for (Answer a : answers) {
            a.updateAnswerCounter(this, counterMap);
        }

        for (String s : counterMap.keySet()) {
            ui.display(String.format("%s : %d\n", s, counterMap.get(s)));
        }
    }
}
