package br.com.poo2.bookreaderapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import br.com.poo2.bookreaderapp.R;
import br.com.poo2.bookreaderapp.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityWelcomeBinding binding;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonGetStarted.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            goToNextActivity();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_get_started){
            goToNextActivity();
        }
    }

    private void goToNextActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }
}