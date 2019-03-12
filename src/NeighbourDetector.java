import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class NeighbourDetector {
	
	
	
	public static Map<Integer, List<Particle>> BruteForce(Particle[] particles, int N, double L,  double rc, boolean isPeriodic) {
		Map<Integer, List<Particle>> output = new HashMap<>();
		for (Particle particle : particles)
		{
			output.put(particle.getNumber(), getNeighboursByBruteForce(particle, particles, L, rc, isPeriodic));
		}
		return output;
	}
	
	public static Map<Integer, List<Particle>> CellIndexMethod(Particle[] particles, int N, double L, int M, double rc, boolean isPeriodic) {
		boolean[][] output = new boolean[particles.length][particles.length];
		Map<Integer, LinkedList<Particle>> grid = makeGrid(particles, L, M);
		for (Particle particle : particles)
		{
			boolean[] neighboursOfParticle = getNeighboursByCellIndexMethod(grid, particle, particles, L, M, rc, isPeriodic);
			for(int i = 0; i < neighboursOfParticle.length; i++) {
				if(neighboursOfParticle[i]) {
					output[i][particle.getNumber()] = true;
					output[particle.getNumber()][i] = true;
				}
			}
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

	private static boolean[] getNeighboursByCellIndexMethod(Map<Integer, LinkedList<Particle>> grid, 
																	Particle particle, 
																	Particle[] particles, 
																	double L, int M, double rc, boolean isPeriodic)
	{
		boolean[] neighbours = new boolean[particles.length];
		double gridSideLength = L / M;
		int gridParticle = (int) Math.floor(particle.getX() / gridSideLength) + 
				(int) Math.floor(particle.getY() / gridSideLength) * M;
		int row = Math.floorDiv(gridParticle, M);
		int column = Math.floorMod(gridParticle, M);
		int neighbourRow;
		int neighbourColumn;
		int neighbourGrid;
		for(int i = 0; i <= 1; i++) {
			for(int j = 0; j <= 1; j++) {
				if(!(row + i == M || column + j == M) || isPeriodic) {
					neighbourRow = Math.floorMod(row + i, M);
					neighbourColumn = Math.floorMod(column + j, M);
					neighbourGrid = neighbourRow * M + neighbourColumn;
					List<Particle> cellParticles = grid.get(neighbourGrid);
					if(cellParticles != null) {
						for(Particle p : cellParticles) {
							if(isPeriodic) {
								if(particle.getPeriodicContornDistance(p, L) <= rc) {
									neighbours[particle.getNumber()] = true;
								}
							}
							else {
								if(particle.getDistance(p) <= rc) {
									neighbours[particle.getNumber()] = true;
								}
							}
						}
					}
				}	
			}
		}
		
		//Check for Lower Right Tile
		if(!(row + 1 == M || column - 1 == -1) || isPeriodic) {
			neighbourRow = Math.floorMod(row + 1, M);
			neighbourColumn = Math.floorMod(column -1, M);
			neighbourGrid = neighbourRow * M + neighbourColumn;
			List<Particle> cellParticles = grid.get(neighbourGrid);
			if(cellParticles != null) {
				for(Particle p : cellParticles) {
					if(isPeriodic) {
						if(particle.getPeriodicContornDistance(p, L) <= rc) {
							neighbours[particle.getNumber()] = true;
						}
					}
					else {
						if(particle.getDistance(p) <= rc) {
							neighbours[particle.getNumber()] = true;
						}
					}
				}
			}	
		}
		
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
