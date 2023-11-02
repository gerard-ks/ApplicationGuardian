package ci.esatic.applicationguardian;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Incident extends AppCompatActivity {

    private JsonPlaceHolderApi jsonPlaceHolderAPI;
    private Integer userId;
    private String token;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("StaticsFile", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0); // null est la valeur par défaut si la clé n'est pas trouvée
        token += "Bearer ";
        token += sharedPreferences.getString("authToken", null);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident);

        Spinner dropdownPriorite = findViewById(R.id.dropdownPriorite);
        TextInputEditText descPriorite = findViewById(R.id.descIncident);
        Button validerBtn = findViewById(R.id.btn_valider_incident);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.14:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderApi.class);

        validerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = descPriorite.getText().toString();
                // Faire +1 pour avoir l'id de la priorite en question
                Integer priorite = dropdownPriorite.getSelectedItemPosition() + 1;

                enregistrerIncident(description, priorite, userId);
            }
        });
    }

    private void enregistrerIncident(String description, Integer priorite, Integer userId) {
        IncidentApi incident = new IncidentApi(1, priorite, userId, description);
        Call<IncidentApi> call = jsonPlaceHolderAPI.storeIncident(token, incident);

        System.out.println(description + " " + priorite + " " + userId);

        call.enqueue(new Callback<IncidentApi>() {
            @Override
            public void onResponse(Call<IncidentApi> call, Response<IncidentApi> response) {
                Integer status;
                String message;
                IncidentApi reponse = response.body();
                System.out.println(response.code());
                System.out.println(reponse);
                status = reponse.getStatut();
                message = reponse.getMessage();

                if (!response.isSuccessful()){
                    error.setText("Code " + response.code());
                    return;
                } else if (status == 0) {
                    error.setText(message);
                    return;
                } else if (status == 1) {
                    message = reponse.getMessage();

                    Intent intent = new Intent(Incident.this, Guardian.class);
                    intent.putExtra("message", message); // Ajoutez le message comme données supplémentaires
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<IncidentApi> call, Throwable t) {

            }
        });
    }
}