package ci.esatic.applicationguardian;


import retrofit2.Call;
import retrofit2.http.*;

public interface JsonPlaceHolderApi {

    // @GET("utilisateur/index")
    // Call<List<User>> getUtilisateurs();

    @POST("utilisateur/login")
    Call<Login> login(@Body Login login);
}