
public class Test {

	public static void main(String[] args) {
		Particle[] p = {new Particle(1, 4, 14, 0), new Particle(1, 4, 17, 1), new Particle(0.5, 12, 13, 2)};
		NeighbourDetector n = new NeighbourDetector();
		boolean[][] o = n.CellIndexMethod(p, p.length, 20, 4, 1, true);
		boolean[][] o1 = n.BruteForce(p, 3, 20, 1, true);
		for(int i = 0; i < o.length; i++) {
			for(int j = 0; j< o.length; j++) {
				System.out.print(" " + o[i][j]);
			}
			System.out.println("");
		}

	}

}
