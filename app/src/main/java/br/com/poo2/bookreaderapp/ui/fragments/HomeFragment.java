package br.com.poo2.bookreaderapp.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import br.com.poo2.bookreaderapp.R;
import br.com.poo2.bookreaderapp.databinding.FragmentHomeBinding;
import br.com.poo2.bookreaderapp.model.BookModel;
import br.com.poo2.bookreaderapp.model.BookModelItem;
import br.com.poo2.bookreaderapp.retrofit.ApiService;
import br.com.poo2.bookreaderapp.retrofit.RetrofitClient;
import br.com.poo2.bookreaderapp.ui.adapters.HomeRecyclerViewAdapter;
import br.com.poo2.bookreaderapp.utils.AppSharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<BookModelItem> bookModelItems = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        ApiService apiService = RetrofitClient.getApiService();

        AppSharedPreferences appSharedPreferences = new AppSharedPreferences(requireContext());
        String userId = appSharedPreferences.getStoredString("USER_ID");

        if(userId == null){
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            userId = firebaseUser.getUid();
        }


        Call<List<BookModel>> call = apiService.getPDFFiles(userId);
        call.enqueue(new Callback<List<BookModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<BookModel>> call, @NonNull Response<List<BookModel>> response) {
                if (response.isSuccessful()) {
                    List<BookModel> pdfFiles = response.body();
                    setUpCommonBookLessons(pdfFiles);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<BookModel>> call, @NonNull Throwable t) {

            }
        });

        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(requireContext(), bookModelItems);
        binding.recyclerViewHomeBooks.setAdapter(adapter);
        binding.recyclerViewHomeBooks.setLayoutManager(new GridLayoutManager(requireContext(), 4));

        return binding.getRoot();
    }

    private void setUpCommonBookLessons(List<BookModel> pdfFiles) {

        for (BookModel bookModel : pdfFiles) {
            bookModelItems.add(new BookModelItem(
                    bookModel.getId(),
                    bookModel.getCustomerId(),
                    bookModel.getFileName(),
                    bookModel.getFileData(),
                    R.drawable.ic_pdf)
            );
        }

    }
}
