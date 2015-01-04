package com.luckysun.paycode;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.WindowManager;

public class ScreenWindowUtils {

	public static final String TAG = "ScreenWindowUtils";


    public static int convertDPtoPX(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int convertPXtoDP(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
	
	/** 
     * 设置亮度 ,最大为255
     * 
     * @param activity 
     * @param brightness 
     */ 
    public static void setBrightness(Activity activity, int brightness) { 
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes(); 
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f); 
        activity.getWindow().setAttributes(lp); 

    } 
	
	/** 
    * 停止自动亮度调节 
    * 
    */ 
   public static void stopAutoBrightness(Activity activity) { 
       Settings.System.putInt(activity.getContentResolver(),
    		   Settings.System.SCREEN_BRIGHTNESS_MODE, 
               Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL); 
   } 
	
   
   /** 
    * 开启亮度自动调节 
    * 
    * @param activity 
    */ 
   public static void startAutoBrightness(Activity activity) { 
       Settings.System.putInt(activity.getContentResolver(), 
               Settings.System.SCREEN_BRIGHTNESS_MODE, 
               Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC); 
   } 
   
   /** 
    * 判断是否开启了自动亮度调节 
    * 
    * @param aContentResolver
    * @return 
    */ 
   public static boolean isAutoBrightness(ContentResolver aContentResolver) { 
       boolean automicBrightness = false; 
       try { 
           automicBrightness = Settings.System.getInt(aContentResolver, 
                   Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC; 
       } catch (SettingNotFoundException e) { 
           e.printStackTrace(); 
       } 
       return automicBrightness; 
   } 
}
