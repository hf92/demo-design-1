import java.util.ArrayList;
import java.util.List;

public class Match extends Question {
    /**
     * 
     */
    private static final long serialVersionUID = -5007295469547914604L;
    private int numberOfChoices;
    private ArrayList<String> leftChoices;
    private ArrayList<String> rightChoices;

    public Match() {
        super();
        numberOfChoices = 0;
        leftChoices = new ArrayList<String>();
        rightChoices = new ArrayList<String>();
    }

    public int getNumberOfChoices() {
        return numberOfChoices;
    }

    public void setLeftChoice(int i, String c) {
        leftChoices.set(i, c);
    }

    public void setRightChoice(int i, String c) {
        rightChoices.set(i, c);
    }

    public String getLeftChoice(String a) {
        for (int i = 0; i < 26; i++) {
            if (String.valueOf((char) ('a' + i)).equals(a.toLowerCase())) {
                return leftChoices.get(i);
            }
        }
        return "";
    }

    public String getRightChoice(String a) {
        for (int i = 0; i < 26; i++) {
            if (String.valueOf((char) ('a' + i)).equals(a)) {
                return rightChoices.get(i);
            }
        }
        return "";
    }

    public String getLeftChoiceInt(int i) {
        return leftChoices.get(i);
    }

    public String getRightChoiceInt(int i) {
        return rightChoices.get(i);
    }

    public void removeChoice(int l, int r) {
        leftChoices.remove(l);
        rightChoices.remove(r);
        numberOfChoices--;
    }

    public void addChoice(String left, String right) {
        leftChoices.add(left);
        rightChoices.add(right);
        numberOfChoices++;
    }

    public void print(UI ui) {
        super.print(ui);

        for (int i = 0; i < numberOfChoices; i++) {
            ui.display(String.format("%s) %s\t| %s) %s\n", String.valueOf((char) ('a' + i)), leftChoices.get(i),
                    String.valueOf((char) ('a' + i)), rightChoices.get(i)));
        }
        ui.display("\n");
    }

    @Override
    public void newChoices(UI ui) {
        ui.display("Enter the number of choices:");
        int i = ui.captureInt();

        for (int j = 0; j < i; j++) {
            ui.display(String.format("Enter a left choice %s:\n", String.valueOf((char) ('a' + j))));
            String l = ui.captureString();

            ui.display(String.format("Enter a right choice %s:\n", String.valueOf((char) ('a' + j))));
            String r = ui.captureString();

            addChoice(l, r);
        }
    }

    @Override
    public void modifyChoices(UI ui) {
        boolean repeat = true;
        while (repeat) {
            print(ui);
            ui.display("1) Modify existing left choice\n");
            ui.display("2) Modify existing right choice\n");
            ui.display("3) Remove match\n");
            ui.display("4) Add new match\n");
            ui.display("5) Go back to Main Menu\n");

            switch (ui.captureInt()) {
                case 1:
                    ui.display("Which choice do you wish to modify? ");
                    int i = ui.captureLetter(getNumberOfChoices());
                    ui.display("Enter the new choice text: ");
                    setLeftChoice(i, ui.captureString());
                    break;
                case 2:
                    ui.display("Which choice do you wish to modify? ");
                    int j = ui.captureLetter(getNumberOfChoices());
                    ui.display("Enter the new choice text: ");
                    setRightChoice(j, ui.captureString());
                    break;
                case 3:
                    ui.display("Which left choice do you wish to remove? ");
                    int l = ui.captureLetter(getNumberOfChoices());
                    ui.display("Which right choice do you wish to remove? ");
                    int r = ui.captureLetter(getNumberOfChoices());
                    removeChoice(l, r);
                    break;
                case 4:
                    ui.display("Enter the new left choice: ");
                    String left = ui.captureString();
                    ui.display("Enter the new right choice: ");
                    String right = ui.captureString();
                    addChoice(left, right);
                    break;
                case 5:
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
        for (int j = 0; j < numberOfChoices; j++) {
            String choiceOption = String.format("%s) %s", String.valueOf((char) ('a' + j)), getLeftChoiceInt(j));
            ui.display("Matches for: " + choiceOption + "\n");

            for (int k = 0; k < numberOfChoices; k++) {
                String choiceOptionRight = String.format("%s) %s", String.valueOf((char) ('a' + k)),
                        getRightChoiceInt(k));
                ui.display("    " + choiceOptionRight + " : ");
                int total = 0;

                for (Answer a : answers) {
                    MatchingAnswer mAns = (MatchingAnswer) a;

                    if (mAns.getAnswer().get(choiceOption).equalsIgnoreCase(choiceOptionRight)) {
                        total++;
                    }
                }
                ui.display(String.format("%d\n", total));
            }
        }
    }

}
