package br.com.poo2.bookreaderapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import br.com.poo2.bookreaderapp.R;
import br.com.poo2.bookreaderapp.databinding.ActivityAdminDashBoardBinding;
import br.com.poo2.bookreaderapp.model.CategoryModel;
import br.com.poo2.bookreaderapp.ui.adapters.AdminDashBoardAdapter;

public class AdminDashBoardActivity extends AppCompatActivity {

    private ActivityAdminDashBoardBinding binding;
    private List<CategoryModel> categoryModels = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpCategoryModels();

        binding.recyclerViewAdminActions.setAdapter(new AdminDashBoardAdapter(this, categoryModels));
        binding.recyclerViewAdminActions.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpCategoryModels() {
        String[] categoriesTitles = getResources().getStringArray(R.array.recycler_categories);

        for(String i : categoriesTitles){
            categoryModels.add(new CategoryModel(i));
        }
    }

}