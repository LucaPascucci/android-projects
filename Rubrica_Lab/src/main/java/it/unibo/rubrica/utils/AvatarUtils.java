package it.unibo.rubrica.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import it.unibo.rubrica.R;

public class AvatarUtils {

    private static final String AVATAR_UTILS = "AvatarUtils";

    public static final int CAMERA_ACTIVITY = 326;

    public static final int GALLERY_ACTIVITY = 327;

    public static final String FOTO_SCRIPT = "fotoScript";

    private static final String IMAGE_PATH = "path";

    private static final String IMAGE_EXTRA = "extra";

    public static final String FOTO_MAIN_FOLDER = "Immagini";

    private static final String AVATAR = "avatar";

    private static final String AVATAR_FOLDER = "AvatarImages";

    /**
     * Mostra una dialog per far scegliere all'utente se vuole scattare o
     * caricara dalla memoria la foto
     *
     * @param activity
     */
    public static void scattaFoto(final Activity activity) {
        final String pathImage = ImageStorage.getFileForNewFoto(activity, AVATAR_FOLDER,
                    ImageStorage.getNameForNewFoto(AVATAR)).getAbsolutePath();

        salvaDati(pathImage, AVATAR_FOLDER, activity);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(true)
                .setItems(R.array.foto_input, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                takeNewCameraFoto(pathImage, activity);
                                break;
                            case 1:
                                takeNewGalleryFoto(activity);
                                break;
                        }
                    }
                }).show();
    }

    /**
     * Fa scegliere l'immagine all'utente dalla galleria o da un file explorer di immagini
     * @param activity
     */
    public static void takeNewGalleryFoto(Activity activity) {
        try {

            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activity.startActivityForResult(Intent.createChooser(
                            intent, activity.getString(R.string.dialog_apri_con)),
                    GALLERY_ACTIVITY);
        } catch (Exception e) {
            Log.e(AVATAR_UTILS, "impossibile aprire la galleria!", e);
            Toast.makeText(activity,
                    R.string.toast_impossibile_aprire_galleria,
                    Toast.LENGTH_LONG).show();
        }
    }
    /**
     * Apre la fotocamera per scattare una nuova foto
     * @param pathImage
     * @param activity
     */
    public static void takeNewCameraFoto(String pathImage, Activity activity) {
        try {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(pathImage)));
            activity.startActivityForResult(intent, CAMERA_ACTIVITY);
        } catch (Exception e) {
            Log.e(AVATAR_UTILS, "impossibile aprire la fotocamera!", e);
            Toast.makeText(activity,
                    R.string.toast_impossibile_aprire_fotocamera,
                    Toast.LENGTH_LONG).show();
        }
    }

    public static void clearDati(Context context) {
        SharedPreferences shared = context.getSharedPreferences(
                FOTO_SCRIPT, Context.MODE_PRIVATE);
        Editor edit = shared.edit();
        edit.clear();
        edit.apply();
    }

    private static void salvaDati(String pathImage, String extra, Context context) {
        SharedPreferences shared = context.getSharedPreferences(
                FOTO_SCRIPT, Context.MODE_PRIVATE);
        Editor edit = shared.edit();
        edit.putString(IMAGE_PATH, pathImage);
        edit.putString(IMAGE_EXTRA, extra);
        edit.apply();
    }

    public static String getPathImage(Context context) {
        SharedPreferences shared = context.getSharedPreferences(
                FOTO_SCRIPT, Context.MODE_PRIVATE);
        return shared.getString(IMAGE_PATH, null);
    }

    public static String getExtra(Context context) {
        SharedPreferences shared = context.getSharedPreferences(
                FOTO_SCRIPT, Context.MODE_PRIVATE);
        return shared.getString(IMAGE_EXTRA, null);
    }

    /**
     * Restitusice il path per caricare la foto selezionata dall'utente, se la
     * foto è una foto valida deve essere verificato dal chiamante
     *
     * @param context
     * @param requestCode
     * @param data
     * @return path foto oppure null in caso di errore
     */
    public static String mangeActivityResultScattaFoto(Context context, int requestCode, Intent data) {
        String avatarImage = null;
        if (requestCode == AvatarUtils.CAMERA_ACTIVITY) {
            Log.d(AVATAR_UTILS, "CAMERA_ACTIVITY");
            avatarImage = getPathImage(context);
        } else if (requestCode == AvatarUtils.GALLERY_ACTIVITY && data != null) {
            Log.d(AVATAR_UTILS, "GALLERY_ACTIVITY");
            //dovuta al fatto che l'app foto di google può restituire
            //immagini cloud quindi non presenti effettivamente all'interno del device
            try {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = context.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                avatarImage = cursor.getString(columnIndex);
                cursor.close();

                if (avatarImage != null && !avatarImage.isEmpty()) {
                    File dest = new File(getPathImage(context));
                    FileUtils.copiaFile(new File(avatarImage), dest);
                    avatarImage = dest.getAbsolutePath();
                } else {
                    avatarImage = null;
                }
            } catch (Exception e) {
                Log.d(AVATAR_UTILS, "GALLERY_ACTIVITY errore durante il recupero dell'immagine", e);
            }
        }

        Log.d(AVATAR_UTILS, "avatarImage " + avatarImage);

        return avatarImage;
    }
}