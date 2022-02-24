import java.util.Map;

public class Contributor {

    private final String name;
    private Map<String, Integer> skills;

    public Contributor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
