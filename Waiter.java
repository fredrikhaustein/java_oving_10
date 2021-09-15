package restaurant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Handles guests arriving at and departing from a Restaurant.
 */
public class Waiter implements CapacityListener{

	private final Restaurant restaurant;
	private Collection<Group> waitingGroups = new ArrayList<>();

	/**
	 * Initialized this Waiter for the provided Restaurant.
	 * @param restaurant
	 */
	public Waiter(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	
	/**
	 * Handles arriving groups, by either seating them immediately
	 * (if possible) or putting them in queue. Those enqueued will
	 * be seated when the Restaurant's (change in) capacity allows it.
	 * @param group
	 */
	public void groupArrived(Group group) {
		if (! restaurant.seatGroup(group)) {
			waitingGroups.add(group);
		}
	}

	/**
	 * Handles departing groups, by removing their seating.
	 * @param tableNum the table where the group was seated
	 */
	public void groupDeparted(int tableNum) {
		restaurant.removeGroupFromTable(tableNum);
	}

	
	// to support testing
	public boolean isWaiting(Group group) {
		return waitingGroups.contains(group);
	}


	@Override
	public void capacityChanged(Restaurant restaurant) {
		for (Group group : waitingGroups) {
			List<Table> tables = restaurant.findAvailableTables(group.getGuestCount());
			if(!tables.isEmpty()) {
				Table table = tables.get(0);
				table.setGroup(group);
			}
		}
	}
	
	
	private Group checkTableCapacity(Table table) {
		for (Group group : waitingGroups) {
			if (table.getCapacity() >= group.getGuestCount()) {
				return group;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		Restaurant r1 = new Restaurant();
		
		Group g1 = new Group(10);
		Group g2 = new Group(15);
		Group g3 = new Group(7);
		Group g4 = new Group(5);
		Group g5 = new Group(2);
		
		Table t1 = new SimpleTable(11);
		Table t2 = new SimpleTable(5);
		Table t3 = new SimpleTable(20);
		Table t4 = new SimpleTable(3);
		Table t5 = new SimpleTable(8);
		Table t6 = new SimpleTable(15);
		Table t7 = new SimpleTable(6);
		Table t8 = new SimpleTable(12);
		
		r1.addTable(t1);
		r1.addTable(t2);
		r1.addTable(t3);
		r1.addTable(t4);
		r1.addTable(t5);
		r1.addTable(t6);
		r1.addTable(t7);
		r1.addTable(t8);
		
		r1.seatGroup(g1);
		r1.seatGroup(g2);
		r1.seatGroup(g3);
		r1.seatGroup(g4);
		r1.seatGroup(g5);
		
		System.out.println(t1.getGroup());
		
	}
}
