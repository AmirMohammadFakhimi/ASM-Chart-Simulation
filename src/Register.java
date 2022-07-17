import java.util.ArrayList;

public class Register {
    private static final ArrayList<Register> registers = new ArrayList<>();

    private String name;
    private int value;

    public Register(String name) {
        this.name = name;
        this.value = 0;

        registers.add(this);
    }

    public static ArrayList<Register> getRegisters() {
        return new ArrayList<>(registers);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
