package realtimecaltrain.realtimecaltrain.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import realtimecaltrain.realtimecaltrain.R;
import realtimecaltrain.realtimecaltrain.api.CaltrainApi;
import realtimecaltrain.realtimecaltrain.api.CaltrainService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaltrainDataSource {


    private static final String TAG = "Data source";


    private final CaltrainService service;

    private MutableLiveData<String> data;

    public CaltrainDataSource() {
        this(CaltrainApi.getService());
    }


    public CaltrainDataSource(CaltrainService service) {
        this.service = service;
    }


    public LiveData<String> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
            Executors.newSingleThreadExecutor().submit(new Runnable() {
                @Override
                public void run() {
                    getNewData();

                }
            });
        }
        return data;
    }


    public void getNewData() {

        String result = CaltrainAssetManager.getInstance().getAssetAsString(R.raw.asset);

        if (result != null && false) {
            handleHtmlString(result);
        } else {
            service.getHillsdaleRealTime().enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (!response.isSuccessful()) {
                        data.postValue(String.format("We got a bad response code: %s", response.code()));
                    } else {

                        StringBuilder builder = new StringBuilder();

                        builder.append(String.format("Http message: %s%n", response.message()));

                        okhttp3.Headers headers = response.headers();
                        for (String headerName : headers.names()) {

                            builder.append(String.format("Header: %s, %s%n", headerName, headers.get(headerName)));

                        }

                        ResponseBody body = response.body();
                        if (body != null) {
                            try {
                                handleHtmlString(body.string());
                            } catch (IOException e) {
                                Log.e(TAG, "Problem getting body", e);
                            }
                        }

                    }


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    data.postValue(String.format("Things went wrong:%n%s", t));

                }
            });
        }
    }


    private void handleHtmlString(String html) {
        Document document = Jsoup.parse(html);

        Element ipsttrains = document.getElementById("ipsttrains");

        Element table = document.getElementsByClass("ipf-caltrain-table-trains").get(0);// Should be only 1
        // This element is a table



        data.postValue(table.toString());

    }
}
