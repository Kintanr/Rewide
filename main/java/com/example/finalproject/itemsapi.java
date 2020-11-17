package com.example.finalproject;

public class itemsapi {
    String sapikey;
    String title;
    String weight;
    String breed;
    String sex;
    String place;
    String date;

    public itemsapi() {

    }

    public itemsapi(String sapikey, String title, String weight, String breed, String sex, String place, String date) {
        this.sapikey = sapikey;
        this.title = title;
        this.weight = weight;
        this.breed = breed;
        this.sex = sex;
        this.place = place;
        this.date = date;
    }


    public String getSapikey() {
        return sapikey;
    }

    public void setSapikey(String sapikey) {
        this.sapikey = sapikey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}