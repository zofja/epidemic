package simulators;

import IO.Out;
import agent.Agent;

import java.util.Random;
import java.util.Vector;

public class DaySimulation {

    private long seed;
    private double meetingProbability;
    private double infectionProbability;
    private double recoveryProbability;
    private double mortality;
    private int days;

    private Random r = new Random(seed);

    public DaySimulation(long seed, double meetingProbability, double infectionProbability, double recoveryProbability, double mortality, int days) {
        this.seed = seed;
        this.meetingProbability = meetingProbability;
        this.infectionProbability = infectionProbability;
        this.recoveryProbability = recoveryProbability;
        this.mortality = mortality;
        this.days = days;
    }

    public boolean nextRandomBoolean(double d) {
        return r.nextDouble() < d;
    }

    public void meet(Vector<Agent> agents, int i) {
        for (Agent a : agents) {
            Vector<Meeting> meetings = a.todayMeetings(i);
            for (Meeting m : meetings) {
                if (m.getDay() == i) {
                    if (!a.getHealthy() && !a.getResistant()) {
                        Agent b = m.getFriend();
                        if (!b.getResistant()) {
                            boolean infected = nextRandomBoolean(infectionProbability);
                            if (infected) {
                                b.setHealthy(false);
                            }
                        }
                    }
                }
            }
        }
    }

    public void plan(Vector<Agent> agents, int i, int d) {

        Vector<Agent> morgue = new Vector<>();

        for (Agent a : agents) {
            if (!morgue.contains(a)) {
                if (!a.getHealthy()) {
                    boolean dead = nextRandomBoolean(mortality);

                    if (dead) {
                        morgue.add(a);
                    }
                }
            }

            if (!morgue.contains(a)) {
                if (!a.getHealthy()) {
                    boolean healthy = nextRandomBoolean(recoveryProbability);
                    if (healthy) {
                        a.setHealthy(true);
                        a.setResistant(true);
                    }
                }

                boolean meet;

                do {
                    meet = a.plan(meetingProbability);
                    if (meet) {
                        int dd = r.nextInt(d) + i + 1;
                        a.addMeeting(dd);
                    }
                } while (meet);
            }
        }

        for (Agent a : agents) {
            a.removeDead(morgue);
        }
        agents.removeAll(morgue);
    }

    public void simulate(Vector<Agent> agents, String file, String data) {
        Out out = new Out(agents, file);
        out.printData(data);
        out.printAgents();
        out.printGraph();

        StringBuilder SB = new StringBuilder();

        for (int i = 0; i < days; i++) {
            plan(agents, i, days);
            meet(agents, i);
            SB.append(print(agents));
        }

        out.printReport(SB.toString());
        out.print();
    }

    public String print(Vector<Agent> agents) {
        int healthy = 0;
        int diseased = 0;
        int immune = 0;
        for (Agent a : agents) {
            if (a.getHealthy() && !a.getResistant()) healthy++;
            if (a.getResistant()) immune++;
            if (!a.getHealthy()) diseased++;
        }
        return (healthy + " " + diseased + " " + immune + "\n");
    }
}
