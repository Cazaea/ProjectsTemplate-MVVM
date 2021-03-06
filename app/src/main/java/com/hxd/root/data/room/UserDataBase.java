package com.hxd.root.data.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.hxd.root.app.RootApplication;

/**
 * @author Cazaea
 * @date 2018/4/20
 * @description you've changed schema but forgot to update the version number
 * 改变数据库结构要改变版本号,不然会抛异常！
 */

@Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class UserDataBase extends RoomDatabase {

    private static UserDataBase sInstance;

    public abstract UserDao waitDao();

    public static UserDataBase getDatabase() {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(RootApplication.getInstance(),
                    UserDataBase.class, "User.db")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return sInstance;
    }

    /**
     * 版本号迁移：
     * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/0728/8278.html
     */
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    public static void onDestroy() {
        sInstance = null;
    }


}
