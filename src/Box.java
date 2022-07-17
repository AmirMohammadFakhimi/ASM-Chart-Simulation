import java.util.ArrayList;

public abstract class Box {
    private static final ArrayList<Box> boxes = new ArrayList<>();

    private int id;
    private ArrayList<Integer> inputs = new ArrayList<>();

    public Box() {
        this.id = boxes.size();
        boxes.add(this);
    }

    public static int getSizeOfBoxes() {
        return boxes.size();
    }

    public static Box getBox(int id) {
        return boxes.get(id);
    }

    public void addInput(int input) {
        this.inputs.add(input);
    }

    public int getId() {
        return id;
    }
}
