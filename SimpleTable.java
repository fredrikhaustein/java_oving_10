package restaurant;

public class SimpleTable extends Table {// TODO

	public static int num=1;
	
	public SimpleTable(int capacity) {
		super(capacity);
		num += 1;
	}

	@Override
	public int getCapacity() {
		return super.capacity;
	}

	public int getID() {
		return this.num;
	}

	@Override
	public String toString() {
		return "SimpleTable [toString()=" + super.toString() + "]";
	}
}
