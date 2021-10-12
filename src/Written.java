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
        HashMap<String, String> totals = new HashMap<String, String>();

        for (Answer a : answers) {
            EssayAnswer essAns = (EssayAnswer) a;
            String ans = essAns.getAnswer().toLowerCase();

            if (totals.containsKey(ans)) {
                int total = Integer.parseInt(totals.get(ans));
                totals.remove(ans);
                totals.put(ans, String.format("%d", ++total));
            } else {
                totals.put(ans, String.format("%d", 1));
            }
        }

        for (String s : totals.keySet()) {
            ui.display(String.format("%s : %s\n", s, totals.get(s)));
        }
    }

}
