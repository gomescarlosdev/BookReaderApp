package br.com.poo2.bookreaderapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.poo2.bookreaderapp.R;
import br.com.poo2.bookreaderapp.databinding.ActivitySignInBinding;
import br.com.poo2.bookreaderapp.model.User;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 101;
    private ActivitySignInBinding binding;

    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth auth;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            goToNextActivity(auth.getCurrentUser());
        }

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        binding.buttonSignWithGoogle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_sign_with_google){
            startActivityForResult(googleSignInClient.getSignInIntent(), RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult();
            authWithGoogle(account.getIdToken());
        }
    }

    private void authWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        User user = new User(
                                firebaseUser.getUid(),
                                firebaseUser.getDisplayName(),
                                firebaseUser.getPhotoUrl().toString(),
                                firebaseUser.getEmail(),
                                null
                        );
                        database.getReference()
                                .child("Users")
                                .child(user.getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(this, MainActivity.class));
                                        finishAffinity();
                                    } else {
                                        Toast.makeText(
                                                this,
                                                task.getException().getLocalizedMessage(),
                                                Toast.LENGTH_LONG
                                        ).show();
                                    }
                                });
                        Log.e("profile", firebaseUser.getDisplayName());
                    } else {
                        Log.e("err", task.getException().getLocalizedMessage());
                    }
                });
    }

    private void goToNextActivity(FirebaseUser firebaseUser){

        DatabaseReference userRef = database.getReference()
                .child("Users")
                .child(firebaseUser.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user == null)
                    throw new RuntimeException("Google login failed!!");

//                if (user.getUserType() != null && user.getUserType().equals("admin")) {
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();
//                }else{
//                    startActivity(new Intent(SignInActivity.this, AdminDashBoardActivity.class));
//                    finish();
//                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}