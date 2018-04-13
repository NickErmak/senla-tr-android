package com.paranoid.paranoidtwitter.services;

import com.google.gson.JsonArray;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TwitterService {

    @GET("/1.1/statuses/home_timeline.json?" +
            "tweet_mode=extended&include_cards=true&cards_platform=TwitterKit-13")
    Call<List<Tweet>> myHomeTimeline(@Query("count") Integer count,
                                     @Query("since_id") Long sinceId,
                                     @Query("max_id") Long maxId,
                                     @Query("trim_user") Boolean trimUser,
                                     @Query("exclude_replies") Boolean excludeReplies,
                                     @Query("contributor_details") Boolean contributeDetails,
                                     @Query("include_entities") Boolean includeEntities
    );
}
