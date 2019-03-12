import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;

import jdk.jfr.events.FileWriteEvent;

public final class IOManager
{
    private double L = 20; // Side length
    private double rc = 1; // interaction radius
    private int N; // number of particles
    private int M; // gridSize
    private Particle[] particles;
    private double maxL;
    private int maxN;
    private double maxR = 0.5;
    private final double minL = 0.0001;
    
    
    public IOManager(int maxN, double maxR, double minL)
    {
        this.maxN = maxN;
        this.maxR = maxR;
        this.minL = minL;
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
    }

    public void readStaticFile(String file)
    {
        try(Scanner scanner = new Scanner(new File(file))) {
            int i = scanner.nextInt();
            this.L = scanner.nextDouble();
            this.N = i;
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
            while(i++ < this.N)
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
        Random random = new Random();
        this.N = random.nextInt(this.maxN) + 1;
        this.L = (random.nextDouble() + 0.0000001) * this.maxL;
        String ret = this.N + "\n" + this.L + "\n";
        while(this.N-- > 0)
        {
            ret += random.nextDouble() + " " + random.nextDouble() + "\n";
        }
        return ret;
    }

    private String getRandomDynamicFileContent() {
        Random random = new Random();
        String ret = random.nextInt() + "\n";
        while(N-- > 0)
        {
            // TODO: checkForCollisions();
            ret += (random.nextDouble() +  this.minL) * this.L + " " + (random.nextDouble() + this.minL) * this.L + "\n";
        }
        return ret;
    }

}
