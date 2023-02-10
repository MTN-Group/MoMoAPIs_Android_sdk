package com.momo.sdk;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileReader {

    public static String readFromFile(String filename) throws IOException {
         Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

            InputStream inputStream = appContext.getAssets().open(filename);
            StringBuilder stringBuilder = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            for (int ch; (ch = inputStreamReader.read()) != -1; ) {
                stringBuilder.append((char) ch);
            }
        return stringBuilder.toString();
    }



}
