package com.akarbowy.tagop.network;

import com.akarbowy.tagop.model.QueryResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WykopService {
    String BASE_URL = "https://a.wykop.pl/";

    @GET("tag/entries/{tagValue}/page/{pageValue}")
    Call<QueryResult> search(@Path("tagValue") String tag, @Path("pageValue") int page);
}
