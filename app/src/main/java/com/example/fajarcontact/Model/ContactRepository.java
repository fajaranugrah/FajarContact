package com.example.fajarcontact.Model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactRepository {

    public ContactDataAccessObject contactDataAccessObject;
    private LiveData<List<Contact>> allContact;

    public ContactRepository(Application application) {
        ContactDatabase contactDatabase = ContactDatabase.getInstance(application);
        contactDataAccessObject = contactDatabase.contactDataAccessObject();
        allContact = contactDataAccessObject.getAllContact();
    }

    public void insert(Contact contact) {
        new InsertContactAsyncTask(contactDataAccessObject).execute(contact);
    }

    public  void update(Contact contact) {
        new UpdateContactAsyncTask(contactDataAccessObject).execute(contact);
    }

    public void delete(Contact contact) {
        new DeleteContactAsyncTask(contactDataAccessObject).execute(contact);
    }

    public void deleteAll() {
        new DeleteAlltContactsAsyncTask(contactDataAccessObject).execute();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContact;
    }

    private static class InsertContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDataAccessObject contactDataAccessObject;

        private InsertContactAsyncTask(ContactDataAccessObject contactDataAccessObject) {
            this.contactDataAccessObject = contactDataAccessObject;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDataAccessObject.insert(contacts[0]);
            return null;
        }
    }

    private static class UpdateContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDataAccessObject contactDataAccessObject;

        private UpdateContactAsyncTask(ContactDataAccessObject contactDataAccessObject) {
            this.contactDataAccessObject = contactDataAccessObject;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDataAccessObject.update(contacts[0]);
            return null;
        }
    }

    private static class DeleteContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDataAccessObject contactDataAccessObject;

        private DeleteContactAsyncTask(ContactDataAccessObject contactDataAccessObject) {
            this.contactDataAccessObject = contactDataAccessObject;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDataAccessObject.delete(contacts[0]);
            return null;
        }
    }

    private static class DeleteAlltContactsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ContactDataAccessObject contactDataAccessObject;

        private DeleteAlltContactsAsyncTask(ContactDataAccessObject contactDataAccessObject) {
            this.contactDataAccessObject = contactDataAccessObject;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contactDataAccessObject.deleteAllContact();
            return null;
        }
    }
}