package com.example.bashreader.service.data;

import com.example.bashreader.service.model.BashQuote;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BashAPI {

    @GET("/api/get")
    Call<List<BashQuote>> getData (@Query("site") String site,
                                   @Query("name") String name,
                                   @Query("num") int num);
}
