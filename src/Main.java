public class Main 
{
    public static void main(String[] args) {
        try {
            boolean isPeriodic;
            IOManager io = null;
            long starTime;
            int i = 0;
            if (args.length == 0)
                throw new IllegalArgumentException("No arguments were passed. For help: java -jar CellIndexMethod.jar -h");
            if (!args[i].equals("-r") && !args[i].equals("-i") && !args[i].equals("-h"))
                throw new IllegalArgumentException("Invalid arguments. For help: java -jar CellIndexMethod.jar -h");
            // Generate files
            if (args[i].equals("-r") && args.length == 6) {
                i++;
                io = new IOManager(Integer.parseInt(args[i++]), 
                                        Double.parseDouble(args[i++]), 
                                        Double.parseDouble(args[i++]));
                io.writeRandomInputs(args[i++], args[i]);
                return;
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
                    starTime = System.currentTimeMillis();                     
                    io.setOutput(NeighbourDetector.CellIndexMethod(io.getParticles(),
                                                    io.getN(), io.getL(), 
                                                    io.getM(), io.getRc(), isPeriodic), System.currentTimeMillis() - starTime);
                }
                else if(args[i].equals("-b"))
                {
                    System.out.println("Calculation starting...");
                    starTime = System.currentTimeMillis();
                    io.setOutput(NeighbourDetector.BruteForce(io.getParticles(),
                        io.getN(), io.getL(), io.getRc(), isPeriodic), System.currentTimeMillis() - starTime);
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

    private static void printHelp() {
        System.out.println("\n");
        System.out.println("Available Commands:");
        System.out.println("java -jar CellIndexMethod.jar -r [MAX N] [MAX RADIUS] [MAXL] [STATIC FILE NAME] [DYNAMIC FILENAME]");
        System.out.println("java -jar CellIndexMethod.jar -i [STATIC FILE] [DYNAMIC FILE] [METHOD OPTIONS] [PERIODIC OR NOT][MINL] [STATIC FILE NAME] [DYNAMIC FILENAME]");
        System.out.println("METHOD OPTIONS: -c for cell index method or -b for brute force");
        System.out.println("PERIODIC OR NOT: -p for periodic contorn or -np for not periodic option.");
    }
}

