//Rishika Gautam
//Last Edited: 1/03/24

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;


public class BigMatrix 
{
	//creating separate HashMaps for the rows and the columns
	private HashMap<Integer, HashMap<Integer, Integer>> rows;
	private HashMap<Integer, HashMap<Integer, Integer>> cols;
	
	public BigMatrix()
	{
		rows = new HashMap<Integer, HashMap<Integer, Integer>>();
		cols = new HashMap<Integer, HashMap<Integer, Integer>>();
	}
	
	//method that sets a value in the matrix or removes empty space from matrix if value is 0
	public void setValue(int row, int col, int value)
	{
		//checking whether the value is 0 - if it 0 then removing the specified column or the whole row if it is empty
		if(value == 0) {
			//if the row is not empty then just removing the specified column in that row
			if(rows.containsKey(row) && !rows.get(row).isEmpty()){
				if(rows.get(row).containsKey(col)) {
					rows.get(row).remove(col);
				}
			}
			//if the row is empty then removing the whole row
			if(rows.containsKey(row) && rows.get(row).isEmpty()) {
				rows.remove(row);
			}
			
			//if the column is not empty then removing the specified row in that column
			if(cols.containsKey(col) && !cols.get(col).isEmpty()) {
				if(cols.get(col).containsKey(row)) {
					cols.get(col).remove(row);
				}
			}
			
			//if the column is empty then removing the whole column
			if(cols.containsKey(col) && cols.get(col).isEmpty()) {
				cols.remove(col);
			}
			
		}else { //if the value is not equal to zero actually setting a value in the matrix
			
			//checking if the matrix contains the specified row - if not creating a row
			if(!rows.containsKey(row)) {
				rows.put(row, new HashMap<>());
				rows.get(row).put(col, value);
			}else { //if the matrix does contain the row then setting the value in the specified column in the row
				rows.get(row).put(col, value);
			}
			
			//checking if the matrix contains the specified column - if not creating a column
			if(!cols.containsKey(col)) {
				cols.put(col, new HashMap<>());
				cols.get(col).put(row, value);
			}else { //if the matrix does contain the column then setting the value in the specified row in the column
				cols.get(col).put(row, value);
			}
			
		}
		
		
	}
	
	//method that returns a value from the matrix
	public int getValue(int row, int col)
	{
		//checking if the matrix contains the specified column and if the column contains the specified row - if so then the matrix contains the value
		if(cols.containsKey(col) && cols.get(col).containsKey(row)) {
			return (rows.get(row).get(col));
		}	
		return 0; //if the matrix does not have a value at the given row and column returning 0
	}
	
	//method returning a list of all the non empty rows in the matrix
	public List<Integer> getNonEmptyRows()
	{
		//Java's HashMap method - keySet - returns set view of all the keys that are 
		//in the map - keys that are not empty - so initializing an arrayList with the key
		//set will just return a list of the keys of the rows that are not empty
		ArrayList<Integer> nonEmptyRows = new ArrayList<Integer>(rows.keySet());
		return nonEmptyRows;
	}
	
	//method returning a list of all the non empty columns in a given row
	public List<Integer> getNonEmptyRowsInColumn(int col)
	{
		//accessing the specific column in the HashMap then accessing the keySet - 
		//of all the non empty keys of the rows in that specified column
		//initializing an arrayList with the key set to return a list of the keys of the rows
		//that are not empty in the specified column
		ArrayList<Integer> nonEmptyRowsinCols = new ArrayList<Integer>(cols.get(col).keySet());
		return nonEmptyRowsinCols;
	}
	
	//method returning a list of all the non empty columns in the matrix
	public List<Integer> getNonEmptyCols()
	{
		//Java's HashMap method - keySet - returns set view of all the keys that are 
		//in the map - keys that are not empty - so initializing an arrayList with the key
		//set will just return a list of the keys of the columns that are not empty
		ArrayList<Integer> nonEmptyCols = new ArrayList<Integer>(cols.keySet());
		return nonEmptyCols;
	}
	
	//method returning a list of all the non empty rows in a given column
	public List<Integer> getNonEmptyColsInRow(int row)
	{
		//accessing the specific row in the HashMap then accessing the keySet - 
		//of all the non empty keys of the columns in that specified row
		//initializing an arrayList with the key set to return a list of the keys of the 
		//columns that are not empty in the specified row
		ArrayList<Integer> nonEmptyColsinRow = new ArrayList<Integer>(rows.get(row).keySet());
		return nonEmptyColsinRow;
	}
	
	//method that returns the sum of all the integers in a given row in the matrix
	public int getRowSum(int row)
	{
		int sum = 0;
		//checking if the given row exists in the matrix
		if(rows.containsKey(row)) {
			for(Integer i : rows.get(row).values()) { //looping through all the values in the given row and adding them together
				sum += i;
			}
		}
		return sum;
	}
	
	//method that returns the sum of all the integers in a give column in the matrix
	public int getColSum(int col)
	{
		int sum = 0;
		//checking if the given column exists in the matrix
		if(cols.containsKey(col)) {
			for(Integer i : cols.get(col).values()) { //looping through all the values in the given column and adding them together
				sum += i;
			}
		}
		return sum;
	}
	
	//method that returns the sum of all the integers in the matrix
	public int getTotalSum()
	{
		int sum = 0;
		for(Integer col : cols.keySet()) { //looping through all the columns in the matrix 
			sum += getColSum(col); //getting the sum of each column and adding them together
		}
		return sum;
	}
	
	//method that multiplies each value in the matrix by the given constant
	public BigMatrix multiplyByConstant(int constant)
	{ 
		if(constant == 0) { //checking if the constant is 0 - if so returning an empty matrix
			return new BigMatrix();
		}
		
		BigMatrix multiplied = new BigMatrix();
		for(Integer row: this.rows.keySet()) { //looping through all the rows in the current matrix
			for(Integer col : this.rows.get(row).keySet()) { //looping through all the columns in the current row 
				int newVal = this.rows.get(row).get(col) * constant; //multiply each value in the current by the constant 
				multiplied.setValue(row, col, newVal); //adding the multiplied value to the new matrix at the given row and column
			}
		}
		
		return multiplied; //returning the new and multiplied matrix
	}
	
	//method that adds matrices of the same dimensions 
	public BigMatrix addMatrix(BigMatrix other)
	{
		BigMatrix added = new BigMatrix(); //creating a new matrix
		//making the new matrix have same dimensions as current matrix
		added.rows = this.rows;
		added.cols = this.cols;
		
		for(Integer row: other.getNonEmptyRows()){ //looping through all the non empty rows in the other matrix
			for(Integer col: other.getNonEmptyColsInRow(row)) { //looping through all the non empty columns in the current row
				int val1 = added.getValue(row, col);
				int val2 = other.getValue(row, col);
				added.setValue(row, col,(val1+val2)); //adding the added values to the new matrix at the given row and column
			}
		}
		return added;//returning the added matrix 
	}
}
