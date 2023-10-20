package com.emirhankaraarslan.quote.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.emirhankaraarslan.quote.R;
import com.emirhankaraarslan.quote.databinding.RecyclerRowBinding;
import com.emirhankaraarslan.quote.model.Quote;
import com.emirhankaraarslan.quote.views.HomeFragmentDirections;

import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Quote> quoteList;

    //Bunlar yerin enum kullanılabilir
    private static final int typeA = 0;
    private static final int typeB = 1;
    private static final int typeC = 2;

    public TextView recyclerQuoteText;
    public TextView recyclerAuthorText;
    public TextView recyclerBookText;


    public QuoteAdapter(List<Quote> quoteList){
        this.quoteList = quoteList;
    }

    public class QuoteHolder extends RecyclerView.ViewHolder{
        public QuoteHolder(@NonNull View itemView) {
            super(itemView);

            recyclerQuoteText = (TextView) itemView.findViewById(R.id.recyclerViewQuoteText);
            recyclerAuthorText = (TextView) itemView.findViewById(R.id.recyclerViewAuthorText);
            recyclerBookText = (TextView) itemView.findViewById(R.id.recyclerViewBookText);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 3 == 0)
            return typeA;
        else if (position % 3 == 1)
            return typeB;
        else
            return typeC;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType){
            case typeA:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row2,parent,false);
                break;
            case typeB:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row,parent,false);
                break;
            case typeC:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row3,parent,false);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row,parent,false);
        }

        return new QuoteHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        recyclerQuoteText.setText("''" + quoteList.get(position).quoteName + "''");
        recyclerAuthorText.setText(quoteList.get(position).authorName);
        recyclerBookText.setText(quoteList.get(position).bookName);

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
