package restaurant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A place where groups of guests can buy a meal
 */
public class Restaurant {

	private Collection<Table> tables = new ArrayList<>();
	private Collection<CapacityListener> capacityListeners = new ArrayList<>();

	// to support testing
	int getTableCount() {
		return tables.size();
	}

	// to support testing
	Table getTable(int n) {
		Iterator<Table> it = tables.iterator();
		while (n > 0) {
			it.next();
		}
		return it.next();
	}

	/**
	 * Tells whether a Table is occupied.
	 * @param table the Table to check
	 * @return true if anyone is sitting at the provided Table
	 */
	public boolean isOccupied(Table table) {
		if (table.getGroup() != null) {
			return false;
		}
		return true;
	}


	/**
	 * Computes the guest capacity,
	 * either the remaining (includeOccupied == false) or total (includeOccupied == true).
	 * @param includeOccupied controls whether to include tables that are occupied.
	 * @return the guest capacity
	 */
	public int getCapacity(boolean includeOccupied) {
		if(includeOccupied) {
			return this.tables.stream().mapToInt(t -> t.getCapacity()).sum();
		}
		return this.tables.stream().filter(t -> !isOccupied(t))
				.mapToInt(t -> t.getCapacity()).sum();
	}

	/**
	 * Adds a table to this Restaurant
	 * @param table
	 */
	public void addTable(Table table) {
		if (this.tables.contains(table)) {
			throw new IllegalArgumentException("Finnes fra før");
		}
		this.tables.add(table);
	}

	/**
	 * Removes a Table from this Restaurant.
	 * If the table is occupied an IllegalArgumentException exception should be thrown.
	 * @param table
	 * @throws IllegalArgumentException
	 */
	public void removeTable(Table table) {
		if(isOccupied(table)) {
			throw new IllegalArgumentException("Kan ikke fjerne bord som er opptatt");
		}
		this.tables.remove(table);
	}


	/**
	 * Merges two tables, i.e. replaces two tables with one table.
	 * lostCapacity is the difference between the old capacity and the new.
	 * This number is typically positive, since seats are lost when moving two tables close to each other.
	 * @param table1
	 * @param table2
	 * @param lostCapacity
	 * @throws IllegalArgumentException if any of the tables are occupied
	 */
	public void mergeTables(Table table1, Table table2, int lostCapacity) {
		if (isOccupied(table1) || isOccupied(table2)) {
			throw new IllegalArgumentException("Ett ab bordene er opptatt");
		}
		Table newTable = new CompositeTable(table1, table2,lostCapacity);
		removeTable(table1);
		removeTable(table2);
		addTable(newTable);
	}

	/**
	 * Splits a table into two, i.e. replaces one tables with two tables.
	 * @param table
	 * @throws IllegalArgumentException if any of the table is occupied
	 */
	public void splitTable(CompositeTable table) {
		Table t1 = table.getTable1();
		Table t2 = table.getTable2();
		removeTable(table);
		addTable(t1);
		addTable(t2);
	}

	/**
	 * Tells whether a table has the provided capacity,
	 * i.e. if that number of new guests can be seated there.
	 * Note that a table cannot be shared among different groups.
	 * @param table
	 * @param capacity
	 * @return true of capacity number of guests can be seated here, false otherwise.
	 */
	public boolean hasCapacity(Table table, int capacity) {
		if(isOccupied(table)) {
			//			throw new IllegalArgumentException("Bordet er opptatt");
			return false;
		}
		if(table.getCapacity() < capacity) {
			//			throw new IllegalArgumentException("Ikke plass på bordet");
			return false;
		}
		return true;


	}

	/**
	 * Returns the tables that has the provided capacity.
	 * The tables should be sorted with the one with the least capacity (but enough) first.
	 * @param capacity
	 * @return the tables that has the provided capacity
	 */
	public List<Table> findAvailableTables(int capacity) {
		List<Table> availableTables = new ArrayList<>();
		for (Table table : this.tables) {
			if((table.getGroup() == null) && table.getCapacity() >= capacity) {
				availableTables.add(table);
			}
		}
		Collections.sort(availableTables);
		System.out.println(availableTables);
		return availableTables;
	}

	/**
	 * Finds a suitable table for the provided group.
	 * The chosen table should be the one with the least capacity.
	 * @param group the group to be seated
	 * @return true if group was seated, false otherwise
	 * @throws IllegalArgumentException if Group already is seated (in this Restaurant)
	 */
	public boolean seatGroup(Group group) {
		if (group.getTable() != null) {
			throw new IllegalArgumentException("Group is already seated");
		}
		List<Table> availableTables = findAvailableTables(group.getGuestCount());
		if (availableTables.isEmpty()) {
			return false;
		}
		Table table = availableTables.get(0);
		group.setTable(table);
		table.setGroup(group);
		System.out.println(table);
		return true;
	}

	/**
	 * Removes the group for the provided table (number), if one exists
	 * @param tableID the number of the table to remove the group from
	 */
	public void removeGroupFromTable(int tableID) {
		Table table = findTable(tableID);
		if (table == null) {
			throw new IllegalArgumentException("Finnes ikke");
		}
		table.setGroup(null);

		// TODO Add necessary code
	}

	/**
	 * Finds the table with table number equal to tableID
	 * @param tableID
	 * @return Table-object
	 */
	private Table findTable(int tableID) {
		for (Table table : tables) {
			if (isTable(table, tableID)) {
				return table;
			}
		}
		return null;
	}

	/**
	 * Checks whether or not table has tableID as its table number
	 * @param table
	 * @param tableID
	 * @return true if table has tableID as its table number
	 */
	private boolean isTable(Table table, int tableID) {
		if (table instanceof SimpleTable) {
			return (((SimpleTable) table).getID() == tableID);
		} else if (table instanceof CompositeTable) {
			CompositeTable comp = (CompositeTable) table;
			if (isTable(comp.getTable1(), tableID) || isTable(comp.getTable2(), tableID)) {
				return true;
			}
		}
		return false;
	}

	public void addCapacityListener(CapacityListener cl) {
		if(this.capacityListeners.contains(cl)) {
			throw new IllegalArgumentException("Finnes fra før");
		}
		this.capacityListeners.add(cl);
	}
	
	public void removeCapacityListener(CapacityListener cl) {
		if(!this.capacityListeners.contains(cl)) {
			throw new IllegalArgumentException("Finnes ikke");
		}
		this.capacityListeners.remove(cl);
	}
	
	public void fireCapacityChange() {
		for (CapacityListener capacityListener : capacityListeners) {
			capacityListener.capacityChanged(this);
		}
	}

}
