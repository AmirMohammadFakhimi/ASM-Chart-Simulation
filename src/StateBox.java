import java.util.ArrayList;
import java.util.Arrays;

public class StateBox extends Box {
    private static final ArrayList<StateBox> stateBoxes = new ArrayList<>();

    private final ArrayList<Expression> registerOperations;
    private final String name;
    private int output;

    public StateBox(String name, ArrayList<Expression> registerOperations) {
        super();

        this.registerOperations = registerOperations;
        this.name = name;

        stateBoxes.add(this);
    }

    public static ArrayList<StateBox> getStateBoxes() {
        return new ArrayList<>(stateBoxes);
    }

    public void setOutput(int output) {
        this.output = output;
    }

    public String getName() {
        return name;
    }
}
