package com.jingpai.pos.customer.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.jingpai.pos.customer.base.Constant;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
	
	private Button gotoBtn, regBtn, launchBtn, checkBtn, payBtn, favButton;
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this, Constant.WEIXIN_APP_ID, false);
//    	api.registerApp(Constant.WEIXIN_APP_ID);
		if (!api.handleIntent(getIntent(), this)) {
			finish();
		}
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
//		if(req.getType() == ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX){
//
//			ShowMessageFromWX.Req showReq = (ShowMessageFromWX.Req) req;
//
//			WXMediaMessage wxMsg = showReq.message;
//
//			WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
//
//			String extInfo = obj.extInfo;// 对应 小程序 app_paramter 参数
//		}
		finish();
	}

	@Override
	public void onResp(BaseResp resp) {
//		if (resp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
//			WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) resp;
//			String extraData =launchMiniProResp.extMsg; //对应小程序组件 <button open-type="launchApp"> 中的 app-parameter 属性
//		}
		finish();
	}

}