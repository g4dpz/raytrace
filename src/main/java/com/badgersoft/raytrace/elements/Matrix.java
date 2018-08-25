package com.badgersoft.raytrace.elements;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;

public class Matrix extends Array2DRowRealMatrix {

    public Matrix(double[][] values) {
        super(values);
    }

    public Matrix(double[] values) {
        super(values);
    }

    public double[][] multiply(double[][] multiplicand) {
        Matrix other = new Matrix(multiplicand);
        final Array2DRowRealMatrix matrix = multiply(other);
        return matrix.getData();
    }

    public Matrix multiply(Matrix other) {
        final Array2DRowRealMatrix multiply = super.multiply(other);
        return new Matrix(multiply.getData());
    }

    public Matrix identity() {
        double[][] matrix = new double[getColumnDimension()][getRowDimension()];
        for(int i = 0; i < getColumnDimension(); i++) matrix[i][i] = 1;
        return new Matrix(matrix);
}

    public Matrix transpose() {
        return new Matrix(super.transpose().getData());
    }

    public static double determinant(Matrix m1) {

        double value = 0.0;

        if (m1.getRowDimension() == 2 && m1.getColumnDimension() ==2) {
            value = (m1.getEntry(0, 0) * m1.getEntry(1, 1)) - (m1.getEntry(0, 1) * m1.getEntry(1, 0));
        }
        else {
            for(int i = 0; i < m1.getColumnDimension(); i++) {
                double cell = m1.getEntry(0, i);
                double cofactor = cofactor(m1, 0, i);
                value = value + (cell * cofactor);
            }
        }

        return value;
    }

    public static Matrix submatrix(Matrix m1, int row, int col) {
        double[][] newMatrix = new double[m1.getRowDimension() - 1][m1.getColumnDimension() - 1];

        int rowCount = 0;
        int colCount = 0;

        for (int i = 0 ; i < m1.getRowDimension() ; i++) {
            if (i == row) {
                continue;
            }
            colCount = 0;
            for (int j = 0 ; j < m1.getColumnDimension() ; j++) {
                if (j == col) {
                    continue;
                }
                newMatrix[rowCount][colCount] = m1.getData()[i][j];
                colCount++;
            }
            rowCount++;
        }

        return new Matrix(newMatrix);
    }

    public static double minor(Matrix matrix, int row, int col) {
        final Matrix submatrix = submatrix(matrix, row, col);
        return determinant(submatrix);
    }

    public static double cofactor(Matrix matrix, int row, int col) {
        double minor = minor(matrix, row, col);
        minor = minor * ((((row + col) % 2) == 1) ? -1.0 : 1.0);
        return minor;
    }
}
