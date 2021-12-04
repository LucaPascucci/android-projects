package it.lucapascucci.check_in;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Luca on 22/04/15.
 */
public class CheckIn implements Serializable{

    public static CheckIn createNewCheckIn(){
        return new CheckIn(new Date());
    }

    private final Date date;
    private double latitudine,longitudine;

    public CheckIn(Date data){
        this.date = data;
    }

    public Date getDate() {
        return date;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public boolean isValid(){
        return true;
    }
}
