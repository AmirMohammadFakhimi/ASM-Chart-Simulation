import java.util.ArrayList;

public class ConditionalBox extends Box {
    private static final ArrayList<ConditionalBox> conditionalBoxes = new ArrayList<>();

    private final ArrayList<Expression> registerOperations;
    private int output;

    public ConditionalBox(ArrayList<Expression> registerOperations) {
        super();

        this.registerOperations = registerOperations;
        conditionalBoxes.add(this);
    }

    public static ArrayList<ConditionalBox> getConditionalBoxes() {
        return new ArrayList<>(conditionalBoxes);
    }

    public void setOutput(int output) {
        this.output = output;
    }

    public ArrayList<Expression> getRegisterOperations() {
        return registerOperations;
    }

    public int getOutput() {
        return output;
    }
}
