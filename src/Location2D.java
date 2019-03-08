
public class Location2D {

	private double x;
	private double y;
	
	public Location2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getDistanceToLocation(Location2D location) {
		
		double distanceX = this.x - location.getX();
		double distanceY = this.y - location.getY();
		
		return Math.pow(Math.pow(distanceX, 2) + Math.pow(distanceY, 2), 0.5);
	}
	
}
