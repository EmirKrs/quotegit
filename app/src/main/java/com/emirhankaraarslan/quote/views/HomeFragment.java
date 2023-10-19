package com.emirhankaraarslan.quote.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emirhankaraarslan.quote.adapter.QuoteAdapter;
import com.emirhankaraarslan.quote.databinding.FragmentHomeBinding;
import com.emirhankaraarslan.quote.model.Quote;
import com.emirhankaraarslan.quote.room.QuoteDao;
import com.emirhankaraarslan.quote.room.QuoteDatabase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    QuoteAdapter quoteAdapter;
    QuoteDatabase quoteDatabase;
    QuoteDao quoteDao;
    private final CompositeDisposable mDisposable = new CompositeDisposable();


    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        quoteDatabase = Room.databaseBuilder(requireContext(),
                QuoteDatabase.class,"Quotes").build();

        quoteDao = quoteDatabase.quoteDao();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        setMenuVisibility(false);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);

        getData();
    }


    public void getData(){
        mDisposable.add(quoteDao.getArtWithNameAndId()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(HomeFragment.this::handleResponse));
    }

    public void handleResponse(List<Quote> quoteList){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        quoteAdapter = new QuoteAdapter(quoteList);
        binding.recyclerView.setAdapter(quoteAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        mDisposable.clear();
    }
}