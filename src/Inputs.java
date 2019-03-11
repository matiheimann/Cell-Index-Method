import java.util.Random;

public final class Inputs
{
    private double L = 20; // Side length
    private double rc = 1; // interaction radius
    private double r = 0.25; // radius
    private int N; // number of particles
    private int M; // gridSize
    private Particle[] particles;
    private boolean isPeriodic;
    
    
    public Inputs(int maxN, int maxM)
    {
        Random rand = new Random();
        this.N = rand.nextInt(maxN) + 1;
        this.M = rand.nextInt(maxM) + 1;
        this.particles = new Particle[N];
        for(int i = 0; i < particles.length; i++) {
            particles[i] =  new Particle(this.r, Math.random() * this.L, Math.random() * this.L, i);
        }

    }

    public Inputs(int N, double L, int M, double rc, double r) 
    {
        this(N, L, M, rc, r, false);
    }

    public Inputs(int N, double L, int M, double rc, double r, boolean isPeriodic) 
    {
        this.N = N;
        this.L = L;
        this.M = M;
        this.rc = rc;
        this.r = r;
        this.isPeriodic = isPeriodic;
        for(int i = 0; i < particles.length; i++) {
            particles[i] =  new Particle(this.r, Math.random() * this.L, Math.random() * this.L, i);
        }
    }

    public double getL() {
        return L;
    }

    public double getRc() {
        return rc;
    }

    public double getR() {
        return r;
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

    public boolean isPeriodic() {
        return isPeriodic;
    }
}