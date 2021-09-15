package restaurant;

public abstract class Table implements Comparable<Table>{
	
	private Group group;
	public int capacity;
	
	Table(int capacity){
		this.capacity = capacity;
	}
	
	public abstract int getCapacity();
	
	public Group getGroup() {
		return this.group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	
	@Override
	public String toString() {
		return "Table [group=" + group + ", capacity=" + capacity + "]";
	}

	@Override
	public int compareTo(Table table) {
		return this.capacity-table.getCapacity();
	}
}
