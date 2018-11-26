package com.test.transformerbattle.data.datasource.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.test.transformerbattle.data.datasource.local.transformer.TransformerDao;
import com.test.transformerbattle.data.entity.TransformerEntity;

@Database(entities = {TransformerEntity.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase INSTANCE;

    public static synchronized LocalDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    LocalDatabase.class,
                    "TransformerBattle.db")
                    .build();
        }

        return INSTANCE;
    }

    public abstract TransformerDao transformerDao();

}