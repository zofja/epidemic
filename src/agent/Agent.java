package agent;

import simulators.Meeting;

import java.util.Random;
import java.util.Vector;

public abstract class Agent {

    private int id;
    private long seed;
    protected Vector<Agent> friends = new Vector<>();
    protected Vector<Meeting> meetings = new Vector<>();
    protected Vector<Agent> friendsOfFriends = new Vector<>();
    private boolean healthy;
    private boolean resistant;


    protected Random r = new Random(seed);

    public Agent(int i, long seed) {
        this.seed = seed;
        this.id = i;
        this.healthy = true;
        this.resistant = false;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    public void removeDead(Vector<Agent> morgue) {
        this.friends.removeAll(morgue);
        this.friendsOfFriends.removeAll(morgue);
    }

    public void setResistant(boolean resistant) {
        this.resistant = resistant;
    }

    public boolean getResistant() {
        return resistant;
    }

    public boolean getHealthy() {
        return healthy;
    }

    public int getId() {
        return id;
    }

    public void addAdjacent(Agent a) {
        friends.add(a);

    }

    public void addMeeting(int d) {
        Agent b;
        int i = r.nextInt(friends.size());
        b = friends.get(i);
        this.meetings.add(new Meeting(b, d));
    }

    public boolean plan(double ps) {
        return nextRandomBoolean(ps);
    }

    public boolean nextRandomBoolean(double d) {
        return r.nextDouble() < d;
    }

    public void addFriendsOfFriends() {

        for (Agent a : this.friends) {
            for (Agent b : a.friends) {
                if (!friendsOfFriends.contains(b)) {
                    friendsOfFriends.add(b);
                }
            }
        }
    }

    public String toString() {
        return this.id + (this.getHealthy() ? "" : "*") + ' ';
    }

    public Vector<Meeting> todayMeetings(int i) {
        Vector<Meeting> updatedMeetings = new Vector<>();
        for (Meeting s : this.meetings) {
            if (s.getDay() == i) {
                updatedMeetings.add(s);
            }
        }
        return updatedMeetings;
    }

    public String printAdjacent() {
        StringBuilder SB = new StringBuilder();
        for (Agent i : friends) {
            SB.append(i.getId() + " ");
        }
        SB.append("\n");
        return SB.toString();
    }
}
