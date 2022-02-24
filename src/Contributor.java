import java.util.HashSet;
import java.util.Map;

public class Contributor {

    private final String name;
    private boolean assigned;
    private Map<String, Integer> skills;
    private HashSet<String> assignments;
    private String lockedTo;

    public Contributor(String name) {
        this.assignments = new HashSet<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isAssigned(String assignment) {
        return assignments.contains(assignment);
    }

    public void assign(String assignment) {
        this.assignments.add(assignment);
    }

    public void unassign(String assignment) {
        this.assignments.remove(assignment);
    }

    public String getLockedTo() {
        return lockedTo;
    }

    public void setLockedTo(String lockedTo) {
        this.lockedTo = lockedTo;
    }

    public Map<String, Integer> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, Integer> skills) {
        this.skills = skills;
    }
}
