package com.jingpai.pos.customer.activity.web;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.show.Home.ShowActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.bean.EventBusMessage;
import com.jingpai.pos.bean.Community;
import com.jingpai.pos.customer.fragment.ServerTabFragment;
import com.jingpai.pos.customer.utils.CommonUtil;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.OpenWxUtil;
import com.jingpai.pos.customer.utils.WxShareUtils;
import com.jingpai.pos.customer.views.ScrollConflictWebView;

import org.greenrobot.eventbus.EventBus;

import static com.jingpai.pos.BuildConfig.SHOP_URL;

/*
 * function:
 */
public class CityLifeWebFragment extends Fragment  {
    private ScrollConflictWebView mWebView;
    //    SmartRefreshLayout smartRefreshLayout;
    private  StringBuilder mUrl ;
    private  String tempUrl ="" ;
    private View view;
    // 用来显示视频的布局
    private FrameLayout mLayout;

    public CityLifeWebFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (view == null){
        view = inflater.inflate(R.layout.fragment_web, container, false);
        initView(view);
//        }
        return view;
    }

    private void initView(View view) {
        String url = "";
        Bundle bundle = getArguments();
        if(bundle!=null){
            url = bundle.getString("url");
            if (url.startsWith("/")){
                url = url.substring(1,url.length());
            }
        }
        mUrl = new StringBuilder();
        mUrl.append(SHOP_URL);
        mUrl.append(url);
        tempUrl = mUrl.toString();

        mWebView = view.findViewById(R.id.webview);
        mLayout = view.findViewById(R.id.fl_video);
//        smartRefreshLayout = view.findViewById(R.id.smart_refresh_layout);
        setWebviewAttr();
        mWebView.loadUrl(mUrl.toString());
//        smartRefreshLayout.setRefreshHeader(new RefreshHeader(getContext()));
//        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
//            mWebView.reload();
//        });
    }

