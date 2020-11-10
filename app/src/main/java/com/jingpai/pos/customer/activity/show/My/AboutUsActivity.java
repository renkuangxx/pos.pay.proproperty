package com.jingpai.pos.customer.activity.show.My;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.web.CityLifeWebViewActivity;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.views.TipsDialog;
import com.jingpai.pos.presenter.login.VersionPresenter;
import com.jingpai.pos.customer.utils.DataCleanManager;
import com.jingpai.pos.utils.ToastUtils;
import com.jingpai.pos.customer.views.DownLoadDialog;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 时间: 2020/2/29
 * 功能:
 */
public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.tv_app_name)
    TextView tvAppName;
    @BindView(R.id.tv_version_number)
    TextView tvVersionNumber;
    @BindView(R.id.tv_about_us)
    LinearLayout tvAboutUs;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.iv_about)
    ImageView ivAbout;
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.iv_cache)
    ImageView ivCache;
    private static final int DOWNLOADAPK_ID = 10;
    @BindView(R.id.ll_main)
    LinearLayout llMain;

    @Override
    protected int getLayout() {
        return R.layout.activity_about;
    }


    @Override
    protected void initData() {
        tvAppName.setText(AppUtils.getAppName());
        String versionName = AppUtils.getAppVersionName();
        if (!versionName.startsWith("V")) {
            versionName = "V" + versionName;
        }
        tvVersionNumber.setText(versionName);
        tvVersion.setText(versionName);
        initEx();
    }

    private void initEx()  {
        try {
            long cacheSize = getFolderSize(getCacheDir());
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                cacheSize += getFolderSize(getExternalCacheDir());
            }
            tvCache.setText(getFormatSize(cacheSize).toString());//将获取到的大小set进去
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static String getFormatSize(double size) throws Exception {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    @OnClick({R.id.tv_cache, R.id.iv_cache})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cache:
            case R.id.iv_cache:

                TipsDialog tipsDialog = new TipsDialog(this, "确定清除缓存吗？");
                tipsDialog.show();
                tipsDialog.setOnOkClick(v -> {
                    DataCleanManager.cleanApplicationData(AboutUsActivity.this);
                    Toast.makeText(AboutUsActivity.this, "清除成功", Toast.LENGTH_SHORT).show();
                    tvCache.setText("0.0MB");
                    tipsDialog.dismiss();
                });

                break;

        }
    }


    @OnClick({R.id.ll_service_agreement})
    public void toServiceAgreement() {
        Intent intent = new Intent(AboutUsActivity.this, CityLifeWebViewActivity.class);
        intent.putExtra(Constant.WEB_URL, Constant.AGREEMENT_URL + "?code=0");
        startActivity(intent);
    }

    @OnClick({R.id.ll_privacy_agreement})
    public void toPrivacyAgreement() {
        Intent intent = new Intent(AboutUsActivity.this, CityLifeWebViewActivity.class);
        intent.putExtra(Constant.WEB_URL, Constant.AGREEMENT_URL + "?code=1");
        startActivity(intent);
    }

    @OnClick(R.id.tv_about_us)
    public void checkVersion() {
        VersionPresenter versionPresenter = new VersionPresenter(this);
        versionPresenter.checkUpdate(versionBean -> {
            if (versionBean != null && versionBean.getVersionCode() > AppUtils.getAppVersionCode()) {
                DownLoadDialog downLoadDialog = new DownLoadDialog(AboutUsActivity.this, versionBean);
                downLoadDialog.setOkListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        versionPresenter.startDownLoad(versionBean.getDownloadUrl());
                    }
                });
                downLoadDialog.show(llMain);
            }{
                ToastUtils.INSTANCE.showToast("已是最新版本");
            }
        });

//        //检测版本更新的弹窗
//        AppUpdateDialog appUpdateDialog = new AppUpdateDialog(this);
//        getLifecycle().addObserver(appUpdateDialog);
    }


    /**
     * 用来判断服务是否运行.
     *
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    private boolean isServiceRunning(String className) {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) this
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}