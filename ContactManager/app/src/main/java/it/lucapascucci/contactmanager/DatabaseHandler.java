package it.lucapascucci.contactmanager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca on 07/03/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "contactManager",
            TABLE_CONTACTS = "contacts",
            KEY_ID = "id",
            KEY_NAME = "name",
            KEY_PHONE = "phone",
            KEY_EMAIL = "email",
            KEY_ADDRESS = "address",
            KEY_IMAGEURI = "imageUri";

    public DatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db){

        String querySQL = "CREATE TABLE " + TABLE_CONTACTS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                KEY_NAME + " TEXT,"+
                KEY_PHONE + " TEXT,"+
                KEY_EMAIL + " TEXT,"+
                KEY_ADDRESS + " TEXT,"+
                KEY_IMAGEURI + " TEXT )";
        db.execSQL(querySQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int newVersion){
        String querySQL = "DROP TABLE IF EXISTS " + TABLE_CONTACTS;
        db.execSQL(querySQL);
        onCreate(db);

    }

    public void createContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, contact.get_name());
        values.put(KEY_PHONE, contact.get_phone());
        values.put(KEY_EMAIL, contact.get_email());
        values.put(KEY_ADDRESS, contact.get_address());
        values.put(KEY_IMAGEURI, contact.get_imageURI().toString());

        db.insert(TABLE_CONTACTS,null,values);
        db.close();
    }

    public Contact getContact (int id){
        SQLiteDatabase db = getReadableDatabase();

        String[] columns = new String[]{KEY_ID,KEY_NAME,KEY_PHONE,KEY_EMAIL,KEY_ADDRESS,KEY_IMAGEURI};
        String[] stringID = new String[]{String.valueOf(id)};

        Cursor cursor = db.query(TABLE_CONTACTS,columns, KEY_ID + "=?",stringID,null,null,null,null);

        if (cursor != null){
            cursor.moveToFirst();
        }
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),Uri.parse(cursor.getString(5)));
        cursor.close();
        db.close();
        return contact;
    }

    public void deleteContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();
        String[] stringID = new String[]{String.valueOf(contact.get_id())};
        db.delete(TABLE_CONTACTS,KEY_ID + "=?",stringID);
        db.close();
    }

    public int getContactsCount(){
        SQLiteDatabase db = getReadableDatabase();
        String querySQL = "SELECT * FROM " + TABLE_CONTACTS;
        Cursor cursor = db.rawQuery(querySQL,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int updateContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, contact.get_name());
        values.put(KEY_PHONE, contact.get_phone());
        values.put(KEY_EMAIL, contact.get_email());
        values.put(KEY_ADDRESS, contact.get_address());
        values.put(KEY_IMAGEURI, contact.get_imageURI().toString());

        String[] stringID = new String[]{String.valueOf(contact.get_id())};
        int rowsAffected = db.update(TABLE_CONTACTS,values,KEY_ID + "=?",stringID);
        db.close();
        return rowsAffected;
    }

    public List<Contact> getAllContacts (){
        List<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String querySQL = "SELECT * FROM " + TABLE_CONTACTS;
        Cursor cursor = db.rawQuery(querySQL,null);

        if (cursor.moveToFirst()){
            do{
                contacts.add(new Contact(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),Uri.parse(cursor.getString(5))));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }
}
