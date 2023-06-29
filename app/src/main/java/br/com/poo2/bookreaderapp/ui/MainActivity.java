package br.com.poo2.bookreaderapp.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.installations.remote.TokenResult;

import java.io.File;
import java.util.UUID;

import br.com.poo2.bookreaderapp.R;
import br.com.poo2.bookreaderapp.databinding.ActivityMainBinding;
import br.com.poo2.bookreaderapp.retrofit.ApiService;
import br.com.poo2.bookreaderapp.retrofit.RetrofitClient;
import br.com.poo2.bookreaderapp.ui.fragments.HomeFragment;
import br.com.poo2.bookreaderapp.utils.AppSharedPreferences;
import br.com.poo2.bookreaderapp.utils.FileUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_PDF_FILE = 1;
    private static final int REQUEST_PERMISSION = 2;

    private ActivityMainBinding binding;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService = RetrofitClient.getApiService();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame_layout, new HomeFragment())
                .commit();

        binding.fabFindNewCall.setOnClickListener(this);
        binding.homeBottomNavView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_item_home) {
                replaceFragment(new HomeFragment());
            }
            if (item.getItemId() == R.id.menu_item_exit) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
            return true;
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_find_new_call) {
            requestPermissionAndOpenFilePicker();
        }
    }

    private void requestPermissionAndOpenFilePicker() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        } else {
            openFilePicker();
        }
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, REQUEST_PDF_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PDF_FILE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                String filePath = getFilePathFromUri(uri);
                if (filePath != null) {
                    File file = new File(filePath);
                    if (file.exists()) {
                        uploadFile(file);
                    }
                }
            }
        }
    }

    private String getFilePathFromUri(Uri uri) {
        return FileUtils.getFilePathFromUri(this, uri);
    }

    private void uploadFile(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/pdf"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        AppSharedPreferences appSharedPreferences = new AppSharedPreferences(this);
        String userId = appSharedPreferences.getStoredString("USER_ID");

        Call<TokenResult.ResponseCode> call = apiService.uploadFile(userId, filePart);
        call.enqueue(new Callback<TokenResult.ResponseCode>() {
            @Override
            public void onResponse(@NonNull Call<TokenResult.ResponseCode> call, @NonNull Response<TokenResult.ResponseCode> response) {
                Toast.makeText(MainActivity.this, "Upload realizado com sucesso!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(@NonNull Call<TokenResult.ResponseCode> call, @NonNull Throwable t) { }
        });
    }

    private String getUniqueID() {
        return UUID.randomUUID().toString();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
