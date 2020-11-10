package com.jingpai.pos.customer.base;
/**
 * Create by liujinheng
 * date 2020/5/18
 * function
 */
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.di.component.ActivityComponent;
import com.jingpai.pos.customer.di.component.DaggerActivityComponent;
import com.jingpai.pos.customer.di.module.ActivityModule;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class MVPBaseActivity<T extends MVPBaseContract.BasePresenter> extends RxAppCompatActivity implements MVPBaseContract.BaseView, Toolbar.OnMenuItemClickListener {
    @Nullable
    @Inject
    protected T mPresenter;
    protected ActivityComponent mActivityComponent;

    protected Toolbar mToolbar;
    protected TextView mToolbarTitle;

    protected ProgressDialog mProgressDialog;

    private Unbinder unbinder;

    private long mBackPressedTime;
    protected boolean isDoubleClick = false;

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initView();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityComponent();
        ARouter.getInstance().inject(this);
        int layoutId = getLayoutId();
        setContentView(layoutId);
        initInjector();
        unbinder = ButterKnife.bind(this);
//        ImmersionBarHelper.init(this);
        //activity 管理
        ActivityManager.getAppManager().addActivity(this);
        attachView();
        initView();
        if (!NetworkUtils.isConnected()) showNoNet();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
//        ImmersionBarHelper.destory(this);
        detachView();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        ImmersionBarHelper.setDarkFont(this, null);
    }

    @Override
    public void showLoading() {
        mProgressDialog= ProgressDialog.show(this,
                "", "加载中...", true, false);
    }
    public boolean isLoading() {
        return mProgressDialog.isShowing();
    }
    @Override
    public void hideLoading() {
        if (null != mProgressDialog){
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showFailed(String message) {
        ToastUtils.showShort(message);
    }

    @Override
    public void jumpToLogin() {
//        ARouter.getInstance().build(Constant.LOGIN)
//                .navigation();
    }
    protected void initTitle(String titleText) {
//        ToolbarHelper.instance().init(this, null)
//                .setTitle(titleText)
//                .setTitleColor(Color.WHITE)
//                .setLeft(R.drawable.base_ic_black_back, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        finish();
//                    }
//                });
    }

    @Override
    public void showSuccess(String successMsg) {
        ToastUtils.showShort(successMsg);
    }


    @Override
    public void showNoNet() {
//        ToastUtils.showShort(R.string.no_network_connection);
    }


    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }


    protected void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    /**
     * 初始化ActivityComponent
     */
    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((MyApplication) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                break;
        }
        return true;
    }

    /**
     * 只有返回的toolbar
     *
     * @param hasToolbar
     * @param hasBack
     */
    protected void setToolbarView(boolean hasToolbar, boolean hasBack) {
        mToolbar = findViewById(R.id.toolbar);
        mToolbarTitle = findViewById(R.id.toolbar_title);

        if (hasToolbar) {
            if (mToolbar == null) {
                return;
            }
            mToolbarTitle.setText(getString(R.string.app_name));

            if (hasBack) {
//                mToolbar.setNavigationIcon(R.mipmap.arrow_back);
//                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        finish();
//                    }
//                });
            }
        } else {
            if (mToolbar != null) {
                mToolbar.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 有title的toolbar
     *
     * @param hasToolbar
     * @param hasBack
     * @param title
     */
    protected void setToolbarView(boolean hasToolbar, boolean hasBack, String title) {
        setToolbarView(hasToolbar, hasBack);
        if (title != null) {
            mToolbarTitle.setText(title);
        }
    }

    /**
     * 有menu的toolbar
     *
     * @param hasToolbar
     * @param hasBack
     * @param title
     * @param menuId
     */
    protected void setToolbarView(boolean hasToolbar, boolean hasBack, String title, int menuId) {
        setToolbarView(hasToolbar, hasBack, title);
        setUpMenu(menuId);
    }

    /**
     * 设置menu
     *
     * @param menuId
     */
    protected void setUpMenu(int menuId) {
        if (mToolbar != null) {
            mToolbar.getMenu().clear();
            if (menuId > 0) {
                mToolbar.inflateMenu(menuId);
                mToolbar.setOnMenuItemClickListener(this);
            }
        }
    }

    /**
     * 贴上view
     */
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 分离view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    public void setDoubleClick(boolean doubleClick) {
        isDoubleClick = doubleClick;
    }

    /**
     * 双击退出程序
     */
    @Override
    public void onBackPressed() {
        if (isDoubleClick) {
            long curTime = SystemClock.uptimeMillis();
            if ((curTime - mBackPressedTime) < (2 * 1000)) {
                finish();
                ActivityManager.getAppManager().AppExit();
            } else {
                mBackPressedTime = curTime;
                Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
            }
        } else {
            finish();
        }
    }
}
