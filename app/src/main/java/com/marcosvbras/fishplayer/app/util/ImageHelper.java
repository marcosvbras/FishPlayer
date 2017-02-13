package com.marcosvbras.fishplayer.app.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by marcos on 17/01/2017.
 */

public class ImageHelper {

    // Extrai a imagem do ImageView retornando um Bitmap
    public static Bitmap extractBitmapFromImageView(ImageView imageView) {
        return ((BitmapDrawable)imageView.getDrawable()).getBitmap();
    }

    // Converte uma imagem em uma string Base64 para Bitmap
    public static Bitmap decodeBase64ToBitmap(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    // Converte um Bitmap para uma imagem em string Base64
    public static String encodeBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // Na compressão, utilizar sempre PNG, JPEG perde a qualidade
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    // Faz o download de uma imagem na web e retorna como Bitmap
    public static Bitmap downloadBitmap(String url) {
        Bitmap bitmap = null;

        try {
            // Faz o download da imagem
            InputStream inputStream = new URL(url).openStream();
            // Converte a InputStream do Java para Bitmap
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch(Exception ex) {}

        return bitmap;
    }

    // Redimensiona um Bitmap de acordo com um tamamho máximo definido
    public static Bitmap resizeBitmap(Bitmap bitmapOriginal, int maxSize) {
        int bitmapWidth = bitmapOriginal.getWidth();
        int bitmapHeight = bitmapOriginal.getHeight();

        float bitmapRatio = (float)bitmapWidth / (float)bitmapHeight;

        // Definindo se a imagem é horizontal ou vertical
        boolean isLandscapeImage = bitmapRatio > 1 ? true : false;

        if(isLandscapeImage && bitmapWidth > maxSize) {
            bitmapWidth = maxSize;
            bitmapHeight = (int)(bitmapWidth / bitmapRatio);
            return Bitmap.createScaledBitmap(bitmapOriginal, bitmapWidth, bitmapHeight, true);
        } else if(!isLandscapeImage && bitmapHeight > maxSize) {
            bitmapHeight = maxSize;
            bitmapWidth = (int)(bitmapHeight * bitmapRatio);
            return Bitmap.createScaledBitmap(bitmapOriginal, bitmapWidth, bitmapHeight, true);
        } else {
            return bitmapOriginal;
        }
    }

    // Retorna um bitmap circular
    public static RoundedBitmapDrawable getRoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap);
        roundedBitmapDrawable.setCircular(true);
        roundedBitmapDrawable.setAntiAlias(true);
        return roundedBitmapDrawable;
    }

    // Arredonda uma imagem nos cantos definidos
    public static Bitmap getRoundedCornerBitmap(Context context, Bitmap bitmapInput, int pixels, int width, int height, boolean squareTopLeft, boolean squareTopRight, boolean squareBottomLeft, boolean squareBottomRight) {
        Bitmap bitmapOutput = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapOutput);
        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);

        //make sure that our rounded corner is scaled appropriately
        final float roundPx = pixels*densityMultiplier;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        //draw rectangles over the corners we want to be square
        if (squareTopLeft ) {
            canvas.drawRect(0, 0, width/2, height/2, paint);
        }
        if (squareTopRight ) {
            canvas.drawRect(width/2, 0, width, height/2, paint);
        }
        if (squareBottomLeft ) {
            canvas.drawRect(0, height/2, width/2, height, paint);
        }
        if (squareBottomRight ) {
            canvas.drawRect(width/2, height/2, width, height, paint);
        }

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapInput, 0,0, paint);

        return bitmapOutput;
    }
}
