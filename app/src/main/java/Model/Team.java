package Model;

import java.util.Objects;

public class Team {

    private int id;
    private String name;

    public Team(String name, int id){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id == team.id &&
                Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
