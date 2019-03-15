import java.io.PrintWriter;

public class OvitoFile {

    private PrintWriter writer = null;
    private String path;
    private int iteration;
    
    public OvitoFile(String path) {
        try {
        	this.iteration = 0;
        	this.path = path;
            this.writer = new PrintWriter(path, "UTF-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	public void write(double L, Particle[] particles) {
        this.writer.println(particles.length + 4);
        this.writer.println(iteration);
	    writer.println("0.005 0 0 0 0 1 0 0 100");
	    writer.println("0.005 "+L+" 0 0 0 1 0 0 100");
	    writer.println("0.005 0 " + L + " 0 0 1 0 0 100");
	    writer.println("0.005 " + L + " " + L + " 0 0 1 0 0 100");

	    writer.println(particles[0].print() + " 0 100 0 0");
        for (int i = 1; i < particles.length; i++)
            this.writer.println(particles[i].print() + " 0 0 0 100"); //escribo la nueva posicion de un agente en archivo
        iteration++;
    }

	public void write(double L, Particle[] particles, double t) {
        this.writer.println(particles.length + 4);
        this.writer.println(iteration);
        writer.println("0.005 0 0 0 0 1 0 0 100");
        writer.println("0.005 "+L+" 0 0 0 1 0 0 100");
        writer.println("0.005 0 " + L + " 0 0 1 0 0 100");
        writer.println("0.005 " + L + " " + L + " 0 0 1 0 0 100");

        iteration++;
    }

    public void closeFile() {
        this.writer.close();
    }
}