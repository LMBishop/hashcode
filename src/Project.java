import java.util.Map;

public class Project {

    private final String name;
    private final int duration;
    private final int bestBefore;
    private int score;
    private final int maxScore;
    private int completionDay;
    private int daysWorked;
    private Map<Role, Contributor> contributors;

    public Project(String name, int duration, int maxScore, int bestBefore) {
        this.name = name;
        this.duration = duration;
        this.maxScore = maxScore;
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

    public int getDaysWorked() {
        return daysWorked;
    }

    public boolean isComplete() {
        return this.daysWorked >= this.duration;
    }

    public void setDaysWorked(int daysWorked) {
        this.daysWorked = daysWorked;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public Map<Role, Contributor> getContributors() {
        return contributors;
    }

    public void setContributors(Map<Role, Contributor> contributors) {
        this.contributors = contributors;
    }

    public int getCompletionDay() {
        return completionDay;
    }

    public void setCompletionDay(int completionDay) {
        this.completionDay = completionDay;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean hasEnoughContributors() {
        // if missing contributor
        for (Map.Entry<Role, Contributor> entry : contributors.entrySet()) {
            if (entry.getValue() == null) {
                return false;
            }
        }
        return true;
    }
}
