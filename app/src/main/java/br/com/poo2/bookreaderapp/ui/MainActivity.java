package br.com.poo2.bookreaderapp.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.UUID;

import br.com.poo2.bookreaderapp.R;
import br.com.poo2.bookreaderapp.databinding.ActivityMainBinding;
import br.com.poo2.bookreaderapp.ui.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    private FirebaseDatabase database;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame_layout, new HomeFragment())
                .commit();

//        binding.buttonChooseFile.setOnClickListener(this);
//        binding.buttonFileUpload.setOnClickListener(this);
        binding.homeBottomNavView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_item_home) {
                replaceFragment(new HomeFragment());
            }
            if (item.getItemId() == R.id.menu_item_exit) {
                finish();
            }
            return true;
        });
    }

    @Override
    public void onClick(View view) {
//        if(view.getId() == R.id.menu_item_home){
//
//        }
//        if(view.getId() == R.id.menu_item_library){
//
//        }
//        if(view.getId() == R.id.menu_item_favorites){
//
//        }
//        if(view.getId() == R.id.menu_item_exit){
//
//        }
//        if (view.getId() == R.id.button_choose_file) {
//
//        }
//        if (view.getId() == R.id.button_file_upload) {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("application/epub+zip");
//            filePickerLauncher.launch(intent);
//        }
    }

    private final ActivityResultLauncher<Intent> filePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null && data.getData() != null) {
                                Uri fileUri = data.getData();
                                uploadFile(fileUri);
                            }
                        }
                    });

    private void uploadFile(Uri fileUri) {
        String fileName = getFileName(fileUri);
        String bookUniqueId = getUniqueID();

        StorageReference fileRef = storage.getReference()
                .child("library/".concat(fileName));

        UploadTask uploadTask = (UploadTask) fileRef.putFile(fileUri)
                .addOnSuccessListener(taskSnapshot ->
                        Toast.makeText(this, "Arquivo enviado com sucesso!", Toast.LENGTH_SHORT).show()
                ).addOnFailureListener(e ->
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show()
                );

//        uploadTask.addOnSuccessListener(taskSnapshot ->
//                fileRef.getDownloadUrl().addOnSuccessListener(downloadUri ->
//                        database.getReference()
//                        .child("Library")
//                        .child(bookUniqueId)
//                        .setValue(new BookFileRef(fileName, downloadUri.toString()))
//                        .addOnSuccessListener(aVoid ->
//                                Toast.makeText(this, "Arquivo enviado com sucesso!", Toast.LENGTH_SHORT).show()
//                        )
//                        .addOnFailureListener(e -> Toast.makeText(
//                                this, "Erro ao enviar o arquivo: " + e.getMessage(), Toast.LENGTH_SHORT).show()
//                        ))).addOnFailureListener(e -> Toast.makeText(
//                this, "Erro ao enviar o arquivo: " + e.getMessage(), Toast.LENGTH_SHORT).show()
//        );
    }

    private String getFileName(Uri fileUri) {
        String fileName = null;
        if (fileUri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(fileUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                fileName = cursor.getString(nameIndex);
                Log.i("nome do arquivo: ", fileName);
                cursor.close();
            }
        } else if (fileUri.getScheme().equals("file")) {
            fileName = new File(fileUri.getPath()).getName();
        }
        return fileName;
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
