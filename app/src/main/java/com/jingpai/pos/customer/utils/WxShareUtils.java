package com.jingpai.pos.customer.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.show.My.VisitorActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.base.MyApplication;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WxShareUtils {
    /**
     * 分享网页类型至微信
     *
     * @param webUrl 网页的url
     * @param title  网页标题
     */
    public static void share(String image, String title, String description, String webUrl) {
        Context context = MyApplication.getContext();
//        Glide.with(context).asBitmap().load(image).into(new SimpleTarget<Bitmap>() {
//            /**
//             * 成功的回调
//             */
//            @Override
//            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
//                WxShareUtils.share(bitmap, title, description, webUrl);
//            }
//            /**
//             * 失败的回调
//             */
//            @Override
//            public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                super.onLoadFailed(errorDrawable);
//                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
//                WxShareUtils.share(bitmap, title, description, webUrl);
//            }
//        });



        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(image);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    WxShareUtils.share(bitmap, title, description, webUrl);


                } catch (IOException e) {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
                    WxShareUtils.share(bitmap, title, description, webUrl);
                    e.printStackTrace();
                }
            }
        }).start();

//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
//        WxShareUtils.share(bitmap, title, description, webUrl);
    }

    public static void share(Bitmap bitmap, String title, String description, String webUrl) {
        Context context = MyApplication.getContext();

        // 通过appId得到IWXAPI这个对象
        IWXAPI wxapi = WXAPIFactory.createWXAPI(context, Constant.WEIXIN_APP_ID);
        // 检查手机或者模拟器是否安装了微信
        if (!wxapi.isWXAppInstalled()) {
            ToastUtils.showShort("您还没有安装微信");
            return;
        }

        // 初始化一个WXWebpageObject对象
        WXWebpageObject webpageObject = new WXWebpageObject();
        // 填写网页的url
        webpageObject.webpageUrl = webUrl;

        // 用WXWebpageObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        // 填写网页标题、描述、位图
        msg.title = title;
        msg.description = description;
        // 如果没有位图，可以传null，会显示默认的图片
        msg.setThumbImage(bitmap);
        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        // transaction用于唯一标识一个请求（可自定义）
        req.transaction = "webpage";
        // 上文的WXMediaMessage对象
        req.message = msg;
        // SendMessageToWX.Req.WXSceneSession是分享到好友会话
        // SendMessageToWX.Req.WXSceneTimeline是分享到朋友圈
        req.scene = SendMessageToWX.Req.WXSceneSession;

        // 向微信发送请求
        wxapi.sendReq(req);
    }

    /**
     * 把网络资源图片转化成bitmap
     * @param url 网络资源图片
     * @return Bitmap
     */
    public static Bitmap GetLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

        public static void showShareDialog(Context context, String password, Bitmap bitmap, String text) {
        showShareDialog(context, password, null, bitmap, text);

    }

    public static void showShareDialog(Context context, String password, String qrCode, String text) {
        showShareDialog(context, password, qrCode, null, text);
    }

    /**
     * @param context
     * @param password
     * @param qrCode   Base64编码的二维码图片
     * @param bitmap   二维码图片
     * @param text
     */
    public static void showShareDialog(Context context, String password, String qrCode, Bitmap bitmap, String text) {
        //1、使用Dialog、设置style
        Dialog dialog = new Dialog(context, R.style.DialogTheme);
        //2、设置布局
        View view1 = View.inflate(context, R.layout.visitor_dialog, null);
        dialog.setContentView(view1);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        TextView tvTitle = dialog.findViewById(R.id.tv_title);
        tvTitle.setText(text);
        TextView tvPassword = dialog.findViewById(R.id.tv_password);
        if (TextUtils.isEmpty(password)) {
            tvPassword.setVisibility(View.GONE);
        } else {
            tvPassword.setText(password);
        }
        ImageView ivVisitorImage = dialog.findViewById(R.id.iv_visitor_image);

        Bitmap use = null;
        if (!TextUtils.isEmpty(qrCode)) {
            use = base64ToBitmap(qrCode);
        }
        if (bitmap != null) {
            use = bitmap;
        }
        ivVisitorImage.setImageBitmap(use);

        //关闭dialog
        dialog.findViewById(R.id.iv_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (context instanceof VisitorActivity) {
                    ((VisitorActivity) context).finish();
                }
            }
        });
        //照片保存到相册
        dialog.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout visitor_ll = dialog.findViewById(R.id.visitor_ll);
                Bitmap viewBitmap = createViewBitmap(visitor_ll);
                ImgUtils.saveImageToGallery(context, viewBitmap);
                ToastUtils.showShort("保存成功");
            }
        });
        //微信分享
        dialog.findViewById(R.id.weChat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout visitor_ll = dialog.findViewById(R.id.visitor_ll);
                Bitmap viewBitmap = createViewBitmap(visitor_ll);
                shareToWXSceneTimeline(viewBitmap, context,SendMessageToWX.Req.WXSceneSession);
                dialog.dismiss();
                if (context instanceof VisitorActivity) {
                    ((VisitorActivity) context).finish();
                }
            }
        });
    }

    //base64转为bitmap
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    public static void shareToWXSceneTimeline(Bitmap bitmap, Context context,int shareTo) {//传的图片地址
        IWXAPI wxapi = WXAPIFactory.createWXAPI(context, Constant.WEIXIN_APP_ID);
        wxapi.registerApp(Constant.WEIXIN_APP_ID);
        if (!wxapi.isWXAppInstalled()) {
            Toast.makeText(context, "您还没有安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        //Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        //设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        bitmap.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        /**分享到微信朋友圈WXSceneTimeline*/
        /**分享到微信会话WXSceneSession*/
        req.scene = shareTo;
        //调用api接口，发送数据到微信
        wxapi.sendReq(req);
    }

    private static byte[] bmpToByteArray(Bitmap bitmap, boolean recycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (recycle) {
            bitmap.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
