import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class NeighbourDetector {
	public static boolean[][] BruteForce(Particle[] particles, int N, double L,  double rc, boolean isPeriodic) {
		boolean[][] output = new boolean[particles.length][particles.length];
		for(int i = 0; i < particles.length; i++) {
			for(int j = 0; j < i; j++) {
				if((!isPeriodic && particles[i].getDistanceTo(particles[j]) <= rc) 
						|| (isPeriodic && particles[i].getPeriodicContornDistance(particles[j], L) <= rc)) {
					output[i][j] = true;
					output[j][i] = true;
				}				
			}
		}
		return output;
	}
	
	public static boolean[][] CellIndexMethod(Particle[] particles, int N, double L, int M, double rc, boolean isPeriodic) {
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
