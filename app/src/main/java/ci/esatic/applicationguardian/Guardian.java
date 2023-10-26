package ci.esatic.applicationguardian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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


    }
}