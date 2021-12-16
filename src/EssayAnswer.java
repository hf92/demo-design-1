import java.util.HashMap;

public class EssayAnswer extends Answer {
    /**
     * 
     */
    private static final long serialVersionUID = -7661942985734422004L;
    private String answer;

    public EssayAnswer() {
        super();
        answer = "";
    }

    public void setAnswer(String a) {
        answer = a;
    }

    public String getAnswer() {
        return answer;
    }

    public void print(UI ui) {
        ui.display(answer);
        ui.display("\n");
    }

    @Override
    public void newAnswer(UI ui, Question q, boolean isCorrect) {
        Written write = (Written) q;

        if (isCorrect) {
            if (write.getLength() < 500) {
                ui.display("Enter answer:\n");
                setAnswer(ui.captureString());
            }
        } else {
            boolean repeat = true;

            while (repeat) {
                ui.display("Enter answer:\n");

                String a = ui.captureString();

                if (a.length() < write.getLength()) {
                    setAnswer(a);
                    repeat = false;
                } else {
                    ui.display("Answer too long.\n");
                }
            }
        }
    }

    @Override
    public boolean equals(Answer otherAnswer) {
        boolean isCorrect = true;
        EssayAnswer essay = (EssayAnswer) otherAnswer;

        if (!(essay.getAnswer().equalsIgnoreCase(answer))) {
            isCorrect = false;
        }

        return isCorrect;
    }

    @Override
    public void updateAnswerCounter(Question q, HashMap<String, Integer> counterMap) {
        String ans = this.getAnswer().toLowerCase();

        if (counterMap.containsKey(ans)) {
            int total = counterMap.get(ans);
            counterMap.put(ans, ++total);
        } else {
            counterMap.put(ans, 1);
        }
    }

}
