package com.jingpai.pos.customer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jingpai.pos.R;

import static com.bumptech.glide.Glide.with;


public class GlideUtils {
    private static Bitmap bitmap;

    static RequestOptions options = new RequestOptions()
            .placeholder(R.color.white)	//加载成功之前占位图
            .error(R.color.white)	//加载错误之后的错误图
            .override(300,300)	//指定图片的尺寸
//            .fitCenter()   //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。是指其中一个满足即可不会一定铺满imageview）
            .centerCrop()//指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的宽高都大于等于ImageView的宽度，然后截取中间的显示。）
//            .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
            .priority(Priority.HIGH)//指定优先级.Glide
            .skipMemoryCache(true)	//不使用内存缓存
//                .diskCacheStrategy(DiskCacheStrategy.ALL)	//缓存所有版本的图像
                .diskCacheStrategy(DiskCacheStrategy.NONE)	//不使用硬盘本地缓存
//                .diskCacheStrategy(DiskCacheStrategy.DATA)	//只缓存原来分辨率的图片
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)	//只缓存最终的图片
;

    static RequestOptions optionsLong = new RequestOptions()
            .placeholder(R.color.white)	//加载成功之前占位图
            .error(R.color.white)	//加载错误之后的错误图
            .override(300,300)	//指定图片的尺寸
//            .fitCenter()   //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。是指其中一个满足即可不会一定铺满imageview）
//            .centerCrop()//指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的宽高都大于等于ImageView的宽度，然后截取中间的显示。）
//            .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
//            .centerInside()
            .priority(Priority.HIGH)//指定优先级.Glide
            .skipMemoryCache(true)	//不使用内存缓存
//                .diskCacheStrategy(DiskCacheStrategy.ALL)	//缓存所有版本的图像
//            .diskCacheStrategy(DiskCacheStrategy.NONE)	//不使用硬盘本地缓存
//                .diskCacheStrategy(DiskCacheStrategy.DATA)	//只缓存原来分辨率的图片
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)	//只缓存最终的图片
            ;

    static RequestOptions gifOptions = new RequestOptions()
            .placeholder(R.color.white)	//加载成功之前占位图
            .error(R.color.white)	//加载错误之后的错误图
            .override(300,300)	//指定图片的尺寸
//            .fitCenter()   //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。是指其中一个满足即可不会一定铺满imageview）
            .centerCrop()//指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的宽高都大于等于ImageView的宽度，然后截取中间的显示。）
//            .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
            .priority(Priority.HIGH)//指定优先级.Glide
            .skipMemoryCache(true)	//不使用内存缓存
//                .diskCacheStrategy(DiskCacheStrategy.ALL)	//缓存所有版本的图像
//            .diskCacheStrategy(DiskCacheStrategy.NONE)	//不使用硬盘本地缓存
    //                .diskCacheStrategy(DiskCacheStrategy.DATA)	//只缓存原来分辨率的图片
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)	//只缓存最终的图片
    ;

    static RequestOptions circleOptions = new RequestOptions()
            .placeholder(R.drawable.ic_avatar_default)	//加载成功之前占位图
            .error(R.drawable.ic_avatar_default)	//加载错误之后的错误图
//            .override(100,100)	//指定图片的尺寸
//            .fitCenter()   //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。是指其中一个满足即可不会一定铺满imageview）
//            .centerCrop()//指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的宽高都大于等于ImageView的宽度，然后截取中间的显示。）
            .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
            .priority(Priority.HIGH)//指定优先级.Glide
            .skipMemoryCache(true)	//不使用内存缓存
