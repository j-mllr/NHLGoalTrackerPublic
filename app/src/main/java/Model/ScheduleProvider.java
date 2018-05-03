package Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ScheduleProvider {

    private JsonArray listOfGames;
    private int numOfGames;



    public void getSchedule() throws MalformedURLException {
        String sURL = "https://statsapi.web.nhl.com/api/v1/schedule";
        // Connect to the URL using java's native library
        URL url = new URL(sURL);

        try {
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            numOfGames = rootobj.get("totalGames").getAsInt();
            listOfGames = rootobj.get("dates").getAsJsonArray().get(0).getAsJsonObject().get("games").getAsJsonArray();

            if (numOfGames > 0){
                getGameInfo();
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }

    }

    public void getGameInfo(){

        for(int i = 0; i < numOfGames; i++) {
            int gameId = listOfGames.get(i).getAsJsonObject().get("gamePk").getAsInt();
            int awayId = listOfGames.get(i).getAsJsonObject().get("teams").getAsJsonObject().get("away").getAsJsonObject().get("team").getAsJsonObject().get("id").getAsInt();
            int homeId = listOfGames.get(i).getAsJsonObject().get("teams").getAsJsonObject().get("home").getAsJsonObject().get("team").getAsJsonObject().get("id").getAsInt();
            String awayName = listOfGames.get(i).getAsJsonObject().get("teams").getAsJsonObject().get("away").getAsJsonObject().get("team").getAsJsonObject().get("name").getAsString();
            String homeName = listOfGames.get(i).getAsJsonObject().get("teams").getAsJsonObject().get("home").getAsJsonObject().get("team").getAsJsonObject().get("name").getAsString();

            Schedule.getInstance().addGame(new Game(gameId, new Team(awayName, awayId), new Team(homeName,homeId)));
        }
    }
}

