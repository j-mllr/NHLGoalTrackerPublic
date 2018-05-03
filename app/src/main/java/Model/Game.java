package Model;

public class Game {

    private int id;
    private Team home;
    private Team away;

    public Game(int id, Team away, Team home){
        this.id = id;
        this.away = away;
        this.home = home;
    }

    public int getId() {
        return id;
    }

    public Team getHome() {
        return home;
    }

    public Team getAway() {
        return away;
    }

    public boolean isPlaying(String s){
        return home.getName().equals(s) || away.getName().equals(s);
    }
}
