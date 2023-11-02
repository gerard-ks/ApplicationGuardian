package ci.esatic.applicationguardian;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.textview.MaterialTextView;

public class Guardian extends AppCompatActivity {
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("StaticsFile", MODE_PRIVATE);
        username = sharedPreferences.getString("userName", null); // null est la valeur par défaut si la clé n'est pas trouvée

        if (username == null) {
            Intent intent = new Intent(Guardian.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_guardian);

            MaterialTextView showUsername = findViewById(R.id.userName);
            showUsername.setText(username);
        }

        ImageButton registerVisiteur = findViewById(R.id.btn_register_visiteur);
        ImageButton registerIncident = findViewById(R.id.btn_register_incident);
        ImageButton scanQR = findViewById(R.id.btn_scan_qrcode);
        ImageButton logout = findViewById(R.id.btn_logout);


        // DECONNEXION
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deconnexion(); // Appeler la fonction de déconnexion
            }
        });

        // VISITEUR
        registerVisiteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageVisiteur();
            }
        });

        // INCIDENT
        registerIncident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageIncident();
            }
        });

        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageScanQR();
            }
        });

        String message = getIntent().getStringExtra("message");

        // Vérifiez si un message a été passé et affichez-le dans une boîte de dialogue
        if (message != null) {
            afficherAlerte(message);
        }
    }

    private void afficherAlerte(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Gérer le clic sur le bouton OK si nécessaire
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void deconnexion() {
        // Supprimer les données d'authentification des SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("StaticsFile", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("authToken");
        editor.remove("userName");

        editor.apply();

        // Rediriger l'utilisateur vers l'écran de connexion (ou une autre page d'accueil)
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Terminer l'activité actuelle
    }

    private void pageVisiteur(){
        Intent intent = new Intent(this, Visiteur.class);
        startActivity(intent);
        finish();
    }

    private void pageIncident(){
        Intent intent = new Intent(this, Incident.class);
        startActivity(intent);
        finish();
    }

    private void pageScanQR(){
        return;
    }


}