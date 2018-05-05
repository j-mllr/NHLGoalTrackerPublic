package nhlgoalnotification.nhlgoaltracker;

import Model.Game;
import Model.LiveEventProvider;
import Model.Schedule;
import Model.ScheduleProvider;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {

    private String selectedTeam;
    private Schedule schedule;
    private String currentEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner);

        final ArrayAdapter<String> myAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Teams));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                new initializeData().execute();

                if (i != 0) {
                    selectedTeam = myAdapter.getItem(i);
                    if (schedule.isPlaying(selectedTeam)) {
                        new checkLiveData().execute();

                        if (currentEvent.equals("Scheduled")) {
                            Intent intent = new Intent(MainActivity.this, Scheduled.class);
                            intent.putExtra("gameTime", schedule.findGame(selectedTeam).getStartTime());
                            startActivity(intent);
                        }
                    } else {
                        startActivity(new Intent(MainActivity.this, NoGames.class));
                    }
                }
                }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public class initializeData extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            initializeData();
            schedule = Schedule.getInstance();

            return null;
        }
    }

    public class checkLiveData extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            checkFeed();
            currentEvent = Schedule.getInstance().findGame(selectedTeam).getCurrEvent();
            return null;
        }
    }


    private void initializeData() {
        ScheduleProvider sp = new ScheduleProvider();
        try {
            sp.getSchedule();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void checkFeed(){
        Game game = Schedule.getInstance().findGame(selectedTeam);
        LiveEventProvider feed = new LiveEventProvider();

        try {
            feed.getEvents(game);
            feed.getCurrEvent();
            System.out.println(currentEvent);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
