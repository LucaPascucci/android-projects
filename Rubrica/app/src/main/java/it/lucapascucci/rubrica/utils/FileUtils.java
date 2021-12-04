package it.lucapascucci.rubrica.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;

/**
 * Created by Luca on 26/03/15.
 */
public class FileUtils {

    public static void writeToFile(String data, String fileName, Context context) {
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(data);
            outputStreamWriter.flush();
        } catch (IOException e) {
            Logging.error("FileUtils", "File write failed: " + e.toString());
        } finally {
            if (fileOutputStream != null)
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                }
            if (outputStreamWriter != null)
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                }
        }

    }

    public static String readFromFile(String fileName, Context context) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        String ret = "";
        try {
            inputStream = context.openFileInput(fileName);
            inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((receiveString = bufferedReader.readLine()) != null) {
                stringBuilder.append(receiveString);
            }

            ret = stringBuilder.toString();
        } catch (FileNotFoundException e) {
            Logging.error("FileUtils", "File not found: " + e.toString());
        } catch (IOException e) {
            Logging.error("FileUtils", "Can not read file: " + e.toString());
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            if (inputStreamReader != null)
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                }
        }

        return ret;
    }

    /**
     * Copia due file
     *
     * @param src
     *            file da copia
     * @param dst
     *            file di destinazione
     * @return <code>true</code> operazione andata a buon fine,
     *         <code>false</code> in caso contrario
     */
    public static boolean copiaFile(File src, File dst) {
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        boolean result = false;
        try {
            inStream = new FileInputStream(src);
            outStream = new FileOutputStream(dst);
            inChannel = inStream.getChannel();
            outChannel = outStream.getChannel();
            long res = inChannel.transferTo(0, inChannel.size(), outChannel);
            if (res > 0)
                result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outStream != null)
                try {
                    outStream.close();
                } catch (IOException e) {}
            if (inStream != null)
                try {
                    inStream.close();
                } catch (IOException e) {}
            if (outChannel != null)
                try {
                    outChannel.close();
                } catch (IOException e) {}
            if (inChannel != null)
                try {
                    inChannel.close();
                } catch (IOException e) {}
        }

        return result;
    }
}
