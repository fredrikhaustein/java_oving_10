package restaurant;

/**
 * A table that consists of other tables.
 */
public class CompositeTable extends Table{
	
	private Table table1;
	private Table table2;
	private int lostCapacity;
	
	public CompositeTable(Table table1, Table table2, int lostCapacity) {
		super(table1.getCapacity()+table2.getCapacity()-lostCapacity);
		this.lostCapacity = lostCapacity;
		
	}

	public Table getTable1() {
		return this.table1;
	}
	
	public Table getTable2() {
		return this.table2;
	}
	
	@Override
	public int getCapacity() {
		return table1.getCapacity()+table2.getCapacity()-lostCapacity;
	}

	@Override
	public String toString() {
		return "CompositeTable [table1=" + table1 + ", table2=" + table2 + ", lostCapacity=" + lostCapacity
				+ ", capacity=" + capacity + "]";
	}
}
