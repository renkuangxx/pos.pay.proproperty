package com.jingpai.pos.presenter.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.core.content.FileProvider;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.bean.VersionBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class VersionPresenter extends BasePresenter_Old {
    private static final String APK_NAME = "CityLife.apk";

    private static final int LOAD_APK_PROGRESS = 1;

    private static final int FINSHED_LOAD_APK = 2;

    private boolean cancel;

    private int progress;

    private Activity context;

//    private VersionBean versionBean;

    private Dialog progressDialog;

    private ProgressBar progressBar;

    private Timer timer;

    private boolean isForce =false ;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg != null) {
                try {
                    switch (msg.what) {
                        case LOAD_APK_PROGRESS: {
                            progressBar.setProgress(progress);
                            break;
                        }
                        case FINSHED_LOAD_APK: {
                            if (timer != null) {
                                timer.cancel();
                            }
                            context.finish();
                            progressDialog.dismiss();

                            installApk(new File(getApkFilePath()));
                            break;
                        }
                    }
                } catch (Exception e) {
                    Log.e(Constant.TAG, e.toString());
                }
            }
        }
    };

    private String getApkFilePath() {
        return getDownloadPath() + File.separator + APK_NAME;
    }

    public VersionPresenter(Activity context) {
        this.context = context;
    }

    public void checkUpdate(Callback<VersionBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.getLatestVersion(Constant.APP_ID),VersionBean.class, callback);
    }

    private void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    private String getDownloadPath() {
        return context.getExternalFilesDir("external_files").toString();
    }

    private void showProgressDialog() {
        // 实例化布局文件
        View view = View.inflate(context, R.layout.layout_process, null);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setMax(100);

        // 实例化 Dialog
        progressDialog = new Dialog(context);
        // 获取窗口管理器
        Window dialogWindow = progressDialog.getWindow();
        // 隐藏标题栏
        dialogWindow.requestFeature(Window.FEATURE_NO_TITLE);
        // 设置显示的View
        dialogWindow.setContentView(view);
        // 设置显示的位置
        dialogWindow.setGravity(Gravity.CENTER);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        if (isForce){
            progressDialog.setCancelable(!isForce);
        }


    }

    private void initTime() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(LOAD_APK_PROGRESS);
            }
        }, 1000, 1000);
    }

    public void startDownLoad(String downLoadUrl){
        download(downLoadUrl);
        showProgressDialog();
        initTime();
    }
    public void download(String downLoadUrl) {
        progress = 0;
        cancel = false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                FileOutputStream fos = null;
                InputStream is = null;
                try {
                    // 判断SD卡是否存在，并且是否具有读写权限
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        // 获得存储卡的路径
                        String mSavePath = getDownloadPath();
                        URL url = new URL(downLoadUrl);
                        // 创建连接
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.connect();
                        // 获取文件大小
                        int length = conn.getContentLength();

                        // 创建输入流
                        is = conn.getInputStream();

                        File file = new File(mSavePath);
                        // 判断文件目录是否存在
                        if (!file.exists()) {
                            file.mkdirs();
                        }

                        File apkFile = new File(getApkFilePath());

                        if (!apkFile.exists()) {
                            apkFile.createNewFile();
                        }

                        fos = new FileOutputStream(apkFile);
                        int count = 0;
                        // 缓存
                        byte buf[] = new byte[1024 * 512];
                        // 写入到文件中
                        int numread = 0;
                        while (!cancel && (numread = is.read(buf)) > 0) {
                            count += numread;
                            // 计算进度条位置
                            progress = (int) (((float) count / length) * 100);
                            // 写入文件
                            fos.write(buf, 0, numread);
                        }
                        ;

                        if (count == length) {
                            // 下载完成
                            handler.sendEmptyMessage(FINSHED_LOAD_APK);
                        }

                        fos.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }

                        if (is != null) {
                            is.close();
                        }
                    } catch (Exception ex) {

                    }
                }
            }
        }).start();

    }

    public int getProgress() {
        return progress;
    }

    public void cancelUpdate() {
        cancel = true;
    }

}