//                .diskCacheStrategy(DiskCacheStrategy.ALL)	//缓存所有版本的图像
//                .diskCacheStrategy(DiskCacheStrategy.NONE)	//不使用硬盘本地缓存
//                .diskCacheStrategy(DiskCacheStrategy.DATA)	//只缓存原来分辨率的图片
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)	//只缓存最终的图片
    ;
    /**
     * @param context   传入当前activity的context
     * @param url
     * @param imageView
     */
    public static void LoadingImg(Context context, String url, ImageView imageView) {
        with(context).load(url).apply(options).into(imageView);//指定显示图片的ImageView
    }
    public static void LoadingImg(Context context, int url, ImageView imageView) {
        with(context).load(url).apply(options).into(imageView);//指定显示图片的ImageView
    }
    public static void LoadingLongImg(Context context, int url, ImageView imageView) {
        with(context).load(url).apply(optionsLong).into(imageView);//指定显示图片的ImageView
    }
    public static void LoadingGifImg(Context context, String url, ImageView imageView) {
        with(context).asGif().load(url).apply(options).into(imageView);//指定显示图片的ImageView
    }
    public static void LoadingGifImg(Context context, int url, ImageView imageView) {
        with(context).asGif().load(url).apply(options).into(imageView);//指定显示图片的ImageView
    }
    public static void LoadingCircleImg(Context context, int url, ImageView imageView) {
        with(context).load(url).apply(circleOptions).into(imageView);//指定显示图片的ImageView
    }

    public static void LoadingCircleImg(Context context, String url, ImageView imageView) {
        with(context).load(url).apply(circleOptions).into(imageView);//指定显示图片的ImageView
    }

    public static void LoadingGif(Context context, int mipmap, ImageView imageView) {
        with(context).load(mipmap).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).apply(options).into(imageView);//指定显示图片的ImageView
    }
    public static void LoadingImgOrg(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(new RequestOptions().override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .placeholder(R.color.white).error(R.color.white).dontAnimate())
                .into(imageView);
    }


    public static void LoadingRoundedCornersImg(Context context, String url, ImageView imageView,int corner) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(PixelUtil.dp2px(corner));
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        RequestManager requestManager = Glide.with(context);
        with(context).load(url).apply(options).into(imageView);//指定显示图片的ImageView
    }
