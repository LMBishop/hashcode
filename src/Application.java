import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Application {

    public static void main(String[] args) throws Exception {

        int day = 0;

        List<Project> projects = new ArrayList<>();
        List<Contributor> contributors = new ArrayList<>();

        List<Project> completedProjects = new ArrayList<>();


//        Contributor anna = new Contributor("Anna");
//        Map<String, Integer> annaSkills = new HashMap<>();
//        annaSkills.put("C++", 2);
//        anna.setSkills(annaSkills);
//
//        Contributor bob = new Contributor("Bob");
//        Map<String, Integer> bobSkills = new HashMap<>();
//        bobSkills.put("HTML", 5);
//        bobSkills.put("CSS", 5);
//        bob.setSkills(bobSkills);
//
//        Contributor maria = new Contributor("Maria");
//        Map<String, Integer> mariaSkills = new HashMap<>();
//        mariaSkills.put("Python", 3);
//        maria.setSkills(mariaSkills);
//
//        contributors.add(anna);
//        contributors.add(bob);
//        contributors.add(maria);

//        Project loggingProject = new Project("Logging", 5, 10, 5);
//        Map<Role, Contributor> loggingProjectContributors = new HashMap<>();
//        loggingProjectContributors.put(new Role("C++", 3), null);
//        loggingProject.setContributors(loggingProjectContributors);
//
//        Project webprojectProject = new Project("WebServer", 7, 10, 7);
//        Map<Role, Contributor> webprojectProjectContributors = new HashMap<>();
//        webprojectProjectContributors.put(new Role("HTML", 3), null);
//        webprojectProjectContributors.put(new Role("C++", 2), null);
//        webprojectProject.setContributors(webprojectProjectContributors);
//
//        Project webchatProject = new Project("WebChat", 10, 20, 20);
//        Map<Role, Contributor> webchatProjectContributors = new HashMap<>();
//        webchatProjectContributors.put(new Role("Python", 3), null);
//        webchatProjectContributors.put(new Role("HTML", 3), null);
//        webchatProject.setContributors(webchatProjectContributors);
//
//        projects.add(loggingProject);
//        projects.add(webprojectProject);
//        projects.add(webchatProject);

        int numContribs;
        int numProjects;

        List<String> lines = Files.readAllLines(Path.of("input.txt"));
        String[] numParts = lines.get(0).split(" ");
        numContribs = Integer.parseInt(numParts[0]);
        numProjects = Integer.parseInt(numParts[1]);

        int curLine = 1;

        for (int i = 0; i < numContribs; i++) {
            String contributorLine = lines.get(curLine);
            String[] contributorLineParts = contributorLine.split(" ");
            String name = contributorLineParts[0];
            int numSkills = Integer.parseInt(contributorLineParts[1]);
            Contributor contributor = new Contributor(name);

            Map<String, Integer> skills = new HashMap<>();
            for (int j = 0; j < numSkills; j++) {
                curLine++;
                String skillLine = lines.get(curLine);
                String[] skillLineParts = skillLine.split(" ");
                String skillName = skillLineParts[0];
                int skillLevel = Integer.parseInt(skillLineParts[1]);

                skills.put(skillName, skillLevel);
            }

            contributor.setSkills(skills);
            contributors.add(contributor);

            curLine++;
        }

        for (int i = 0; i < numProjects; i++) {
            String projectLine = lines.get(curLine);
            String[] projectLineParts = projectLine.split(" ");
            String name = projectLineParts[0];
            int duration = Integer.parseInt(projectLineParts[1]);
            int maxScore = Integer.parseInt(projectLineParts[2]);
            int bestBefore = Integer.parseInt(projectLineParts[3]);
            int numSkills = Integer.parseInt(projectLineParts[4]);

            Project project = new Project(name, duration, maxScore, bestBefore);

            Map<Role, Contributor> pContributors = new LinkedHashMap<>();
            for (int j = 0; j < numSkills; j++) {
                curLine++;
                String skillLine = lines.get(curLine);
                String[] skillLineParts = skillLine.split(" ");
                String skillName = skillLineParts[0];
                int skillLevel = Integer.parseInt(skillLineParts[1]);

                pContributors.put(new Role(skillName, skillLevel), null);
            }

            project.setContributors(pContributors);
            projects.add(project);

            curLine++;
        }

        System.out.println(numContribs + " contributors and " + numProjects + " projects");
        System.out.println("(" + contributors.size() + ", " + projects.size() + ")");

        while (true) {
            System.out.println("DAY " + day);
            for (Project p : projects) {
                if (p.hasEnoughContributors()) {
                    continue;
                }

                Map<Role, Contributor> roles = new LinkedHashMap<>(p.getContributors());

                for (Contributor contributor : contributors) {
                    if (!contributor.isAssigned(p.getName())) {

                        // for each role needed
                        for (Map.Entry<Role, Contributor> role : p.getContributors().entrySet()) {
                            // if role filled
                            if (role.getValue() != null) {
                                continue;
                            }

                            // if contributor has skill
                            if (contributor.getSkills().containsKey(role.getKey().getName()) && contributor.getSkills().get(role.getKey().getName()) >= role.getKey().getLevel()) {
                                roles.put(role.getKey(), contributor);
                                System.out.println(contributor.getName() + " assigned to " + p.getName());
                                contributor.assign(p.getName());
                            } else {
                                if (!contributor.getSkills().containsKey(role.getKey().getName())) {
                                    contributor.getSkills().put(role.getKey().getName(), 0);
                                }
                                if (role.getKey().getLevel() - contributor.getSkills().get(role.getKey().getName()) == 1) {
                                    // skill too low
                                    for (Map.Entry<Role, Contributor> otherentry : roles.entrySet()) {
                                        // unassigned
                                        if (role.getValue() == null) {
                                            continue;
                                        }

                                        if (otherentry.getValue().getSkills().containsKey(role.getKey().getName())
                                                && (otherentry.getValue().getSkills().get(role.getKey().getName()) >= role.getKey().getLevel())) {
                                            // mentorship
                                            roles.put(role.getKey(), contributor);
                                            System.out.println(contributor.getName() + " assigned to " + p.getName() + " (mentorship)");
                                            contributor.assign(p.getName());
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
                p.setContributors(roles);
            }

            ploop: for (Project p : projects) {
                if (p.hasEnoughContributors() && !p.isComplete()) {
                    for (Contributor c : p.getContributors().values()) {
                        if (c.getLockedTo() != null && !c.getLockedTo().equals(p.getName())) {
                            continue ploop;
                        }
                    }
                    for (Contributor c : p.getContributors().values()) {
                        System.out.println(c.getName() + " locked to " + p.getName());
                        c.setLockedTo(p.getName());
                    }
                    p.setDaysWorked(p.getDaysWorked() + 1);
                    if (p.isComplete()) {
                        completedProjects.add(p);
                        p.setCompletionDay(day);
                        p.setScore(Math.max(0, p.getBestBefore() - p.getCompletionDay() >= 0 ? p.getMaxScore() : p.getMaxScore() - ((p.getCompletionDay() + 1) - p.getBestBefore())));
                        System.out.println(p.getName() + " complete (" + p.getScore() + " points)");

                        for (Map.Entry<Role, Contributor> role : p.getContributors().entrySet()) {
                            role.getValue().unassign(p.getName());
                            if (role.getKey().getLevel() >= role.getValue().getSkills().get(role.getKey().getName())) {
                                role.getValue().getSkills().put(role.getKey().getName(), role.getValue().getSkills().get(role.getKey().getName()) + 1);
                                System.out.println(role.getValue().getName() + " skill increased in " + role.getKey().getName() + " to " + role.getValue().getSkills().get(role.getKey().getName()));
                            }
                            role.getValue().setLockedTo(null);
                        }
                    }
                }
            }

            if (completedProjects.size() == projects.size()) {
                break;
            }

            day++;
        }

        System.out.println("Simulation complete");
        int totalPoints = 0;
        for (Project p : projects) {
            totalPoints += p.getScore();
        }
        System.out.println("Final score: " + totalPoints);

        try {
            FileWriter myWriter = new FileWriter("output.txt");

            myWriter.write(numProjects + "\n"); // number of projects

            for (int i = 0; i < completedProjects.size(); i++) {

                Map<Role, Contributor> contributors2 = completedProjects.get(i).getContributors();
                myWriter.write(completedProjects.get(i).getName() + "\n");

                for (Map.Entry<Role,Contributor> entry : contributors2.entrySet()) {
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
