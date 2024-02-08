package mca.miniproject.healthchallenge;
public class Badge {
    private int id;
    private String name;
    private String description;

    public Badge(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
