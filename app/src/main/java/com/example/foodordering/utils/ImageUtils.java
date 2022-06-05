package com.example.foodordering.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static String imageToBase64(ContentResolver resolver, Uri uri) {
        final Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
        final int compressQuality = 100;

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            // https://stackoverflow.com/questions/3879992/how-to-get-bitmap-from-an-uri
            bitmap.compress(compressFormat, compressQuality, stream);
            byte[] bytes = stream.toByteArray();

            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    /**
     * From: https://stackoverflow.com/questions/4837110/how-to-convert-a-base64-string-into-a-bitmap-image-to-show-it-in-a-imageview
     *
     * @param base64 Base64 string of the encoded image
     * @return Decoded image
     */
    public static Bitmap imageFromBase64(String base64) {
        try {
            byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}
