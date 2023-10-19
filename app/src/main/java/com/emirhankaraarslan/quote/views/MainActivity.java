package com.emirhankaraarslan.quote.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.emirhankaraarslan.quote.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_quote){
            HomeFragmentDirections.ActionHomeFragmentToQuoteFragment action = HomeFragmentDirections.actionHomeFragmentToQuoteFragment("new");
            Navigation.findNavController(this, R.id.fragment).navigate(action);

        }
        return super.onOptionsItemSelected(item);
    }


}