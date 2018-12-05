package com.hxd.root.data.room;

import com.hxd.root.utils.AppExecutors;

/**
 * @author Cazaea
 * @date 2018/4/19
 * @Description
 */

public class Injection {

    public static UserDataBaseSource get() {
        UserDataBase database = UserDataBase.getDatabase();
        return UserDataBaseSource.getInstance(new AppExecutors(), database.waitDao());
    }

}
