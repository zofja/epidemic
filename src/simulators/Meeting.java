package simulators;

import agent.Agent;

public class Meeting {

    private Agent friend;
    private int day;

    public Meeting(Agent z, int day) {
        this.friend = z;
        this.day = day;
    }

    public Agent getFriend() {
        return friend;
    }

    public int getDay() {
        return day;
    }
}
