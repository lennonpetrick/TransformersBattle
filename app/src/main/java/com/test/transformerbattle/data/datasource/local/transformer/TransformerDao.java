package com.test.transformerbattle.data.datasource.local.transformer;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.test.transformerbattle.data.datasource.local.DBConstant;
import com.test.transformerbattle.data.entity.TransformerEntity;

import java.util.List;

@Dao
public interface TransformerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TransformerEntity entity);

    @Update()
    void update(TransformerEntity entity);

    @Delete()
    void delete(TransformerEntity entity);

    @Query("SELECT * FROM " + DBConstant.TRANSFORMER_TABLE)
    List<TransformerEntity> getAll();

}
