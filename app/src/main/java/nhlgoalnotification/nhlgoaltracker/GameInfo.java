package nhlgoalnotification.nhlgoaltracker;

import Model.Game;
import Model.LiveEventProvider;
import Model.Schedule;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.net.MalformedURLException;

public class GameInfo extends AppCompatActivity {

    private String selectedTeam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        TextView home = (TextView) findViewById(R.id.textView2);
        TextView away = (TextView) findViewById(R.id.textView4);
        TextView goalsHome = (TextView) findViewById(R.id.textView7);
        TextView goalsAway = (TextView) findViewById(R.id.textView9);
        TextView periodInfo = (TextView) findViewById(R.id.textView13);

        home.setText(getIntent().getExtras().getString("homeTeam"));
        away.setText(getIntent().getExtras().getString("awayTeam"));
        goalsHome.setText(getIntent().getExtras().get("scoreHome").toString());
        goalsAway.setText(getIntent().getExtras().get("scoreAway").toString());

       if (getIntent().getExtras().getString("period") == null){
           String periodString = getIntent().getExtras().getString("timeRemaining");
           periodInfo.setText(periodString);
       } else {
           String periodString = getIntent().getExtras().getString("period") + ":" + getIntent().getExtras().getString("timeRemaining");
           periodInfo.setText(periodString);
       }

        selectedTeam = getIntent().getExtras().getString("selectedTeam");

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                new checkLiveData().execute();
            }
        });
    }

        public class checkLiveData extends AsyncTask<String, String, String> {

            @Override
            protected String doInBackground(String... strings) {
                return checkFeed();
            }

            @Override
            protected void onPostExecute(String s) {
                    Schedule schedule = Schedule.getInstance();
                    Intent intent = new Intent(GameInfo.this, GameInfo.class);
                    intent.putExtra("selectedTeam", selectedTeam);
                    intent.putExtra("homeTeam", schedule.findGame(selectedTeam).getHome().getName());
                    intent.putExtra("awayTeam", schedule.findGame(selectedTeam).getAway().getName());
                    intent.putExtra("scoreAway", schedule.findGame(selectedTeam).getScoreAway());
                    intent.putExtra("scoreHome", schedule.findGame(selectedTeam).getScoreHome());
                    intent.putExtra("period", schedule.findGame(selectedTeam).getPeriod());
                    intent.putExtra("timeRemaining", schedule.findGame(selectedTeam).getTimeInPeriod());
                    startActivity(intent);
                }

            private String checkFeed(){
                Game game = Schedule.getInstance().findGame(selectedTeam);
                LiveEventProvider feed = new LiveEventProvider();

                try {
                    feed.getEvents(game);
                    return feed.getCurrEvent();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return null;
            }
            }
        }


