package it.lucapascucci.laboratorio_3;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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
            Log.e("FileUtils", "File write failed: " + e.toString());
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
            Log.e("", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("", "Can not read file: " + e.toString());
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
}
