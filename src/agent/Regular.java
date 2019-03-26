package agent;

public class Regular extends Agent {

    public Regular(int id, long seed) {
        super(id, seed);
    }

    @Override
    public String toString() {
        return super.toString() + "zwykły";
    }

    @Override
    public boolean plan(double ps) {
        if (!this.getHealthy()) return nextRandomBoolean(ps * 0.5);
        else return super.plan(ps);
    }
}
