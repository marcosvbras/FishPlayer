package com.marcosvbras.fishplayer.app.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

/**
 * Created by marcosvbras on 17/01/2017.
 */

public class ImageResizeUtils {

    private static final String TAG = ImageResizeUtils.class.getName();

    public static int calculateInSampleSize(int originalWidth, int originalHeight, int requiredWidth, int requiredHeight) {
        // Raw height and width of image
        final int height = originalHeight;
        final int width = originalWidth;
        int inSampleSize = 1;

        if (height > requiredHeight || width > requiredWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= requiredHeight
                    && (halfWidth / inSampleSize) >= requiredWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * Resize a large Bitmap to scaled Bitmap
     * @param bitmapOriginal Large bitmap to be scaled.
     * @param maxSize The max size for scale.
     * @return The scaled bitmap, or null if the large bitmap could not be scaled, or,
     * if bitmapOriginal is non-null. If maxSize is less than or equal to the original bitmap size, the original bitmap will be returned.
     * */
    public static Bitmap resizeBitmap(Bitmap bitmapOriginal, int maxSize) {
        if(bitmapOriginal == null) { return null; }

        int originalWidth = bitmapOriginal.getWidth();
        int originalHeight = bitmapOriginal.getHeight();

        float bitmapRatio = (float)originalWidth / (float)originalHeight;

        if(bitmapRatio == 1 && originalWidth > maxSize) {
            bitmapOriginal = Bitmap.createScaledBitmap(bitmapOriginal, maxSize, maxSize, true);
        } else if(bitmapRatio > 1 && originalHeight > maxSize) {
            originalWidth = (originalWidth * maxSize) / originalHeight;
            originalHeight = maxSize;
            bitmapOriginal = Bitmap.createScaledBitmap(bitmapOriginal, originalWidth, originalHeight, true);
        } else if(bitmapRatio < 1 && originalWidth > maxSize) {
            originalHeight = (originalHeight * maxSize) / originalWidth;
            originalWidth = maxSize;
            bitmapOriginal =  Bitmap.createScaledBitmap(bitmapOriginal, originalWidth, originalHeight, true);
        }

        return bitmapOriginal;
    }

    /**
     * Resize a large resource to scaled Bitmap
     * @param resources The resources object containing the image data.
     * @param drawableId The resource id of the image data.
     * @param maxSize The max size for scale.
     * @return The scaled bitmap. If maxSize is less than or equal to the original resource size, the original bitmap will be returned.
     * */
    public static Bitmap resizeResource(Resources resources, int drawableId, int maxSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, drawableId, options);
        options.inJustDecodeBounds = false;

        return resizeBitmap(BitmapFactory.decodeResource(resources, drawableId, options), maxSize);
    }

    public static Bitmap getResizedImage(Uri uriFile, int maxSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        return resizeBitmap(BitmapFactory.decodeFile(uriFile.getPath(), options), maxSize);
    }
}
