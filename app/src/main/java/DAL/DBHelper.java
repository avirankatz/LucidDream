package DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import models.Dream;

/**
 * Created by Aviran on 07/05/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LucidDream.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDreamTable = String.format("CREATE TABLE IF NOT EXISTS %s" +
                "( %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s VARCHAR, " +
                "%s TEXT, " +
                "%s INTEGER);",
                DreamContract.Dream.DREAM_TABLE_NAME,
                DreamContract.Dream._ID,
                DreamContract.Dream.COLUMN_NAME_DREAM_TITLE,
                DreamContract.Dream.COLUMN_NAME_DREAM_CONTENT,
                DreamContract.Dream.COLUMN_NAME_TIME_OF_CREATION);
//        Log.d("avirankatz", createDreamTable);
        db.execSQL(createDreamTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new UnsupportedOperationException();
    }

    public boolean editDream(long id, Dream newDream) {
        ContentValues values = new ContentValues();
        values.put(DreamContract.Dream.COLUMN_NAME_DREAM_TITLE, newDream.getTitle());
        values.put(DreamContract.Dream.COLUMN_NAME_DREAM_CONTENT, newDream.getContent());
        values.put(DreamContract.Dream.COLUMN_NAME_TIME_OF_CREATION, newDream.getTimeOfCreation());
        try {
            getWritableDatabase().update(DreamContract.Dream.DREAM_TABLE_NAME,
                    values,
                    DreamContract.Dream._ID + "=?",
                    new String[] {String.valueOf(id)});
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean insertNewDream(Dream dream) {
        ContentValues values = new ContentValues();
        values.put(DreamContract.Dream.COLUMN_NAME_DREAM_TITLE, dream.getTitle());
        values.put(DreamContract.Dream.COLUMN_NAME_DREAM_CONTENT, dream.getContent());
        values.put(DreamContract.Dream.COLUMN_NAME_TIME_OF_CREATION, dream.getTimeOfCreation());
        try {
            getWritableDatabase().insert(DreamContract.Dream.DREAM_TABLE_NAME, null, values);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public Cursor getAllDreams() {
        return getReadableDatabase().rawQuery(String.format("SELECT * FROM %s", DreamContract.Dream.DREAM_TABLE_NAME), null);
    }

    public Cursor getDreamTitles() {
        return getReadableDatabase().rawQuery(String.format("SELECT %s, %s FROM %s", DreamContract.Dream._ID, DreamContract.Dream.COLUMN_NAME_DREAM_TITLE, DreamContract.Dream.DREAM_TABLE_NAME), null);
    }

    public Dream getDreamById(long id) {
        Cursor c = getReadableDatabase().rawQuery(String.format("SELECT * FROM %s WHERE %s=%s ", DreamContract.Dream.DREAM_TABLE_NAME, DreamContract.Dream._ID, id), null);
        c.moveToFirst();
        Log.d("getDreamById", "got cursor");
        String dreamTitle = c.getString(c.getColumnIndex(DreamContract.Dream.COLUMN_NAME_DREAM_TITLE));
        Log.d("getDreamById", "got title");
        String dreamContent = c.getString(c.getColumnIndex(DreamContract.Dream.COLUMN_NAME_DREAM_CONTENT));
        long dreamTimeOfCreation = c.getLong(c.getColumnIndex(DreamContract.Dream.COLUMN_NAME_TIME_OF_CREATION));
        c.close();
        Log.d("getDreamById", "got all relevant data");
        return new Dream(dreamTitle, dreamContent, dreamTimeOfCreation);
    }

    public boolean removeDreamById(long id) {
        return getWritableDatabase().delete(DreamContract.Dream.DREAM_TABLE_NAME,
                DreamContract.Dream._ID + "=?",
                new String[]{String.valueOf(id)}) > 0;
    }
}
