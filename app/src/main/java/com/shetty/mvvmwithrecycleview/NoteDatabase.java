package com.shetty.mvvmwithrecycleview;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static  NoteDatabase instance;

    public abstract NoteDao noteDao();
    public static final String TAG="pavan";
    public static synchronized NoteDatabase getInstance(Context context){
        if (instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database").fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
            Log.d(TAG, "getInstance: ");
        }
        return instance;
    }
    private static final RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(TAG, "onCreate: RoomDatabase");
            new PopulateAsyncTask(instance).execute();
        }
    };

    private static class PopulateAsyncTask extends AsyncTask<Void,Void,Void>{
        private final NoteDao noteDao;

        private PopulateAsyncTask(NoteDatabase bd){
            noteDao=bd.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1", "Desc 1",1));
            noteDao.insert(new Note("Title 2", "Desc 2",2));
            noteDao.insert(new Note("Title 3", "Desc 3",3));
            Log.d(TAG, "doInBackground: add");
            return null;
        }
    }
}
