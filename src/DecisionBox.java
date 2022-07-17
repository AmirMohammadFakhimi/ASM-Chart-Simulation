import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DecisionBox extends Box {
    private static final ArrayList<DecisionBox> decisionBoxes = new ArrayList<>();

    private final Expression condition;
    private final HashMap<Integer, Integer> outputs = new HashMap<>();

    public DecisionBox(Expression condition, Integer[] outputs) {
        super();

        this.condition = condition;
        for (int i = 0; i < outputs.length; i++)
            this.outputs.put(i, -1);


        decisionBoxes.add(this);
    }

    public static ArrayList<DecisionBox> getDecisionBoxes() {
        return new ArrayList<>(decisionBoxes);
    }

    public HashMap<Integer, Integer> getOutputs() {
        return new HashMap<>(outputs);
    }

    public void putToOutputs(int index, int value) {
        outputs.put(index, value);
    }
}
