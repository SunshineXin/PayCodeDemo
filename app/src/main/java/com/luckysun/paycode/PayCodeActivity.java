package com.luckysun.paycode;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.luckysun.paycode.zxing.encoding.EncodingHandler;


public class PayCodeActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView mTitle;
    private ImageButton mRefreshImageBtn;

    private int mCountdownTime = 60000;//60秒倒计时
    private TimeCount mTimeCount;

    private String mPayCodeStr;
    private ImageView mBarCodeIV;
    private ImageView mQRCodeIV;
    private TextView mBarCodeTV;

    private int mId = 523456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_code);

        initView();

        if (ScreenWindowUtils.isAutoBrightness(getContentResolver())) {
            ScreenWindowUtils.stopAutoBrightness(this);
        }

        ScreenWindowUtils.setBrightness(this, 255);


        refreshPayCode();
        mTimeCount = new TimeCount(mCountdownTime, 1000);
        mTimeCount.start();

    }


    private void refreshPayCode() {
        try {
            mPayCodeStr = PayCodeUtils.generate(String.valueOf(mId));
            mBarCodeTV.setText(StringUtils.addSpaceToMemberCardNumber(mPayCodeStr));
            mBarCodeIV.setImageBitmap(EncodingHandler.createMemberCardBarcode128(mPayCodeStr, ScreenWindowUtils.convertDPtoPX(this, 300), ScreenWindowUtils.convertDPtoPX(this, 100)));
            mQRCodeIV.setImageBitmap(EncodingHandler.createQRCode(mPayCodeStr, ScreenWindowUtils.convertDPtoPX(this, 200)));
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        mTitle = (TextView) findViewById(R.id.tv_title);
        mTitle.setText("付款码");

        mRefreshImageBtn = (ImageButton) findViewById(R.id.btn_refresh);
        mRefreshImageBtn.setVisibility(View.VISIBLE);
        mRefreshImageBtn.setOnClickListener(this);

        mBarCodeIV = (ImageView) findViewById(R.id.iv_barcode);
        mBarCodeTV = (TextView) findViewById(R.id.tv_barcode);
        mQRCodeIV = (ImageView) findViewById(R.id.iv_QRCode);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_refresh:
                refreshPayCode();
                mTimeCount.start();
                customerToast(this,"刷新成功");
                break;
            default:
                break;
        }
    }

    /**
     * 倒计时的内部类
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            refreshPayCode();
            this.start();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            System.out.println("timer:" + String.valueOf(millisUntilFinished / 1000));

        }
    }


    /**
     * 自定义Toast
     */
    public void customerToast(Context context, String message) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.register_false_toast, null);
        TextView contant = (TextView) view.findViewById(R.id.toast_contant);
        contant.setText(message);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimeCount.cancel();
        mTimeCount = null;
    }



}
