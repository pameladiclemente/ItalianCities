package it.univaq.italiancities.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import it.univaq.italiancities.model.ItalianCity;

@Dao
public interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(List<ItalianCity> italianCities);

    @Query("SELECT * FROM italianCities ORDER BY nome")
    List<ItalianCity> findAll();


}
