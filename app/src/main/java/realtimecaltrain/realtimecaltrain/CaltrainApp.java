package realtimecaltrain.realtimecaltrain;

import android.app.Application;

import realtimecaltrain.realtimecaltrain.repository.CaltrainAssetManager;

public class CaltrainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CaltrainAssetManager.initialize(this);
    }
}
