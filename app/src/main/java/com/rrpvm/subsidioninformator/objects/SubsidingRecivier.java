package com.rrpvm.subsidioninformator.objects;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class SubsidingRecivier implements Serializable {
    public SubsidingRecivier(boolean male, String surname, String name, String patronymic, String region, String city, Date birthdate, String position, String itn, String passportId, Subsidion subsidion, String image) {
        this.male = male;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.region = region;
        this.city = city;
        this.birthdate = birthdate;
        this.position = position;
        this.image = image;
        this.ITN = itn;
        this.passportId = passportId;
        this.subsidionData = subsidion;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getRegion() {
        return region;
    }

    public String getPosition() {
        return position;
    }

    public String getCity() {
        return city;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getImage() {
        return image;
    }

    public String getITN() {
        return ITN;
    }

    public String getPassportId() {
        return passportId;
    }

    public boolean isMale() {
        return male;
    }

    public Subsidion getSubsidionData() {
        return this.subsidionData;
    }

    public String getPIB() {
        return surname + " " + name + " " + patronymic;
    }
    public void setSNP(String splitedString){//NSP-name,surname,patronomyc
        splitedString = splitedString.trim();
        String strs[] = splitedString.split(" ");
        name = strs[1];
        surname=strs[0];
        patronymic=strs[2];
    }


    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public void setITN(String ITN) {
        this.ITN = ITN;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public void setSubsidionData(Subsidion subsidionData) {
        this.subsidionData = subsidionData;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object s1) {//pib, passId, ITN, region, city, subs id(больше  - не надо)
        if (this == s1) return true;
        SubsidingRecivier s = (SubsidingRecivier) s1;
        if (passportId.equals(s.passportId) && ITN.equals(s.ITN) && region.equals(s.region) && city.equals(s.city))
            if (name.equals(s.name)&&surname.equals(s.surname)&&patronymic.equals(s.patronymic)&&birthdate.equals(s.birthdate))
                return true;
        return false;
    }

    //general data:
    private String name;
    private String surname;
    private String patronymic;
    private String region;
    private String city;
    private String position;
    private Date birthdate;
    private boolean male;
    private String ITN;//ИНН
    private String passportId;//номер паспорта
    private Subsidion subsidionData;
    //RENDER DATA:
    private String image;//path for (icon of person)
}
