import java.util.Random;

public class Main 
{
    public static void main(String[] args) {
        try {
            boolean isPeriodic;
            IOManager io;
            int i = 0;

            if (args.length == 0)
                throw new IllegalArgumentException("No arguments were passed");
            if (!args[i].equals("-") && !args[i].equals("-i"))
                throw new IllegalArgumentException("Invalid arguments.");
            // Generate files
            if (args[i].equals("-r") && args.length == 6) {
                i++;
                io = new IOManager(Integer.parseInt(args[i++]), 
                                        Double.parseDouble(args[i++]), 
                                        Double.parseDouble(args[i++]));
                io.writeRandomInputs(args[i++], args[i]);
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
                double startTime = TIme
                // Method determination
                if(args[i].equals("-c"))
                     
                    io.setOutput(NeighbourDetector.CellIndexMethod(io.getParticles(),
                                                    io.getN(), io.getL(), 
                                                    io.getM(), io.getRc(), isPeriodic));
                else if(args[i].equals("-b"))
                    io.setOutput(NeighbourDetector.BruteForce(io.getParticles(),
                        io.getN(), io.getL(), io.getRc(), isPeriodic));
                else
                    throw new IllegalArgumentException("Invalid arguments.");
            } else {
                throw new IllegalArgumentException("Invalid arguments.");
            }
            io.HandleOutput();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
