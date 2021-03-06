package it.lucapascucci.contactmanager;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private static final int EDIT = 0, DELETE = 1;

    EditText nameTxt, phoneTxt, emailTxt, addressTxt;
    List<Contact> Contacts = new ArrayList<Contact>();
    ImageView contactImageView;
    ListView contactListView;
    Uri imageURI = Uri.parse("android.resource://it.lucapascucci.contactmanager/drawable/no_user_logo");
    DatabaseHandler dbHandler;
    int longClickedItemIndex;
    ArrayAdapter<Contact> contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameTxt = (EditText) findViewById(R.id.txtName);
        phoneTxt = (EditText) findViewById(R.id.txtPhone);
        emailTxt = (EditText) findViewById(R.id.txtEmail);
        addressTxt = (EditText) findViewById(R.id.txtAddress);
        contactListView = (ListView) findViewById(R.id.listView);
        contactImageView = (ImageView) findViewById(R.id.imgViewContactImage);
        dbHandler = new DatabaseHandler(getApplicationContext());

        registerForContextMenu(contactListView);
        contactListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemIndex = position;
                return false;
            }
        });

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.tabCreator);
        tabSpec.setIndicator("Create");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.tabContactList);
        tabSpec.setIndicator("List");
        tabHost.addTab(tabSpec);

        final Button addBtn = (Button) findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = new Contact(dbHandler.getContactsCount(),String.valueOf(nameTxt.getText()),String.valueOf(phoneTxt.getText()),String.valueOf(emailTxt.getText()),String.valueOf(addressTxt.getText()),imageURI);
                if (contactExists(contact)){
                    Toast.makeText(getApplicationContext(),String.valueOf(nameTxt.getText()) + " already exists",Toast.LENGTH_SHORT).show();
                }else{
                    dbHandler.createContact(contact);
                    Contacts.add(contact);
                    contactAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),String.valueOf(nameTxt.getText()) + " has been added",Toast.LENGTH_SHORT).show();
                }
            }
        });

        nameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addBtn.setEnabled(!String.valueOf(nameTxt.getText()).trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contactImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select contact image"),1);
            }
        });

        if (dbHandler.getContactsCount() != 0){
            Contacts.addAll(dbHandler.getAllContacts());
        }

        populateList();
    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,view,menuInfo);

        menu.setHeaderIcon(R.drawable.option);
        menu.setHeaderTitle("Contact Options");
        menu.add(menu.NONE,EDIT,menu.NONE, "Edit Contact");
        menu.add(menu.NONE,DELETE,menu.NONE, "Delete Contact");
    }

    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case EDIT:
                //TODO: implementare modifica di un contatto
                break;

            case DELETE:
                dbHandler.deleteContact(Contacts.get(longClickedItemIndex));
                Contacts.remove(longClickedItemIndex);
                contactAdapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);

    }

    private boolean contactExists(Contact contact){
        String name = contact.get_name();
        int contactCount = Contacts.size();

        for (int i = 0; i < contactCount; i++){
            if (name.compareToIgnoreCase(Contacts.get(i).get_name())== 0){
                return true;
            }
        }
        return false;
    }

    public void onActivityResult(int reqCode, int resCode,Intent data){
        if (resCode == RESULT_OK){
            if (reqCode == 1){
                imageURI = data.getData();
                contactImageView.setImageURI(data.getData());
            }
        }
    }

    private void populateList(){
        contactAdapter = new ContactListAdapter();
        contactListView.setAdapter(contactAdapter);
    }

    private class ContactListAdapter extends ArrayAdapter<Contact>{
        public ContactListAdapter(){
            super(MainActivity.this,R.layout.listview_item, Contacts);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);
            }

            Contact currentContact = Contacts.get(position);

            TextView name = (TextView) view.findViewById(R.id.contactName);
            name.setText(currentContact.get_name());

            TextView phone = (TextView) view.findViewById(R.id.phoneNumber);
            phone.setText(currentContact.get_phone());

            TextView email = (TextView) view.findViewById(R.id.emailAddress);
            email.setText(currentContact.get_email());

            TextView address = (TextView) view.findViewById(R.id.cAddress);
            address.setText(currentContact.get_address());

            ImageView ivContactImage = (ImageView) view.findViewById(R.id.ivContactImage);
            ivContactImage.setImageURI(currentContact.get_imageURI());
            return view;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
