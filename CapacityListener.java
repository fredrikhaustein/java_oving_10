package restaurant;

public interface CapacityListener {
	/**
	 * Called to inform that a Restaurant has changed capacity
	 * @param restaurant
	 */
	public void capacityChanged(Restaurant restaurant);
}
