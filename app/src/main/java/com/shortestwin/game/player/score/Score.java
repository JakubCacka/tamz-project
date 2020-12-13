package com.shortestwin.game.player.score;

import android.content.Context;

import com.shortestwin.game.utils.DatabaseConnection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Score {
    private ArrayList<LevelStats> levelsStats;
    private Comparator<LevelStats> levelsComparator;

    private DatabaseConnection DB;


    public Score(Context context) {
        this.levelsStats = new ArrayList<>();

        this.levelsComparator = new Comparator<LevelStats>() {
            @Override
            public int compare(LevelStats o1, LevelStats o2) {
                return o1.getLevel() - o2.getLevel();
            }
        };

        this.DB = new DatabaseConnection(context);
    }

    public void addNewLevelStats(LevelStats newLevelStats) {
        if(this.getLevelsScore(newLevelStats.getLevel()) == null) {
            this.levelsStats.add(newLevelStats);
            Collections.sort(this.levelsStats, this.levelsComparator);
        }
    }

    public void addMoveToLevel(int levelNum) {
        this.getLevelsScore(levelNum).addMove();
    }

    public LevelStats getLevelsScore(int levelNum) {
        for(LevelStats levelStats : this.levelsStats) {
            if(levelStats.getLevel() == levelNum) {
                return levelStats;
            }
        }

        return null;
    }

    public LevelStats getLastLevelStats() {
        if(this.levelsStats.size() > 0)
            return this.levelsStats.get(this.levelsStats.size() - 1);

        return null;
    }

    public void storeLastLevelToDB() {
        LevelStats levelStats = this.getLastLevelStats();

        if(levelStats != null) {
            ArrayList<LevelStats> statsForLevel = this.DB.getScoresByLevel(levelStats.getLevel());

            if(statsForLevel.size() > 0) {
                LevelStats statToUpdate = statsForLevel.get(0);
                this.DB.updateItem(statToUpdate);
            } else {
                this.DB.insertLevelScore(levelStats);
            }
        }
    }
}
