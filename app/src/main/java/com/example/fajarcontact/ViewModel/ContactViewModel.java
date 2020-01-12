package com.example.fajarcontact.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fajarcontact.Connection.APICallTask.AddContact.AddContact;
import com.example.fajarcontact.Connection.APICallTask.DeleteContact.DeleteContact;
import com.example.fajarcontact.Connection.APICallTask.GetContact.GetContact;
import com.example.fajarcontact.Connection.APICallTask.UpdateContact.UpdateContact;
import com.example.fajarcontact.MainActivity;
import com.example.fajarcontact.View.AddorEditContactActivity;
import com.example.fajarcontact.ListAdapter.ContactAdapter;
import com.example.fajarcontact.Model.Contact;
import com.example.fajarcontact.Model.ContactRepository;
import com.example.fajarcontact.View.DetailContactActivity;

import java.util.List;
import java.util.regex.Pattern;

public class ContactViewModel extends AndroidViewModel {

    public ContactRepository contactRepository;
    private LiveData<List<Contact>> allContact;
    public static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public ContactViewModel(@NonNull Application application) {
        super(application);
        contactRepository = new ContactRepository(application);
        allContact = contactRepository.getAllContacts();
    }

    public void insert(@NonNull Activity activity, Contact contact) {
        //save database phone
        contactRepository.insert(contact);
        //save to server
        AddContact(activity, contact);
    }

    public static String insertSucced(Contact contact){
        if (!isValidEmpty(contact.getName())) {
            return "Contact Not Saved";
        } else if (!isValidEmpty(contact.getNomor_telepon())){
            return "Contact Not Saved";
        } else if (!isValidEmpty(contact.getAlamat())){
            return "Contact Not Saved";
        } else if (!isValidEmail(contact.getEmail())){
            return "Contact Not Saved";
        } else {
            return "Contact Saved";
        }
    }

    public void update(@NonNull Activity activity, Contact contact) {
        //update database phone
        contactRepository.update(contact);
        //update to server
        UpdateContact(activity, contact);
    }

    public void delete(@NonNull Activity activity, Contact contact) {
        //delete database phone
        contactRepository.delete(contact);
        //delete to server
        DeleteContact(activity, contact);
    }

    public void deleteAllContacss() {
        contactRepository.deleteAll();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContact;
    }

    public void TouchAdapter(@NonNull final Activity activity, final ContactAdapter contactAdapter, final RecyclerView recyclerView) {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                delete(activity, contactAdapter.getContactAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);
    }

    public void AddContact(@NonNull final Activity activity){
        Intent intent = new Intent(activity, AddorEditContactActivity.class);
        activity.startActivityForResult(intent, MainActivity.RESULT_ADD_CONTACT);
    }

