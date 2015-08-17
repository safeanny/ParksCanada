package com.example.tiobob.parkscanada;

public class Fees {

    // class members

    private int _id;
    private String _enDescription;
    private String _frDescription;

    // default constructor
    public Fees(){

    }

    // setters

    public void set_id(int id) {
        this._id = id;
    }

    public void set_enDescription(String enDescription) {
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
