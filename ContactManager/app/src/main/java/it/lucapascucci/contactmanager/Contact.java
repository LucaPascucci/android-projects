package it.lucapascucci.contactmanager;

import android.net.Uri;

/**
 * Created by Luca on 07/03/15.
 */
public class Contact {

    private int _id;
    private String _name,_phone,_email,_address;
    private Uri _imageURI;

    public Contact (int id,String name,String phone,String email, String address, Uri imageURI){
        this._id = id;
        this._name = name;
        this._phone = phone;
        this._email = email;
        this._address = address;
        this._imageURI = imageURI;
    }

    public int get_id(){
        return this._id;
    }

    public String get_name(){
        return this._name;
    }

    public String get_phone(){
        return this._phone;
    }

    public String get_email(){
        return this._email;
    }

    public String get_address(){
        return this._address;
    }

    public Uri get_imageURI(){
        return this._imageURI;
    }
}
