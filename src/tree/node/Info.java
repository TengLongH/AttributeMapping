package tree.node;

public class Info {

	private String name;
	private int colum;
	private int row;
	private boolean leaf;
	
	public Info( String name, boolean leaf ){
		this( name, -2, -2, leaf );
	}
	public Info(String name, int colums, int row, boolean leaf) {
		super();
		this.name = name;
		this.colum = colums;
		this.row = row;
		this.leaf = leaf;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getColum() {
		if( !leaf )return -2;
		return colum;
	}
	public void setColum(int colum) {
		
		this.colum = colum;
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	
	public String toString(){
		return name;
	}
}
