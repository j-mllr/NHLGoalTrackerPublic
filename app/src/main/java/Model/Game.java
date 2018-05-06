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
    private int period;
    private String timeInPeriod;

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

    public void setScoreAway(int scoreAway) {
        this.scoreAway = scoreAway;
    }

    public void setScoreHome(int scoreHome) {
        this.scoreHome = scoreHome;
    }

    public int getScoreAway() {
        return scoreAway;
    }

    public int getScoreHome() {
        return scoreHome;
    }

    public String getCurrEvent() {
        return currEvent;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getTimeInPeriod() {
        return timeInPeriod;
    }

    public void setTimeInPeriod(String timeInPeriod) {
        this.timeInPeriod = timeInPeriod;
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
