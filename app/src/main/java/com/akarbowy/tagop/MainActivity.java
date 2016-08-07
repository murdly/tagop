package com.akarbowy.tagop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.akarbowy.tagop.model.QueryResult;
import com.akarbowy.tagop.network.WykopService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Inject
    WykopService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TagopApplication)getApplication()).component().inject(this);

        service.getTag("android", 1)
                .enqueue(new Callback<QueryResult>() {
                    @Override
                    public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {
                        Timber.i("response code %s", response.code());
                    }

                    @Override
                    public void onFailure(Call<QueryResult> call, Throwable t) {
                        Timber.e("fail", t);
                    }
                });
    }
}
