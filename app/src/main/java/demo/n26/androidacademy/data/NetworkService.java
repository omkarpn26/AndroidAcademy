package demo.n26.androidacademy.data;

import demo.n26.androidacademy.data.model.FloodProtectionStatus;
import retrofit2.http.GET;
import rx.Observable;

/**
 Created by omkarpimple on 21.06.17.
 */

public interface NetworkService {

    @GET("index.json")
    Observable<FloodProtectionStatus> getFloodProtectionStatus();
}
