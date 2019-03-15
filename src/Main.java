import java.util.Random;

public class Main 
{
    public static void main(String[] args) {
        try {
            boolean isPeriodic;
            boolean[][] neighbours;
            IOManager io = null;
            long starTime;
            int i = 0;
            if (args.length == 0)
                throw new IllegalArgumentException("No arguments were passed. For help: java -jar CellIndexMethod.jar -h");
            // Generate files
            if (args[i].equals("-r") && args.length == 6) {
                i++;
                io = new IOManager(Integer.parseInt(args[i++]), 
                                        Double.parseDouble(args[i++]), 
                                        Double.parseDouble(args[i++]));
                io.writeRandomInputs(args[i++], args[i]);
                return;
            } else if(args[i].equals("-m") && args.length == 1) {
                getMultipleTableInfo();
            } else if(args[i].equals("-d") && args.length == 1) {
                getMultipleDensityTableInfo();
            }
            // Find neighbours reading from files.
            else if(args[i].equals("-i") && args.length == 5) {
                i++;
                io = new IOManager();
                io.readInputs(args[i++], args[i++]);
                // Is periodic
                if(args[i].equals("-p")) {
                    isPeriodic = true;
                } else if(args[i].equals("-np")) {
                // Is not periodic
                   isPeriodic = false;
                } else {
                    throw new IllegalArgumentException("Invalid arguments.");
                }
                i++;
                // Method determination
                if(args[i].equals("-c"))
                {
                    System.out.println("Calculation starting...");
                    starTime = System.nanoTime();
                    neighbours = NeighbourDetector.CellIndexMethod(io.getParticles(), io.getN(), io.getL(), 
                                                                    io.getM(), io.getRc(), isPeriodic);            
                    io.setOutput(neighbours, System.nanoTime() - starTime);
                }
                else if(args[i].equals("-b"))
                {
                    System.out.println("Calculation starting...");
                    starTime = System.nanoTime();
                    neighbours = NeighbourDetector.BruteForce(io.getParticles(),
                                                            io.getN(), io.getL(), io.getRc(), isPeriodic);
                    io.setOutput(neighbours, System.nanoTime() - starTime);
                }
                else
                    throw new IllegalArgumentException("Invalid arguments. For help: java -jar CellIndexMethod.jar -h");
            } else if(args[i].equals("-h") && args.length == 1) {
               printHelp();
               return;
            } else {
                throw new IllegalArgumentException("Invalid arguments. For help: java -jar CellIndexMethod.jar -h");
            }
            if(io != null)
                io.handleOutput();
        } catch(Exception e) {
            System.out.println(e);
            System.out.println(e.getStackTrace());
        }
    }

    private static void getMultipleTableInfo() {
        double radius = 0.25;
        int[] multipleFiles = { 10, 15, 25 , 35, 50, 70, 90, 100, 200, 300 , 600, 1000};
        System.out.println("M value: " +  10);
        System.out.println("rc value: " + 1);
        System.out.println("r  value: " + 0.25);
        System.out.println("L value: " + 20);
        System.out.println("CELL INDEX METHOD NOT PERIODIC");
        for(int i : multipleFiles) {
            System.out.println("N value: " + i);
            long starTime = System.nanoTime();
            NeighbourDetector.CellIndexMethod(generateParticles(i, radius, 20), i, 20, 
            10, 1, false);
            System.out.println("Excecution time: " + Long.toString(System.nanoTime() - starTime));
        }
        System.out.println("BRUTE FORCE NOT PERIODIC");
        for(int i : multipleFiles) {
            System.out.println("N value: " + i);
            long starTime = System.nanoTime();
            NeighbourDetector.BruteForce(generateParticles(i, radius, 20), i,20, 1, false);
            System.out.println("Excecution time: " + Long.toString(System.nanoTime() - starTime));
        }
        System.out.println("CELL INDEX METHOD PERIODIC");
        for(int i : multipleFiles) {
            System.out.println("N value: " + i);
            long starTime = System.nanoTime();
            NeighbourDetector.CellIndexMethod(generateParticles(i, radius, 20), i, 20, 
            10, 1, true);
            System.out.println("Excecution time: " + Long.toString(System.nanoTime() - starTime));
        }
        System.out.println("BRUTE FORCE PERIODIC");
        for(int i : multipleFiles) {
            System.out.println("N value: " + i);
            long starTime = System.nanoTime();
            NeighbourDetector.BruteForce(generateParticles(i, radius, 20), i, 20, 1, true);
            System.out.println("Excecution time: " + Long.toString(System.nanoTime() - starTime));
        }
    }

    private static void getMultipleDensityTableInfo() {
        double radius = 0.25;
        int[] multipleFiles = { 10, 15, 25 , 35, 50, 70, 90, 100, 200, 300 , 600, 1000};
        int[] multipleM = {5, 7, 8, 9, 10, 11, 12, 13};
        System.out.println("rc value: " + 1);
        System.out.println("r  value: " + 0.25);
        System.out.println("L value: " + 20);
        System.out.println("CELL INDEX METHOD NOT PERIODIC");
        for(int i : multipleFiles) {
            for(int j: multipleM) {
                System.out.println("N value: " + i);
                System.out.println("M value: " + j);
                System.out.println("D density value " + Double.toString(i/(double)(20.0*20.0)));
                long starTime = System.nanoTime();
                NeighbourDetector.CellIndexMethod(generateParticles(i, radius, 20), i, 20, 
                j, 1, false);
                System.out.println("Excecution time: " + Long.toString(System.nanoTime() - starTime));
            }
        }
        System.out.println("CELL INDEX METHOD PERIODIC");
        for(int i : multipleFiles) {
            for(int j: multipleM) {
                System.out.println("N value: " + i);
                System.out.println("M value: " + j);
                System.out.println("D density value " + Double.toString(i/(double)(20.0*20.0)));
                long starTime = System.nanoTime();
                NeighbourDetector.CellIndexMethod(generateParticles(i, radius, 20), i, 20, 
                j, 1, true);
                System.out.println("Excecution time: " + Long.toString(System.nanoTime() - starTime));
            }
        }
    }

    private static Particle[] generateParticles(int N, double r, double L) {
        Particle[] particles = new Particle[N];
        Random random = new Random();
        for(int i = 0; i < N; i++) {
            double x = random.nextDouble() * (L - 2 * r) + r;
            double y = random.nextDouble() * (L - 2 * r) + r;
            particles[i] = new Particle(r, x, y, i);
        }
        return particles;
    }

    private static void printHelp() {
        System.out.println("\n");
        System.out.println("Available Commands:");
        System.out.println("For multiple runs comparing brute force and CIM: java -jar CellIndexMethod.jar -m");
        System.out.println("For multiple runs to determine best M given a density: java -jar CellIndexMethod.jar -d");
        System.out.println("java -jar CellIndexMethod.jar -r [N] [RADIUS] [L] [STATIC FILE NAME] [DYNAMIC FILENAME]");
        System.out.println("java -jar CellIndexMethod.jar -i [STATIC FILE] [DYNAMIC FILE] [METHOD OPTIONS] [PERIODIC OR NOT]");
        System.out.println("METHOD OPTIONS: -c for cell index method or -b for brute force");
        System.out.println("PERIODIC OR NOT: -p for periodic contorn or -np for not periodic option.");
    }
}

