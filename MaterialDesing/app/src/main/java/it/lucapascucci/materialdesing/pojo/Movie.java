package it.lucapascucci.materialdesing.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Luca on 19/03/15.
 */
public class Movie implements Parcelable{

    private long id;
    private String title;
    private Date releaseDateTheather;
    private int audienceScore;
    private String synopsis;
    private String urlThumbnail;
    private String urlSelf;
    private String urlCast;
    private String urlReview;
    private String urlSimilar;

    public Movie (){

    }

    public Movie(Parcel input){
        this.id = input.readLong();
        this.title = input.readString();
        this.releaseDateTheather = new Date(input.readLong());
        this.audienceScore = input.readInt();
        this.synopsis = input.readString();
        this.urlThumbnail = input.readString();
        this.urlSelf = input.readString();
        this.urlCast = input.readString();
        this.urlReview = input.readString();
        this.urlSimilar = input.readString();
    }

    public Movie(long id, String title, Date releaseDateTheather, int audienceScore, String synopsis, String urlThumbnail, String urlSelf, String urlCast, String urlReview, String urlSimilar) {
        this.id = id;
        this.title = title;
        this.releaseDateTheather = releaseDateTheather;
        this.audienceScore = audienceScore;
        this.synopsis = synopsis;
        this.urlThumbnail = urlThumbnail;
        this.urlSelf = urlSelf;
        this.urlCast = urlCast;
        this.urlReview = urlReview;
        this.urlSimilar = urlSimilar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDateTheather() {
        return releaseDateTheather;
    }

    public void setReleaseDateTheather(Date releaseDateTheather) {
        this.releaseDateTheather = releaseDateTheather;
    }

    public int getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(int audienceScore) {
        this.audienceScore = audienceScore;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    public String getUrlSelf() {
        return urlSelf;
    }

    public void setUrlSelf(String urlSelf) {
        this.urlSelf = urlSelf;
    }

    public String getUrlCast() {
        return urlCast;
    }

    public void setUrlCast(String urlCast) {
        this.urlCast = urlCast;
    }

    public String getUrlReview() {
        return urlReview;
    }

    public void setUrlReview(String urlReview) {
        this.urlReview = urlReview;
    }

    public String getUrlSimilar() {
        return urlSimilar;
    }

    public void setUrlSimilar(String urlSimilar) {
        this.urlSimilar = urlSimilar;
    }

    @Override
    public String toString() {
        return "ID: " + this.id +
                "Title " + this.title +
                "Date " + this.releaseDateTheather +
                "Synopsis " + this.synopsis +
                "Score " + this.audienceScore +
                "urlThumbnail " + this.urlThumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeLong(this.releaseDateTheather.getTime());
        dest.writeInt(this.audienceScore);
        dest.writeString(this.synopsis);
        dest.writeString(this.urlThumbnail);
        dest.writeString(this.urlSelf);
        dest.writeString(this.urlCast);
        dest.writeString(this.urlReview);
        dest.writeString(this.urlSimilar);
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie [size];
        }
    };
}
