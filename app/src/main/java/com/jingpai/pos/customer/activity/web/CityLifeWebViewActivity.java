package com.jingpai.pos.customer.activity.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hb.dialog.dialog.LoadingDialog;
import com.jingpai.pos.BuildConfig;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.housemember.MemberManageActivity;
import com.jingpai.pos.activity.payment.PaymentActivity;
import com.jingpai.pos.customer.activity.show.Home.ShowActivity;
import com.jingpai.pos.customer.base.BaseForLiveShowActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.bean.EventBusMessage;
import com.jingpai.pos.customer.bean.WebViewJSBean;
import com.jingpai.pos.bean.Community;
import com.jingpai.pos.customer.utils.CommonUtil;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.MD5Utils;
import com.jingpai.pos.customer.utils.OpenWxUtil;
import com.jingpai.pos.customer.utils.WxShareUtils;
import com.jingpai.pos.customer.views.ScrollConflictWebView;
import com.stx.xhb.androidx.OnDoubleClickListener;

import org.greenrobot.eventbus.EventBus;

import static com.jingpai.pos.customer.base.Constant.WEB_BACK_URL;

public class CityLifeWebViewActivity extends BaseForLiveShowActivity  {

    private ScrollConflictWebView mWebView;
    private LoadingDialog loadingDialog;
    // 用来显示视频的布局
    private FrameLayout mLayout;
    String url;
    String backUrl;
    String mTitle;
    int isGoHomepage;
    int isNeedRight;
    boolean showBack;
    @Override
    protected int getLayout() {
        return R.layout.activity_web_view;
    }
    @Override
    protected void initData() {
        loadingDialog = new LoadingDialog(this);
        mWebView = findViewById(R.id.webview);
        mLayout = findViewById(R.id.fl_video);
        setWebviewAttr();
        Intent intent = getIntent();
        url = intent.getStringExtra(Constant.WEB_URL);
        backUrl = intent.getStringExtra(Constant.WEB_BACK_URL);
        String html = intent.getStringExtra(Constant.WEB_HTML);
        showBack = intent.getBooleanExtra(Constant.WEB_BACK, false);
        mTitle = intent.getStringExtra(Constant.WEB_TITLE);
        if (intent!=null){
            isGoHomepage = intent.getIntExtra(Constant.WEB_BAOBING_BACK,0);
            isNeedRight = intent.getIntExtra(Constant.WEB_TITLE_RIGHT,0);
        }
        showTitle();

        if (!StringUtils.isEmpty(url)) {
            if(url.contains("live.vhall.com")){
                url = url +"?email="+ MD5Utils.INSTANCE.digest(
                        LocalCache.getUser().getPhone()+"YFNR15b8FgX0sZgO")+"@jphl.com&name="
                        +CommonUtil.strEncry(LocalCache.getUser().getPhone(), 3, 7);
                isLiveShow();
            }
            mWebView.loadUrl(url);
        } else {
            System.out.println("#####"+html);
            mWebView.loadHtml(setWebVIewImage(html));
        }

//        url = "http://139.199.151.180:8007/htValidate/mobile/getInfoPage?" +
//                "INFO=%7B%22DATA_TYPE%22%3A%222%22%2C%22LEVEL%22%3A%225%22%2C%22ORGID%22%3A%229100%22%2C%22REQ_SN%22%3A%22HT1318140546073530368%22%2C%22SUBMIT_TIME%22%3A%2220201019184250%22%2C%22SYSID%22%3A%221902271423530473681%22%2C%22TRX_CODE%22%3A%22101004%22%2C%22VERSION%22%3A%22v2.0%22%2C%22SIGNED_MSG%22%3A%22eVywvbCJYmuBjfFKozHMEGctUh6XMwPkMuJEyV0wl3VljRpgAdydCI%2BRX7DmfcOvpyOiITT%2BwPVWXp4vqyn%2BToXGsi00SuB2ldPkmol6w%2BaxeYaOpQILCNovzCwKQxHtKJ07GSwucoc7VckNNnbFOsBfAwNKLE1FWPXqn%2F4LMJhmN2domVnRLnpfNuzkuGvSu4s%2FoHQXXOXz%2BUFzJwevED6LaIexdMhXiQu832OV%2FRlddIRiXau1xSQVjNA6jHKUs5HcNmx1lDzxLFOH%2BqmWWnAASDKAw1JvD7kP7QzCN27yl2215AVJQ5OQRotFXmvTW887p7Fg%2BZF6Em0HcoXY5Q%3D%3D%22%7D&BODY=%7B%22SUB_ACCT_TYPE%22%3A1%2C%22SUB_ACCT_NO%22%3A%229120001000541864381%22%2C%22BACK_URL%22%3A%22http%3A%2F%2F116.228.64.55%3A6900%2Fyuncallback%2FhtBank%2FsubAccountInfoMaintain%22%2C%22ACCT_NO%22%3A%221010012198010000039%22%2C%22JUMP_URL%22%3A%22https%3A%2F%2Fshoph5test.jphl.com%2F%23%2Fdistribution%2FturningPage%22%7D";
//        mWebView.loadUrl(url);
    }
    // 适配image和table标签
    public static String setWebVIewImage(String star) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:auto; height:auto;}</style>"
                + "<style>table{max-width: 100%; width:auto; height:auto;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + star + "</body></html>";
    }

    private void showTitle(){
        if (!StringUtils.isEmpty(mTitle)) {
            mToolBar.setTitle(mTitle);

            if (showBack) {
                mToolBar.setVisibility(View.VISIBLE);
                mToolBar.setBackClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(backUrl)){
                            Intent intent = new Intent();
                            intent.putExtra(WEB_BACK_URL,backUrl);
                            setResult(RESULT_OK,intent);
                            finish();
                            return;
                        }
                        if (isGoHomepage==1){
//                            startActivity(new Intent(CityLifeWebViewActivity.this,ShowActivity.class));
                            finish();
                        }else {
                            onBackPressed();
                        }
                    }
                });
            }
            if (isNeedRight == 1){
//                mToolBar.getRight
                mToolBar.setRightTitleVisibility(true);
                mToolBar.setRightTitle("分享");
                //分享按钮
                mToolBar.setRightTvTitleClick(new OnDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        String title =getResources().getString(R.string.share_title) ;
                        String content = getResources().getString(R.string.share_content);
                        String shareUrl = "";
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_test, null);
                        WxShareUtils.share(bitmap, title, content, url);
                    }
                });
            }

        }else{
            mToolBar.setVisibility(View.GONE);
        }
    }

    private void isLiveShow(){
        if (mToolBar.getVisibility()==View.VISIBLE)return;
        mToolBar.setVisibility(View.VISIBLE);
        mToolBar.setBackClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
//        mWebView.getSettings().setSupportZoom(true);
//        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setUseWideViewPort(true);//将图片调整到适合webview的大小/
        mWebView.getSettings().setLoadWithOverviewMode(true);// 缩放至屏幕的大小
//        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
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
                    CityLifeWebViewActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                } catch (Exception e) {
//                    new AlertDialog.Builder(CityLifeWebViewActivity.this)
//                            .setMessage("未检测到支付宝客户端，请安装后重试。")
//                            .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
                                    Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                    CityLifeWebViewActivity.this.startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
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
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            handler.proceed();

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loadingDialog.show();

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loadingDialog.hide();
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
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏

        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            String murl = view.getUrl();
            if(murl.contains("live.vhall.com")){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isLiveShow();
            }else{
                showTitle();
            }
            if(TextUtils.isEmpty(mTitle)){
                mToolBar.setTitle(title);
            }

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
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }


    /**
     * 给WebView同步Cookie
     *
     * @param context 上下文
     * @param url     可以使用[domain][host]
     */
    public static void synchronousWebCookies(Context context,String url,String cookies){
        if ( !TextUtils.isEmpty(url) ) {
            if (!TextUtils.isEmpty(cookies)) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    CookieSyncManager.createInstance(context);
                }
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                cookieManager.removeSessionCookie();// 移除
                cookieManager.removeAllCookie();
                StringBuilder sbCookie = new StringBuilder();//创建一个拼接cookie的容器,为什么这么拼接，大家查阅一下http头Cookie的结构
                sbCookie.append(cookies);//拼接sessionId
//			sbCookie.append(String.format(";domain=%s", ""));
//			sbCookie.append(String.format(";path=%s", ""));
                String cookieValue = sbCookie.toString();
                cookieManager.setCookie(url, cookieValue);//为url设置cookie
                CookieSyncManager.getInstance().sync();//同步cookie
                String newCookie = cookieManager.getCookie(url);
                LogUtils.i("同步后cookie", newCookie);
            }
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        loadingDialog.dismiss();
        mWebView.onDestroy();
        mWebView = null;

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        if (requestCode ==100&&resultCode==RESULT_OK){
            String url = intent.getStringExtra(WEB_BACK_URL);
            mWebView.loadUrl(url);
        }else if (requestCode ==100&&resultCode==RESULT_CANCELED){
            Intent intent1 = new Intent();
            intent1.putExtra(WEB_BACK_URL,backUrl);
            setResult(RESULT_OK,intent1);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * Js中调用的类
     */
    private class AndroidAndJsInterface {

        @JavascriptInterface
        public void goback() {
            finish();
        }

        @JavascriptInterface
        public void gohome() {
            startActivity(new Intent(CityLifeWebViewActivity.this, ShowActivity.class));
            finish();
        }

        @JavascriptInterface
        public void go2AddFamily() {
            startActivity(new Intent(CityLifeWebViewActivity.this, MemberManageActivity.class));
//            finish();
        }
        @JavascriptInterface
        public void jump2NewACT(String url){
            Intent intent = new Intent(CityLifeWebViewActivity.this, CityLifeWebViewActivity.class);
            intent.putExtra(Constant.WEB_URL,url);
            startActivity(intent);
        }
        @JavascriptInterface
        public void go2PayPropertyCosts() {
            startActivity(new Intent(CityLifeWebViewActivity.this, PaymentActivity.class));
//            finish();
        }

        @JavascriptInterface
        public String getUserId() {
            return TextUtils.isEmpty(LocalCache.getUserId())?"":LocalCache.getUserId();
        }
        @JavascriptInterface
        public void jumpToLive(String url){
            Intent intent = new Intent(CityLifeWebViewActivity.this, CityLifeWebViewActivity.class);
            intent.putExtra(Constant.WEB_URL,url);
            startActivity(intent);
        }

        @JavascriptInterface
        public void showTitle(boolean flag){
            mToolBar.setVisibility(flag?View.VISIBLE:View.GONE);
        }

        @JavascriptInterface
        public void goBackMIniPage(){
            Intent intent = new Intent();
            intent.putExtra(WEB_BACK_URL,backUrl);
            setResult(RESULT_CANCELED,intent);
            finish();
        }

        /**
         * Js中调用的方法
         */
        @JavascriptInterface
        public String getUser() {
            return JSON.toJSONString(LocalCache.getUser());
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
        public void goShopHomepage() {
            finish();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().postSticky(new EventBusMessage(Constant.EVENT_BUS_COMMUNITY_GROUP_BUY,null));
                }
            }, 500);
//

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
        @JavascriptInterface
        public void share(String image, String title, String description, String url) {
            WxShareUtils.share(image, title, description, url);
        }
        @JavascriptInterface
        public void mooncakeGamblingGameShare() {
            String title1 =getResources().getString(R.string.share_title) ;
            String content = getResources().getString(R.string.share_content);
            String shareUrl = "";
            if(BuildConfig.DEBUG){
                shareUrl = "https://shoph5test.jphl.com/#/activities/mooncakeGamblingShare";
            }else{
                shareUrl = "https://shoph5.jphl.com/#/activities/mooncakeGamblingShare";
            }

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_test, null);
            WxShareUtils.share(bitmap, title1, content, shareUrl);
        }

        @JavascriptInterface
        public String getCommunityList() {
            return JSON.toJSONString(LocalCache.getUser().getCommunities());
        }

        @JavascriptInterface
        public void wxPay(String orderNo, int tradeAmt, String businessType,String token) {
            OpenWxUtil.openWX(CityLifeWebViewActivity.this);
            OpenWxUtil.wxPay(CityLifeWebViewActivity.this, orderNo, tradeAmt, businessType,token);
        }
        @JavascriptInterface
        public void wxPay(String orderNo, int tradeAmt, String businessType) {
            OpenWxUtil.openWX(CityLifeWebViewActivity.this);
            OpenWxUtil.wxPay(CityLifeWebViewActivity.this, orderNo, tradeAmt, businessType);
        }

        @JavascriptInterface
        public void toast(String msg) {
            ToastUtils.showShort(msg);
        }
        /**
         * 拨打电话
         */
        @JavascriptInterface
        public void dialPhoneNumber(String phone) {
            CommonUtil.showCallDialog(phone, CityLifeWebViewActivity.this);
        }
        @JavascriptInterface
        public void openPage(String pageName) {
            AppPage appPage = AppPage.instanceOf(pageName);

            try {
                if (appPage != null) {
                    startActivity(new Intent(CityLifeWebViewActivity.this, appPage.getActivity()));
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        @JavascriptInterface
        public void toImplantPage(Object object){
            WebViewJSBean webViewJSBean = (WebViewJSBean)object;
            mWebView.loadUrl(webViewJSBean.getImplantUrl());
            mToolBar.setTitle(webViewJSBean.getTitle());
            mToolBar.setBackClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mWebView.loadUrl(webViewJSBean.getReturnUrl());
                }
            });
        }
        @JavascriptInterface
        public void toImplantPage(String implantUrl,String returnUrl,String title){
            if (TextUtils.isEmpty(returnUrl)){
                startWebViewActivity(CityLifeWebViewActivity.this,implantUrl,title);
            }else{
                startWebViewActivityForResult(CityLifeWebViewActivity.this,implantUrl,title,returnUrl);
            }

//            mWebView.loadUrl(implantUrl);
//            mToolBar.setTitle(title);
//            mToolBar.setVisibility(View.VISIBLE);
//            mToolBar.setBackClick(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mWebView.loadUrl(returnUrl);
//                }
//            });
        }
    }

    private void startWebViewActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, CityLifeWebViewActivity.class);
        intent.putExtra(Constant.WEB_URL, url);
        intent.putExtra(Constant.WEB_BACK, true);
        intent.putExtra(Constant.WEB_TITLE, title);
        startActivity(intent);
    }
    private void startWebViewActivityForResult(Context context, String url, String title,String backUrl) {
        Intent intent = new Intent(context, CityLifeWebViewActivity.class);
        intent.putExtra(Constant.WEB_URL, url);
        intent.putExtra(Constant.WEB_BACK, true);
        intent.putExtra(Constant.WEB_BACK_URL, backUrl);
        intent.putExtra(Constant.WEB_TITLE, title);
        startActivityForResult(intent,100);
    }
//    private void imgReset() {
//        loadUrl("javascript:(function(){"
//                + "var objs = document.getElementsByTagName('img'); "
//                + "for(var i=0;i<objs.length;i++)  " + "{"
//                + "var img = objs[i];   "
//                + "    img.style.width = '100%';   "
//                + "    img.style.height = 'auto';   "
//                + "}" + "})()");
//    }
}
