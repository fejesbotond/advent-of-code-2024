package day17;

public final class Day17_1 {

    private static final int REG_A = 64012472;
    private static final int REG_B = 0;
    private static final int REG_C = 0;
    private static final int[] PROGRAM_TEXT = new int[]{2,4,1,7,7,5,0,3,1,7,4,1,5,5,3,0};

    private static long getComboOperand(int operand, long regA, long regB, long regC){
          return switch (operand){
                case 0, 1, 2, 3 -> operand;
                case 4 -> regA;
                case 5 -> regB;
                case 6 -> regC;
                default -> -1;
          };
    }

    private static void solve(){
        var r = runProgram(REG_A);
        System.out.println(r);
    }

    private static String runProgram(long a){
        var instructionPointer = 0;
        var programText = PROGRAM_TEXT;
        long regA = a;
        long regB = REG_B;
        long regC = REG_C;
        var output = new StringBuilder();
        while(instructionPointer >= 0 && instructionPointer < PROGRAM_TEXT.length){
            var opcode = programText[instructionPointer];
            var operand = programText[instructionPointer + 1];
            var comboOperand = getComboOperand(operand, regA, regB, regC);
            switch (opcode){
                case 0 -> regA = regA / (1L << comboOperand);
                case 1 -> regB = regB ^ operand;
                case 2 -> regB = comboOperand % 8;
                case 3 -> {
                    if (regA != 0){
                        instructionPointer = operand;
                        continue;
                    }
                }
                case 4 -> regB = regB ^ regC;
                case 5 -> {
                    output.append(comboOperand % 8);
                    output.append(',');
                }
                case 6 -> regB = regA / (1L << comboOperand);
                case 7 -> regC = regA / (1L << comboOperand);
            }
            instructionPointer += 2;
        }
        return output.toString();
    }

    public static void main(String[] args){
       solve();
    }
}
