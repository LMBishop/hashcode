import java.util.Map;

public class Contributor {

    private final String name;
    private boolean assigned;
    private Map<String, Integer> skills;

    public Contributor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public Map<String, Integer> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, Integer> skills) {
        this.skills = skills;
    }
}
