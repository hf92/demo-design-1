import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Choice extends Question {
    /**
     * 
     */
    private static final long serialVersionUID = 7726549883000777189L;
    private int numberOfChoices;
    private ArrayList<String> choices;
    private boolean isSingleSelection;

    public Choice() {
        super();
        numberOfChoices = 0;
        choices = new ArrayList<String>();
    }

    public int getNumberOfChoices() {
        return numberOfChoices;
    }

    public void setChoice(int i, String c) {
        choices.set(i, c);
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public String getChoice(String a) {
        if (a.length() == 1) {
            int i = a.toLowerCase().charAt(0) - 'a';
            if (0 < i && choices.size() > i) {
                return choices.get(i);
            }
        }
        return "";
    }

    public void removeChoice(int i) {
        choices.remove(i);
        numberOfChoices--;
    }

    public void addChoice(String c) {
        choices.add(c);
        numberOfChoices++;
    }

    public String getChoiceInt(int i) {
        return choices.get(i);
    }

    public void setIsSingleSelection(boolean b) {
        isSingleSelection = b;
    }

    public boolean getIsSingleSelection() {
        return isSingleSelection;
    }

    public void print(UI ui) {
        super.print(ui);

        for (int i = 0; i < numberOfChoices; i++) {
            ui.display(String.format("%s) %s  ", String.valueOf((char) ('a' + i)), choices.get(i)));
        }
        ui.display("\n");
    }

    @Override
    public void newChoices(UI ui) {
        ui.display("Enter the number of choices:");
        int i = ui.captureInt();

        for (int j = 0; j < i; j++) {
            System.out.printf("Enter a choice %s:\n", String.valueOf((char) ('a' + j)));
            addChoice(ui.captureString());
        }

        boolean repeat = true;
        while (repeat) {
            ui.display("Enter 0 for single-selection, or 1 for multi-selection : ");

            switch (ui.captureInt()) {
                case 0:
                    setIsSingleSelection(true);
                    repeat = false;
                    break;
                case 1:
                    setIsSingleSelection(false);
                    repeat = false;
                    break;
                default:
                    ui.display("Invalid choice.");
                    break;
            }
        }
    }

    @Override
    public void modifyChoices(UI ui) {
        boolean repeat = true;
        while (repeat) {
            print(ui);
            ui.display("1) Modify existing choice\n");
            ui.display("2) Remove choice\n");
            ui.display("3) Add new choice\n");
            ui.display("4) Go back to Main Menu\n");

            switch (ui.captureInt()) {
                case 1:
                    ui.display("Which choice do you wish to modify? ");
                    int i = ui.captureLetter(getNumberOfChoices());
                    ui.display("Enter the new choice text: ");
                    setChoice(i, ui.captureString());
                    break;
                case 2:
                    ui.display("Which choice do you wish to remove? ");
                    int j = ui.captureLetter(getNumberOfChoices());
                    removeChoice(j);
                    break;
                case 3:
                    ui.display("Enter the new choice:");
                    addChoice(ui.captureString());
                    break;
                case 4:
                    repeat = false;
                    break;
                default:
                    ui.display("Invalid choice.");
                    break;
            }
        }
    }

    @Override
    public void tabulateAnswers(UI ui, List<Answer> answers) {
        ui.display("\n");
        print(ui);
        HashMap<String, Integer> counterMap = new HashMap<>();

        for (Answer a : answers) {
            a.updateAnswerCounter(this, counterMap);
        }

        for (int j = 0; j < choices.size(); j++) {
            String choiceOption = String.format("%s) %s", String.valueOf((char) ('a' + j)), getChoiceInt(j));
            int total = counterMap.get(choiceOption);
            ui.display(choiceOption + " : ");
            ui.display(String.format("%d\n", total));
        }
    }
}
