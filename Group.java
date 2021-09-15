package restaurant;

/**
 * A group (of people) dining together, and should be seated at the same table.
 */
public class Group{

	public final int guestCount;
	Table table;
	
	public Group(int guestCount) {
		this.guestCount = guestCount;
	}
	
	public int getGuestCount() {
		return this.guestCount;
	}
	
	@Override
	public String toString() {
		return "Group [guestCount=" + guestCount + ", table=" + table + "]";
	}

	public void setTable(Table table) {
		if(table.getCapacity() < guestCount) {
			throw new IllegalArgumentException("Bordet er for lite");
		}
		this.table = table;
	}
	
	public Table getTable() {
		return this.table;
	}

}
