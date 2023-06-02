package br.com.poo2.bookreaderapp.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;

import br.com.poo2.bookreaderapp.R;
import br.com.poo2.bookreaderapp.databinding.FragmentHomeBinding;
import br.com.poo2.bookreaderapp.model.Book;
import br.com.poo2.bookreaderapp.ui.adapters.HomeRecyclerViewAdapter;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayList<Book> commonBookLesson = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        setUpCommonBookLessons();

        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(requireContext(), commonBookLesson);
        binding.recyclerViewHomeBooks.setAdapter(adapter);
        binding.recyclerViewHomeBooks.setLayoutManager(new GridLayoutManager(requireContext(), 4));

        return binding.getRoot();
    }

    private void setUpCommonBookLessons() {
        String[] bookTitles = getResources().getStringArray(R.array.book_titles);

        for (String bookTitle : bookTitles) {
            commonBookLesson.add(new Book(bookTitle, "Tolkien", R.drawable.cover_lord_of_the_rings));
            commonBookLesson.add(new Book(bookTitle, "Tolkien", R.drawable.cover_game_of_thrones));
            commonBookLesson.add(new Book(bookTitle, "Tolkien", R.drawable.cover_the_name_of_the_wind));
            commonBookLesson.add(new Book(bookTitle, "Tolkien", R.drawable.cover_the_wheel_of_time));
        }
    }
}
