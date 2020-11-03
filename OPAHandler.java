import java.util.HashMap;

import static java.lang.System.exit;

public class OPAHandler {

    private int[][] matrix;
    private char leftStack[];
    private char rightStack[];
    private int leftStackPointer;
    private int rightStackPointer;
    private int rightStackLength;
    private String sentence;
    private HashMap<Character, Integer> indexMap;
    private char terminateSymbol = 'N';

    public OPAHandler(String sentence) {
        this.sentence = sentence;
        this.matrix = PriorityMatrix.getPriorityMatrix().getMatrix();
        this.leftStack = new char[2048];
        this.rightStack = new char[2048];
        this.leftStackPointer = -1;
        this.rightStackPointer = -1;
        this.indexMap = new HashMap<>();
        indexMap.put('+', 1);
        indexMap.put('*', 2);
        indexMap.put('i', 3);
        indexMap.put('(', 4);
        indexMap.put(')', 5);
        indexMap.put('#', 6);
    }

    /**
     * 在优先矩阵中获得二者的优先级
     * first在左栈, second在右栈
     * @param first
     * @param second
     * @return
     */
    public int comparePriority(char first, char second) {
        int firstIndex = 7;
        int secondIndex = 7;
        if (indexMap.get(first) != null) {
            firstIndex = indexMap.get(first);
        }
        if (indexMap.get(second) != null) {
            secondIndex = indexMap.get(second);
        }
        if (firstIndex <= 6 && secondIndex <= 6) {
            return matrix[firstIndex][secondIndex];
        } else {
            printE();
            exit(0);
        }
        return 0;
    }

    public void decreaseLeftPointer() {
        leftStackPointer --;
    }

    public void increaseLeftPointer() {
        leftStackPointer ++;
    }

    public void decreaseRightPointer() {
        rightStackPointer --;
    }

    public void increaseRightPointer() {
        rightStackPointer ++;
    }

    public void initLeftStack() {
        increaseLeftPointer();
        leftStack[leftStackPointer] = '#';
    }

    /**
     * 初始化右边栈 右边栈指针指向右栈的 0 索引处
     */
    public void initRightStack() {
        int i = 0;
        increaseRightPointer();
        for (i = 0; i < sentence.length(); i ++) {
//            if (Character.isLetter(sentence.charAt(i))) {
//                rightStack[i] = 'i';
//                continue;
//            }
            rightStack[i] = sentence.charAt(i);
        }
        rightStack[i] = '#';
        rightStackLength = sentence.length() + 1;
    }

    /**
     * 比较后决定移进
     * 将右栈字符移进至左栈
     */
    public void moveToLeftStack() {
        increaseLeftPointer();
        leftStack[leftStackPointer] = rightStack[rightStackPointer];
        printI(rightStack[rightStackPointer]);
        increaseRightPointer();
    }

    /**
     * 比较后决定规约
     * 如果是 i 就规约成 N
     * 如果是 + 检查前后两个符号是不是 N
     * 如果是 * 检查前后两个符号是不是 N
     */
    public void reduce(char character, int index) {
        if (character == 'i') {
            leftStack[leftStackPointer] = 'N';
            printR();
        } else if (character == '+') {
            if (leftStack[index - 1] == 'N' && leftStack[index + 1] == 'N') {
                setEmpty(leftStackPointer);
                decreaseLeftPointer();
                setEmpty(leftStackPointer);
                decreaseLeftPointer();
                leftStack[leftStackPointer] = 'N';
                printR();
            } else {
                printRE();
                exit(0);
            }
        } else if (character == '*') {
            if (leftStack[index - 1] == 'N' && leftStack[index + 1] == 'N') {
                setEmpty(leftStackPointer);
                decreaseLeftPointer();
                setEmpty(leftStackPointer);
                decreaseLeftPointer();
                leftStack[leftStackPointer] = 'N';
                printR();
            } else {
                printRE();
                exit(0);
            }
        } else if (character == ')') {
            if (leftStack[index - 1] == 'N' && leftStack[index - 2] == '(') {
                setEmpty(leftStackPointer);
                decreaseLeftPointer();
                setEmpty(leftStackPointer);
                decreaseLeftPointer();
                leftStack[leftStackPointer] = 'N';
                printR();
            } else {
                printRE();
                exit(0);
            }
        } else if (character == '(') {
            printRE();
            exit(0);
        }
    }

    public void printI(char character) {
        StringBuilder sb = new StringBuilder();
        sb.append("I");
        sb.append(character);
        System.out.println(sb.toString());
    }

    public void printR() {
        System.out.println("R");
    }

    public void printRE() {
        System.out.println("RE");
    }

    public void printE() {
        System.out.println("E");
    }

    public void setEmpty(int index) {
        leftStack[index] = '\u0000';
    }

    public void opaHandler() {
        initLeftStack();
        initRightStack();
//        for (int i = 0; i < rightStackLength; i ++) {
//            System.out.print(rightStack[i]);
//        }
//        for (int i = 0; i <= leftStackPointer; i ++) {
//            System.out.print(leftStack[i]);
//        }
        while (rightStackPointer != rightStackLength - 1 || leftStackPointer != 1 || (leftStackPointer == 1 && leftStack[leftStackPointer] != 'N')) {

            int tmpLeftStackPointer = leftStackPointer;
            while (leftStack[tmpLeftStackPointer] == 'N' && tmpLeftStackPointer >= 0) {
                tmpLeftStackPointer --;
            }

            int compareResult = comparePriority(leftStack[tmpLeftStackPointer], rightStack[rightStackPointer]);

            if (compareResult == -1 || compareResult == 0) {
                moveToLeftStack();
            } else if (compareResult == 1) {
                reduce(leftStack[tmpLeftStackPointer], tmpLeftStackPointer);
            } else {
                printE();
                exit(0);
            }
        }

    }
}
