package com.jingpai.pos.customer.activity.show.My;

import android.view.View;
import android.widget.EditText;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.bean.User;
import com.jingpai.pos.customer.mvp.presenter.show.my.NicknamePresenterImp;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.utils.ToastUtils;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 时间: 2020/2/5
 * 功能:修改用户名
 */
public class NickNameActivity extends BaseActivity {
    @BindView(R.id.et_nickName)
    EditText etNickName;
    private String name;

    private NicknamePresenterImp nicknamePresenterImp;

    @Override
    protected int getLayout() {
        return R.layout.nick_name_activity;
    }


    @Override
    protected void initData() {
        nicknamePresenterImp = new NicknamePresenterImp();
    }

    public void nickNameData(Object o) {
        if (o==null){
            return;
        }
        ToastUtils.INSTANCE.showToast(this, "修改成功");
        User user = LocalCache.getUser();
        user.setName(name);
        LocalCache.saveUser(user);
        finish();

    }

    @OnClick({R.id.iv_back, R.id.tv_right_btn, R.id.empty, R.id.et_nickName})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right_btn:
                name = etNickName.getText().toString().trim();
                if (name.equals("")) {
                    ToastUtils.INSTANCE.showToast(NickNameActivity.this, "输入框不能为空");
                } else {
                    TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
                    baseRequest.put("nickname", name);
                    nicknamePresenterImp.nickNameData(baseRequest,this::nickNameData);
                }
                break;
            case R.id.empty:
                etNickName.setText("");
                break;
            case R.id.et_nickName:
                etNickName.setCursorVisible(true);
                etNickName.setFocusable(true);
                etNickName.setFocusableInTouchMode(true);
                etNickName.requestFocus();
                break;
        }
    }



    protected void onResume() {
        super.onResume();
        etNickName.setText(LocalCache.getUserName());
    }

}