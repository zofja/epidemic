package simulators;

import agent.Agent;
import agent.Friendly;
import agent.Regular;
import graph.Edge;
import graph.Network;

import java.util.Random;
import java.util.Vector;

public class AgentSimulation {

    private long seed;
    private int numberOfAgents;
    private double friendlyProbability;
    private int friendsAverage;

    private Random r = new Random(seed);

    public AgentSimulation(long seed, int numberOfAgents, int friendsAverage, double friendlyProbability) {
        this.seed = seed;
        this.numberOfAgents = numberOfAgents;
        this.friendsAverage = friendsAverage;
        this.friendlyProbability = friendlyProbability;
    }

    public boolean nextRandomBoolean(double d) {
        return r.nextDouble() < d;
    }

    public void addAgent(Vector<Agent> agents, Edge e) {

        int i = e.getU();
        int j = e.getV();

        agents.get(i - 1).addAdjacent(agents.get(j - 1));
        agents.get(j - 1).addAdjacent(agents.get(i - 1));
    }

    public void generatePopulation(Vector<Agent> agents) {
        for (int i = 0; i < numberOfAgents; i++) {
            boolean type = nextRandomBoolean(friendlyProbability);
            if (type) {
                agents.add(i, new Friendly(i + 1, seed));
            } else {
                agents.add(i, new Regular(i + 1, seed));
            }
        }
    }

    public void simulate(Vector<Agent> agents) {

        generatePopulation(agents);
        Network S = new Network(numberOfAgents, seed);

        S.genEdges();

        int m = numberOfAgents * friendsAverage / 2;

        for (int i = 0; i < m; i++) {
            Edge e = S.getRandomEdge();
            addAgent(agents, e);
        }

        for (Agent a : agents) {
            a.addFriendsOfFriends();
        }

        int z = r.nextInt(numberOfAgents) + 1;

        agents.get(z - 1).setHealthy(false);
    }
}
