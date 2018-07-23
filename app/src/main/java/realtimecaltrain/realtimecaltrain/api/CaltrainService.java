package realtimecaltrain.realtimecaltrain.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface CaltrainService {




    @GET("schedules/realtime/stations/hillsdalestation-mobile.html")
    Call<ResponseBody> getHillsdaleRealTime();


}
