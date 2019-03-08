import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ParticleSystem {
	
	private HashMap<Integer, Location2D> particles;
	private long sideLength;
	private double interactionRatio;
	
	public ParticleSystem(int numberOfParticles, long sideLength, 
			double interactionRatio) {
	
		this.sideLength = sideLength;
		this.interactionRatio = interactionRatio;
		this.particles = new HashMap<>();
		
		for(int i = 0; i < numberOfParticles; i++) {
			particles.put(i, new Location2D(Math.random() * sideLength, 
					Math.random() * sideLength));
		}
		
	}
	
	public List<Integer> getNeighboursByBruteForce(int particle){
		
		List<Integer> neighbours = new LinkedList<>();
		Location2D locationParticle = this.particles.get(particle);
		Iterator<Entry<Integer, Location2D>> it = this.particles.entrySet().iterator();
		
		while(it.hasNext()) {
			Map.Entry<Integer, Location2D> entry = (Entry<Integer, Location2D>) it.next();
			if(locationParticle.getDistanceToLocation(entry.getValue()) <=
					this.interactionRatio) {
				neighbours.add(entry.getKey());
			}
		}
		
		return neighbours;
		
	}
	
	public List<Integer> getNeighboursByCellIndexMethod(int particle, int gridsBySide, 
			boolean isPeriodic){
		
		List<Integer> neighbours = new LinkedList<>();
		HashMap<Integer, LinkedList<Integer>> grids = makeGrids(gridsBySide);
		double gridSideLength = Double.valueOf(this.sideLength) / gridsBySide;
		int gridParticle = (int) Math.floor(particles.get(particle).getX() / gridSideLength) + 
				(int) Math.floor(particles.get(particle).getY() / gridSideLength) * gridsBySide;
		int row = Math.floorDiv(gridParticle, gridsBySide);
		int column = Math.floorMod(gridParticle, gridsBySide);
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				if((row + i == gridsBySide || row + i == -1 || 
						column + j == gridsBySide || column + j == -1) || isPeriodic) {
					int neighbourRow = Math.floorMod(row + i, gridsBySide);
					int neighbourColumn = Math.floorMod(column + j, gridsBySide);
					int neighbourGrid = neighbourRow * gridsBySide + neighbourColumn;
					if(grids.get(neighbourGrid) != null) {
						neighbours.addAll(grids.get(neighbourGrid));
					}
				}	
			}
		}
		
		neighbours.remove(particle);
		
		return neighbours;
	}
	
	public HashMap<Integer, LinkedList<Integer>> makeGrids(int gridsBySide){
		
		HashMap<Integer, LinkedList<Integer>> grids = new HashMap<>();
		Iterator<Entry<Integer, Location2D>> it = this.particles.entrySet().iterator();
		
		double gridSideLength = Double.valueOf(this.sideLength) / gridsBySide;
		
		while(it.hasNext()) {
			
			Map.Entry<Integer, Location2D> entry = (Entry<Integer, Location2D>) it.next();
			int grid = (int) Math.floor(entry.getValue().getX() / gridSideLength) + 
					(int) Math.floor(entry.getValue().getY() / gridSideLength) * gridsBySide;
			
			if(grids.get(grid) == null) {
				grids.put(grid, new LinkedList<>());
			}
			
			grids.get(grid).add(entry.getKey());
			
		}
		
		return grids;
	}
	
}
