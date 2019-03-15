public class Particle extends Location2D {
    
    private double radius;
    private double property;
    private int number;

    public Particle() { }

    public Particle(double radius, double x, double y, int number) {
        super(x,y);
        this.radius = radius;
        this.number = number;
    }

    public double getDistanceTo(Particle particle) {
        double distanceX = this.getX() - particle.getX();
		double distanceY = this.getY() - particle.getY();
		return Math.max(0, Math.pow(Math.pow(distanceX, 2) + Math.pow(distanceY, 2), 0.5) - particle.getRadius() - this.radius);
    }

    public double getPeriodicContornDistance(Particle particle, double L)
    {
        double  distanceX = Math.abs(this.getX() - particle.getX());
        distanceX = (distanceX <= L/2)? distanceX : L - distanceX;
        double  distanceY = Math.abs(this.getY() - particle.getY());
        distanceY = (distanceY <= L/2)? distanceY : L - distanceY;
		return Math.max(0, Math.pow(Math.pow(distanceX, 2) + Math.pow(distanceY, 2), 0.5) - particle.getRadius() - this.radius);
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getProperty() {
        return this.property;
    }

    public void setProperty(double property) {
        this.property = property;
    }
    
    public String print() {
		return getRadius() + " " + getX() + " " + getY();
	}
    
}
