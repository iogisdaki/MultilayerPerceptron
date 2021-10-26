public class Matrix {
    int rows, cols;
    double[][] matrix;

    Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        matrix = new double[rows][cols];
    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(this.matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void randomize() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                this.matrix[i][j] = Math.random() * 2 - 1;
    }

    public void add(int scalar) {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                this.matrix[i][j] += scalar;
    }

    public void add(Matrix m) {
        if (cols != m.cols || rows != m.rows) throw new RuntimeException("Illegal Vector Dimensions.");
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                this.matrix[i][j] += m.matrix[i][j];
    }

    public static Matrix fromArray(double[] x) {
        Matrix temp = new Matrix(x.length, 1);
        for (int i = 0; i < x.length; i++)
            temp.matrix[i][0] = x[i];
        return temp;

    }

    public double[] toArray() {
        double[] temp = new double[rows];
        for (int i = 0; i < rows; i++)
            temp[i] = matrix[i][0];
        return temp;
    }

    public static Matrix subtract(Matrix a, Matrix b) {
        Matrix temp = new Matrix(a.rows, a.cols);
        for (int i = 0; i < a.rows; i++)
            for (int j = 0; j < a.cols; j++)
                temp.matrix[i][j] = a.matrix[i][j] - b.matrix[i][j];
        return temp;
    }

    public static Matrix transpose(Matrix a) {
        Matrix temp = new Matrix(a.cols, a.rows);
        for (int i = 0; i < a.rows; i++)
            for (int j = 0; j < a.cols; j++)
                temp.matrix[j][i] = a.matrix[i][j];
        return temp;
    }

    public static Matrix dotProduct(Matrix a, Matrix b) {
        if (a.cols != b.rows) throw new RuntimeException("Illegal Matrix Dimensions.");
        Matrix temp = new Matrix(a.rows, b.cols);
        for (int i = 0; i < temp.rows; i++) {
            for (int j = 0; j < temp.cols; j++) {
                double sum = 0;
                for (int k = 0; k < a.cols; k++) {
                    sum += a.matrix[i][k] * b.matrix[k][j];
                }
                temp.matrix[i][j] = sum;
            }
        }
        return temp;
    }

    public void hadamardMultiply(Matrix a) {
        for (int i = 0; i < a.rows; i++)
            for (int j = 0; j < a.cols; j++)
                this.matrix[i][j] *= a.matrix[i][j];

    }

    public void scalarMultiply(double a) {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                this.matrix[i][j] *= a;
    }

    public void passThroughSigmoid() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                this.matrix[i][j] = 1 / (1 + Math.exp(-this.matrix[i][j]));
    }

    public Matrix derivativeOfSigmoid() {
        Matrix temp = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                temp.matrix[i][j] = this.matrix[i][j] * (1 - this.matrix[i][j]);
        return temp;
    }
}
