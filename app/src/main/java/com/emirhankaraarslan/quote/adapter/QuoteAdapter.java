package com.emirhankaraarslan.quote.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.emirhankaraarslan.quote.databinding.RecyclerRowBinding;
import com.emirhankaraarslan.quote.model.Quote;
import com.emirhankaraarslan.quote.views.HomeFragmentDirections;

import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.QuoteHolder> {

    List<Quote> quoteList;

    public QuoteAdapter(List<Quote> quoteList){
        this.quoteList = quoteList;
    }



    public class QuoteHolder extends RecyclerView.ViewHolder{
        private RecyclerRowBinding binding;
        public QuoteHolder(RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    @NonNull
    @Override
    public QuoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new QuoteHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.recyclerViewQuoteText.setText("''" + quoteList.get(position).quoteName + "''");
        holder.binding.recyclerViewAuthorText.setText(quoteList.get(position).authorName);
        holder.binding.recyclerViewBookText.setText(quoteList.get(position).bookName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragmentDirections.ActionHomeFragmentToQuoteFragment action = HomeFragmentDirections.actionHomeFragmentToQuoteFragment("old");
                action.setQuoteId(quoteList.get(position).id);
                action.setControl("old");
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quoteList.size();
    }


}
