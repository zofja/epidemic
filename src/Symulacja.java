import IO.Data;
import agent.Agent;
import exceptions.NoFileGiven;
import exceptions.NoKeyValueGiven;
import exceptions.WrongFileFormat;
import exceptions.WrongKeyValue;
import simulators.AgentSimulation;
import simulators.DaySimulation;

import java.util.Vector;

public class Symulacja {

    private static Vector<Agent> agenci = new Vector<>();

    public static void main(String[] args) {

        Data data = new Data();

        try {
            data.read();

            AgentSimulation SA = new AgentSimulation(data.getSeed(), data.getNumberOfAgents(), data.getFriendsAverage(), data.getFriendlyProbability());

            SA.simulate(agenci);

            DaySimulation SD = new DaySimulation(data.getSeed(), data.getMeetingProbability(), data.getInfectionProbability(), data.getRecoveryProbability(), data.getMortality(), data.getDays());

            SD.simulate(agenci, data.getFilename(), data.getData());
        } catch (NoKeyValueGiven e) {
            System.out.println("Brak wartości dla klucza " + e.getMessage());
        } catch (NoFileGiven e) {
            System.out.println("Brak pliku " + e.getMessage());
        } catch (WrongFileFormat e) {
            System.out.println(e.getMessage());
        } catch (WrongKeyValue e) {
            System.out.println("Niedozwolona wartość " + e.getMessage());
        }

    }
}
