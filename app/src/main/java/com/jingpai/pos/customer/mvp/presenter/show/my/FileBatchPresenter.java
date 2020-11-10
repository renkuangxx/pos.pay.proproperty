package com.jingpai.pos.customer.mvp.presenter.show.my;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.bean.show.FileBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 时间: 2020/2/13
 * 功能:
 */
public class FileBatchPresenter extends BasePresenter_Old {

    public void getFileBatch(ArrayList<File> files, String fileType, Callback<JSONArray> callback) {

        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
        }

        exeCallback(NetWorkUtil.getInstance().apiService.fileBatch(parts, fileType), JSONArray.class, callback);
    }


    public void updateFile(File file, Callback<FileBean> callback) {
        RequestBody requestBody = MultipartBody.create(MediaType.parse("multipart/form-data"), file);
        Log.d(Constant.TAG, "image size: " + (file.length() / 1024));
        RequestBody fb = RequestBody.create(MediaType.parse("fileType"), "IMAGE");
        MultipartBody.Part images = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        exeCallback(NetWorkUtil.getInstance().apiService.file(images, fb), FileBean.class, callback);
    }

    public void updateFile(Bitmap bitmap, Callback<FileBean> callback) {
        File file = saveFile(bitmap);
        RequestBody requestBody = MultipartBody.create(MediaType.parse("multipart/form-data"), file);
        Log.d(Constant.TAG, "image size: " + (file.length() / 1024));
        RequestBody fb = RequestBody.create(MediaType.parse("fileType"), "IMAGE");
        MultipartBody.Part images = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        exeCallback(NetWorkUtil.getInstance().apiService.file(images, fb), FileBean.class, callback);
    }

    public File saveFile(Bitmap bitmap) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "citylift_image_temp.jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
