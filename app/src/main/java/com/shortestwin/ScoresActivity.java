package com.shortestwin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shortestwin.game.player.score.LevelStats;
import com.shortestwin.game.utils.DatabaseConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScoresActivity extends AppCompatActivity {

    private ListView scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        this.scores = findViewById(R.id.scoresList);
        DatabaseConnection DB = new DatabaseConnection(this);
        ArrayList<LevelStats> levelsStats = DB.getScores();

        ArrayList<String> list = new ArrayList<>();
        for(LevelStats levelStats : levelsStats) {
            list.add("Level " + levelStats.getLevel() + ": " + levelStats.getMoves() + " moves");
        }

        StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        scores.setAdapter(adapter);
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}