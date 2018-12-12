/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.hxd.root.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.hxd.root.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 保存图片，重复插入图片提示已存在
 *
 * @author Cazaea
 */
public class RxSaveImage {

    private static Observable<Uri> saveImageAndGetPathObservable(Activity context, String url, String title) {
        return Observable.unsafeCreate((ObservableSource<Bitmap>) subscriber -> {
            // 检查路径
            if (TextUtils.isEmpty(url) || TextUtils.isEmpty(title)) {
                subscriber.onError(new Exception("请检查图片路径"));
            }
            // 检查图片是否已存在
            File appDir = new File(Environment.getExternalStorageDirectory(), "濠寓相册");
            if (appDir.exists()) {
                String fileName = title.replace('/', '-') + ".jpg";
                File file = new File(appDir, fileName);
                if (file.exists()) {
                    subscriber.onError(new Exception("图片已存在"));
                }
            }
            // 获得Bitmap
            Bitmap bitmap = null;
            try {
                bitmap = Glide.with(context)
                        .asBitmap()
                        .load(url)
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();

            } catch (Exception e) {
                subscriber.onError(e);
            }
            if (bitmap == null) {
                subscriber.onError(new Exception("无法下载到图片"));
            }
            subscriber.onNext(bitmap);
            subscriber.onComplete();
        }).flatMap(bitmap -> {
            File appDir = new File(Environment.getExternalStorageDirectory(), "濠寓相册");
            if (!appDir.exists()) {
                boolean success = appDir.mkdir();
                DebugUtil.debug("相册创建：" + (success ? "成功" : "失败"));
            }
            String fileName = title.replace('/', '-') + ".jpg";
            File file = new File(appDir, fileName);
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                assert bitmap != null;
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Uri uri = Uri.fromFile(file);
            // 通知图库更新
            Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
            context.sendBroadcast(scannerIntent);
            return Observable.just(uri);
        }).subscribeOn(Schedulers.io());
    }


    @SuppressLint("CheckResult")
    public static void saveImageToGallery(Activity context, String mImageUrl, String mImageTitle) {
        // @formatter:off
        RxSaveImage.saveImageAndGetPathObservable(context, mImageUrl, mImageTitle)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> {
                    File appDir = new File(Environment.getExternalStorageDirectory(), "濠寓相册");
                    String msg = String.format(CommonUtils.getString(R.string.picture_has_save_to), appDir.getAbsolutePath());
                    ToastUtil.showLong(msg);
                }, error -> ToastUtil.showLong(error.getMessage()));
//         @formatter:on
//        addSubscription(s);
    }

}
