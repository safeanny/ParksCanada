package com.example.tiobob.parkscanada;

public class ParksId {

    // class members

    private int _id;
    private String _upEngFull;
    private String _upFreFull;
    private String _lowEngFull;
    private String _lowFreFull;
    private String _lowEng;
    private String _lowFre;
    private String _upAlphaFrFull;
    private String _lowFrNameFirst;
    private String _upNoEnFull;
    private String _upNoFrFull;

    // default constructor
    public ParksId(){

    }

    // setters

    public void set_id(int id) {
        this._id = id;
    }

    public void set_upEngFull(String upEngFull) {
        this._upEngFull = upEngFull;
    }

    public void set_upFreFull(String upFreFull) {
        this._upFreFull = upFreFull;
    }

    public void set_lowEngFull(String lowEngFull) {
        this._lowEngFull = lowEngFull;
    }

    public void set_lowFreFull(String lowFreFull) {
        this._lowFreFull = lowFreFull;
    }

    public void set_lowEng(String lowEng) {
        this._lowEng = lowEng;
    }

    public void set_lowFre(String lowFre) {
        this._lowFre = lowFre;
    }

    public void set_upAlphaFrFull(String upAlphaFrFull) {
        this._upAlphaFrFull = upAlphaFrFull;
    }

    public void set_lowFrNameFirst(String lowFrNameFirst) {
        this._lowFrNameFirst = lowFrNameFirst;
    }

    public void set_upNoEnFull(String upNoEnFull) {
        this._upNoEnFull = upNoEnFull;
    }

    public void set_upNoFrFull(String upNoFrFull) {
        this._upNoFrFull = upNoFrFull;
    }

    // getters

    public int get_id() {
        return _id;
    }

    public String get_upEngFull() {
        return _upEngFull;
    }

    public String get_upFreFull() {
        return _upFreFull;
    }

    public String get_lowEngFull() {
        return _lowEngFull;
    }

    public String get_lowFreFull() {
        return _lowFreFull;
    }

    public String get_lowEng() {
        return _lowEng;
    }

    public String get_lowFre() {
        return _lowFre;
    }

    public String get_upAlphaFrFull() {
        return _upAlphaFrFull;
    }

    public String get_lowFrNameFirst() {
        return _lowFrNameFirst;
    }

    public String get_upNoEnFull() {
        return _upNoEnFull;
    }

    public String get_upNoFrFull() {
        return _upNoFrFull;
    }

    @Override
    public String toString() {

        // chk which language to return
        if(MyConstants.LANGUAGE == 1){
            return _lowEngFull;
        }
        else{
            return _lowFreFull;
        }
    }
}
