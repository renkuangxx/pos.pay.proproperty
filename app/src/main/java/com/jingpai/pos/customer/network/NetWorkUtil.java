package com.jingpai.pos.customer.network;

import com.jingpai.pos.BuildConfig;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.mvp.DownloadCallBack;
import com.jingpai.pos.customer.mvp.MyConstant;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.SPDownloadUtil;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;

import static com.jingpai.pos.customer.utils.LocalCache.IS_VISITOR;


public class NetWorkUtil {
    public ApiService apiService;
    private static final int CONNECT_TIME_OUT = 50;//连接超时时长x秒
    private static final int READ_TIME_OUT = 50;//读数据超时时长x秒
    private static final int WRITE_TIME_OUT = 50;//写数据接超时时长x秒
    private static final long TIME_OUT_MIAO = 24 * 60 * 60 * 1000;//写数据接超时时长x秒
    private static final String TIME = "TIME_OUT";

    private NetWorkUtil() {
        //拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("Content-Type",Constant.CONTENT_TYPE)
                            .addHeader("User-Token", LocalCache.getToken())
                            .addHeader("Community-Id", LocalCache.getBooleanValue(IS_VISITOR)?"":LocalCache.getCurrentCommunityId())
                            .build();
                    return chain.proceed(newRequest);
                })
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                //拦截器
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                //根接口地址
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        //创建ApiService实例
        apiService = retrofit.create(ApiService.class);
    }

    public static NetWorkUtil getInstance() {
        return NetWork.util;
    }

    private static class NetWork {
        private static final NetWorkUtil util = new NetWorkUtil();
    }

    //创建接口回调数据
    public interface MyCallBack {
        void succeed(String succeed);
    }
    /**
     * 获取基础request参数
     */
    public TreeMap<String, Object> getBaseRequest() {
        TreeMap<String, Object> map = new TreeMap<>();
        //map.put(k_key, appKey);
        return map;
    }

    public void downloadFile(final long range, final String url, final String fileName, final DownloadCallBack downloadCallback) {
        //断点续传时请求的总长度
        File file = new File(MyConstant.APP_ROOT_PATH + MyConstant.DOWNLOAD_DIR, fileName);
        String totalLength = "-";
        if (file.exists()) {
            totalLength += file.length();
        }

        apiService.executeDownload(url)
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        RandomAccessFile randomAccessFile = null;
                        InputStream inputStream = null;
                        long total = range;
                        long responseLength = 0;
                        try {
                            byte[] buf = new byte[2048];
                            int len = 0;
                            responseLength = responseBody.contentLength();
                            inputStream = responseBody.byteStream();
                            String filePath = MyConstant.APP_ROOT_PATH + MyConstant.DOWNLOAD_DIR;
                            File file = new File(filePath, fileName);
                            File dir = new File(filePath);
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }
                            randomAccessFile = new RandomAccessFile(file, "rwd");
                            if (range == 0) {
                                randomAccessFile.setLength(responseLength);
                            }
                            randomAccessFile.seek(range);

                            int progress = 0;
                            int lastProgress = 0;

                            while ((len = inputStream.read(buf)) != -1) {
                                randomAccessFile.write(buf, 0, len);
                                total += len;
                                SPDownloadUtil.getInstance().save(url, total);
                                lastProgress = progress;
                                progress = (int) (total * 100 / randomAccessFile.length());
                                if (progress > 0 && progress != lastProgress) {
                                    downloadCallback.onProgress(progress);
                                }
                            }
                            downloadCallback.onCompleted();
                        } catch (Exception e) {
                            downloadCallback.onError(e.getMessage());
                            e.printStackTrace();
                        } finally {
                            try {
                                if (randomAccessFile != null) {
                                    randomAccessFile.close();
                                }

                                if (inputStream != null) {
                                    inputStream.close();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }
}