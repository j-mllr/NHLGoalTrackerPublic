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

                g.setCurrEvent(rootobj.getAsJsonObject("liveData").getAsJsonObject("plays").getAsJsonObject("currentPlay").getAsJsonObject("result").get("event").getAsString()); //just grab the zipcode
            } else {
                g.setCurrEvent("Scheduled");
                String datatime = rootobj.get("gameData").getAsJsonObject().get("datetime").getAsJsonObject().get("dateTime").getAsString();
                String datatimetrimmed = datatime.substring(datatime.length() - 9, datatime.length()-1);
                String timeZone = rootobj.get("gameData").getAsJsonObject().get("teams").getAsJsonObject().get("away").getAsJsonObject().get("venue").getAsJsonObject().get("timeZone").getAsJsonObject().get("tz").getAsString();
                int timeZoneDiff = rootobj.get("gameData").getAsJsonObject().get("teams").getAsJsonObject().get("away").getAsJsonObject().get("venue").getAsJsonObject().get("timeZone").getAsJsonObject().get("offset").getAsInt();

                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
                try {
                    Date origGameTime = (Date) formatter.parse(datatimetrimmed);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(origGameTime);
                    cal.add(Calendar.HOUR, timeZoneDiff);
                    int newHour = cal.get(Calendar.HOUR_OF_DAY);
                    int newMin = cal.get(Calendar.MINUTE);

                    if (newMin == 0){
                        String newGameTime = newHour + ":" + newMin + "0 " + timeZone;
                        g.setTime(newGameTime);
                    } else {
                        String newGameTime = newHour + ":" + newMin + timeZone;
                        g.setTime(newGameTime);
                    }



                    System.out.println("Ee");

                } catch (ParseException e) {
                    e.printStackTrace();
                }

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
