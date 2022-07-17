public class Expression {
    private final Pair<ExpressionType, ExpressionOperator> type = new Pair<>();

    private final String expression;
    private Register register1;
    private Register register2;
    //    destination register
    private Register register3;

    private int number;

    public Expression(String expression, boolean isCondition) {
        this.expression = expression;

//        Register Operations
        if (expression.contains("<=") && expression.contains("+")) {
            compileArithmeticRegisterOperations(expression, '+', false);
            type.setFirst(ExpressionType.ADD);
        } else if (expression.contains("=") && expression.contains("+")) {
            compileArithmeticRegisterOperations(expression, '+', true);
            type.setFirst(ExpressionType.ADD);
        } else if (expression.contains("<=") && expression.contains("-")) {
            compileArithmeticRegisterOperations(expression, '-', false);
            type.setFirst(ExpressionType.SUB);
        } else if (expression.contains("=") && expression.contains("-")) {
            compileArithmeticRegisterOperations(expression, '-', true);
            type.setFirst(ExpressionType.SUB);
        } else if (expression.contains("<=") && expression.contains("*")) {
            compileArithmeticRegisterOperations(expression, '*', false);
            type.setFirst(ExpressionType.MUL);
        } else if (expression.contains("=") && expression.contains("*")) {
            compileArithmeticRegisterOperations(expression, '*', true);
            type.setFirst(ExpressionType.MUL);
        } else if (expression.contains("<=") && expression.contains("/")) {
            compileArithmeticRegisterOperations(expression, '/', false);
            type.setFirst(ExpressionType.DIV);
        } else if (expression.contains("=") && expression.contains("/")) {
            compileArithmeticRegisterOperations(expression, '/', true);
            type.setFirst(ExpressionType.DIV);
        } else if (expression.contains("<=") && expression.contains("%")) {
            compileArithmeticRegisterOperations(expression, '%', false);
            type.setFirst(ExpressionType.MODE);
        } else if (expression.contains("=") && expression.contains("%")) {
            compileArithmeticRegisterOperations(expression, '%', true);
            type.setFirst(ExpressionType.MODE);
        } else if (expression.contains("<=") && !isCondition) {
            compileAssignment(expression, false);
            type.setFirst(ExpressionType.ASSIGN);
        } else if (expression.contains("=") && !isCondition) {
            compileAssignment(expression, true);
            type.setFirst(ExpressionType.ASSIGN);
        }

//        Conditions
        else if (expression.contains("==")) {
            compileTwoOperatorConditions(expression, "==");
            type.setFirst(ExpressionType.EQUAL);
        } else if (expression.contains("<")) {
            compileTwoOperatorConditions(expression, "<");
            type.setFirst(ExpressionType.LESS);
        } else if (expression.contains("<=") && isCondition) {
            compileTwoOperatorConditions(expression, "<=");
            type.setFirst(ExpressionType.LESS_EQUAL);
        } else if (expression.contains("&&")) {
            compileTwoOperatorConditions(expression, "&&");
            type.setFirst(ExpressionType.LOGICAL_AND);
        } else if (expression.contains("||")) {
            compileTwoOperatorConditions(expression, "||");
            type.setFirst(ExpressionType.LOGICAL_OR);
        } else if (expression.contains("&") && expression.charAt(0) != '&') {
            compileTwoOperatorConditions(expression, "&");
            type.setFirst(ExpressionType.BITWISE_AND);
        } else if (expression.contains("|") && expression.charAt(0) != '|') {
            compileTwoOperatorConditions(expression, "|");
            type.setFirst(ExpressionType.BITWISE_OR);
        } else if (expression.contains("&") && expression.charAt(0) == '&') {
            compileOneOperatorConditions(expression);
            type.setFirst(ExpressionType.BITWISE_AND);
        } else if (expression.contains("|") && expression.charAt(0) == '|') {
            compileOneOperatorConditions(expression);
            type.setFirst(ExpressionType.BITWISE_OR);
        } else {
            compileInput(expression);
            type.setFirst(ExpressionType.INPUT);
        }
    }

    private void compileArithmeticRegisterOperations(String expression, char operator, boolean isBlocking) {
        String register3Name;
        String register1Name;
        String register2Name;
        if (isBlocking) {
            register3Name = expression.substring(0, expression.indexOf("="));
            register1Name = expression.substring(expression.indexOf("=") + 1, expression.indexOf(operator));
        } else {
            register3Name = expression.substring(0, expression.indexOf("<="));
            register1Name = expression.substring(expression.indexOf("<=") + 2, expression.indexOf(operator));
        }
        register2Name = expression.substring(expression.indexOf(operator) + 1);


        register3 = Register.createRegister(register3Name);
        if (Utils.isInteger(register1Name)) {
            number = Integer.parseInt(register1Name);
            register1 = Register.createRegister(register2Name);

            register2 = null;

            type.setSecond(ExpressionOperator.NR);
        } else if (Utils.isInteger(register2Name)) {
            number = Integer.parseInt(register2Name);
            register1 = Register.createRegister(register1Name);

            register2 = null;

            type.setSecond(ExpressionOperator.RN);
        } else {
            register1 = Register.createRegister(register1Name);
            register2 = Register.createRegister(register2Name);

            number = 0;

            type.setSecond(ExpressionOperator.RR);
        }
    }

    private void compileAssignment(String expression, boolean isBlocking) {
        String register3Name;
        String register1Name;
        if (isBlocking) {
            register3Name = expression.substring(0, expression.indexOf("="));
            register1Name = expression.substring(expression.indexOf("=") + 1);
        } else {
            register3Name = expression.substring(0, expression.indexOf("<="));
            register1Name = expression.substring(expression.indexOf("<=") + 2);
        }


        register3 = Register.createRegister(register3Name);
        register2 = null;

        if (register1Name.startsWith("Input")) {
            register1 = null;
            number = 0;

            type.setSecond(ExpressionOperator.INPUT);
            return;
        }

        if (Utils.isInteger(register1Name)) {
            number = Integer.parseInt(register1Name);
            register1 = null;

            type.setSecond(ExpressionOperator.N);
        } else {
            register1 = Register.createRegister(register1Name);
            number = 0;

            type.setSecond(ExpressionOperator.R);
        }
    }

    private void compileTwoOperatorConditions(String expression, String operator) {
        String register1Name = expression.substring(0, expression.indexOf(operator));
        String register2Name = expression.substring(expression.indexOf(operator) + operator.length());

        register3 = null;
        if (Utils.isInteger(register1Name)) {
            number = Integer.parseInt(register1Name);
            register1 = Register.createRegister(register2Name);

            register2 = null;

            type.setSecond(ExpressionOperator.NR);
        } else if (Utils.isInteger(register2Name)) {
            register1 = Register.createRegister(register1Name);
            number = Integer.parseInt(register2Name);

            register2 = null;

            type.setSecond(ExpressionOperator.RN);
        } else {
            register1 = Register.createRegister(register1Name);
            register2 = Register.createRegister(register2Name);

            number = 0;

            type.setSecond(ExpressionOperator.RR);
        }
    }

    private void compileOneOperatorConditions(String expression) {
        register1 = Register.createRegister(expression.substring(1));

        register2 = null;
        register3 = null;
        number = 0;

        type.setSecond(ExpressionOperator.R);
    }

    private void compileInput(String expression) {
        register1 = null;
        register2 = null;
        register3 = null;
        number = 0;

        type.setSecond(null);
    }

    public int doWork() {
//        return -1 means arithmetic operation
//        return -2 means unexpected error in Conditions
//        return -3 means unexpected error not in Register Operations and also not in Conditions

//        Register Operations
        if (type.getFirst().equals(ExpressionType.ADD)) {
            if (type.getSecond().equals(ExpressionOperator.RR))
                register3.setValue(register1.getValue() + register2.getValue());
            else if (type.getSecond().equals(ExpressionOperator.RN) || type.getSecond().equals(ExpressionOperator.NR))
                register3.setValue(register1.getValue() + number);

            return -1;
        } else if (type.getFirst().equals(ExpressionType.SUB)) {
            if (type.getSecond().equals(ExpressionOperator.RR))
                register3.setValue(register1.getValue() - register2.getValue());
            else if (type.getSecond().equals(ExpressionOperator.RN))
                register3.setValue(register1.getValue() - number);
            else if (type.getSecond().equals(ExpressionOperator.NR))
                register3.setValue(number - register1.getValue());

            return -1;
        } else if (type.getFirst().equals(ExpressionType.MUL)) {
            if (type.getSecond().equals(ExpressionOperator.RR))
                register3.setValue(register1.getValue() * register2.getValue());
            else if (type.getSecond().equals(ExpressionOperator.RN) || type.getSecond().equals(ExpressionOperator.NR))
                register3.setValue(register1.getValue() * number);

            return -1;
        } else if (type.getFirst().equals(ExpressionType.DIV)) {
            if (type.getSecond().equals(ExpressionOperator.RR))
                register3.setValue(register1.getValue() / register2.getValue());
            else if (type.getSecond().equals(ExpressionOperator.RN))
                register3.setValue(register1.getValue() / number);
            else if (type.getSecond().equals(ExpressionOperator.NR))
                register3.setValue(number / register1.getValue());

            return -1;
        } else if (type.getFirst().equals(ExpressionType.MODE)) {
            if (type.getSecond().equals(ExpressionOperator.RR))
                register3.setValue(register1.getValue() % register2.getValue());
            else if (type.getSecond().equals(ExpressionOperator.RN))
                register3.setValue(register1.getValue() % number);
            else if (type.getSecond().equals(ExpressionOperator.NR))
                register3.setValue(number % register1.getValue());

            return -1;
        } else if (type.getFirst().equals(ExpressionType.ASSIGN)) {
            if (type.getSecond().equals(ExpressionOperator.R))
                register3.setValue(register1.getValue());
            else if (type.getSecond().equals(ExpressionOperator.N))
                register3.setValue(number);
            else if (type.getSecond().equals(ExpressionOperator.INPUT))
                register3.setValue(getInput());

            return -1;
        }

//        Conditions
        else if (type.getFirst().equals(ExpressionType.EQUAL)) {
            if (type.getSecond().equals(ExpressionOperator.RR))
                return register1.getValue() == register2.getValue() ? 1 : 0;
            else if (type.getSecond().equals(ExpressionOperator.RN) || type.getSecond().equals(ExpressionOperator.NR))
                return register1.getValue() == number ? 1 : 0;

            return -2;
        } else if (type.getFirst().equals(ExpressionType.LESS)) {
            if (type.getSecond().equals(ExpressionOperator.RR))
                return register1.getValue() < register2.getValue() ? 1 : 0;
            else if (type.getSecond().equals(ExpressionOperator.RN))
                return register1.getValue() < number ? 1 : 0;
            else if (type.getSecond().equals(ExpressionOperator.NR))
                return number < register1.getValue() ? 1 : 0;

            return -2;
        } else if (type.getFirst().equals(ExpressionType.LESS_EQUAL)) {
            if (type.getSecond().equals(ExpressionOperator.RR))
                return register1.getValue() <= register2.getValue() ? 1 : 0;
            else if (type.getSecond().equals(ExpressionOperator.RN))
                return register1.getValue() <= number ? 1 : 0;
            else if (type.getSecond().equals(ExpressionOperator.NR))
                return number <= register1.getValue() ? 1 : 0;

            return -2;
        } else if (type.getFirst().equals(ExpressionType.LOGICAL_AND)) {
            if (type.getSecond().equals(ExpressionOperator.RR))
                return register1.getValue() != 0 && register2.getValue() != 0 ? 1 : 0;
            else if (type.getSecond().equals(ExpressionOperator.RN))
                return register1.getValue() != 0 && number != 0 ? 1 : 0;
            else if (type.getSecond().equals(ExpressionOperator.NR))
                return number != 0 && register1.getValue() != 0 ? 1 : 0;

            return -2;
        } else if (type.getFirst().equals(ExpressionType.LOGICAL_OR)) {
            if (type.getSecond().equals(ExpressionOperator.RR))
                return register1.getValue() != 0 || register2.getValue() != 0 ? 1 : 0;
            else if (type.getSecond().equals(ExpressionOperator.RN))
                return register1.getValue() != 0 || number != 0 ? 1 : 0;
            else if (type.getSecond().equals(ExpressionOperator.NR))
                return number != 0 || register1.getValue() != 0 ? 1 : 0;

            return -2;
        } else if (type.getFirst().equals(ExpressionType.BITWISE_AND)) {
            if (type.getSecond().equals(ExpressionOperator.RR))
                return register1.getValue() & register2.getValue();
            else if (type.getSecond().equals(ExpressionOperator.RN) || type.getSecond().equals(ExpressionOperator.NR))
                return register1.getValue() & number;
            else if (type.getSecond().equals(ExpressionOperator.R)) {
                if (register1.getValue() == 0) {
                    return 0;
                } else {
                    int highestOneBit = Integer.highestOneBit(register1.getValue());
                    return Math.pow(2, highestOneBit) - 1 == register1.getValue() ? 1 : 0;
                }
            }

            return -2;
        } else if (type.getFirst().equals(ExpressionType.BITWISE_OR)) {
            if (type.getSecond().equals(ExpressionOperator.RR))
                return register1.getValue() | register2.getValue();
            else if (type.getSecond().equals(ExpressionOperator.RN) || type.getSecond().equals(ExpressionOperator.NR))
                return register1.getValue() | number;
            else if (type.getSecond().equals(ExpressionOperator.R))
                return register1.getValue() != 0 ? 1 : 0;

            return -2;
        } else if (type.getFirst().equals(ExpressionType.INPUT))
            return getInput();


        return -3;
    }

    private int getInput() {
        do {
            System.out.print("Please enter input number for \"" + expression + "\" condition: ");
            try {
                return Integer.parseInt(Utils.getScanner().nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please try again.");
            }
        } while (true);
    }
}
