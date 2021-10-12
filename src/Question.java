import java.io.Serializable;
import java.util.List;

public abstract class Question implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 3838710951395215594L;
    protected String prompt;

    public Question() {
        prompt = "";
    }

    public final String getPrompt() {
        return prompt;
    }

    public void print(UI ui) {
        ui.display(prompt);
        ui.display("\n");
    }

    public final void create(UI ui) {
        newPrompt(ui);
        newChoices(ui);
    }

    public final void newPrompt(UI ui) {
        ui.display("Enter the question prompt: ");
        prompt = ui.captureString();
    }

    public abstract void newChoices(UI ui);

    public final void modify(UI ui) {
        print(ui);
        modifyPrompt(ui);
        modifyChoices(ui);
    }

    public final void modifyPrompt(UI ui) {
        boolean repeat = true;
        while (repeat) {
            ui.display("Do you wish to modify the prompt? ('y' for yes or 'n' for no.) : ");

            String s = ui.captureString();
            if (s.equalsIgnoreCase("y") || s.equalsIgnoreCase("n")) {
                if (s.equalsIgnoreCase("y")) {
                    newPrompt(ui);
                }
                repeat = false;
            } else {
                ui.display("Invalid choice. Try again: ");
            }
        }
    }

    public abstract void modifyChoices(UI ui);

    public abstract void tabulateAnswers(UI ui, List<Answer> answers);

}
