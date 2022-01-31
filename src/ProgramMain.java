import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgramMain {
    private static HashMap<String, Survey> sheets = new HashMap<String, Survey>();
    private static HashMap<Survey, List<AnswerSheet>> answerSheets = new HashMap<Survey, List<AnswerSheet>>();
    private static CommandLineUI ui;

    public static void main(String args[]) {
        ui = new CommandLineUI();
        mainMenu();
    }

    public static void mainMenu() {
        boolean isGo = true;

        while (isGo) {
            ui.display("\n1) Create a new Survey\n");
            ui.display("2) Create a new Test\n");
            ui.display("3) Save a Survey\n");
            ui.display("4) Save a Test\n");
            ui.display("5) Modify an Existing Survey\n");
            ui.display("6) Modify an Existing Test\n");
            ui.display("7) Load a Survey\n");
            ui.display("8) Load a Test\n");
            ui.display("9) Take a Survey\n");
            ui.display("10) Take a Test\n");
            ui.display("11) Grade a Test\n");
            ui.display("12) Quit\n");

            switch (ui.captureInt()) {
                case 1:
                    createNew(false);
                    break;
                case 2:
                    createNew(true);
                    break;
                case 3:
                    saveFile(false);
                    break;
                case 4:
                    saveFile(true);
                    break;
                case 5:
                    modDoc(false);
                    break;
                case 6:
                    modDoc(true);
                    break;
                case 7:
                    loadDoc(false);
                    break;
                case 8:
                    loadDoc(true);
                    break;
                case 9:
                    takeDoc(false);
                    break;
                case 10:
                    takeDoc(true);
                    break;
                case 11:
                    gradeTest();
                    break;
                case 12:
                    isGo = false;
                    break;
                default:
                    ui.display("Invalid choice.\n");
                    break;
            }
        }
    }

    private static void createNew(boolean isTest) {
        ui.display("Enter a name for this survey/test: ");
        Survey sheet;

        if (isTest) {
            sheet = new Test(ui.captureString());
        } else {
            sheet = new Survey(ui.captureString());
        }

        sheets.put(sheet.getName(), sheet);
        newQuest(isTest, sheet);
    }

    private static void newQuest(boolean isTest, Survey sheet) {
        boolean isGo = true;
        while (isGo) {
            ui.display("\n");
            ui.display("1) Add a new T/F question\n");
            ui.display("2) Add a new multiple choice question\n");
            ui.display("3) Add a new short answer question\n");
            ui.display("4) Add a new essay question\n");
            ui.display("5) Add a new ranking question\n");
            ui.display("6) Add a new matching question\n");
            ui.display("7) Go back to Main Menu\n");

            switch (ui.captureInt()) {
                case 1:
                    addMultiChoice(sheet, isTest, true);
                    break;
                case 2:
                    addMultiChoice(sheet, isTest, false);
                    break;
                case 3:
                    addEssayQuestion(sheet, isTest, 250);
                    break;
                case 4:
                    addEssayQuestion(sheet, isTest, 1000);
                    break;
                case 5:
                    addMatchingQuestion(sheet, isTest, true);
                    break;
                case 6:
                    addMatchingQuestion(sheet, isTest, false);
                    break;
                case 7:
                    isGo = false;
                    break;
                default:
                    ui.display("Invalid choice.");
                    break;
            }
        }
    }

    private static void addMultiChoice(Survey sheet, boolean isTest, boolean isTrueFalse) {
        Choice multi = new Choice();

        if (isTrueFalse) {
            multi.newPrompt(ui);
            multi.addChoice("True");
            multi.addChoice("False");
            multi.setIsSingleSelection(true);
        } else {
            multi.create(ui);
        }

        sheet.addQuestion(multi);

        if (isTest) {
            ChoiceAnswer answer = new ChoiceAnswer();
            addAnswer(sheet, multi, answer);
        }
    }

    private static void addMatchingQuestion(Survey sheet, boolean isTest, boolean isRanking) {
        Matching match = new Matching();

        if (isRanking) {
            match.newPrompt(ui);

            ui.display("Enter the number of choices:");
            int i = ui.captureInt();

            for (int j = 0; j < i; j++) {
                ui.display(String.format("Enter a left choice %s:\n", String.valueOf((char) ('a' + j))));
                String l = ui.captureString();
                String r = String.format("%d", j);

                match.addChoice(l, r);
            }

        } else {
            match.create(ui);
        }

        sheet.addQuestion(match);

        if (isTest) {
            MatchingAnswer answer = new MatchingAnswer();
            addAnswer(sheet, match, answer);
        }
    }

    private static void addEssayQuestion(Survey sheet, boolean isTest, int l) {
        Written essayQ = new Written(l);
        essayQ.create(ui);
        sheet.addQuestion(essayQ);

        if (isTest) {
            EssayAnswer answer = new EssayAnswer();
            addAnswer(sheet, essayQ, answer);
        }
    }

    private static void addAnswer(Survey sheet, Question q, Answer a) {
        q.print(ui);
        Test t = (Test) sheet;
        a.newAnswer(ui, q, true);
        t.addAnswer(q, a);
        sheet = t;
    }

    private static Survey selectLoaded(boolean isTest) {
        Survey sheet = null;
        ui.display("Loaded options: \n");
        for (String s : sheets.keySet()) {
            if (isTest && (sheets.get(s) instanceof Test)) {
                ui.display(s);
            } else if (!isTest && !(sheets.get(s) instanceof Test)) {
                ui.display(s);
            }
            ui.display("\n");
        }

        ui.display("\nEnter the name: ");
        String name = ui.captureString();

        if (sheets.containsKey(name)) {
            if (isTest) {
                sheet = (Test) sheets.get(name);
            } else {
                sheet = sheets.get(name);
            }
        } else {
            ui.display("Could not find test/survey with that name.\n");
        }

        return sheet;
    }

    private static void saveFile(boolean isTest) {
        Survey sheet = selectLoaded(isTest);

        if (sheet != null) {
            ui.display("Enter file name: ");
            String file = ui.captureString();
            SaveLoadFile slf = new SaveLoadFile();
            try {
                slf.saveDoc(file, sheet);
                ui.display("\nFile saved.\n\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadDoc(boolean isTest) {
        ui.display("Enter file name:\n");
        try {
            String file = ui.captureString();
            SaveLoadFile slf = new SaveLoadFile();
            Survey sheet = slf.modDoc(file);

            if (sheet instanceof AnswerSheet) {
                if (sheets.containsKey(sheet.getName())) {
                    Survey s = sheets.get(sheet.getName());
                    AnswerSheet as = (AnswerSheet) sheet;

                    if (answerSheets.containsKey(s)) {
                        answerSheets.get(s).add(as);
                    } else {
                        List<AnswerSheet> tempList = new ArrayList<AnswerSheet>();
                        tempList.add(as);
                        answerSheets.put(s, tempList);
                    }
                }
            } else {
                sheets.put(sheet.getName(), sheet);
            }
        } catch (IOException e) {
            ui.display("ERROR: Failed to load file.\n");
            return;
        } catch (ClassNotFoundException e) {
            ui.display("ERROR: Failed to create survey/test.\n");
            return;
        }
    }

    private static void takeDoc(boolean isTest) {
        Survey sheet = selectLoaded(isTest);

        if (sheet != null) {
            ui.display("Enter your name: ");
            String name = ui.captureString();
            String filename = sheet.getName() + "-" + name;
            AnswerSheet answerSheet = new AnswerSheet(sheet.getName(), name);

            for (int i = 0; i < sheet.getQuestions().size(); i++) {
                ui.display(String.format("Question #%d:\n", i + 1));
                Question q = sheet.getQuestions().get(i);
                q.print(ui);

                if (q instanceof Written) {
                    Written write = (Written) q;
                    EssayAnswer a = new EssayAnswer();
                    answerQuestion(answerSheet, write, a);
                } else if (q instanceof Choice) {
                    Choice choice = (Choice) q;
                    ChoiceAnswer a = new ChoiceAnswer();
                    answerQuestion(answerSheet, choice, a);
                } else if (q instanceof Matching) {
                    Matching match = (Matching) q;
                    MatchingAnswer a = new MatchingAnswer();
                    answerQuestion(answerSheet, match, a);
                }
                ui.display("\n");
            }

            if (answerSheets.containsKey(sheet)) {
                answerSheets.get(sheet).add(answerSheet);
            } else {
                List<AnswerSheet> tempList = new ArrayList<AnswerSheet>();
                tempList.add(answerSheet);
                answerSheets.put(sheet, tempList);
            }

            SaveLoadFile slf = new SaveLoadFile();
            try {
                slf.saveDoc(filename, answerSheet);
                ui.display("Answer sheet saved.\n\n");
            } catch (IOException e) {
                ui.display("ERROR: failed to save file.\n");
            }
        }
    }

    private static void answerQuestion(AnswerSheet answerSheet, Question q, Answer a) {
        a.newAnswer(ui, q, false);
        answerSheet.addAnswer(q, a);
    }

    private static void modDoc(boolean isTest) {
        Survey sheet = selectLoaded(isTest);
        boolean isGo = true;

        if (sheet != null) {
            try {
                while (isGo) {
                    ui.display("1) Add a Question\n");
                    ui.display("2) Modify a Question\n");
                    ui.display("3) Go Back\n");

                    switch (ui.captureInt()) {
                        case 1:
                            newQuest(isTest, sheet);
                            break;
                        case 2:
                            modQuest(isTest, sheet);
                            break;
                        case 3:
                            isGo = false;
                            break;
                        default:
                            ui.display("Invalid choice.");
                            break;
                    }
                }
            } catch (Exception e) {
                System.err.println("An error occured while modifying.");
            }
        }
    }

    private static void modQuest(boolean isTest, Survey sheet) {
        boolean isGo = true;

        try {
            while (isGo) {
                ui.display("What question do you wish to modify? Enter '-1' to go back.");

                int i = ui.captureInt() - 1;
                if (i < 0) {
                    isGo = false;
                } else if (i < sheet.getQuestions().size()) {
                    Question q = sheet.getQuestions().get(i);
                    q.modify(ui);

                    if (isTest) {
                        Test test = (Test) sheet;
                        Answer answer = test.getAnswer(q);
                        ui.display("Correct Answer: ");
                        answer.print(ui);

                        boolean repeat = true;
                        while (repeat) {
                            ui.display("Do you wish to modify the correct answer? 'y' for yes or 'n' for no. : ");
                            String s = ui.captureString();
                            if (s.equalsIgnoreCase("y") || s.equalsIgnoreCase("n")) {
                                if (s.equalsIgnoreCase("y")) {
                                    if (q instanceof Written) {
                                        Written write = (Written) q;
                                        EssayAnswer a = new EssayAnswer();
                                        addAnswer(sheet, write, a);
                                    } else if (q instanceof Choice) {
                                        Choice choice = (Choice) q;
                                        ChoiceAnswer a = new ChoiceAnswer();
                                        addAnswer(sheet, choice, a);
                                    } else if (q instanceof Matching) {
                                        Matching match = (Matching) q;
                                        MatchingAnswer a = new MatchingAnswer();
                                        addAnswer(sheet, match, a);
                                    }
                                }
                                repeat = false;
                            } else {
                                ui.display("Invalid choice.");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            ui.display("Error: An error occured while modifying.");
        }
    }

    private static void gradeTest() {
        Test t = (Test) selectLoaded(true);

        if (t != null) {
            if (answerSheets.containsKey(t)) {
                int totalQ = t.getQuestions().size();
                int correctA = 0;

                for (AnswerSheet a : answerSheets.get(t)) {
                    ui.display(String.format("Grading responses from %s.\n", a.getTakerName()));

                    for (Question q : t.getQuestions()) {
                        if ((q instanceof Written) && (((Written) q).getLength() > 250)) {
                            totalQ--;
                            ui.display("Essay must be hand graded:\n");
                            a.getAnswer(q).print(ui);
                            ui.display("\n");
                        } else {
                            if (t.getAnswer(q).equals(a.getAnswer(q))) {
                                correctA++;
                            }
                        }
                    }

                    ui.display(String.format("Grade: %d / %d\n", correctA, totalQ));
                }
            } else {
                ui.display("No one has taken this test yet.\n");
            }
        }
    }

    class PrintVisitor {
        private UI ui;

        public PrintVisitor(UI uiPrinter) {
            ui = uiPrinter;
        }

        public void visit(Choice q) {
            q.print(ui);
        }

        public void visit(Matching q) {
            q.print(ui);
        }

        public void visit(Written q) {
            q.print(ui);
        }

        public void visit(ChoiceAnswer a) {
            a.print(ui);
        }

        public void visit(MatchingAnswer a) {
            a.print(ui);
        }

        public void visit(EssayAnswer a) {
            a.print(ui);
        }

    }
}
