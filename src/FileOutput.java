import jdk.dynalink.linker.LinkerServices;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FileOutput {

    public static void setNumOfProjects(int numOfProjects) {
        FileOutput.numOfProjects = numOfProjects;
    }

    public static void setProjects(LinkedList<Project> projects) {
        FileOutput.projects = projects;
    }

    private static int numOfProjects;

    private static LinkedList<Project> projects = new LinkedList<>();

    public static void writeToFile(String fileName){
        try {
            FileWriter myWriter = new FileWriter(fileName + ".txt");

            myWriter.write(numOfProjects + "\n"); // number of projects

            for (int i = 0; i < numOfProjects; i++) {

                LinkedHashMap<Role, Contributor> contributors = projects.get(i).getContributors();
                myWriter.write(projects.get(i).getName() + "\n");

                for (Map.Entry<Role,Contributor> entry : contributors.entrySet()) {
                    myWriter.write(entry.getValue().getName() + " ");
                }
                myWriter.write("\n");
            }

            myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
