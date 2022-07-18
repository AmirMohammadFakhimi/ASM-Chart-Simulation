import java.util.ArrayList;
import java.util.Objects;

public class Register {
    private static final ArrayList<Register> registers = new ArrayList<>();

    private final String name;
    private int oldValue;
    private int newValue;

    private Register(String name) {
        this.name = name;
        this.oldValue = 0;
        this.newValue = 0;

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

    public static void updateRegisters() {
        for (Register register : registers)
            register.oldValue = register.newValue;
    }

    public String getName() {
        return name;
    }

    public int getOldValue() {
        return oldValue;
    }

    public int getNewValue() {
        return newValue;
    }

    public void setNewValue(int newValue) {
        this.newValue = newValue;
    }
}
