import java.io.Serializable;

public abstract class Answer implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6280687163475407908L;

    public abstract void print(UI ui);

    public abstract boolean equals(Answer otherAnswer);

    public abstract void newAnswer(UI ui, Question q, boolean isCorrect);

    public final void modifyAnswer(UI ui, Question q) {
        print(ui);

        boolean repeat = true;
        while (repeat) {
            ui.display("Do you wish to modify the answer? ('y' for yes or 'n' for no.) : ");

            String s = ui.captureString();
            if (s.equalsIgnoreCase("y") || s.equalsIgnoreCase("n")) {
                if (s.equalsIgnoreCase("y")) {
                    newAnswer(ui, q, true);
                }
                repeat = false;
            } else {
                ui.display("Invalid choice. Try again: ");
            }
        }
    }
}
