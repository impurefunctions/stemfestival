package org.gdggaborone.stemfestival2019.models;

import java.util.ArrayList;


public class SpeakerModel {

  private int id;
  private String name, gdgName, interests, bio, photoUrl, shortBio, title, country, company;
  private ArrayList<SocialModel> socials;
  private ArrayList<String> tags;

  public  SpeakerModel() {}

  public SpeakerModel(int id, String name, String gdgName, String interests, String bio, String photoUrl, String shortBio, String title, String country, String company, ArrayList<SocialModel> socials, ArrayList<String> tags) {
    this.id = id;
    this.name = name;
    this.gdgName = gdgName;
    this.interests = interests;
    this.bio = bio;
    this.photoUrl = photoUrl;
    this.shortBio = shortBio;
    this.title = title;
    this.country = country;
    this.company = company;
    this.socials = socials;
    this.tags = tags;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public ArrayList<SocialModel> getSocials() {
    return socials;
  }

  public void setSocials(ArrayList<SocialModel> socials) {
    this.socials = socials;
  }

  public ArrayList<String> getTags() {
    return tags;
  }

  public void setTags(ArrayList<String> tags) {
    this.tags = tags;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGdgName() {
    return gdgName;
  }

  public void setGdgName(String gdgName) {
    this.gdgName = gdgName;
  }

  public String getInterests() {
    return interests;
  }

  public void setInterests(String interests) {
    this.interests = interests;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public String getShortBio() {
    return shortBio;
  }

  public void setShortBio(String shortBio) {
    this.shortBio = shortBio;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }
}