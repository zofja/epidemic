package IO;

import agent.Agent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class Out {

    Vector<Agent> agents;
    private String file;
    private String data;
    private String record;
    private String graph;
    private String report;

    public Out(Vector<Agent> agents, String file) {
        this.agents = new Vector<>(agents);
        this.file = file;
    }

    public void print() {
        FileWriter FW = null;
        BufferedWriter BW = null;
        try {
            FW = new FileWriter(file);
            BW = new BufferedWriter(FW);

            BW.write("# twoje wyniki powinny zawierać te komentarze\n");
            BW.write(data);

            BW.write("# agents jako: getId typ lub getId* typ dla chorego\n");
            BW.write(record);

            BW.write("# graph\n");
            BW.write(graph);

            BW.write("# liczność w kolejnych dniach\n");
            BW.write(report);

        } catch (IOException e) {
            System.out.println("Nie można zapisać raportu do pliku");
        } finally {
            try {
                if (BW != null) {
                    BW.close();
                }

                if (FW != null) {
                    FW.close();
                }
            } catch (IOException e) {
                System.out.println("Nie można zamknąć pliku");
            }
        }
    }

    public void printAgents() {
        StringBuilder SB = new StringBuilder();
        for (Agent a : agents) {
            SB.append(a.toString() + "\n");
        }
        this.record = SB.toString() + "\n";
    }

    public void printGraph() {
        StringBuilder SB = new StringBuilder();

        for (Agent a : agents) {
            SB.append(a.getId() + " ");
            SB.append(a.printAdjacent());
        }
        this.graph = SB.toString() + "\n";
    }

    public void printReport(String s) {
        this.report = s;
    }

    public void printData(String s) {
        this.data = s + "\n";
    }
}
