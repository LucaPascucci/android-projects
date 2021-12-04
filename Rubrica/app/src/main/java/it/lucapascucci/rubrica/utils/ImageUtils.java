package it.lucapascucci.rubrica.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import java.io.File;

/**
 * @author Andrea
 *
 */
public class ImageUtils {

	private ImageUtils() {}

    /**
     * Converte i dp in pixel in base alla densita del dispositivo
     *
     * @param dp
     * @param context
     * @return
     */
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }


    /**
     * Verifica se il path corrisponde ad un immagine
     * @param filePath
     * @return
     */
	public static boolean isValidImage(String filePath) {
		if (filePath != null && !filePath.trim().isEmpty() && new File(filePath).exists()) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filePath, options);
			return options.outWidth != -1 && options.outHeight != -1;
		}
		
		return false;
	}

    /**
     * Scala l'immagine in base alle dimensioni richieste, evita un {@link java.lang.OutOfMemoryError}
     * @param path
     * @param reqWidth
     * @param reqHeight
     * @return
     */
	public static Bitmap decodeSampledBitmapFromMemory(String path,
			int reqWidth, int reqHeight) {

		// Controlliamo la dimensione dell'immagine "caricando" solo la cornice
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calcoliamo il rateo di ridimensionamento
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Carichiamo l'immagine con i nuovi paramentri
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	/**
	 * Calcola di quanto l'immagine può essere ridotta per adattarsi alle dimensioni specificate
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

		// Dimensioni dell'immagine
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			int halfHeight = height / 2;
			int halfWidth = width / 2;

            // Calcoliamo il piu grande valore di inSampleSize in una potenza di 2
            //manteniamo sempre la l'altezza e la larghezza maggiori rispetto all'altezza e alla larghezza richiesta
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}
}