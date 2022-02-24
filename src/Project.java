import java.util.Map;

public class Project {

    private String name;
    private int duration;
    private int score;
    private int bestBefore;
    private Map<Role, Contributor> contributors;

    private Project(String name, int duration, int score, int bestBefore) {
        this.name = name;
        this.duration = duration;
        this.score = score;
        this.bestBefore = bestBefore;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getScore() {
        return score;
    }

    public int getBestBefore() {
        return bestBefore;
    }

    public Map<Role, Contributor> getContributors() {
        return contributors;
    }
}
