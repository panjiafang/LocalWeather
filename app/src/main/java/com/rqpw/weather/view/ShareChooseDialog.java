package com.rqpw.weather.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.rqpw.weather.R;
import com.rqpw.weather.db.SettingPreference;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Pan Jiafang on 2014/8/27.
 */
public class ShareChooseDialog extends Dialog implements View.OnClickListener {

    private Button btn_cancel;
    private ImageView iv_weixin, iv_weixincircle;

    private Activity context;

    public ShareChooseDialog(Activity context) {
        super(context, R.style.dialog);

        this.context = context;

        init();
    }

    private void init() {

        setContentView(R.layout.dialog_sharechoose);

        btn_cancel = (Button) findViewById(R.id.dialog_sharechoose_cancel);
        iv_weixin = (ImageView) findViewById(R.id.sharechoose_weixin);
        iv_weixincircle = (ImageView) findViewById(R.id.sharechoose_weixincircle);

        iv_weixin.setOnClickListener(this);
        iv_weixincircle.setOnClickListener(this);

        btn_cancel.setOnClickListener(this);
    }

    public void showOnekeyshare(String platform, boolean silent) {
        OnekeyShare oks = new OnekeyShare();

        // 分享时Notification的图标和文字
        oks.setNotification(R.drawable.laucher,
                context.getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(context.getString(R.string.share));
        // text是分享文本，所有平台都需要这个字段
        oks.setText("自我天气,凸显自我");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(context.getExternalCacheDir().getAbsolutePath()+"/share.png");
        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://app.mi.com/detail/69770");
        // 是否直接分享（true则直接分享）
        oks.setSilent(silent);
        // 指定分享平台，和slient一起使用可以直接分享到指定的平台
        if (platform != null) {
            oks.setPlatform(platform);
        }
        // 去除注释可通过OneKeyShareCallback来捕获快捷分享的处理结果
//        oks.setCallback(new OneKeyShareCallback());

        oks.show(context);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if(v == iv_weixin){
            ShareSDK.initSDK(context);
//            showOnekeyshare("Wechat", false);


            Platform.ShareParams shareParams = new Platform.ShareParams();
            shareParams.setImagePath(context.getExternalCacheDir().getAbsolutePath()+"/share.png");
            shareParams.setTitle("分享");
            shareParams.setUrl("http://app.mi.com/detail/69770");
            shareParams.setText("自我天气,凸显自我");
            shareParams.setExtInfo("你好");
            shareParams.setShareType(Platform.SHARE_IMAGE);

            Platform platform = ShareSDK.getPlatform(context, "Wechat");
            platform.share(shareParams);
        }
        else if(v == iv_weixincircle){
            ShareSDK.initSDK(context);
//            showOnekeyshare("WechatMoments", false);
            Platform.ShareParams shareParams = new Platform.ShareParams();
            shareParams.setImagePath(context.getExternalCacheDir().getAbsolutePath()+"/share.png");
            shareParams.setTitle("分享");
            shareParams.setUrl("http://app.mi.com/detail/69770");
            shareParams.setText("自我天气,凸显自我");
            shareParams.setShareType(Platform.SHARE_IMAGE);
            shareParams.setExtInfo("你个2货");
            Platform platform = ShareSDK.getPlatform(context, "WechatMoments");
            platform.share(shareParams);
        }

    }
}
