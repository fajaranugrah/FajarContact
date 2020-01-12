package com.example.fajarcontact;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.fajarcontact.ListAdapter.ContactAdapter;
import com.example.fajarcontact.Model.Contact;
import com.example.fajarcontact.View.AddorEditContactActivity;
import com.example.fajarcontact.ViewModel.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int RESULT_ADD_CONTACT = 1;

    private ContactViewModel contactViewModel;
    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.add_contact);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        contactAdapter = new ContactAdapter();
        recyclerView.setAdapter(contactAdapter);

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                //update RecyclerView
                contactAdapter.setContacts(contacts);
            }
        });
        contactViewModel.TouchAdapter(MainActivity.this, contactAdapter, recyclerView);

        contactViewModel.SelectPositionAdapter(MainActivity.this, contactAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactViewModel.AddContact(MainActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_ADD_CONTACT && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddorEditContactActivity.EXTRA_NAME);
            String no_telp = data.getStringExtra(AddorEditContactActivity.EXTRA_NOMOR_TELEPHONE);
            String email = data.getStringExtra(AddorEditContactActivity.EXTRA_EMAIL);
            String alamat = data.getStringExtra(AddorEditContactActivity.EXTRA_ALAMAT);

            Contact contact = new Contact(name, no_telp, email, alamat);
            contactViewModel.insert(MainActivity.this, contact);

            Toast.makeText(MainActivity.this, "Contact Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Contact Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_contact:
                contactViewModel.deleteAllContacss();
                Toast.makeText(MainActivity.this, "Contact delete all", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
