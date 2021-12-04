package it.lucapascucci.rubrica.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**
 *
 * @author Andrea
 *
 */
public class ImageStorage {

	private static File getDirPictures(Context context) {
		File dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		if (!dir.exists())
    		dir.mkdirs();

		return dir;
	}

	/**
	 * Restiuisce la directory con la chiave passata in ingresso dove salvare le immagini
	 * @param context
	 * @param key
	 * @return
	 */
	public static File getDirectoryImageForKey(Context context, String key) {
		File dir = new File(getDirPictures(context).getAbsolutePath()
				+ (key != null && !key.isEmpty() ? (File.separator + key) : ""));

		if (!dir.exists())
    		dir.mkdirs();

		return dir;
	}

	/**
	 * Crea un file dove salvare la foto
	 * @param context
	 * @param key chiave per creare una sottocategoria per la foto
	 * @param nomeImage nome dell'immagine se null ne crea uno univoco
	 * @return
	 */
	public static File getFileForNewFoto(Context context, String key, String nomeImage) {
		if (nomeImage == null || nomeImage.isEmpty())
			nomeImage = getNameForNewFoto(nomeImage);
		
		if (!nomeImage.endsWith(".jpg"))
			nomeImage += ".jpg";
		
		return new File(getDirectoryImageForKey(context, key), nomeImage);
    }
	
	/**
	 * Resistuisce un nome univoco per un nuova foto con in aggiunta l'eventuale stringa passata in ingresso
	 * @param nomeFoto nome iniziale oppure <code>null</code>
	 * @return
	 */
	public static String getNameForNewFoto(String nomeFoto) {
		if (nomeFoto == null)
			nomeFoto = "";
		
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(Calendar.getInstance().getTime());
		
		return nomeFoto + timeStamp + "_" + (!nomeFoto.endsWith(".jpg") ? ".jpg" : "");
	}
	
	/**
	 * Restituisce il file con il nome passato in ingresso
	 * @param context
	 * @param key
	 * @param nomeImage
	 * @return
	 */
	public static File getFileImage(Context context, String key, String nomeImage) {
		return new File(getDirectoryImageForKey(context, key), nomeImage);
	}
	
	/**
	 * Controlla se l'immagine esiste ed � una immagine valida
	 * @param context
	 * @param key
	 * @param nomeImage
	 * @return <code>true</code> immagine esiste ed � valida, <code>false</code> in caso contrario
	 */
	public static boolean isImageExitsAndValid(Context context, String key, String nomeImage) {
		File img =  getFileImage(context, key, nomeImage);
		if (img.exists()) {
			return isValidImage(img.getAbsolutePath());
		}
		
		return false;
	}

    /**
     * Controlla se l'immagine è un immagine vera andando a controllare l'altezza e la larghezza della cornice dell'immagine
     * @param filePath
     * @return
     */
    public static boolean isValidImage(String filePath) {
        if (filePath != null && !filePath.isEmpty() && new File(filePath).exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            return options.outWidth != -1 && options.outHeight != -1;
        }

        return false;
    }
}
