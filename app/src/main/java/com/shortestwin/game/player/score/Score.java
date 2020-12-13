package com.shortestwin.game.player.score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Score {
    private ArrayList<LevelStats> levelsStats;
    private Comparator<LevelStats> levelsComparator;


    public Score() {
        this.levelsStats = new ArrayList<>();

        this.levelsComparator = new Comparator<LevelStats>() {
            @Override
            public int compare(LevelStats o1, LevelStats o2) {
                return o1.getLevel() - o2.getLevel();
            }
        };
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
}
