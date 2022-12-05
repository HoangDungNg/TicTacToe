package com.example.tictactoe;
import java.io.Serializable;

public class Matrix implements Serializable  {
    private int[][] matrix;
    private int size;

    public Matrix(){
        matrix = new int[5][5];
        size = 5;
    }

    public void set(int rowIndex, int colIndex, int data){
        matrix[rowIndex][colIndex] = data;
    }

    public int get(int rowIndex, int colIndex){
        return matrix[rowIndex][colIndex];
    }

    public int getSize() {
        return size;
    }

}
