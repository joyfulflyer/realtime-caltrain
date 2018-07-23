package realtimecaltrain.realtimecaltrain.api;

import org.w3c.dom.Document;

import retrofit2.Retrofit;

public class CaltrainApi {


    private static CaltrainService service;


    public static CaltrainService getService() {
        if (service == null) {
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl("http://www.caltrain.com");
            service = retrofitBuilder.build().create(CaltrainService.class);
        }



        return service;
    }
}
