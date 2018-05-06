package Model;

import java.util.Objects;

public class Game {

    private int id;
    private Team home;
    private Team away;
    private String startTime;
    private String currEvent;
    private int scoreAway;
    private int scoreHome;

    public Game(int id, Team away, Team home){
        this.id = id;
        this.away = away;
        this.home = home;
    }

    public String getStartTime() {
        return startTime;
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

    public void setTime(String t){
        this.startTime = t;
    }

    public void setCurrEvent(String currEvent) {
        this.currEvent = currEvent;
    }

    public String getCurrEvent() {
        return currEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id &&
                Objects.equals(home, game.home) &&
                Objects.equals(away, game.away);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, home, away);
    }
}
