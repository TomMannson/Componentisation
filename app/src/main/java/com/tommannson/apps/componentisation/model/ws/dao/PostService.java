package com.tommannson.apps.componentisation.model.ws.dao;

import com.jmoraes.componentizationsample.model.ws.Post;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface PostService {
    public static final String SERVICE_ENDPOINT = "https://api.github.com/";


//    @GET("/posts/{id}")
//    public Observable<Post> getPost(@Path("id") int postId);

    @GET("/posts/{id}")
    public Call<Post> getPost(@Path("id") int postId);

    @GET("/posts")
    public Observable<List<Post>>
    getPosts();

}