    private void setWebviewAttr() {
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);//开启硬件加速
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.getSettings().setBlockNetworkImage(false);
        // 设置编码
//        webSettings.setSupportZoom(true);
//        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setUseWideViewPort(true);//将图片调整到适合webview的大小
        mWebView.getSettings().setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        mWebView.addJavascriptInterface(new AndroidAndJsInterface(), "CityLife");
        mWebView.setHapticFeedbackEnabled(false);
        //禁止长按
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        mWebView.setLongClickable(false);
        //点击超链接的时候重新在原来的进程上加载URL
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
    }


    private class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // ------  对alipays:相关的scheme处理 -------
            if(url.startsWith("alipays:") || url.startsWith("alipay")) {
                try {
                    getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                } catch (Exception e) {
//                    new AlertDialog.Builder(CityLifeWebViewActivity.this)
//                            .setMessage("未检测到支付宝客户端，请安装后重试。")
//                            .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
                    Uri alipayUrl = Uri.parse("https://d.alipay.com");
                    getActivity().startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
//                                }
//                            }).setNegativeButton("取消", null).show();
                }
                return true;
            }

            //Android 8.0以下版本的需要返回true 并且需要loadUrl()
            if (Build.VERSION.SDK_INT < 26) {
                view.loadUrl(url);
                return true;
            } else {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
            }

            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } catch (Exception e) {
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            CityLifeWebFragment.this.onPageStarted(url, favicon);
//                smartRefreshLayout.finishRefresh();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            CityLifeWebFragment.this.onPageFinished(url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }

    }


    private class MyWebChromeClient extends WebChromeClient {
        private CustomViewCallback mCustomViewCallback;
        //  横屏时，显示视频的view
        private View mCustomView;
        // 全屏的时候调用
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {

            super.onShowCustomView(view, callback);
            //如果view 已经存在，则隐藏
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }

            mCustomView = view;
            mCustomView.setVisibility(View.VISIBLE);
            mCustomViewCallback = callback;
            mLayout.addView(mCustomView);
            mLayout.setVisibility(View.VISIBLE);
            mLayout.bringToFront();

            //设置横屏
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        }

        // 切换为竖屏的时候调用
        @Override
        public void onHideCustomView() {

            super.onHideCustomView();
            if (mCustomView == null) {
                return;
            }
            mCustomView.setVisibility(View.GONE);
            mLayout.removeView(mCustomView);
            mCustomView = null;
            mLayout.setVisibility(View.GONE);
            try {
                mCustomViewCallback.onCustomViewHidden();
            } catch (Exception e) {
            }
//            titleView.setVisibility(View.VISIBLE);
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏

        }

    }

    /**
     * 横竖屏切换监听
     */
    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        switch (config.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @SuppressLint("NewApi")
    @Override
    public void onPause() {
        mWebView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
        mWebView = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
    }

    public void onPageStarted(String url, Bitmap favicon) {
    }

    public void onPageFinished(String url) {
        tempUrl = url;
        if (!isHidden()){
            //只有当fragment显示的时候，才去控制导航栏的显示隐藏
            ShowActivity showActivity = (ShowActivity) getActivity();
            showActivity.setBottomMenuVisibility(tempUrl.equals(mUrl.toString()));
            ((ServerTabFragment)CityLifeWebFragment.this.getParentFragment()).setTabVisibility(tempUrl.equals(mUrl.toString()));
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            ShowActivity showActivity = (ShowActivity) getActivity();
            if (showActivity==null)return;
            showActivity.setBottomMenuVisibility(tempUrl.equals(mUrl.toString()));
            ((ServerTabFragment)CityLifeWebFragment.this.getParentFragment()).setTabVisibility(tempUrl.equals(mUrl.toString()));
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            ShowActivity showActivity = (ShowActivity) getActivity();
            showActivity.setBottomMenuVisibility(tempUrl.equals(mUrl.toString()));
            ((ServerTabFragment)CityLifeWebFragment.this.getParentFragment()).setTabVisibility(tempUrl.equals(mUrl.toString()));
        }
    }

    private class AndroidAndJsInterface {

        @JavascriptInterface
        public void goback() {
            ShowActivity showActivity = (ShowActivity) getActivity();
            showActivity.goHome();
        }

        @JavascriptInterface
        public void gohome() {
            goback();
        }

        @JavascriptInterface
        public void homeTabShow() {
        }
        @JavascriptInterface
        public void homeTabHide() {
        }

        @JavascriptInterface
        public void jumpToLive(String url){
            Intent intent = new Intent(getActivity(), CityLifeWebViewActivity.class);
            intent.putExtra(Constant.WEB_URL,url);
            startActivity(intent);
        }
        /**
         * Js中调用的方法
         */
        @JavascriptInterface
        public String getUser() {
            return JSON.toJSONString(LocalCache.getUser());
        }

        /**
         * 获取当前小区
         *
         * @return
         */
        @JavascriptInterface
        public String getCurrentCommunity() {
            Community community = LocalCache.getCurrentCommunity();
            if (community==null){
                community = new Community();
                return JSON.toJSONString(community);
            }else{
                community.setCommunityId(community.getOrgId());
                return JSON.toJSONString(community);
            }
        }
        /**
         * 重新加载首页
         */
        @JavascriptInterface
        public void goShopHomepage() {
            EventBus.getDefault().postSticky(new EventBusMessage(Constant.EVENT_BUS_SKIP_TO_SHOPPING,null));
        }

        /**
         * 拨打电话
         */
        @JavascriptInterface
        public void dialPhoneNumber(String phone) {
            CommonUtil.showCallDialog(phone, getActivity());
        }

        @JavascriptInterface
        public void payByUrl(String url) {
            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setComponent(null);
                startActivity(intent);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }

        @JavascriptInterface
        public void share(String image, String title, String description, String url) {
            WxShareUtils.share(image, title, description, url);
        }

        @JavascriptInterface
        public String getCommunityList() {
            return JSON.toJSONString(LocalCache.getUser().getCommunities());
        }

        @JavascriptInterface
        public void wxPay(String orderNo, int tradeAmt, String businessType) {
            OpenWxUtil.openWX(getContext());
            OpenWxUtil.wxPay(getContext(), orderNo, tradeAmt, businessType);
        }
        @JavascriptInterface
        public void wxPay(String orderNo, int tradeAmt, String businessType,String token) {
            OpenWxUtil.openWX(getContext());
            OpenWxUtil.wxPay(getContext(), orderNo, tradeAmt, businessType,token);
        }
        @JavascriptInterface
        public void toast(String msg) {
            ToastUtils.showShort(msg);
        }

        /**
         * 分享到微信，点击链接时进入应用，打开指定的商品界面
         *
         * @return
         */
        @JavascriptInterface
        public String getShopUrl() {
            String shopUrl = LocalCache.getShopUrl();
            if (!shopUrl.isEmpty()) {
                LocalCache.clearShopUrl();
                return shopUrl;
            }
            return null;
        }

        @JavascriptInterface
        public void openPage(String pageName) {
            AppPage appPage = AppPage.instanceOf(pageName);

            try {
                if (appPage != null) {
                    startActivity(new Intent(getActivity(), appPage.getActivity()));
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }

    /**
     * 判断fragment是否可以返回
     *
     * @return 如果WebView  canGoBack 返回 false  否则返回 true
     */
    public boolean onBackPressed() {
        if (mWebView == null) {
            return true;
        }
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return false;
        } else {
            return true;
        }

    }

    public void loadUrl() {
        mWebView.reload();
    }
}
