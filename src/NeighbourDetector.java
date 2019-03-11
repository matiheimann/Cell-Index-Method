import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class NeighbourDetector {

	public static Map<Integer, List<Particle>> BruteForce(Particle[] particles, int N, double L, int M, double rc, boolean isPeriodic) {
		Map<Integer, List<Particle>> output = new HashMap<>();
		for (Particle particle : particles)
		{
			output.put(particle.getNumber(), getNeighboursByBruteForce(particle, particles, L, rc, isPeriodic));
		}
		return output;
	}
	
	public static Map<Integer, List<Particle>> CellIndexMethod(Particle[] particles, int N, double L, int M, double rc, boolean isPeriodic) {
		Map<Integer, List<Particle>> output = new HashMap<>();
		Map<Integer, LinkedList<Particle>> grid = makeGrid(particles, L, M);
		for (Particle particle : particles)
		{
			output.put(particle.getNumber(), getNeighboursByCellIndexMethod(grid, particle, particles, L, M, rc, isPeriodic));
		}
		return output;
	}

	private static List<Particle> getNeighboursByBruteForce(Particle particle, Particle[] particles, double L, double rc, boolean isPeriodic) {
		List<Particle> neighbours = new LinkedList<>();
		for(Particle other : particles) {
			if(particle.getNumber() != other.getNumber()) {
				if((!isPeriodic && particle.getDistanceTo(other) <= rc) 
					|| (isPeriodic && particle.getPeriodicContornDistance(particle, L) <= rc)) 
					neighbours.add(other);
			}
		}
		return neighbours;
	}

	private static List<Particle> getNeighboursByCellIndexMethod(Map<Integer, LinkedList<Particle>> grid, 
																	Particle particle, 
																	Particle[] particles, 
																	double L, int M, double rc, boolean isPeriodic)
	{
		List<Particle> neighbours = new LinkedList<>();
		double gridSideLength = L / M;
		int gridParticle = (int) Math.floor(particle.getX() / gridSideLength) + 
				(int) Math.floor(particle.getY() / gridSideLength) * M;
		int row = Math.floorDiv(gridParticle, M);
		int column = Math.floorMod(gridParticle, M);
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				if(!(row + i == M || row + i == -1 || 
						column + j == M || column + j == -1) || isPeriodic) {
					int neighbourRow = Math.floorMod(row + i, M);
					int neighbourColumn = Math.floorMod(column + j, M);
					int neighbourGrid = neighbourRow * M + neighbourColumn;
					if(grid.get(neighbourGrid) != null) {
						neighbours.addAll(grid.get(neighbourGrid));
					}
				}	
			}
		}
		neighbours.remove(particle);
		return neighbours;
	}
	
	public static Map<Integer, LinkedList<Particle>> makeGrid(Particle[] particles, double L, int M){
		HashMap<Integer, LinkedList<Particle>> grid = new HashMap<>();
		double gridSideLength = L/M;
		
		for(Particle particle : particles) {
			int tile = (int) Math.floor(particle.getX() / gridSideLength) + 
					(int) Math.floor(particle.getY() / gridSideLength) * M;
			
			if(grid.get(tile) == null) {
				grid.put(tile, new LinkedList<>());
			}
			grid.get(tile).add(particle);
		}
		return grid;
	}
}
