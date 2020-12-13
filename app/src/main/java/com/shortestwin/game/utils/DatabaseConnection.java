package com.shortestwin.game.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shortestwin.game.player.score.LevelStats;

import java.util.ArrayList;

public class DatabaseConnection extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "ShortestWin.db";
    public static final String ITEM_COLUMN_LEVEL = "level";
    public static final String ITEM_COLUMN_MOVES = "moves";

    public DatabaseConnection(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE levels_scores " + "(id INTEGER PRIMARY KEY, level INTEGER, moves INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS levels_scores");
        onCreate(db);
    }

    public boolean insertLevelScore(LevelStats score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_COLUMN_LEVEL, score.getLevel());
        contentValues.put(ITEM_COLUMN_MOVES, score.getMoves());
        long insertedId = db.insert("levels_scores", null, contentValues);
        if (insertedId == -1) return false;
        return true;
    }

    public boolean deleteItem (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("levels_scores", "id = ?", new String[] {String.valueOf(id)});
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from levels_scores where id=" + id + "", null);
        return res;
    }

    public boolean updateItem(LevelStats score) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_COLUMN_LEVEL, score.getLevel());
        contentValues.put(ITEM_COLUMN_MOVES, score.getMoves());

        db.update("levels_scores", contentValues, "id = ?", new String[] {String.valueOf(score.getId())});
        return true;
    }

    public ArrayList<LevelStats> getScores() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from levels_scores order by level", null );
        res.moveToFirst();

        ArrayList<LevelStats> levelsStats = this.resToScores(res);

        return levelsStats;
    }

    public ArrayList<LevelStats> getScoresByLevel(int levelNum) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from levels_scores where level=?", new String[] {Integer.toString(levelNum)});
        res.moveToFirst();

        ArrayList<LevelStats> levelsStats = this.resToScores(res);

        return levelsStats;
    }

    private ArrayList<LevelStats> resToScores(Cursor res) {
        ArrayList<LevelStats> levelsStats = new ArrayList<>();

        while(res.isAfterLast() == false){
            int level = res.getInt(res.getColumnIndex(ITEM_COLUMN_LEVEL));
            int moves = res.getInt(res.getColumnIndex(ITEM_COLUMN_MOVES));
            LevelStats levelStats = new LevelStats(level);
            levelStats.setId(res.getInt(0));
            levelStats.setMoves(moves);

            levelsStats.add(levelStats);
            res.moveToNext();
        }

        return levelsStats;
    }

    public int removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        int nRecordDeleted = db.delete("levels_scores", "", new String[]{});
        return nRecordDeleted;
    }
}

