package demo.n26.androidacademy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import demo.n26.androidacademy.data.NetworkService;
import demo.n26.androidacademy.data.model.FloodProtectionStatus;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private NetworkService networkService;

    private TextView textView1;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeDependencies();
        textView1 = (TextView) findViewById(R.id.text_flood_protection);
        textView2 = (TextView) findViewById(R.id.text_flood_protection_probability);
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkService.getFloodProtectionStatus()
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(new Action1<FloodProtectionStatus>() {
                                     @Override
                                     public void call(final FloodProtectionStatus floodProtectionStatus) {
                                         onFloodProtectionStatusFetched(floodProtectionStatus);
                                     }
                                 },

                                 new Action1<Throwable>() {
                                     @Override
                                     public void call(final Throwable throwable) {
                                         throwable.printStackTrace();
                                     }
                                 });
    }

    private void onFloodProtectionStatusFetched(FloodProtectionStatus status) {
        Log.d(TAG, "Delay/probability: " + status.getDelay() + ", " + status.getProbability());
        textView1.setText("Flood protection delay: " + status.getDelay());
        textView2.setText("Flood protection probability: " + status.getProbability());
    }

    private void initializeDependencies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sfpt.n26.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        networkService = retrofit.create(NetworkService.class);
    }
}
