import java.util.Random;

public class Main 
{
    public static void main(String[] args) {
        try {
            if(args.length == 0)
                throw new IllegalArgumentException("No arguments were passed");
            if(!args[0].equals("-g") && !args[0].equals("-i"))
                throw new IllegalArgumentException("Invalid arguments.");
            IOManager inputs = null;
            if(args[0].equals("-r") && args.length == 4) {
                inputs = new IOManager(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            }
            else if(args[0].equals("-i") &&  args.length == 7) {
                inputs = new IOManager(Integer.parseInt(args[2]), 
                                        Double.parseDouble(args[3]), 
                                        Integer.parseInt(args[4]),
                                        Double.parseDouble(args[5]),
                                        Double.parseDouble(args[6]));
            }
            // TODO: else clause
            if(args[1].equals("-b"))
                NeighbourDetector.BruteForce(inputs.getParticles(), inputs.getN(), inputs.getL(), 
                                            inputs.getM(), inputs.getRc(), inputs.isPeriodic());
            else if(args[1].equals("-CIM"))
                NeighbourDetector.CellIndexMethod(inputs.getParticles(), inputs.getN(), inputs.getL(), 
                                          inputs.getM(), inputs.getRc(), inputs.isPeriodic());
            // TODO: else clause
            
        } catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
