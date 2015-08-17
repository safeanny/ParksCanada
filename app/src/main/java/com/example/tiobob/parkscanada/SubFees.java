package com.example.tiobob.parkscanada;

/**
 * Created by TioBob on 2015-08-14.
 */
public class SubFees {

    // class members

    private int _id;
    private String _enDescription;
    private String _frDescription;

    // default constructor

    public SubFees(){

    }

    // setters

    public void set_id(int id) {
        this._id = id;
    }

    public void setEnDescription(String enDescription) {
        this._enDescription = enDescription;
    }

    public void set_frDescription(String frDescription) {
        this._frDescription = frDescription;
    }

    // getters


    public int get_id() {
        return _id;
    }

    public String get_enDescription() {
        return _enDescription;
    }

    public String get_frDescription() {
        return _frDescription;
    }

    @Override
    public String toString() {

        // chk which language to return
        if(MyConstants.LANGUAGE == 1){
            return _enDescription;
        }
        else{
            return _frDescription;
        }
    }
}
