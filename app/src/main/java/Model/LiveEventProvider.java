package Model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LiveEventProvider {

    private String currEvent;

    private String makeURL(int id){

        //"https://statsapi.web.nhl.com/api/v1/game/2017030213/feed/live";

        String base1 = "https://statsapi.web.nhl.com/api/v1/game/";
        String base2 = "/feed/live";

        return base1+id+base2;
    }


    public void getEvents(Game g) throws MalformedURLException {


        String sURL = makeURL(g.getId());

        // Connect to the URL using java's native library
        URL url = new URL(sURL);
        try {
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            String state = rootobj.get("gameData").getAsJsonObject().get("status").getAsJsonObject().get("detailedState").getAsString();
            if (!(state.equals("Scheduled"))) {
                currEvent = rootobj.getAsJsonObject("liveData").getAsJsonObject("plays").getAsJsonObject("currentPlay").getAsJsonObject("result").get("event").getAsString(); //just grab the zipcode
            } else {
                currEvent = "Scheduled";
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }

        System.out.println(currEvent);

    }

    public String getCurrEvent() {
        return currEvent;
    }
}
