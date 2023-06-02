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

import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.UUID;

import br.com.poo2.bookreaderapp.R;
import br.com.poo2.bookreaderapp.databinding.ActivityMainBinding;
import br.com.poo2.bookreaderapp.model.BookFile;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();

        binding.buttonChooseFile.setOnClickListener(this);
        binding.buttonFileUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_choose_file) {

        }
        if (view.getId() == R.id.button_file_upload) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/epub+zip");
            filePickerLauncher.launch(intent);
        }
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
        // Obtém o nome do arquivo a partir do URI
        String fileName = getFileName(fileUri);

        database.getReference()
                .child("Library")
                .child(getUniqueID())
                .setValue(new BookFile(fileName, fileUri.toString()))
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Arquivo enviado com sucesso!", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e -> Toast.makeText(
                        this, "Erro ao enviar o arquivo: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }


    private String getFileName(Uri fileUri) {
        // Extrai o nome do arquivo a partir do URI
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


}
