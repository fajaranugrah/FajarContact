package com.example.fajarcontact.View;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.fajarcontact.R;
import com.example.fajarcontact.ViewModel.ContactViewModel;

public class AddorEditContactActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_NAME = "EXTRA_NAME";
    public static final String EXTRA_NOMOR_TELEPHONE = "EXTRA_NOMOR_TELEPHONE";
    public static final String EXTRA_EMAIL = "EXTRA_EMAIL";
    public static final String EXTRA_ALAMAT = "EXTRA_ALAMAT";

    public EditText editName;
    public EditText editNoTelp;
    private EditText editEmail;
    private EditText editAlamat;

    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_contact);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);

        editName = (EditText) findViewById(R.id.edit_nama);
        editNoTelp = (EditText) findViewById(R.id.edit_no_telp);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editAlamat = (EditText) findViewById(R.id.edit_alamat);

        contactViewModel.ChangeTitle(AddorEditContactActivity.this, editName, editNoTelp, editEmail, editAlamat);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_contact:
                String nama = editName.getText().toString().trim();
                String noTelp = editNoTelp.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String alamat = editAlamat.getText().toString().trim();

                contactViewModel.SaveContact(AddorEditContactActivity.this, nama, noTelp, email, alamat);
                return true;
            default:
                onBackPressed();
                return super.onOptionsItemSelected(item);
        }
    }
}