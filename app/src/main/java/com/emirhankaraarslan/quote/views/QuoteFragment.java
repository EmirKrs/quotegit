package com.emirhankaraarslan.quote.views;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.room.Room;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.emirhankaraarslan.quote.R;
import com.emirhankaraarslan.quote.databinding.FragmentQuoteBinding;
import com.emirhankaraarslan.quote.model.Quote;
import com.emirhankaraarslan.quote.room.QuoteDao;
import com.emirhankaraarslan.quote.room.QuoteDatabase;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class QuoteFragment extends Fragment {
    private FragmentQuoteBinding binding;
    SQLiteDatabase database;
    String info = "";
    QuoteDatabase quoteDatabase;
    QuoteDao quoteDao;
    Quote quoteFromMain;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    public QuoteFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quoteDatabase = Room.databaseBuilder(requireContext(),
                QuoteDatabase.class,"Quotes").build();

        quoteDao = quoteDatabase.quoteDao();
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.add_quote).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQuoteBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = requireActivity().openOrCreateDatabase("Quotes", Context.MODE_PRIVATE, null);

        if (getArguments() != null){
            info = QuoteFragmentArgs.fromBundle(getArguments()).getControl();
        }
        else {
            info = "new";
        }

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);
            }
        });

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(v);
            }
        });

        if (info.equals("new")){
            binding.quotePlainText.setText("");
            binding.authorPlainText.setText("");
            binding.bookPlainText.setText("");
            binding.saveButton.setVisibility(View.VISIBLE);
            binding.deleteButton.setVisibility(View.GONE);


        }
        else {
            int quoteId = QuoteFragmentArgs.fromBundle(getArguments()).getQuoteId();

            binding.saveButton.setVisibility(View.GONE);
            binding.deleteButton.setVisibility(View.VISIBLE);

            binding.quotePlainText.setEnabled(false);
            binding.authorPlainText.setEnabled(false);
            binding.bookPlainText.setEnabled(false);

            compositeDisposable.add(quoteDao.getArtById(quoteId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(QuoteFragment.this::handleResponseWithOldQuote));
        }

    }


    public void save(View view){
        String quoteName = binding.quotePlainText.getText().toString();
        String authorName = binding.authorPlainText.getText().toString();
        String bookName = binding.bookPlainText.getText().toString();

        if (quoteName.equals("")){
            Toast.makeText(requireContext(), "Please Enter Quote", Toast.LENGTH_LONG).show();
        }
        else {
            Quote quote = new Quote(quoteName,authorName,bookName);

            compositeDisposable.add(quoteDao.insert(quote)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(QuoteFragment.this::handleResponse));

            Toast.makeText(requireContext(), "Quote Saved", Toast.LENGTH_SHORT).show();
        }

    }

    public void delete(View view){
        compositeDisposable.add(quoteDao.delete(quoteFromMain)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(QuoteFragment.this::handleResponse));

        Toast.makeText(requireContext(), "Quote Deleted", Toast.LENGTH_SHORT).show();
    }

    public void handleResponseWithOldQuote(Quote quote){
        quoteFromMain = quote;
        binding.quotePlainText.setText(quote.quoteName);
        binding.authorPlainText.setText(quote.authorName);
        binding.bookPlainText.setText(quote.bookName);

    }
    public void handleResponse(){
        NavDirections action = QuoteFragmentDirections.actionQuoteFragmentToHomeFragment();
        Navigation.findNavController(requireView()).navigate(action);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        compositeDisposable.clear();
    }
}