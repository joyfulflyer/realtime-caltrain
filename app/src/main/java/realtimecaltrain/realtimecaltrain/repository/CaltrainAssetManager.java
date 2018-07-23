package realtimecaltrain.realtimecaltrain.repository;

import android.content.Context;
import android.renderscript.ScriptGroup;
import android.support.annotation.RawRes;
import android.support.constraint.ConstraintLayout;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import realtimecaltrain.realtimecaltrain.R;

public class CaltrainAssetManager {

    private static final String TAG = "Asset manager";

    private final Context context;

    private static CaltrainAssetManager instance;

    public synchronized static CaltrainAssetManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Must be initialized");
        }
        return instance;
    }

    public synchronized static CaltrainAssetManager initialize(Context context) {
        if (instance != null){
            throw new IllegalStateException("Please init only once");
        }
        instance = new CaltrainAssetManager(context);
        return instance;
    }

    public CaltrainAssetManager(Context context) {
        this.context = context.getApplicationContext();
    }

    public InputStream getResource(@RawRes int res) {
        return context.getResources().openRawResource(res);
    }

    public String getAssetAsString(@RawRes int res) {

        InputStream inputStream = getResource(res);

        int streamSize = 0;
        try {
            streamSize = inputStream.available();
        } catch (IOException ex) {
            Log.w(TAG, ex.getMessage());
        }

        Writer writer = new StringWriter();
        char[] buffer = new char[streamSize];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int n;
            try {
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                inputStream.close();
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                Log.w(TAG, ex.getMessage());
            }
        }

        return writer.toString();
    }
}
