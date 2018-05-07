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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LiveEventProvider {

    private String currEvent;
    private JsonObject feed;

    private String makeURL(int id) {

        //"https://statsapi.web.nhl.com/api/v1/game/2017030213/feed/live";

        String base1 = "https://statsapi.web.nhl.com/api/v1/game/";
        String base2 = "/feed/live";

        return base1 + id + base2;
    }


    public void getEvents(Game g) throws MalformedURLException {


        String sURL = makeURL(g.getId());
        //String sURL = "https://statsapi.web.nhl.com/api/v1/game/2017030225/feed/live";

        // Connect to the URL using java's native library
        URL url = new URL(sURL);
        try {
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            feed = root.getAsJsonObject(); //May be an array, may be an object.
            getCurrEvent(g);
        } catch (IOException e) {
            //e.printStackTrace();
        }

        System.out.println(currEvent);

    }

    private void getCurrEvent(Game g) {
        String state = feed.get("gameData").getAsJsonObject().get("status").getAsJsonObject().get("detailedState").getAsString();
        if (!(state.equals("Scheduled"))) {

            currEvent = feed.getAsJsonObject("liveData").getAsJsonObject("plays").getAsJsonObject("currentPlay").getAsJsonObject("result").get("event").getAsString();
            g.setCurrEvent(currEvent);
            getScore(g);
            getPeriodInfo(g);
        } else {
            currEvent = "Scheduled";
            g.setCurrEvent(currEvent);
            getStartTime(g);

        }
    }

    private void getPeriodInfo(Game g) {
        g.setPeriod(feed.getAsJsonObject("liveData").getAsJsonObject("plays").getAsJsonObject("currentPlay").getAsJsonObject("about").get("ordinalNum").getAsString());
        g.setTimeInPeriod(feed.getAsJsonObject("liveData").getAsJsonObject("plays").getAsJsonObject("currentPlay").getAsJsonObject("about").get("periodTimeRemaining").getAsString());
    }

    private void getScore(Game g) {
        g.setScoreAway(feed.getAsJsonObject("liveData").getAsJsonObject("linescore").getAsJsonObject("teams").getAsJsonObject("away").get("goals").getAsInt());
        g.setScoreHome(feed.getAsJsonObject("liveData").getAsJsonObject("linescore").getAsJsonObject("teams").getAsJsonObject("home").get("goals").getAsInt());
    }

    public String getCurrEvent() {
        return currEvent;
    }

    public void getStartTime(Game g) {
        String datatime = feed.get("gameData").getAsJsonObject().get("datetime").getAsJsonObject().get("dateTime").getAsString();
        String datatimetrimmed = datatime.substring(datatime.length() - 9, datatime.length() - 1);
        String timeZone = feed.get("gameData").getAsJsonObject().get("teams").getAsJsonObject().get("away").getAsJsonObject().get("venue").getAsJsonObject().get("timeZone").getAsJsonObject().get("tz").getAsString();
        int timeZoneDiff = feed.get("gameData").getAsJsonObject().get("teams").getAsJsonObject().get("away").getAsJsonObject().get("venue").getAsJsonObject().get("timeZone").getAsJsonObject().get("offset").getAsInt();

        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        try {
            Date origGameTime = (Date) formatter.parse(datatimetrimmed);
            Calendar cal = Calendar.getInstance();
            cal.setTime(origGameTime);
            cal.add(Calendar.HOUR, timeZoneDiff);
            int newHour = cal.get(Calendar.HOUR_OF_DAY);
            int newMin = cal.get(Calendar.MINUTE);

            if (newMin == 0) {
                String newGameTime = newHour + ":" + newMin + "0 " + timeZone;
                g.setTime(newGameTime);
            } else {
                String newGameTime = newHour + ":" + newMin + timeZone;
                g.setTime(newGameTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
