public class PriorityMatrix {
    private static PriorityMatrix PriorityMatrixInstance;
    private int [][]priorityMatrix = new int[7][7];
    private int impossible = -10000;

    private PriorityMatrix() {
        for (int i = 0; i < 7; i ++) {
            priorityMatrix[0][i] = 0;
            priorityMatrix[i][0] = 0;
        }
        priorityMatrix[1] = new int[]{0, 1, -1, -1, -1, 1, 1};
        priorityMatrix[2] = new int[]{0, 1, 1, -1, -1, 1, 1};
        priorityMatrix[3] = new int[]{0, 1, 1, impossible, impossible, 1, 1};
//        priorityMatrix[4] = new int[]{0, -1, -1, -1, -1, 0, impossible};
        priorityMatrix[4] = new int[]{0, -1, -1, -1, -1, 0, 1};
        priorityMatrix[5] = new int[]{0, 1, 1, impossible, impossible, 1, 1};
        priorityMatrix[6] = new int[]{0, -1, -1, -1, -1, impossible, impossible};
    }

    public static PriorityMatrix getPriorityMatrix() {
        if (PriorityMatrixInstance == null) {
            PriorityMatrixInstance = new PriorityMatrix();
        }
        return PriorityMatrixInstance;
    }

    public int[][] getMatrix() {
        return PriorityMatrixInstance.priorityMatrix;
    }

    public static void main(String[] args) {
        int[][] m = PriorityMatrix.getPriorityMatrix().getMatrix();
        for (int i = 0; i < 7; i ++) {
            for (int j  = 0; j < 7; j ++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }
}
