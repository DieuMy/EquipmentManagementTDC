package vn.edu.tdc.managementequipmenttdc.tools;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class LoadImage {
    //Load anh co kich thuoc lon
    public Bitmap loadImage(int imageID, int targetHeight, int targetWidth){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //De khong doc toan bo noi dung anh, ma chi doc tham so anh de biet kich thuoc anh
        BitmapFactory.decodeResource(Resources.getSystem(), imageID, options); //imageID: la anh can load/ sau khi thuc hien cau lenh nay options chua cac tham so cua anh
        final int originalWidth = options.outWidth;//originalWidth: Chua chieu rong goc cua anh(anh lon)
        final int originalHeight = options.outHeight;//originalHeight: Chieu cao goc cua anh(anh lon)

        int inSampleSize = 1;

        while ((originalHeight / (inSampleSize * 2)) > targetHeight &&  (originalWidth / (inSampleSize * 2)) > targetWidth) {
            inSampleSize *= 2;
        }

        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false; //Load kich thuoc that cua anh (vi sau khi xu ly anh nay da theo yeu cau)
        return  BitmapFactory.decodeResource(Resources.getSystem(), imageID, options);//Tra ve anh theo kich thuoc mong muon(options: chua kich thuoc anh sau xu ly)
    }
}
