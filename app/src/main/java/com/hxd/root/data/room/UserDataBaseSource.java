package com.hxd.root.data.room;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.hxd.root.utils.AppExecutors;
import com.hxd.root.utils.DebugUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Cazaea
 * @date 2018/4/19
 * @Description
 */
public class UserDataBaseSource {

    private static volatile UserDataBaseSource INSTANCE;
    private UserDao mUserDao;
    private AppExecutors mAppExecutors;

    private UserDataBaseSource(@NonNull AppExecutors mAppExecutors, @NonNull UserDao mUserDao) {
        this.mAppExecutors = mAppExecutors;
        this.mUserDao = mUserDao;
    }

    public static UserDataBaseSource getInstance(@NonNull AppExecutors appExecutors, @NonNull UserDao tasksDao) {
        if (INSTANCE == null) {
            synchronized (UserDataBaseSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserDataBaseSource(appExecutors, tasksDao);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 查找任何的bean(没有数据时会报错！)：
     * 如果数据库里有一条数据就返回这条数据
     * 如果有多条信息，则返回第一条数据
     */
    public void getSingleBean(UserDataCallback callback) {
        Runnable runnable = () -> {
            try {
                User user = mUserDao.findSingleBean();
                mAppExecutors.mainThread().execute(() -> {
                    if (user == null) {
                        callback.onDataNotAvailable();
                    } else {
                        callback.getData(user);
                    }
                });
            } catch (Exception e) {
                DebugUtil.error(e.getMessage());
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    /**
     * 先删除后再添加: 重新登录时
     */
    public void addData(@NonNull User user) {
        Runnable runnable = () -> {
            try {
                mUserDao.addUser(user);
            } catch (Exception e) {
                DebugUtil.error(e.getMessage());
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }


    /**
     * 更新数据
     */
    public void updateData(@NonNull User user) {
        Runnable saveRunnable = () -> {
            try {
                mUserDao.addUser(user);
            } catch (Exception e) {
                DebugUtil.error(e.getMessage());
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    /**
     * 清除数据库
     */
    public void deleteAllData() {
        Runnable saveRunnable = () -> {
            try {
                mUserDao.deleteAll();
            } catch (Exception e) {
                DebugUtil.error(e.getMessage());
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    /**
     * 获取数据集合
     */
    @SuppressLint("CheckResult")
    public void getAll() {
        UserDataBase.getDatabase().waitDao().findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
//                        DebugUtil.error("----waitList.size():" + users.size());
//                        DebugUtil.error("----waitList:" + users.toString());
                });
    }

    /**
     * 获取全部数据集合
     */
    public void getAllData() {
        Runnable runnable = () -> {
            try {
                List<User> waits = mUserDao.findUsers();
                mAppExecutors.mainThread().execute(() -> {

                });
            } catch (Exception e) {
                DebugUtil.error(e.getMessage());
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }
}
