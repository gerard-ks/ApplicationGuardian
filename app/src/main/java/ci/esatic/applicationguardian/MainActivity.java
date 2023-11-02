package ci.esatic.applicationguardian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private TextView testView;
    private TextView error;
    private JsonPlaceHolderApi jsonPlaceHolderAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtenir les références des champs d'e-mail et de mot de passe
        TextInputEditText emailEditText = findViewById(R.id.email);
        TextInputEditText passwordEditText = findViewById(R.id.password);
        Button btnConnexion = findViewById(R.id.login_button);
        error = findViewById(R.id.error_text);


        testView = findViewById(R.id.test);

        // Récupérer les valeurs des champs d'e-mail et de mot de passe


        // Si l'utilisateur clique sur le bouton de connexion


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.14:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderApi.class);

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                connexion(email, password);
            }
        });
    }

    private void connexion(String email, String password) {
        Login login = new Login(email, password);
        Call<Login> call = jsonPlaceHolderAPI.login(login);

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Integer status;
                String message;
                Login reponse = response.body();
                status = reponse.getStatus();
                message = reponse.getMessage();

                if (!response.isSuccessful()){
                    testView.setText("Code " + response.code());
                    return;
                } else if (status == 0) {
                    error.setText(message);
                    return;
                } else if (status == 200) {
                    SharedPreferences sharedPreferences = getSharedPreferences("StaticsFile", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("authToken", reponse.getAccess_token());
                    editor.putString("userName", reponse.getNom());
                    editor.putInt("userId", reponse.getId_Utilisateur());
                    System.out.println(reponse.getNom());
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, Guardian.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                testView.setText(t.getMessage());
            }
        });
    }
}
