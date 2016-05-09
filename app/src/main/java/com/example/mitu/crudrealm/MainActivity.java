package com.example.mitu.crudrealm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.database.ContactModel;
import com.example.database.Crud;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    EditText etName, etPhone;
    ContactModel contactModel;
    Button addbutton,button;

    ListView lvContactList;
    protected RealmResults<ContactModel> resultsshow ;
    LinearLayout linearLayout;
    private ContactAdapter contactAdapter;
    Crud crud = new Crud();
    private Realm myRealm;

    public int _id=0;
    long position_forUpdate= -1 ;
    int delete =0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = (EditText) findViewById(R.id.editText);
        etPhone = (EditText) findViewById(R.id.editText2);
        lvContactList = (ListView) findViewById(R.id.listView);
        button = (Button) findViewById(R.id.button2);
        addbutton = (Button) findViewById(R.id.addcontact);
        linearLayout = (LinearLayout) findViewById(R.id.layoutaddcontact);
        linearLayout.setVisibility(View.GONE);



        contactModel = new ContactModel();

        ///Query
        if(crud.getContactList() != null) {
            resultsshow = crud.getContactList();
            contactAdapter = new ContactAdapter();

            lvContactList.setAdapter(contactAdapter);

            lvContactList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    _id = -1;
                    position_forUpdate = id+1;


                    String name = resultsshow.get(position).getmName();
                    String phone = resultsshow.get(position).getmPhone();
                    addbutton.setVisibility(view.GONE);
                    linearLayout.setVisibility(view.VISIBLE);
                    etName.setText(name+"");
                    etPhone.setText(phone+"");

                    Log.d("Position ",position+"");
                    Log.d("id ",id+"");
                    return true;
                }
            });
        }

       /* for(ContactModel c:resultsshow) {
            Log.d("results1", c.getmName() + c.getmPhone() + " "+c.getmId());
        }*/

    }

    public  void addContact(View v){



        long id1;


        if (_id > -1) {
            id1 = resultsshow.size() == 0 ? 1 : resultsshow.size() + 1;
            String name = etName.getText().toString();
            String phone = etPhone.getText().toString();

            crud.insertContact(contactModel,name,phone,id1);


        }else if (_id == -1){


            String nameupdate = etName.getText().toString();
            String phoneupdate = etPhone.getText().toString();
            crud.updateContact(contactModel,nameupdate,phoneupdate,position_forUpdate);
            position_forUpdate = -1;
            // Log.d("Update","_id "+_id+"Previous "+getname+" ,"+getPhone+","+id2+" Now :"+contactModel.getmName()+","+contactModel.getmPhone()+","+contactModel.getmId());
            _id = 0;
        }

        linearLayout.setVisibility(v.GONE);
        addbutton.setVisibility(v.VISIBLE);
        resultsshow = crud.getContactList(                                                                       );
        contactAdapter = new ContactAdapter();

        lvContactList.setAdapter(contactAdapter);

    }

    public void newcontact(View v){
        addbutton.setVisibility(v.GONE);
        linearLayout.setVisibility(v.VISIBLE);

    }

    public void delete(View view){

        final  ContactModel  d = resultsshow.get((int) position_forUpdate-1);
        delete = 1;
        Log.d("ID jsdkfjsdk",d.getmId()+"");


        //;
        resultsshow = crud.deleteContact(d);
        contactAdapter = new ContactAdapter();

        lvContactList.setAdapter(contactAdapter);


        linearLayout.setVisibility(view.GONE);
        addbutton.setVisibility(view.VISIBLE);


        position_forUpdate = -1;

    }




    class ContactAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (delete == 1){
                delete = 0;
                return resultsshow.size()-1;

            }else {
                return resultsshow.size();
            }
        }

        @Override
        public Object getItem(int position) {
            return resultsshow.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.layout_phonebook,parent,false);

            TextView tvName = (TextView) convertView.findViewById(R.id.textView);
            TextView tvPhone = (TextView) convertView.findViewById(R.id.textView2);

            ContactModel ch = (ContactModel) getItem(position);
            tvName.setText(ch.getmName());
            tvPhone.setText(ch.getmPhone());

            return convertView;
        }
    }
}
