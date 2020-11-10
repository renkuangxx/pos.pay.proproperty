package com.jingpai.pos.customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.utils.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author 86173
 * 拍照添加头像框
 */
public class PhotoActivity extends Activity {
    private Button btn;
    private TextView tv_cancle;
    private ImageView iv_ok;
    private ImageView iv_cancle;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private Bitmap rotatedBitMap;
    private Camera.ShutterCallback shutter;
    private Camera.PictureCallback jepg;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btn = (Button) findViewById(R.id.btn_photo);
        tv_cancle = (TextView) findViewById(R.id.tv_cancle);
        iv_cancle = (ImageView) findViewById(R.id.iv_cancle);
        iv_cancle.setVisibility(View.GONE);
        iv_ok = (ImageView) findViewById(R.id.iv_ok);
        iv_ok.setVisibility(View.GONE);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        surfaceHolder = surfaceView.getHolder();
        try {
            SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    try {
                        camera.stopPreview();
                        camera.release();
                        camera = null;
                    } catch (Exception e) {
                        LogUtil.e("PhotoActivity PushMessage", e.getMessage());
                    }
                }

                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    int numberOfCameras = Camera.getNumberOfCameras();// 获取摄像头个数
                    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                    //遍历摄像头信息
                    for (int i = 0; i < numberOfCameras; i++) {
                        Camera.getCameraInfo(i, cameraInfo);
                        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//前置摄像头
                            camera = Camera.open(i);//打开摄像头
                            break;
                        }
                    }
                    try {
                        camera.setPreviewDisplay(holder);
                        camera.startPreview();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @SuppressWarnings("deprecation")
                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    Camera.Parameters parameters = camera.getParameters();
                    parameters.setPictureSize(640, 480);
                    parameters.getFocusMode();
                    parameters.setPictureFormat(PixelFormat.JPEG);
                    camera.setParameters(parameters);
                    camera.setDisplayOrientation(90);
                    camera.startPreview();
                }
            };
            surfaceHolder.addCallback(surfaceCallback);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 设置显示图片
        jepg = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                camera.startPreview();
                try {
                    camera.stopPreview();
                } catch (Exception e) {
                    LogUtil.e("PhotoActivity PushMessage", e.getMessage());
                }
                btn.setVisibility(View.INVISIBLE);
                Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                Matrix matrix = new Matrix();
                matrix.postRotate(-90);
                rotatedBitMap = Bitmap.createBitmap(bm, 0, 0, 640, 480, matrix, true);
                if (null == rotatedBitMap) return;
                iv_ok.setVisibility(View.VISIBLE);
                iv_cancle.setVisibility(View.VISIBLE);

            }
        };

        shutter = new Camera.ShutterCallback() {
            @Override
            public void onShutter() {
                Toast.makeText(getApplicationContext(), "成功拍照", Toast.LENGTH_SHORT).show();
            }
        };

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    camera.takePicture(shutter, null, jepg);
                } catch (Exception e) {
                    LogUtil.e("PhotoActivity PushMessage", e.getMessage());
                }
            }
        });
        iv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == rotatedBitMap) {
                    return;
                }

                compressImage(rotatedBitMap);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                int csad = getBitmapSize(rotatedBitMap);
//                rotatedBitMap.compress(Bitmap.CompressFormat.JPEG, 15, baos);
//                int  bh= getBitmapSize(rotatedBitMap);
//                byte[] bytes=baos.toByteArray();
//                try {
//                    baos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                Bundle b = new Bundle();
//                b.putByteArray("data", bytes);
//
////                byte buf[] = new byte[1024*1024];
////                buf = Bitmap2Bytes(rotatedBitMap);
//
//                Intent intent = new Intent();
////                intent.putExtra("photo_bmp", buf);
//                intent.putExtras( b);
//                setResult(10, intent);
//                finish();
            }
        });
        //重拍按钮
        iv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.startPreview();
                iv_ok.setVisibility(View.GONE);
                iv_cancle.setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);
//                finish();
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeCamera();
                finish();
            }
        });
    }

    private void compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 80, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            options -= 10;// 每次都减少10
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        byte[] bytes=baos.toByteArray();
        Log.d("zhaopian byte :",bytes.length/1024+"");
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bundle b = new Bundle();
        b.putByteArray("data", bytes);

//                byte buf[] = new byte[1024*1024];
//                buf = Bitmap2Bytes(rotatedBitMap);

        Intent intent = new Intent();
//                intent.putExtra("photo_bmp", buf);
        intent.putExtras( b);
        setResult(10, intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        closeCamera();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeCamera();
    }

    /**
     * 关闭相机，释放资源。
     */
    private void closeCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    private byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }
}
