package com.example.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
public class Sudoku {
	
	private static final int EASYLEVEL = 25;
	private static final int MEDIUMLEVEL = 23;
	private static final int HARDLEVEL = 21;
	
	private int[][] sudoku;
	private int size;
	private int sqr;
	private int countRow = 0, countCol = 0;
	private Cell[][] allCells;

	private int elements;
	private int remainingEmptyFiels;

	public Sudoku(int size) throws Exception {
		setSize(size);
		
		this.sudoku = new int[size][size];
		sqr = (int) Math.sqrt(size);
	
		allCells = new Cell[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				allCells[i][j] = new Cell();
				for (int k = 1; k <= size; k++) {

					allCells[i][j].addAllowedValue(k);
				}
			}
		}

		initSudoku();

		System.out.println(checkSudoku());

	}

	// get a number with square root that belong to N
	private boolean setSize(int size) {
		if (size % Math.sqrt(size) == 0) {
			this.size = size;
			return true;
		}
		return false;
	}

	private void initSudoku() throws Exception {

		int x;
		x = generateRandom();
		sudoku[countRow][countCol] = x;

		if (countCol == size - 1) {
			if (countRow == size - 1) {
				return;
			}
			countRow++;
			countCol = 0;
			// row.clear();
			initSudoku();
		}

		if (countRow == size - 1 && countCol == size - 1) {
			return;
		}
		countCol++;
		initSudoku();
		
	}

	private int generateRandom() throws Exception {

		for (int k = countCol; k < size; k++) {
			for (int j = 0; j < size; j++) {
				allCells[countRow][k].removeAllowed(sudoku[countRow][j]);
			}
		}

		for (int i = countRow; i < size; i++) {
			for (int j = 0; j < size; j++) {
				allCells[i][countCol].removeAllowed(sudoku[j][countCol]);
			}
		}

		// decide in which square need to look for values to exclude.
		int qrIndex1 = countRow / sqr;
		int qrIndex2 = countCol / sqr;
		/////
		//first two loop are to go over each cell in allowed values.
		//second two loop are to go over the sudoku board and find value
		for (int i = qrIndex1 * sqr; i < ((qrIndex1 * sqr) + sqr); i++) {
			for (int j = qrIndex2 * sqr; j < ((qrIndex2 * sqr) + sqr); j++) {
				for (int k = qrIndex1 * sqr; k < ((qrIndex1 * sqr) + sqr); k++) {
					for (int f = qrIndex2 * sqr; f < ((qrIndex2 * sqr) + sqr); f++) {

						allCells[i][j].removeAllowed(sudoku[k][f]);
					}
				}
			}
		}

		ArrayList<Integer> temp = new ArrayList<Integer>(allCells[countRow][countCol].getAllowedValues());

		if (temp.size() == 0) {
			throw new Exception();
		}

		Collections.shuffle(temp);
		return temp.get(0);
	}

	public boolean checkSudoku() {

		for (int i = 0; i < sqr; i++)
			for (int j = 0; j < sqr; j++) {
				if (isValidSodukoQuadrant(i, j) == false)
					return false;

			}
		for (int i = 0; i < size; i++) {
			if (isValidSudokuRow(i) == false)
				return false;
			if (isValidSudokuCol(i) == false)
				return false;
		}

		return true;
	}

	private boolean isValidSodukoQuadrant(int Qr, int Qc) {
		boolean helpArr[] = new boolean[size];
		int q = (int) Math.sqrt(size);
		int k;
		for (int i = Qr * q; i < ((Qr * q) + q); i++) {
			for (int j = Qc * q; j < ((Qc * q) + q); j++) {
				k = sudoku[i][j];
				helpArr[k-1] = true;
			}
		}
		for (int a = 0; a < helpArr.length; a++) {
			if (helpArr[a] == false)
				return false;
		}

		return true;
	}

	private boolean isValidSudokuRow(int indexRow) {
		boolean helpArr[] = new boolean[size];
		int k;

		for (int i = 0; i < sudoku.length; i++) {
			k = sudoku[indexRow][i];
			helpArr[k - 1] = true;
		}
		for (int j = 0; j < helpArr.length; j++) {
			if (helpArr[j] == false)
				return false;
		}

		return true;
	}

	private boolean isValidSudokuCol(int indexCol) {
		boolean helpArr[] = new boolean[size];
		int k;

		for (int i = 0; i < sudoku.length; i++) {
			k = sudoku[i][indexCol];
			helpArr[k - 1] = true;
		}
		for (int j = 0; j < helpArr.length; j++) {
			if (helpArr[j] == false)
				return false;
		}

		return true;

	}

	public String toString() {
		String str = "Sudoku: \n";
		for (int i = 0; i < sudoku.length; i++) {
			str = str + "(";
			for (int j = 0; j < sudoku[i].length; j++) {
				str = str + " " + sudoku[i][j] + " ";
			}
			str = str + ") \n";

		}
		return str;

	}
	
	public int getEllement(int a, int b){
		return sudoku[a][b];
		
	}

	public void prepGameBoard() {
		int elements = size*size - EASYLEVEL;
		int x, y;
		Random rnd = new Random();
		
		while(elements!=0) {
			x = rnd.nextInt(size);
			y = rnd.nextInt(size);
			if(sudoku[x][y]!= 0) {
			sudoku[x][y]= 0;
			--elements;
			}
		}
		
	}

	public void prepGameBoard(int dif) {
		int removeAmount = 0;
		if(dif == 0){
			removeAmount = EASYLEVEL;
		} else if (dif == 1) {
			removeAmount = MEDIUMLEVEL;
		} else if (dif == 2 ) {
			removeAmount = HARDLEVEL;

		}


		elements = size*size - removeAmount;
		remainingEmptyFiels = elements;
		int x, y;
		Random rnd = new Random();

		while(elements!=0) {
			x = rnd.nextInt(size);
			y = rnd.nextInt(size);
			if(sudoku[x][y]!= 0) {
				sudoku[x][y]= 0;
				--elements;
			}
		}
	}

	public void solvedBoard(){
		int x, y;
		Random rnd = new Random();

		x = rnd.nextInt(size);
		y = rnd.nextInt(size);
		sudoku[x][y] = 0;
		remainingEmptyFiels = 1;
	}


	public boolean setItem(int value, int i, int j) {
		return allCells[i][j].addAllowedValue(value);
	}

	public boolean checkLawsWithRequestedValue(int value, int row, int col) {
		for(int i =0; i<size; i++){
			if(sudoku[i][col] == value)
				return false;
		}
		for(int j=0; j<size;j++){
			if(sudoku[row][j] == value)
				return false;
		}


		int lowborderRow = 0;
		int lowBorderCol = 0;

		if(row<3)
			lowborderRow= 0;
		else if(row<6)
			lowborderRow= 3;
		else if(row < 9)
			lowborderRow = 6;



		if(col<3)
			lowBorderCol= 0;
		else if(col<6)
			lowBorderCol= 3;
		else if(col < 9)
			lowBorderCol = 6;


		for (int k = lowborderRow ; k < (lowborderRow + 3); k++) {
			for (int l = lowBorderCol; l < (lowBorderCol + 3); l++) {
				if(sudoku[k][l] == value)
					return false;
			}
		}

		sudoku[row][col] = value;

		remainingEmptyFiels--;
		return true;
	}

	public int getRemainingEmptyFiels(){
		return remainingEmptyFiels;
	}
}