//    public static void LoadRoundImg(Context context, String url, ImageView imageView, int round) {
//        with(context) // 指定Context
//                .load(url)// 指定图片的URL
//                .placeholder(R.drawable.icon_default_head)// 指定图片未成功加载前显示的图片
//                .error(R.drawable.icon_default_head)
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .transform(new CenterCrop(context),new GlideRoundTransform(context,
//                        round))
//                .crossFade()
//                .into(imageView);//指定显示图片的ImageView
//
//    }
//
//    /**
//     * 加载圆形图片
//     *
//     * @param context   传入当前activity的context
//     * @param url
//     * @param imageView
//     */
//
//    public static void LoadingCircleImg(Context context, String url, final ImageView
//            imageView, int width, int height, final LoadingListener loadingListener) {
//        url = getUrl(url, width, height);
//        with(context) // 指定Context
//                .load(url)// 指定图片的URL
//                .placeholder(R.drawable.icon_default_head)// 指定图片未成功加载前显示的图片
//                .error(R.drawable.icon_default_head)
//                .fitCenter()
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .transform(new GlideCircleTransform(context)) //
//                // 指定自定义BitmapTransformation
//                .into(new GlideDrawableImageViewTarget(imageView){
//                    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
//                        super.onResourceReady(resource, animation);
//                        imageView.setImageDrawable(resource);
//                        if(loadingListener!=null){
//                            loadingListener.onSuccess(resource);
//                        }
//                    }
//
//                    @Override
//                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                        super.onLoadFailed(e, errorDrawable);
//                        if(loadingListener!=null){
//                            loadingListener.onSuccess(null);
//                        }
//                    }
//                });//指定显示图片的ImageView
//    }
//     /**
//     * 加载圆形图片
//     *
//     * @param context   传入当前activity的context
//     * @param url
//     * @param imageView
//     */
//
//    public static void LoadingCircleImg(Context context, String url, ImageView
//            imageView, int width, int height) {
//        url = getUrl(url, width, height);
//        with(context) // 指定Context
//                .load(url)// 指定图片的URL
//                .placeholder(R.drawable.icon_default_head)// 指定图片未成功加载前显示的图片
//                .error(R.drawable.icon_default_head)
//                .fitCenter()
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .transform(new GlideCircleTransform(context)) //
//                // 指定自定义BitmapTransformation
//                .into(imageView);//指定显示图片的ImageView
//    }
//
//
//    public static void LoadingCircleImgWithStoke(Context context, String url, final ImageView
//            imageView, int width, int height) {
//        url = getUrl(url, width, height);
//        with(context) // 指定Context
//                .load(url)// 指定图片的URL
//                .placeholder(R.drawable.icon_default_head)// 指定图片未成功加载前显示的图片
//                .error(R.drawable.icon_default_head)
////                .fitCenter()
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
////                .transform(new GlideCircleTransform(SApplication.getInstance(),2,context.getResources().getColor(R.color.white))) //
//                // 指定自定义BitmapTransformation
//                .into(new SimpleTarget<GlideDrawable>() {
//                    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                        imageView.setImageDrawable(resource);
//                    }
//                });//指定显示图片的ImageView
//    }
//
//    /**
//     * 加载圆角图片
//     *
//     * @param context   传入当前activity的context
//     * @param round     圆角度数
//     * @param url
//     * @param imageView
//     */
//    public static void LoadingRoundImg(Context context, String url, ImageView
//            imageView, int round) {
//        url = getUrl(url, 0, 0);
//        with(context) // 指定Context
//                .load(url)// 指定图片的URL
//                .placeholder(R.drawable.icon_default_head)// 指定图片未成功加载前显示的图片
//                .error(R.drawable.icon_default_head)
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .transform(new CenterCrop(context),new GlideRoundTransform(context, round))
//                .crossFade()
//                .into(imageView);//指定显示图片的ImageView
//
//    }
//    public static void LoadingRoundImg(Context context, Integer url, ImageView imageView, int round) {
//        with(context) // 指定Context
//                .load(url)// 指定图片的URL
//                .placeholder(R.drawable.icon_default_head)// 指定图片未成功加载前显示的图片
//                .error(R.drawable.icon_default_head)
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .transform(new CenterCrop(context),new GlideRoundTransform(context, round))
//                .crossFade()
//                .into(imageView);//指定显示图片的ImageView
//
//    }
//   public static void LoadingRoundImg(Context context, String url, int width, int
//           height, ImageView imageView, int round) {
//        url = getUrl(url, width, height);
//        with(context) // 指定Context
//                .load(url)// 指定图片的URL
//                .placeholder(R.drawable.icon_default_head)// 指定图片未成功加载前显示的图片
//                .error(R.drawable.icon_default_head)
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .transform(new CenterCrop(context),new GlideRoundTransform(context,
//                        round))
//                .crossFade()
//                .into(imageView);//指定显示图片的ImageView
//
//    }
//    //加载带圆角的视频缩略图
//    public static void LoadingRoundVideo(Context context, String url, int width, int
//           height, ImageView imageView, int round) {
//        GlideUtils.LoadingImg(context,getVideoUrl(url,width,height), imageView);
//
//    }
//
//    /**
//     * 加载本地图片
//     *
//     * @param context
//     * @param filepath
//     * @param imageView
//     */
//    public static void LoadingLocalImg(Context context, String filepath, ImageView
//            imageView) {
//        with(context)
//                .load(filepath)// 指定图片的URL
//                .placeholder(R.drawable.icon_default_head)
//                .error(R.drawable.icon_default_head)// 指定图片加载失败显示的图片
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .priority(Priority.HIGH)
//                .into(imageView);
//    }
//
//    public static void LoadingGif(Context context, String url, ImageView
//            imageView) {
//
//        with(context) // 指定Context
//                .load(url)// 指定图片的URL
//                .asGif()
////                .placeholder(R.drawable.icon_default_head)// 指定图片未成功加载前显示的图片
//                .error(R.drawable.icon_default_head)// 指定图片加载失败显示的图片
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .priority(Priority.HIGH)//指定优先级.Glide
//                .into(imageView);//指定显示图片的ImageView
//    }
//    public static void LoadingAnimate(Context context, Integer animate, ImageView
//            imageView) {
//        with(context) // 指定Context
//                .load("url")// 指定图片的URL
//                .animate(animate)
////                .placeholder(R.drawable.icon_default_head)// 指定图片未成功加载前显示的图片
//                .error(R.drawable.icon_default_head)// 指定图片加载失败显示的图片
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .priority(Priority.HIGH)//指定优先级.Glide
//                .into(imageView);//指定显示图片的ImageView
//    }
//    public static void LoadingGif(Context context, Integer url, ImageView
//            imageView, int animResid) {
//
//        with(context) // 指定Context
//                .load(url)// 指定图片的URL
//                .asGif()
//                .animate(animResid)
////                .placeholder(R.drawable.icon_default_head)// 指定图片未成功加载前显示的图片
//                .error(R.drawable.icon_default_head)// 指定图片加载失败显示的图片
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .priority(Priority.HIGH)//指定优先级.Glide
//                .into(imageView);//指定显示图片的ImageView
//    }
//     public static void LoadingGif(Context context, Integer url, ImageView
//            imageView) {
//
//        with(context) // 指定Context
//                .load(url)// 指定图片的URL
//                .asGif()
////                .placeholder(R.drawable.icon_default_head)// 指定图片未成功加载前显示的图片
//                .error(R.drawable.icon_default_head)// 指定图片加载失败显示的图片
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .priority(Priority.HIGH)//指定优先级.Glide
//                .into(imageView);//指定显示图片的ImageView
//    }
//
//    public static void LoadingGif(Context context, Integer url, ImageView
//            imageView, final int playNum, final Handler handler) {
//
//        with(context) // 指定Context
//                .load(url)// 指定图片的URL
////                .placeholder(R.drawable.icon_default_head)// 指定图片未成功加载前显示的图片
//                .error(R.drawable.icon_default_head)// 指定图片加载失败显示的图片
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .priority(Priority.HIGH)//指定优先级.Glide
//                .into(new GlideDrawableImageViewTarget(imageView,playNum){
//                    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
//                        super.onResourceReady(resource, animation);
//                        // 计算动画时长
//
//                        GifDrawable drawable = (GifDrawable) resource;
//                        GifDecoder decoder = drawable.getDecoder();
//                        int duration = 0;
//                        for (int i = 0; i < drawable.getFrameCount(); i++) {
//                            duration += decoder.getDelay(i);
//                        }
//                        handler.sendEmptyMessageDelayed(1,(duration*playNum));
//                    }
//                });
//    }
//
//    /**
//     * 将网络图片转换为bitmap
//     *
//     * @param context
//     * @param url
//     * @return
//     */
//
//    public static Bitmap getBitmap(Context context, String url) {
//
//        with(context)
//                .load(url)
//                .asBitmap()
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<?
//                            super Bitmap> glideAnimation) {
//                        bitmap = resource;
//                    }
//                });
//        return bitmap;
//    }
//    public static void getBitmap(Context context, String url, final ImgLodding
//            lodding) {
//        with(context)
//                .load(url)
//                .asBitmap()
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<?
//                            super Bitmap> glideAnimation) {
//                        lodding.getbitmap(resource);
//                    }
//                });
//    }
// public static void getBitmap(Context context, String url, int width, int height, final ImgLodding lodding) {
//     url=getUrl(url, width, height);
//        with(context)
//                .load(url)
//                .asBitmap()
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<?
//                            super Bitmap> glideAnimation) {
//                        lodding.getbitmap(resource);
//                    }
//
//                });
//    }
//
//    /**
//     * 加载bitmap图片图片带圆角
//     * @param context
//     * @param bitmap
//     * @param imageView
//     */
//    public static void loadingBitmap(Context context, Bitmap bitmap, ImageView
//            imageView, int round) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] bytes = baos.toByteArray();
//        if (round == 0) {
//            with(context)
//                    .load(bytes)
//                    .error(R.drawable.icon_default_head)// 指定图片加载失败显示的图片
//                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                    .priority(Priority.HIGH)
//                    .into(imageView);
//        } else {
//            with(context)
//                    .load(bytes)
//                    .error(R.drawable.icon_default_head)// 指定图片加载失败显示的图片
//                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                    .priority(Priority.HIGH)
//                    .transform(new GlideRoundTransform(SApplication.getInstance(),
//                            round))
//                    .into(imageView);
//        }
//    }
//
//    public static void LoadingAllImg(Context context, String url, ImageView imageView) {
//        LoadingAllImg(context,url,0,0,imageView);
//    }
//    /**
//     * 兼容本地服务器图片加载
//     * @param context
//     * @param url
//     * @param imageView
//     */
//    public static void LoadingAllImg(Context context, String url, int width, int
//            height, ImageView imageView) {
//        url = getUrl(url, width, height);
//
//        with(context) // 指定Context
////                .with(SApplication.getInstance()) // 指定Context
//                .load(url)// 指定图片的URL
//                .placeholder(R.drawable.icon_default_head)// 指定图片未成功加载前显示的图片
//                .error(R.drawable.icon_default_head)// 指定图片加载失败显示的图片
//                //.override(300, 300)//指定图片的尺寸
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .priority(Priority.HIGH)//指定优先级.Glide
//                .into(imageView);//指定显示图片的ImageView
//    }
//
//    public static void LoadingAllImg(Context context, int resID, ImageView imageView) {
//
//        with(context) // 指定Context
////                .with(SApplication.getInstance()) // 指定Context
//                .load(resID)// 指定图片的URL
//                .placeholder(R.drawable.icon_default_head)// 指定图片未成功加载前显示的图片
//                .error(R.drawable.icon_default_head)// 指定图片加载失败显示的图片
//                //.override(300, 300)//指定图片的尺寸
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .priority(Priority.HIGH)//指定优先级.Glide
//                .into(imageView);//指定显示图片的ImageView
//    }
//
//    public static void LoadingAllImg(Context context, String url, final ImageView imageView, final LoadingListener loadingListener) {
//        LoadingAllImg(context,url,0,0,imageView,loadingListener);
//    }
//    public static void LoadingAllImg(Context context, String url, int width, int
//            height, final ImageView imageView, final LoadingListener loadingListener) {
//            url = getUrl(url, width, height);
//                with(context) // 指定Context
//                .load(url)// 指定图片的URL
//                .placeholder(R.drawable.icon_default_head)// 指定图片未成功加载前显示的图片
//                .error(R.drawable.icon_default_head)// 指定图片加载失败显示的图片
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .priority(Priority.HIGH)//指定优先级.Glide
//                .into(new GlideDrawableImageViewTarget(imageView){
//                    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
//                        super.onResourceReady(resource, animation);
//                        imageView.setImageDrawable(resource);
//                        if(loadingListener!=null){
//                            loadingListener.onSuccess(resource);
//                        }
//                    }
//
//                    @Override
//                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                        super.onLoadFailed(e, errorDrawable);
//                        if(loadingListener!=null){
//                            loadingListener.onSuccess(null);
//                        }
//                    }
//                });
//
//    }
//
//
//    /**
//     * 兼容本地服务器图片加载
//     * @param context
//     * @param url
//     * @param imageView
//     */
//    public static void LoadingCircleAllImg(Context context, String url, int width, int
//            height, ImageView imageView) {
//        url = getUrl(url, width, height);
//
//        with(context) // 指定Context
////                .with(SApplication.getInstance()) // 指定Context
//                .load(url)// 指定图片的URL
//                .placeholder(R.drawable.icon_default_head)// 指定图片未成功加载前显示的图片
//                .error(R.drawable.icon_default_head)// 指定图片加载失败显示的图片
//                //.override(300, 300)//指定图片的尺寸
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .priority(Priority.HIGH)//指定优先级.Glide
//                .transform(new GlideCircleTransform(SApplication.getInstance())) //
//                .into(imageView);//指定显示图片的ImageView
//    }
//
//    public static void LoadingCircleAllImg(Context context, String url, int width, int
//            height, ImageView imageView, int defaultimage) {
//        url = getUrl(url, width, height);
//
//        with(context) // 指定Context
////                .with(SApplication.getInstance()) // 指定Context
//                .load(url)// 指定图片的URL
//                .placeholder(defaultimage)// 指定图片未成功加载前显示的图片
//                .error(defaultimage)// 指定图片加载失败显示的图片
//                //.override(300, 300)//指定图片的尺寸
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .priority(Priority.HIGH)//指定优先级.Glide
//                .transform(new GlideCircleTransform(context)) //
//                .into(imageView);//指定显示图片的ImageView
//    }
//
//    public static String getUrl(String url, int width, int height) {
//        if(url!=null){
//            if (url.startsWith("http")){
//                if(width!=0){
//                    url=url+ GlideUtils.getImgThumbnail(width,height);
//                }
//            }else if(!url.startsWith("/storage/")&&!url.startsWith(Environment.getRootDirectory().toString())){
//                url = Const.SERVER_CLIENT_URL_IMG+url;
//            }
//        }
//        return url==null?"":url;
//    }
//
//
//    private static String getVideoUrl(String url, int width, int height) {
//        if(url!=null){
//            if (url.startsWith("http")){
//                if(width!=0){
//                    url=url+ GlideUtils.getVideoThumbnail(width,height);
//                }
//            }else if(!url.startsWith("/storage/")&&!url.startsWith(Environment.getRootDirectory().toString())){
//                url = Const.SERVER_CLIENT_URL_IMG+url;
//            }
//        }
//        return url;
//    }
//    /**
//     * 加载不带圆角bitmap对象
//     * @param context
//     * @param bitmap
//     * @param imageView
//     */
//    public static void loadingBitmap(Context context, Bitmap bitmap, ImageView
//            imageView) {
//        loadingBitmap(context,bitmap,imageView,0);
//    }
//
//    /**
//     * 带有进度的
//     * @param context
//     * @param url
//     * @param imageView
//     * @param listener
//     */
//    public static void loadingImgProgress(Context context, String url, final ImageView
//            imageView, final LoadingProgress listener){
//        url = getUrl(url, 0, 0);
//             Glide
//                .with(context)
//                .using(new ProgressModelLoader(new ProgressListener() {
//                            @Override
//                            public void progress(long bytesRead, long contentLength, boolean done) {
//                                //进度
//                                int percent =((int) bytesRead*100)/(int) contentLength;
//                                if(listener!=null){
//                                    listener.progress(percent,done);
//                                }
//                            }
//                        }))
//                .load(url)
//                .asBitmap()
//                  .error(R.drawable.icon_default_head)// 指定图片加载失败显示的图片
//                 .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                 .priority(Priority.HIGH)//指定优先级.Glide
//                  .into(new SimpleTarget<Bitmap>() {
//                      @Override
//                      public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                          listener.getBitmap(resource);
//                      }
//                  });
//    }
//
//
//
//
//
//
//    /**
//     * 七牛缩略图裁剪参数
//     * @param type  0  1 根据宽高等比裁剪  2根据宽高等比压缩  3 4
//     * @param width
//     * @param height
//     * @return
//     */
//    private static String getThumbnailImg(int type, int width, int height){
//        return "?imageView2/"+type+"/w/"+width+"/h/"+height;
//    }
//
//    /**
//     * 获取图片缩略图相关参数
//     * @param width
//     * @param height
//     * @return
//     */
//    public static String getImgThumbnail(int width, int height){
//        return getThumbnailImg(2,width,height);
//    }
//    /**
//     * 获取视频缩略图相关参数
//     * @param width
//     * @param
//     * @return
//     */
//    public static String getVideoThumbnail(int width){
//        int height=(width/3)*5;
////        return "?vframe/png/offset/0/w/"+width+"/h/"+height;
//        return "?vframe/jpg/offset/0/w/"+width+"/h/"+height;
//    }
//    public static String getVideoThumbnail(int width, int height){
////        return "?vframe/png/offset/0/w/"+width+"/h/"+height;
//        return "?vframe/jpg/offset/0/w/"+width+"/h/"+height;
//    }
//
//    public static String getVideoThumbnailNoHigh(int width){
////        return "?vframe/png/offset/0/w/"+width;
//        return "?vframe/jpg/offset/0/w/"+width;
//    }
//
//    /**
//     * 加载聊天室七牛视频缩略图
//     */
//    public static void setChatVideoImage(Context context, ImageView imageView, String path, LoadingListener loadingListener){
////            GlideUtils.LoadingImg(context,getVideoUrl(path,300,500), imageView);
//        GlideUtils.LoadingAllImg(context,getVideoUrl(path,300,500), imageView,loadingListener);
//    }
//    /**
//     * 加载聊天室图片缩略图
//     */
//    public static void setChatImage(Context context, ImageView imageView, String path, LoadingListener loadingListener){
//            GlideUtils.LoadingAllImg(context,path,500,500,imageView,loadingListener);
////        GlideUtils.LoadingImg(context,getUrl(path,500,500),imageView);
//    }
////    public static void setChatImage(Context context,ImageView imageView, String path){
////        GlideUtils.LoadingImg(context,getUrl(path,500,500),
////                imageView);
////    }
//
//    public interface LoadGIFListener{
//        void conclude();
//    }
//    public interface LoadingListener{
//        void onSuccess(Object success);
////        void onFiled(Object error);
//    }
//    public interface ImgLodding{
//        void getbitmap(Bitmap bitmap);
//    }
//     public interface LoadingProgress{
//        void progress(int percent, boolean done);
//         void getBitmap(Bitmap bitmap);
//    }

}
