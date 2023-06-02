package br.com.poo2.bookreaderapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.poo2.bookreaderapp.databinding.RecyclerViewHomeBooksBinding;
import br.com.poo2.bookreaderapp.model.Book;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeViewHolder> {
    private Context context;
    private List<Book> bookModelList;


    public HomeRecyclerViewAdapter(Context context, List<Book> bookModelList) {
        this.context = context;
        this.bookModelList = bookModelList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        RecyclerViewHomeBooksBinding binding = RecyclerViewHomeBooksBinding.inflate(layoutInflater, parent, false);
        return new HomeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        Book book = bookModelList.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return bookModelList.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {

        private RecyclerViewHomeBooksBinding binding;

        public HomeViewHolder(RecyclerViewHomeBooksBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Book book) {
            binding.imgBookCover.setImageResource(book.getCover());
        }
    }

}