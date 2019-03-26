package agent;

import simulators.Meeting;

public class Friendly extends Agent {

    public Friendly(int id, long seed) {
        super(id, seed);
    }

    @Override
    public String toString() {
        return super.toString() + "towarzyski";
    }

    @Override
    public void addMeeting(int d) {

        if (!this.getHealthy() && !this.getResistant()) {
            int i = r.nextInt(friends.size());
            Agent b = friends.get(i);
            this.meetings.add(new Meeting(b, d));
        } else {
            super.addMeeting(d);
        }
    }
}
