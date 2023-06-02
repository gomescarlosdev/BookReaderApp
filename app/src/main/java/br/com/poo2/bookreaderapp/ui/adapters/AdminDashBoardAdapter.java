package br.com.poo2.bookreaderapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.poo2.bookreaderapp.R;
import br.com.poo2.bookreaderapp.databinding.RecyclerViewAdminActionsBinding;
import br.com.poo2.bookreaderapp.model.CategoryModel;

public class AdminDashBoardAdapter extends RecyclerView.Adapter<AdminDashBoardAdapter.HomeViewHolder> {
    private Context context;
    private List<CategoryModel> lessonModelList;


    public AdminDashBoardAdapter(Context context, List<CategoryModel> categoryModelList) {
        this.context = context;
        this.lessonModelList = categoryModelList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        RecyclerViewAdminActionsBinding binding = RecyclerViewAdminActionsBinding.inflate(layoutInflater, parent, false);
        return new HomeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        CategoryModel lesson = lessonModelList.get(position);
        holder.bind(lesson);
    }

    @Override
    public int getItemCount() {
        return lessonModelList.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {

        private RecyclerViewAdminActionsBinding binding;
//        private ImageView imageView;
//        private TextView textActionTitle;
//        private TextView textLessonType;

        public HomeViewHolder(RecyclerViewAdminActionsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
//            textActionTitle = binding.textActionTitle;
        }

        public void bind(CategoryModel lesson) {
            binding.imgCategories.setImageResource(R.drawable.ic_category_options);
            binding.textActionTitle.setText(lesson.getTitle());
        }
    }

}