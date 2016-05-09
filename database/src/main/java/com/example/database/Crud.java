package com.example.database;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mitu on 5/9/16.
 */
public class Crud  {
    public RealmResults<ContactModel> resultcontact;
    private Realm myRealm;
   // ContactModel contactModel = new ContactModel();;

    public void insertContact(ContactModel ch,String name,String phone,long id1){

        myRealm.beginTransaction();
        ch.setmName(name);
        ch.setmPhone(phone);
        ch.setmId( id1 );
        myRealm.copyToRealm(ch);
        myRealm.commitTransaction();

    }
    public  void updateContact(ContactModel ch,String name,String phone,long id1){
        myRealm.beginTransaction();
        ch.setmName(name);
        ch.setmPhone(phone);
        ch.setmId(id1);
        myRealm.copyToRealmOrUpdate(ch);
        myRealm.commitTransaction();
    }

    public RealmResults<ContactModel> getContactList() {
        myRealm = Realm.getDefaultInstance();
        resultcontact = myRealm.where(ContactModel.class).findAll();


        return resultcontact;
    }

    public RealmResults<ContactModel> deleteContact(final  ContactModel  d){
        myRealm.beginTransaction();
        d.removeFromRealm();
        myRealm.commitTransaction();
        resultcontact = myRealm.where(ContactModel.class).findAll();
        return resultcontact;
    }

}
