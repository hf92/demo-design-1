
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
}
