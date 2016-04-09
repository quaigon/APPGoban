package com.quaigon.kamil.connection;

import com.quaigon.kamil.dto.challange.Challenge;
import com.quaigon.kamil.dto.challange.ChallengeResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Kamil on 27.03.2016.
 */
public interface ChallangeConnectionService {

    @FormUrlEncoded
    @POST("api/v1/players/{id}/challenge/")
    Call<ChallengeResponse> sendChallange(@Path("id") int id, @Body Challenge challenge);
}
