import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    final static int printSpace = 10;
    static int maxClock;

    public static void main(String[] args) {
        maxClock = Utils.getIntegerInput("clock");
        getBoxes();
        addConnections();
        int startingBoxId = getStartingBoxId();
        runASMChart(startingBoxId);
    }

    private static void getBoxes() {
        getStateBoxes();
        getDecisionBoxes();
        getConditionalBoxes();
    }

    private static void getStateBoxes() {
        System.out.println("Please enter names and Register Operations of your state boxes same as " +
                "\"state1 RO1 RO2 ..., state2 RO1 RO2 ...\":\n" +
                "* Don't put space in names or ROs.");

        String[] stateBoxes = Utils.getScanner().nextLine().split(",\\s*");
        ArrayList<String> stateBoxesNames = new ArrayList<>();

        for (String stateBox : stateBoxes) {
            String[] stateBoxInfo = stateBox.split("\\s+");
            String name = stateBoxInfo[0];

            if (name.equals(""))
                System.out.println("There isn't any name for a state box.\n" +
                        "So we skip it.");
            else if (stateBoxesNames.contains(name)) {
                System.out.println("State box with name " + name + " already exists!\n" +
                        "So we skip it.");
            } else {
                stateBoxesNames.add(name);
                String[] registerOperations = Arrays.copyOfRange(stateBoxInfo, 1, stateBoxInfo.length);
                new StateBox(name, getRegisterOperationsList(registerOperations));
            }
        }

        if (stateBoxesNames.size() == 0) {
            System.out.println("There isn't any state box.\n" +
                    "Please try again.");
            getStateBoxes();
        } else {
            System.out.println("Id of your state boxes are from " +
                    "0 to " + (stateBoxesNames.size() - 1) + " respectively.");
        }
    }

    private static ArrayList<Expression> getRegisterOperationsList(String[] registerOperations) {
        ArrayList<Expression> registerOperationsList = new ArrayList<>();
        for (String registerOperation : registerOperations) {
            try {
                registerOperationsList.add(new Expression(registerOperation, false));
            } catch (Exception e) {
                System.out.println("There is a problem with Register Operation " + registerOperation +
                        ".\n" +
                        "So we skip it.");
            }
        }
        return registerOperationsList;
    }

    private static void getDecisionBoxes() {
        System.out.println("""
                Please enter condition and outputs of your decision boxes same as "
                id1: condition1 output1 output2 ...
                id2: condition2 output1 output2 ...
                "
                * Please note that I give you the id, and also outputs must be integers.
                * When you enter "end" or press ENTER instead of condition, you will end the input.
                * Don't put space in condition or outputs.""");

        do {
            System.out.print(Box.getSizeOfBoxes() + ": ");
            String[] decisionBoxInfo = Utils.getScanner().nextLine().split("\\s+");

            if (decisionBoxInfo[0].equalsIgnoreCase("end") || decisionBoxInfo[0].equals(""))
                break;

            try {
                String[] outputs = Arrays.copyOfRange(decisionBoxInfo, 1, decisionBoxInfo.length);
                int[] outputsInt = Arrays.stream(outputs).mapToInt(Integer::valueOf).toArray();
                Integer[] outputsInteger = Arrays.stream(outputsInt).boxed().toArray(Integer[]::new);
                if (outputsInteger.length == 0)
                    throw new Exception();

                String condition = decisionBoxInfo[0];
                try {
                    new DecisionBox(new Expression(condition, true), outputsInteger);
                } catch (Exception e) {
                    System.out.println("There is a problem with condition " + condition +
                            " of decision box.\n" +
                            "So we skip it.");
                }
            } catch (Exception e) {
                System.out.println("Decision box must have Outputs and they must be integers!\n" +
                        "So we skip it.");
            }

        } while (true);
    }

    private static void getConditionalBoxes() {
        System.out.println("""
                Please enter Register Operations of your conditional boxes same as "
                id1: RO1 RO2 ...
                id2: RO1 RO2 ...
                "
                * Please note that I give you the id.
                * When you enter "end" instead of RO1, you will end the input.
                * Don't put space in ROs.""");

        do {
            System.out.print(Box.getSizeOfBoxes() + ": ");
            String[] registerOperations = Utils.getScanner().nextLine().split("\\s+");

            if (registerOperations[0].equalsIgnoreCase("end") || registerOperations[0].equals(""))
                break;

            new ConditionalBox(getRegisterOperationsList(registerOperations));
        } while (true);
    }

    private static void addConnections() {
        System.out.println("Now you should choose destination of connections for each box with their id.");
        addConnectionsStateBox();
        addConnectionsDecisionBox();
        addConnectionsConditionalBox();
    }

    private static void addConnectionsStateBox() {
        System.out.println("State boxes");

        for (StateBox stateBox : StateBox.getStateBoxes())
            addConnection(stateBox);
    }

    private static void addConnection(Box box) {
//        for state boxes and conditional boxes
        int id = box.getId();

//            get input until it's valid
        boolean isValid;
        do {
            System.out.print(id + ": ");

            try {
                int destinationId = Integer.parseInt(Utils.getScanner().nextLine());
                if (destinationId == id)
                    throw new Exception();

                if (box instanceof StateBox) {
                    ((StateBox) box).setOutput(destinationId);
                    Box.getBox(destinationId).addInput(id);
                } else if (box instanceof ConditionalBox) {
                    ((ConditionalBox) box).setOutput(destinationId);
                    Box.getBox(destinationId).addInput(id);
                }

                Box.getBox(destinationId).addInput(id);
                isValid = true;
            } catch (Exception e) {
                isValid = false;
                handleConnectionException(e, id);
            }
        } while (!isValid);
    }

    private static void handleConnectionException(Exception e, int id) {
        if (e instanceof NumberFormatException)
            System.out.println("Output id must be an integer!\n" +
                    "Try again.");
        else if (e instanceof NullPointerException)
            System.out.println("Output id must be between 0 and " + (Box.getSizeOfBoxes() - 1) + "!\n" +
                    "Try again.");
        else
            System.out.println("Output not equal to " + id + "!\n" +
                    "Try again.");
    }

    private static void addConnectionsDecisionBox() {
        System.out.println("Decision boxes");

        for (DecisionBox decisionBox : DecisionBox.getDecisionBoxes()) {
            int id = decisionBox.getId();
            System.out.println("Decision box " + id + ": ");

            HashMap<Integer, Integer> outputs = decisionBox.getOutputs();
            for (int outputState : outputs.keySet()) {
                boolean isValid;
                do {
                    System.out.print("    output " + outputState + ": ");

                    try {
                        int destinationId = Integer.parseInt(Utils.getScanner().nextLine());
                        if (destinationId == id)
                            throw new NullPointerException();

                        decisionBox.putToOutputs(outputState, destinationId);
                        Box.getBox(destinationId).addInput(id);
                        isValid = true;
                    } catch (Exception e) {
                        isValid = false;
                        handleConnectionException(e, id);
                    }
                } while (!isValid);
            }


        }
    }

    private static void addConnectionsConditionalBox() {
        System.out.println("Conditional boxes");

        for (ConditionalBox conditionalBox : ConditionalBox.getConditionalBoxes())
            addConnection(conditionalBox);
    }

    private static int getStartingBoxId() {
        boolean isValid;
        int startingBoxId = -1;
        do {
            System.out.println("Please choose a state box (its id) to start with:");

            try {
                startingBoxId = Integer.parseInt(Utils.getScanner().nextLine());
                if (Box.getBox(startingBoxId) instanceof StateBox)
                    isValid = true;
                else
                    throw new Exception();
            } catch (Exception e) {
                isValid = false;
                System.out.println("Invalid id!\n" +
                        "Try again.");
            }
        } while (!isValid);

        if (startingBoxId == -1) {
            System.out.println("Unexpected error!\n" +
                    "startingBoxId is -1.");
            System.exit(0);
        }

        return startingBoxId;
    }

    private static void runASMChart(final int startingBoxId) {
        drawHeaderOfTheTable();
        int currentBoxId = startingBoxId;
        int nextBoxId = -1;

        for (int clock = 1; clock <= maxClock; clock++) {
            do {
                Box box = Box.getBox(currentBoxId);

                if (box instanceof StateBox stateBox) {
                    runRegisterOperations(stateBox.getRegisterOperations());
                    nextBoxId = stateBox.getOutput();
                } else if (box instanceof DecisionBox decisionBox) {
                    boolean isValid;
                    do {
                        int result = decisionBox.getCondition().doWork();
                        try {
                            nextBoxId = decisionBox.getOutputs().get(result);
                            isValid = true;
                        } catch (NullPointerException e) {
                            System.out.println("There isn't matched output.");
                            isValid = false;
                        }
                    } while (!isValid);
                } else if (box instanceof ConditionalBox conditionalBox) {
                    runRegisterOperations(conditionalBox.getRegisterOperations());
                    nextBoxId = conditionalBox.getOutput();
                }

                currentBoxId = nextBoxId;
            } while ((!(Box.getBox(nextBoxId) instanceof StateBox)));

            drawRowOfTheTable(clock, nextBoxId);
        }
    }

    private static void runRegisterOperations(ArrayList<Expression> registerOperations) {
        for (Expression registerOperation : registerOperations) {
            registerOperation.doWork();
        }
    }

    private static void drawHeaderOfTheTable() {
        System.out.format("%" + printSpace + "s |", "clk");
        for (Register register : Register.getRegisters())
            System.out.format("%" + printSpace + "s    |", register.getName());
        System.out.format("%" + printSpace + "s    |\n", "Next ASM Block");

        for (int i = 0; i < printSpace * 2 * Register.getRegisters().size(); i++)
            System.out.print("_");
        System.out.println();
    }

    private static void drawRowOfTheTable(final int clock, final int nextBoxId) {
        System.out.format("%" + printSpace + "d |", clock);
        for (Register register : Register.getRegisters())
            System.out.format("%" + printSpace + "d    |", register.getValue());

        System.out.format("%" + printSpace + "s    |\n", ((StateBox) Box.getBox(nextBoxId)).getName());
    }

}
/*
init, mul
start 0 1
|r1 0 1
r1<=Input1 r2<=Input2 r3<=0 ready<=0
r3<=r3+r2 r1<=r1-1
ready<=1 r4<=r3
2
3
0
4
6
5
1
1
0
0
*/