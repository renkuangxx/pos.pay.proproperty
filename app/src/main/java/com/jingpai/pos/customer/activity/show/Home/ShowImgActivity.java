package com.jingpai.pos.customer.activity.show.Home;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.BaseActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowImgActivity extends BaseActivity {

    @BindView(R.id.iv_image)
    ImageView ivImage;

    @Override
    protected int getLayout() {
        return R.layout.activity_showimg;
    }

    @Override
    protected void initData() {
        int index = getIntent().getIntExtra("index", 1);
        if (index == 1) {
            mToolBar.setTitle("老吴钓鱼");
//            GlideUtils.LoadingLongImg(ShowImgActivity.this,R.mipmap.laowudiaoyu,ivImage);
            Glide.with(ShowImgActivity.this).load(R.mipmap.laowudiaoyu).downloadOnly(new SimpleTarget<File>() {
                @Override
                public void onResourceReady(File resource, Transition<? super File> transition) {
                    Uri uri = Uri.fromFile(resource);
                    ivImage.setImageURI(uri);
                }
            });
        } else {
            mToolBar.setTitle("最爱撸猫");
//            GlideUtils.LoadingLongImg(ShowImgActivity.this,R.mipmap.zuiailumao,ivImage);
            Glide.with(ShowImgActivity.this).load(R.mipmap.zuiailumao).downloadOnly(new SimpleTarget<File>() {
                @Override
                public void onResourceReady(File resource, Transition<? super File> transition) {
                    Uri uri = Uri.fromFile(resource);
                    ivImage.setImageURI(uri);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
