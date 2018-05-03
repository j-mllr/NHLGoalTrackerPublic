package Model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

// follows Singleton Design Pattern
public class Schedule implements Iterable<Game>{


    static private Schedule instance;
    private Set<Game> setOfGames;
//    private Set<Team> setOfTeams;

    private Schedule(){
        this.setOfGames = new HashSet<>();
//        this.setOfTeams = new HashSet<>();
    }

    public static Schedule getInstance(){
        if (instance == null){
            instance = new Schedule();
        }
        return instance;
    }

    public void addGame(Game g){
        setOfGames.add(g);
//        setOfTeams.add(g.getHome());
//        setOfTeams.add(g.getAway());
    }


    public boolean isPlaying(String s){

        for (Game g : setOfGames){
            if (g.isPlaying(s)){
                return true;
            }
        }

        return false;
    }

    public Game findGame(String s){

            for (Game g : setOfGames){
                if (g.isPlaying(s)){
                    return g;
                }
            }
            return null;
        }


    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Game> iterator() {
        return setOfGames.iterator();
    }
}
