package br.com.poo2.bookreaderapp.retrofit;

import com.google.firebase.installations.remote.TokenResult;

import java.util.List;

import br.com.poo2.bookreaderapp.model.BookModel;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    @Multipart
    @POST("v1/books/{customerId}/upload")
    Call<TokenResult.ResponseCode> uploadFile(
            @Path("customerId") String customerId,
            @Part MultipartBody.Part filePart
    );

    @GET("v1/books/{customerId}")
    Call<List<BookModel>> getPDFFiles(
            @Path("customerId") String customerId
    );

    @DELETE("v1/books/{id}")
    Call<TokenResult.ResponseCode> deletePDFFile(
            @Path("id") String id
    );

}