package com.luckysun.paycode.zxing.encoding;

import java.util.Hashtable;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;




public final class EncodingHandler {
	private static final int BLACK = 0xff000000;
	private static final String CODE = "utf-8";  
    private static final int WHITE = 0xFFFFFFFF; 
	
    
    
    /**
     * 生成二维码
     */
	public static Bitmap createQRCode(String str,int widthAndHeight) throws WriterException {
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
        hints.put(EncodeHintType.CHARACTER_SET, CODE); 
		BitMatrix matrix = new MultiFormatWriter().encode(str,BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int[] pixels = new int[width * height];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = BLACK;
				}
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		
		
		return bitmap;
	}
	
	
	/** 
	 * 生成一维码（CODE_128） 
	 */  
	public static Bitmap createMemberCardBarcode128(String str, Integer width, Integer height)  
	{  
		if (width == null || width < 200)  
		{  
			width = 200;  
		}  
		if (height == null || height < 50)  
		{  
			height = 50;  
		}  
		try  
		{  
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
			hints.put(EncodeHintType.CHARACTER_SET, CODE);  
			BitMatrix bitMatrix = new MultiFormatWriter().encode(str,BarcodeFormat.CODE_128, width, height, hints);
			
			return toBufferedImage(bitMatrix);  
		}  
		catch (Exception e)  
		{  
			e.printStackTrace();  
		}  
		return null;  
	}  
	 /** 
     * 生成一维码（CODE_128） 
     */  
    public static Bitmap createBarcode128(String str, Integer width, Integer height)  
    {  
        if (width == null || width < 200)  
        {  
            width = 200;  
        }  
        if (height == null || height < 50)  
        {  
            height = 50;  
        }  
        try  
        {  
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
            hints.put(EncodeHintType.CHARACTER_SET, CODE);  
            BitMatrix bitMatrix = new MultiFormatWriter().encode(str,BarcodeFormat.CODE_128, width, height, hints);
            
            return toBufferedImage(bitMatrix);  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
        return null;  
    }  
	
    /** 
     * 生成一维码（EAN_13） 
     */  
    public static Bitmap createBarcodeEAN_13(String contents, int width, int height) {  
        int codeWidth = 3 + // start guard  
                (7 * 6) + // left bars  
                5 + // middle guard  
                (7 * 6) + // right bars  
                3; // end guard  
        codeWidth = Math.max(codeWidth, width);  
        try {  
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,  BarcodeFormat.EAN_13, codeWidth, height, null);  
            return toBufferedImage(bitMatrix); 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    
    
    /** 
     * 转换成图片 
     *  
     * @author wuhongbo 
     * @param matrix 
     * @return 
     */  
    private static Bitmap toBufferedImage(BitMatrix matrix)  
    {  
        int width = matrix.getWidth();  
        int height = matrix.getHeight();  
        
        Bitmap image = Bitmap.createBitmap(width, height,Bitmap.Config.ARGB_8888);
        for (int x = 0; x < width; x++)  
        {  
            for (int y = 0; y < height; y++)  
            {  
                image.setPixel(x, y, matrix.get(x, y) ? BLACK : WHITE);  
            }  
        }  
        return image;  
    }  
    
    
//    /** 
//     * 解码(二维、一维均可) 
//     */  
//    public static String read(File file)  
//    {  
//  
//        BufferedImage image;  
//        try  
//        {  
//            if (file == null || file.exists() == false)  
//            {  
//                throw new Exception(" File not found:" + file.getPath());  
//            }  
//  
//            image = ImageIO.read(file);  
//  
//            LuminanceSource source = new BufferedImageLuminanceSource(image);  
//            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));  
//  
//            Result result;  
//  
//            // 解码设置编码方式为：utf-8，  
//            Hashtable hints = new Hashtable();  
//            hints.put(DecodeHintType.CHARACTER_SET, "utf-8");  
//  
//            result = new MultiFormatReader().decode(bitmap, hints);  
//  
//            return result.getText();  
//  
//        }  
//        catch (Exception e)  
//        {  
//            e.printStackTrace();  
//        }  
//  
//        return null;  
//    }  
   
}
