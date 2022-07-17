import java.util.ArrayList;
import java.util.Objects;

public class Register {
    private static final ArrayList<Register> registers = new ArrayList<>();

    private String name;
    private int value;

    private Register(String name) {
        this.name = name;
        this.value = 0;

        registers.add(this);
    }

    public static Register createRegister(String name) {
        Register register = getRegister(name);
        return Objects.requireNonNullElseGet(register, () -> new Register(name));
    }

    public static ArrayList<Register> getRegisters() {
        return new ArrayList<>(registers);
    }

    public static Register getRegister(String name) {
        for (Register register : registers)
            if (register.name.equals(name))
                return register;
        return null;
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
