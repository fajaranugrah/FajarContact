package com.example.fajarcontact.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.fajarcontact.Model.Contact;
import com.example.fajarcontact.R;
import com.example.fajarcontact.ViewModel.ContactViewModel;

public class DetailContactActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_NAME = "EXTRA_NAME";
    public static final String EXTRA_NOMOR_TELEPHONE = "EXTRA_NOMOR_TELEPHONE";
    public static final String EXTRA_EMAIL = "EXTRA_EMAIL";
    public static final String EXTRA_ALAMAT = "EXTRA_ALAMAT";

    public static final int RESULT_EDIT_CONTACT = 2;

    public TextView nameDetail;
    public TextView no_telpDetail;
    private TextView emailDetail;
    private TextView alamatDetail;

    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);

        nameDetail = (TextView) findViewById(R.id.full_name);
        no_telpDetail = (TextView) findViewById(R.id.number_telephone);
        emailDetail = (TextView) findViewById(R.id.email_contact);
        alamatDetail = (TextView) findViewById(R.id.address_contact);

        if (getIntent().hasExtra(EXTRA_ID)){
            contactViewModel.FillDetailContact(DetailContactActivity.this, nameDetail, no_telpDetail, emailDetail, alamatDetail);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_EDIT_CONTACT && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddorEditContactActivity.EXTRA_NAME);
            String no_telp = data.getStringExtra(AddorEditContactActivity.EXTRA_NOMOR_TELEPHONE);
            String email = data.getStringExtra(AddorEditContactActivity.EXTRA_EMAIL);
            String alamat = data.getStringExtra(AddorEditContactActivity.EXTRA_ALAMAT);

            int id = data.getIntExtra(AddorEditContactActivity.EXTRA_ID, -1);
            if (id == -1){
                Toast.makeText(DetailContactActivity.this, "Contact can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            Contact contact = new Contact(name, no_telp, email, alamat);
            contact.setId(id);
            contactViewModel.update(DetailContactActivity.this, contact);
            contactViewModel.UpdateDetailContact(nameDetail, no_telpDetail, emailDetail, alamatDetail, contact);

            Toast.makeText(DetailContactActivity.this, "Contact Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DetailContactActivity.this, "Contact Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_contact:
                String editnama = nameDetail.getText().toString().trim();
                String editnoTelp = no_telpDetail.getText().toString().trim();
                String editemail = emailDetail.getText().toString().trim();
                String editalamat = alamatDetail.getText().toString().trim();

                contactViewModel.EditContact(DetailContactActivity.this, getIntent().getIntExtra(EXTRA_ID, -1), editnama, editnoTelp, editemail, editalamat);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}