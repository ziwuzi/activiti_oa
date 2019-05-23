package pagemodel;

import java.util.ArrayList;
import java.util.List;

public class DataGrid<T> {
	private int current;//当前页面号
	private int rowCount;//每页行数
	private int total = 0;//总行数
	private List<T> rows = new ArrayList<>();

	public DataGrid(){

	}

	public DataGrid(int current,int rowCount){
		this.current = current;
		this.rowCount = rowCount;
	}

	public DataGrid(int current,int rowCount,int total,List<T> rows){
		this.current = current;
		this.rowCount = rowCount;
		this.total = total;
		this.rows = rows;
	}

	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
}
