package com.truongsinh.mydirectory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Util {
    public static final String MY_FOLDER = "/DemoReadWriteImage/";
    public static Bitmap readBitmap(String filename, Context context)
    {
        Bitmap bitmap=null;
        String fullpath = Environment.getExternalStorageDirectory().getAbsolutePath()+MY_FOLDER+filename;
        try
        {
            bitmap = BitmapFactory.decodeFile(fullpath);
        }
        catch (Exception ex)
        {
            Log.d("mytag","No image in SDcard");
        }
        // read image from internal Memory
        File file = context.getFileStreamPath(filename);
        try {

            FileInputStream fIn =new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fIn);
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    private static boolean isExternalStorageReadable()
    {
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED) || state.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
            return true;
        return false;
    }
    private static boolean writeImageToSD(String filename, Bitmap image,Context context)
    {
        if (isExternalStorageReadable()) {
            String url = Environment.getExternalStorageDirectory().getAbsolutePath() + MY_FOLDER;

            try {
                // create a directory
                File dir = new File(url);
                if (!dir.exists())
                    dir.mkdirs();
                // create a file
                File file = new File(url, filename);
                if (!file.exists())
                    file.createNewFile();
                // create a FileOutPutStream
                FileOutputStream fOut = new FileOutputStream(file);
                // save image to SD Card
                image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                // free
                fOut.close();


            } catch (Exception ex) {
                return false;
            }
            return true;
        }
        return  false; }
    private static boolean wireImageToInternalMem(String filename, Bitmap image, Context context)
    {
        try
        {
            // create a File rong and 1 FileOutPut and mounted FileOutPut to File rong have created
            FileOutputStream fout = context.openFileOutput(filename,Context.MODE_PRIVATE);
            // save image to internal Memory
            image.compress(Bitmap.CompressFormat.PNG,100,fout);
            fout.flush();
            //
            fout.close();
        }catch (Exception ex)
        {
            return  false;
        }
        return  true;
    }
    public static boolean writeImage(String filename, Bitmap image, Context context)
    {
        if(wireImageToInternalMem(filename,image,context))
            return true;
        return writeImageToSD(filename,image,context);
    }
    public static void deleteImage(String filename, Context context)
    {

        //delete image from internal memory
        context.deleteFile(filename);
        // delete image from external memory
        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath()+MY_FOLDER + filename;
        File file = new File(fullPath);
        file.delete();
    }
    public static String[] detachTime(String date)
    {
        String thu = date.substring(0,3);
        String ngay = date.substring(4,6);
        String thang= date.substring(7,10);
        String gio = date.substring(16);
        return new String[]{thu,ngay,thang,gio};
    }
    public static String formatStringDateToNameImage(String date)
    {
        date.replace(" ","");
        return date;
    }
}
