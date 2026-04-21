package it.univaq.italiancities.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONObject;

import java.io.Serializable;

@Entity(tableName = "italianCities")
public class ItalianCity implements Serializable {

    public static ItalianCity parseData(JSONObject object) {
        try {
            ItalianCity italianCities = new ItalianCity();
            italianCities.setCode(object.getString("codice"));
            italianCities.setNome(object.getString("nome"));
            italianCities.setPrefisso(object.getString("cap"));
            italianCities.setProvinceCode(object.getString("prefisso"));
            italianCities.setCap(object.getString("provincia"));
            JSONObject coordinate = object.getJSONObject("coordinate");
            italianCities.setLatitude(coordinate.getDouble("lat"));
            italianCities.setLongitude(coordinate.getDouble("lng"));

            return italianCities;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PrimaryKey(autoGenerate = true)
    private long id; //per il db
    private String code;
    private String nome;
    private String cap;
    private String prefisso;
    private String provinceCode;
    private double latitude;
    private double longitude;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getPrefisso() {
        return prefisso;
    }

    public void setPrefisso(String prefisso) {
        this.prefisso = prefisso;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}