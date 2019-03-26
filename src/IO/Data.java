package IO;

import exceptions.NoFileGiven;
import exceptions.NoKeyValueGiven;
import exceptions.WrongFileFormat;
import exceptions.WrongKeyValue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class Data {

    private long seed;
    private int numberOfAgents;
    private int friendsAverage;
    private double friendlyProbability;
    private double meetingProbability;
    private double infectionProbability;
    private double recoveryProbability;
    private double mortality;
    private int days;
    private String filename;

    public Data() {
    }

    private Properties p = new Properties();
    StringBuilder SB = new StringBuilder();

    private String readProperty(String s) throws NoKeyValueGiven {
        String value = p.getProperty(s);
        if (value == null) {
            throw new NoKeyValueGiven(s);
        }
        return value;
    }

    private long readLong(String s, String key) throws WrongKeyValue {
        long value;
        try {
            value = Long.parseLong(s);
        } catch (NumberFormatException e) {
            throw new WrongKeyValue(s, key);
        }
        return value;
    }

    private int readInteger(String s, String key, int min, int max) throws WrongKeyValue {
        int value;
        try {
            value = Integer.parseInt(s);
            if (value < min || value > max) {
                throw new WrongKeyValue(s, key);
            }
        } catch (NumberFormatException e) {
            throw new WrongKeyValue(s, key);
        }
        return value;
    }

    private double readDouble(String s, String key) throws WrongKeyValue {
        double value;
        try {
            value = Double.parseDouble(s);
            if (value < 0 || value > 1) {
                throw new WrongKeyValue(s, key);
            }
        } catch (NumberFormatException e) {
            throw new WrongKeyValue(s, key);
        }
        return value;
    }

    public void read() throws NoKeyValueGiven, NoFileGiven, WrongFileFormat, WrongKeyValue {
        try {
            FileInputStream stream = new FileInputStream("/epidemy/default.properties");
            Reader reader = Channels.newReader(stream.getChannel(), StandardCharsets.UTF_8.name());
            p.load(reader);

        } catch (InvalidPropertiesFormatException e) {
            throw new WrongFileFormat("default.properties nie jest tekstowy");
        } catch (IOException e) {
            throw new NoFileGiven("default.properties");
        }

        try {
            FileInputStream stream1 = new FileInputStream("/epidemy/simulation-conf.xml");
            p.loadFromXML(stream1);
        } catch (InvalidPropertiesFormatException e) {
            throw new WrongFileFormat("simulation-conf.xml nie jest XML");
        } catch (IOException e) {
            throw new NoFileGiven("simulation-conf.xml");
        }
        this.load();
    }

    private void load() throws NoKeyValueGiven, WrongKeyValue {

        String property;

        property = readProperty("seed");
        this.seed = readLong(property, "seed");
        SB.append("getSeed=" + seed + "\n");

        property = readProperty("liczbaAgentów");
        this.numberOfAgents = readInteger(property, "liczbaAgentów", 1, 1000000);
        SB.append("getNumberOfAgents=" + numberOfAgents + "\n");

        property = readProperty("prawdTowarzyski");
        friendlyProbability = readDouble(property, "prawdTowarzyski");
        SB.append("getFriendlyProbability=" + friendlyProbability + "\n");

        property = readProperty("prawdSpotkania");
        meetingProbability = readDouble(property, "prawdSpotkania");
        if (meetingProbability == 1) {
            throw new WrongKeyValue(property, "prawdSpotkania");
        }
        SB.append("getMeetingProbability=" + meetingProbability + "\n");

        property = readProperty("prawdZarażenia");
        infectionProbability = readDouble(property, "prawdZarażenia");
        SB.append("getInfectionProbability=" + infectionProbability + "\n");

        property = readProperty("prawdWyzdrowienia");
        recoveryProbability = readDouble(property, "prawdWyzdrowienia");
        SB.append("getRecoveryProbability=" + recoveryProbability + "\n");

        property = readProperty("śmiertelność");
        mortality = readDouble(property, "śmiertelność");
        SB.append("getMortality=" + mortality + "\n");

        property = readProperty("liczbaDni");
        days = readInteger(property, "liczbaDni", 0, 1000);
        SB.append("liczbaDni=" + days + "\n");

        property = readProperty("śrZnajomych");
        friendsAverage = readInteger(property, "śrZnajomych", 0, numberOfAgents - 1);
        SB.append("getFriendsAverage=" + friendsAverage + "\n");

        filename = readProperty("plikZRaportem");
    }

    public double getMeetingProbability() {
        return meetingProbability;
    }

    public double getRecoveryProbability() {
        return recoveryProbability;
    }

    public double getInfectionProbability() {
        return infectionProbability;
    }

    public double getMortality() {
        return mortality;
    }

    public int getDays() {
        return days;
    }

    public long getSeed() {
        return seed;
    }

    public String getFilename() {
        return filename;
    }

    public int getNumberOfAgents() {
        return numberOfAgents;
    }

    public double getFriendlyProbability() {
        return friendlyProbability;
    }

    public int getFriendsAverage() {
        return friendsAverage;
    }

    public String getData() {
        return SB.toString();
    }
}