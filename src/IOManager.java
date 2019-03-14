import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public final class IOManager
{
    private double L = 40; // Side length
    private double rc = 0.9; // interaction radius
    private int N; // number of particles
    private int M = 5; // gridSize
    private Particle[] particles;
    private double maxL = 10;
    private int maxN;
    private double maxR = 0.00001;
    private double minL = 0.00001;
    private double simulationTime = 0;
    private boolean[][] neighbours;
    private long timeElapsed;

    public IOManager() {
    }

    /**
     * @return the timeElapsed
     */
    public long getTimeElapsed() {
        return timeElapsed;
    }

    /**
     * @return the neighbours
     */
    public boolean[][] getNeighbours() {
        return neighbours;
    }

    public void setOutput(boolean[][] neighbours, long timeElapsed) {
        this.neighbours = neighbours;
        this.timeElapsed = timeElapsed;
    }

    public IOManager(int maxN, double maxR, double maxL)
    {
        this.maxN = maxN;
        this.maxR = maxR;
        this.maxL = maxL;
    }

    public double getL() {
        return L;
    }

    public double getRc() {
        return rc;
    }

    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }

    public Particle[] getParticles() {
        return particles;
    }

    public void readInputs(String staticFile, String dynamicFile) {
        readStaticFile(staticFile);
        readDynamicFile(dynamicFile);
        for(Particle p : particles)
            if(this.maxR < p.getRadius())
                this.maxR = p.getRadius();
        if(this.L / this.M <= this.rc + 2 * this.maxR) {
            throw new IllegalArgumentException("L/M > rc + 2 * maxRadius restriction");
        }
        System.out.println("L value: " + this.L);
        System.out.println("N value: " + this.N);
        System.out.println("M value: " + this.M);
        System.out.println("rc value: " + this.rc);
        System.out.println("r max value: " + this.maxR);
    }

    public void readStaticFile(String file)
    {
        try(Scanner scanner = new Scanner(new File(file))) {
            int i = scanner.nextInt();
            this.L = scanner.nextDouble();
            this.N = i;
            i = 0;
            this.particles = new Particle[this.N];
            while(i < this.N)
            {
                Particle particle = new Particle();
                if(scanner.hasNextDouble())
                    particle.setRadius(scanner.nextDouble());
                else
                    throw new IllegalArgumentException("Bad file format missing radius");
                if(scanner.hasNextDouble())
                    particle.setProperty(scanner.nextDouble());
                else
                    throw new IllegalArgumentException("Bad file format missing property");
                particle.setNumber(i);
                this.particles[i++] = particle;
            }
            scanner.close();
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void readDynamicFile(String file)
    {
        try(Scanner scanner = new Scanner(new File(file))) {
            scanner.nextInt();
            int i = 0;
            while(i < this.N)
            {
                // TODO: velocity 
                if(scanner.hasNextDouble())
                    this.particles[i].setX(scanner.nextDouble());
                else
                    throw new IllegalArgumentException("Bad file format missing X value");
                if(scanner.hasNextDouble())
                    this.particles[i].setY(scanner.nextDouble());
                else
                    throw new IllegalArgumentException("Bad file format missing Y value");
                i++;
            }
            scanner.close();
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeRandomInputs(String staticFile, String dynamicFile) {
        try {
            File file = new File(staticFile);
            if(file.createNewFile()) {
                System.out.println("Static file was created.");
            } else {
                System.out.println(staticFile + " static file was not created");
                return;
            }
            
            file = new File(dynamicFile);
            if(file.createNewFile()) {
                System.out.println("dynamic file was created.");
            } else {
                System.out.println(dynamicFile + " dynamic file was not created");
                return;
            }

            FileWriter fileWriter = new FileWriter(staticFile);
            fileWriter.write(getRandomStaticFileContent());
            fileWriter.close();
            fileWriter = new FileWriter(dynamicFile);
            fileWriter.write(getRandomDynamicFileContent());
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("IO exception");
        }
    }

    private String getRandomStaticFileContent() {
        int i = 0;
        Random random = new Random();
        this.N = random.nextInt(this.maxN) + 1;
        this.L = random.nextDouble()  * (this.maxL - this.minL) + this.minL;
        String ret = this.N + "\n" + this.L + "\n";
        while(i++ < this.N)
        {
            ret += random.nextDouble() * (this.maxR - this.minL) + this.minL + " " + random.nextDouble()+ "\n";
        }
        return ret;
    }

    private String getRandomDynamicFileContent() {
        int i = 0;
        Random random = new Random();
        String ret = random.nextInt() + "\n";
        while(i++ < this.N)
        {
            // TODO: checkForCollisions();
            double x = random.nextDouble() * (this.L - this.minL) + this.minL;
            double y = random.nextDouble() * (this.L - this.minL) + this.minL;
            // System.out.println(x + " " + y);
            ret +=  x + " " + y + "\n";
        }
        return ret;
    }


    public void handleOutput() {
        try {
            System.out.println("Excecution time: " + this.timeElapsed);
            File output = new File("simRes" + Long.toString(System.nanoTime()).hashCode());
            if(output.createNewFile()) {
                System.out.println("File was created.");
            } else {
                System.out.println("File was not created.");
            }
            FileWriter writer = new FileWriter(output.getName());
            writer.write(getOutputContent());
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.toString());
        }
    }

    private String getOutputContent() {
        String ret = "";
        for(int i = 0; i < neighbours.length;i++) {
            ret += i;
            for(int j = 0; j < neighbours[i].length ; j++) {
                System.out.print(neighbours[i][j] + " ");
                if(neighbours[i][j])
                    ret += " " + j ;
            }
            System.out.println();
            ret += "\n";            
        }
        return ret;
    }

    /**
     * @param l the l to set
     */
    public void setL(double l) {
        L = l;
    }

    /**
     * @param rc the rc to set
     */
    public void setRc(double rc) {
        this.rc = rc;
    }

    /**
     * @param n the n to set
     */
    public void setN(int n) {
        N = n;
    }

    /**
     * @param m the m to set
     */
    public void setM(int m) {
        M = m;
    }

    /**
     * @param particles the particles to set
     */
    public void setParticles(Particle[] particles) {
        this.particles = particles;
    }

    /**
     * @return the maxL
     */
    public double getMaxL() {
        return maxL;
    }

    /**
     * @param maxL the maxL to set
     */
    public void setMaxL(double maxL) {
        this.maxL = maxL;
    }

    /**
     * @return the maxN
     */
    public int getMaxN() {
        return maxN;
    }

    /**
     * @param maxN the maxN to set
     */
    public void setMaxN(int maxN) {
        this.maxN = maxN;
    }

    /**
     * @return the maxR
     */
    public double getMaxR() {
        return maxR;
    }

    /**
     * @param maxR the maxR to set
     */
    public void setMaxR(double maxR) {
        this.maxR = maxR;
    }

    /**
     * @return the minL
     */
    public double getMinL() {
        return minL;
    }

    /**
     * @param minL the minL to set
     */
    public void setMinL(double minL) {
        this.minL = minL;
    }

    public double getSimulationTime() {
        return simulationTime;
    }

    public void setSimulationTime(double simulationTime) {
        this.simulationTime = simulationTime;
    }

}
