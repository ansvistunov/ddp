package car;

import java.io.*;
import java.util.Scanner;

/**
 * @author : Alex
 * @created : 10.03.2021, среда
 **/
public class FieldMatrix {
    enum CellState{
        EMPTY,CAR,WALL
    }

    final CellState[][] cells;

    public final int rows;
    public final int cols;

    public FieldMatrix(int rows, int cols){
        this.rows = rows; this.cols = cols;
        cells = new CellState[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                cells[i][j] = CellState.EMPTY;
    }
    public static FieldMatrix load(InputStreamReader isr){
        throw new UnsupportedOperationException("Method not implemented");
    }

    public CellState getCellState(int row, int col){
        return cells[row][col];
    }

    public synchronized Position occupyFirstFreeCellByCar(){
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (cells[i][j] == CellState.EMPTY) {
                    cells[i][j] = CellState.CAR;
                    return new Position(i, j);
                }

        throw new RuntimeException("No empty fields!");
    }

    public boolean moveCarTo(int fromrow, int fromcol, int torow, int tocol){
        CellState cellState = cells[torow][tocol];
        synchronized (cellState){
            if (cellState == CellState.EMPTY){
                cells[fromrow][fromcol] = CellState.EMPTY;
                cells[torow][tocol] = CellState.CAR;
                return true;
            }else{
                return false;
            }
        }

    }


}