    public void SelectPositionAdapter(@NonNull final Activity activity, final ContactAdapter contactAdapter) {
        contactAdapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Contact contact) {
                Intent intent = new Intent(activity, DetailContactActivity.class);
                intent.putExtra(DetailContactActivity.EXTRA_ID, contact.getId());
                intent.putExtra(DetailContactActivity.EXTRA_NAME, contact.getName());
                intent.putExtra(DetailContactActivity.EXTRA_NOMOR_TELEPHONE, contact.getNomor_telepon());
                intent.putExtra(DetailContactActivity.EXTRA_EMAIL, contact.getEmail());
                intent.putExtra(DetailContactActivity.EXTRA_ALAMAT, contact.getAlamat());
                activity.startActivity(intent);
            }
        });
    }

    public void EditContact(@NonNull final Activity activity, final int id, final String name, final String noTelp, final String email, final String alamat) {
        Intent intent = new Intent(activity, AddorEditContactActivity.class);
        intent.putExtra(AddorEditContactActivity.EXTRA_ID, id);
        intent.putExtra(AddorEditContactActivity.EXTRA_NAME, name);
        intent.putExtra(AddorEditContactActivity.EXTRA_NOMOR_TELEPHONE, noTelp);
        intent.putExtra(AddorEditContactActivity.EXTRA_EMAIL, email);
        intent.putExtra(AddorEditContactActivity.EXTRA_ALAMAT, alamat);
        activity.startActivityForResult(intent, DetailContactActivity.RESULT_EDIT_CONTACT);
    }

    public void ChangeTitle(@NonNull final Activity activity, final EditText editTitle, final EditText editDesc, final EditText editLevel, final EditText editAlamat) {
        if (activity.getIntent().hasExtra(AddorEditContactActivity.EXTRA_ID)) {
            activity.setTitle("Edit Contact");
            editTitle.setText(activity.getIntent().getStringExtra(AddorEditContactActivity.EXTRA_NAME));
            editDesc.setText(activity.getIntent().getStringExtra(AddorEditContactActivity.EXTRA_NOMOR_TELEPHONE));
            editLevel.setText(activity.getIntent().getStringExtra(AddorEditContactActivity.EXTRA_EMAIL));
            editAlamat.setText(activity.getIntent().getStringExtra(AddorEditContactActivity.EXTRA_ALAMAT));
        } else {
            activity.setTitle("Add Contact");
        }
    }

    public void FillDetailContact(@NonNull final Activity activity, final TextView name, final TextView noTelp, final TextView email, final TextView alamat) {
        if (activity.getIntent().hasExtra(DetailContactActivity.EXTRA_ID)) {

            String valueName = activity.getIntent().getStringExtra(DetailContactActivity.EXTRA_NAME);
            String valueNoTelp = activity.getIntent().getStringExtra(DetailContactActivity.EXTRA_NOMOR_TELEPHONE);
            String valueEmail = activity.getIntent().getStringExtra(DetailContactActivity.EXTRA_EMAIL);
            String valueAlamat = activity.getIntent().getStringExtra(DetailContactActivity.EXTRA_ALAMAT);

            name.setText(valueName);
            noTelp.setText(valueNoTelp);
            email.setText(valueEmail);
            alamat.setText(valueAlamat);
        }
    }

    public void UpdateDetailContact(final TextView name, final TextView noTelp, final TextView email, final TextView alamat, final Contact contact) {
        name.setText(contact.getName());
        noTelp.setText(contact.getNomor_telepon());
        email.setText(contact.getEmail());
        alamat.setText(contact.getAlamat());
    }

    public void SaveContact(@NonNull final Activity activity, final String name, final String noTelp, final String email, final String alamat) {
        if (!isValidEmpty(name)) {
            showToast(activity, "Full Name");
            return;
        } else if (!isValidEmpty(noTelp)){
            showToast(activity, "Number Phone");
            return;
        } else if (!isValidEmpty(alamat)){
            showToast(activity, "Address");
            return;
        } else if (!isValidEmail(email)){
            Toast.makeText(activity, "Please insert a right the email", Toast.LENGTH_SHORT).show();
            return;
        } else {
            processSaveContact(activity, name, noTelp, email, alamat);
        }
    }

    public static boolean isValidEmpty(final String value){
        return value != null && !value.equals("") && !value.trim().isEmpty();
    }

    public static boolean isValidEmail(CharSequence email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public void showToast(@NonNull final Activity activity, String value){
        Toast.makeText(activity, "Please insert a " + value, Toast.LENGTH_SHORT).show();
    }

    public void processSaveContact(@NonNull final Activity activity, final String name, final String noTelp, final String email, final String alamat){
        Intent intent = new Intent();
        intent.putExtra(AddorEditContactActivity.EXTRA_NAME, name);
        intent.putExtra(AddorEditContactActivity.EXTRA_NOMOR_TELEPHONE, noTelp);
        intent.putExtra(AddorEditContactActivity.EXTRA_EMAIL, email);
        intent.putExtra(AddorEditContactActivity.EXTRA_ALAMAT, alamat);

        int id = activity.getIntent().getIntExtra(AddorEditContactActivity.EXTRA_ID, -1);
        if (id != -1) {
            intent.putExtra(AddorEditContactActivity.EXTRA_ID, id);
        }

        activity.setResult(AddorEditContactActivity.RESULT_OK, intent);
        activity.finish();
    }

    public void GetContact(@NonNull final Activity activity, @NonNull final String userSearch,@NonNull final int goPage){
        try {
            new GetContact(activity) {
                @Override
                public void OnApiResult(int statusCode, String failReason) {
                    //dissmiss loading

                    if (statusCode == 1) {
                        //success
                    } else {
                        //failed
                    }
                }
            }.callApi(userSearch, String.valueOf(goPage));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void AddContact(@NonNull final Activity activity, @NonNull final Contact contact){
        try {
            new AddContact(activity) {
                @Override
                public void OnApiResult(int statusCode, String failReason) {
                    //dissmiss loading

                    if (statusCode == 1) {
                        //success
                    } else {
                        //failed
                    }
                }
            }.callApi(contact);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void UpdateContact(@NonNull final Activity activity, @NonNull final Contact contact){
        try {
            new UpdateContact(activity) {
                @Override
                public void OnApiResult(int statusCode, String failReason) {
                    //dissmiss loading

                    if (statusCode == 1) {
                        //success
                    } else {
                        //failed
                    }
                }
            }.callApi(contact);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void DeleteContact(@NonNull final Activity activity, @NonNull final Contact contact){
        try {
            new DeleteContact(activity) {
                @Override
                public void OnApiResult(int statusCode, String failReason) {
                    //dissmiss loading

                    if (statusCode == 1) {
                        //success
                    } else {
                        //failed
                    }
                }
            }.callApi(contact);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}