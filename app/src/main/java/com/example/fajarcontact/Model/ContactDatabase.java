package com.example.fajarcontact.Model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class ContactDatabase extends RoomDatabase {

    private static ContactDatabase instance;
    public abstract ContactDataAccessObject contactDataAccessObject();

    public static synchronized ContactDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContactDatabase.class,
                    "contact_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsynctask(instance).execute();
        }
    };

    private static class PopulateDbAsynctask extends AsyncTask<Void, Void, Void> {

        private ContactDataAccessObject contactDataAccessObject;

        private PopulateDbAsynctask(ContactDatabase db){
            contactDataAccessObject = db.contactDataAccessObject();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}