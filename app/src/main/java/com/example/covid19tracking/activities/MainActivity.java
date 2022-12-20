package com.example.covid19tracking.activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.covid19tracking.R;
import com.example.covid19tracking.databinding.ActivityMainBinding;
import com.example.covid19tracking.ui.GlobalFragment;
import com.example.covid19tracking.ui.HomeFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    private FirebaseAuth firebaseAuth;

    private DrawerLayout drawerLayout;

    TextView textName, textEmail;

    private String searchType = null;

    private String sortType = null;

    private String sortBy = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        loadUserInfo();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;

            case R.id.nav_global:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GlobalFragment()).commit();
                break;

            case R.id.nav_logout:
                firebaseAuth.signOut();
                Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(logoutIntent);
                finish();
                break;
        }
        return true;
    }

    private void loadUserInfo() {
        Log.d(TAG, "loadUserInfo: Loading user "+firebaseAuth.getUid());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(Objects.requireNonNull(firebaseAuth.getUid()))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //get all info of user here from snapshot
                        String email = ""+snapshot.child("email").getValue();
                        String name = ""+snapshot.child("name").getValue();
                        String profileImage = ""+snapshot.child("profileImage").getValue();

                        //set data to ui
                        CircleImageView image_profile = findViewById(R.id.imageProfile);
                        textName = findViewById(R.id.textProfileName);
                        textEmail = findViewById(R.id.textProfileEmail);

                        textName.setText(name);
                        textEmail.setText(email);
                        if(profileImage.equals("") || profileImage.equals("2131165357")){
                            image_profile.setImageResource(R.drawable.ic_account);
                        } else {
                            Uri photo_url = Uri.parse(profileImage);
                            Picasso.get().load(photo_url).into(image_profile);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void dialogSearch() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_search, null);

        EditText inputSearch = v.findViewById(R.id.textSearchBy);
        Button btnSearch = v.findViewById(R.id.btnSearch);
        RadioGroup radioSearchType = v.findViewById(R.id.radioGroupSearchType);
        RadioButton radioSearchContinents = v.findViewById(R.id.radioSearchContinents);
        RadioButton radioSearchCountries = v.findViewById(R.id.radioSearchCountries);

        builder.setView(v);

        AlertDialog dialogSearch = builder.create();
        if (dialogSearch.getWindow() != null) {
            dialogSearch.getWindow().setBackgroundDrawable(new ColorDrawable(0));

            radioSearchType.setOnCheckedChangeListener((group, checkedId) -> {
                if(checkedId == R.id.radioSearchContinents){
                    searchType = radioSearchContinents.getText().toString();
                } else if (checkedId == R.id.radioSearchCountries){
                    searchType = radioSearchCountries.getText().toString();
                }
            });

            btnSearch.setOnClickListener(view -> doSearch(inputSearch.getText().toString()));

            inputSearch.setOnEditorActionListener((v1, actionid, event) -> {
                if (actionid == EditorInfo.IME_ACTION_GO) {
                    doSearch(inputSearch.getText().toString());
                }
                return false;
            });
            dialogSearch.show();
        }
    }

    private void doSearch(String query) {
        if(query.isEmpty()){
            Toast.makeText(getApplicationContext(),"Tidak ada inputan!!!", Toast.LENGTH_SHORT).show();
        }

        if(searchType.isEmpty()){
            Toast.makeText(getApplicationContext(),"Harap pilih tipe search!!!", Toast.LENGTH_SHORT).show();
        }

        else if (searchType.equals("Continents")){
            Intent i = new Intent(MainActivity.this, SearchActivity.class);
            i.putExtra("searchType", "continents");
            i.putExtra("searchQuery", query);
            startActivity(i);
        }

        else if (searchType.equals("Countries")){
            Intent i = new Intent(MainActivity.this, SearchActivity.class);
            i.putExtra("searchType", "countries");
            i.putExtra("searchQuery", query);
            startActivity(i);
        }
    }

    private void dialogSort() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_sort, null);

        Button btnSearch = v.findViewById(R.id.btnSort);
        RadioGroup radioSortGroupType = v.findViewById(R.id.radioGroupSortType);
        RadioButton radioSortContinents = v.findViewById(R.id.radioButtonSortContinents);
        RadioButton radioSortCountries = v.findViewById(R.id.radioButtonSortCountries);

        RadioGroup radioSortGroupBy1 = v.findViewById(R.id.radioGroup1SortBy);
        RadioButton radioSortByTotal = v.findViewById(R.id.radioButtonSortTotal);
        RadioButton radioSortByActive = v.findViewById(R.id.radioButtonSortActive);

        RadioGroup radioSortGroupBy2 = v.findViewById(R.id.radioGroup2SortBy);
        RadioButton radioSortByRecovered = v.findViewById(R.id.radioButtonSortRecovered);
        RadioButton radioSortByDeath = v.findViewById(R.id.radioButtonSortDeaths);

        builder.setView(v);

        AlertDialog dialogSort = builder.create();
        if (dialogSort.getWindow() != null) {
            dialogSort.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            radioSortGroupType.setOnCheckedChangeListener((group, checkedId) -> {
                if(checkedId == R.id.radioButtonSortContinents){
                    sortType = radioSortContinents.getText().toString().toLowerCase();
                } else if (checkedId == R.id.radioButtonSortCountries){
                    sortType = radioSortCountries.getText().toString().toLowerCase();
                }
            });

            radioSortGroupBy1.setOnCheckedChangeListener((group, checkedId) -> {
                if(checkedId == R.id.radioButtonSortTotal){
                    radioSortGroupBy2.setOnCheckedChangeListener(null);
                    sortBy = radioSortByTotal.getText().toString().toLowerCase().replace("total case", "cases");
                } else if (checkedId == R.id.radioButtonSortActive) {
                    radioSortGroupBy2.setOnCheckedChangeListener(null);
                    sortBy = radioSortByActive.getText().toString().toLowerCase().replace("active case", "actives");
                }
            });

            radioSortGroupBy2.setOnCheckedChangeListener((group, checkedId) -> {
                if (checkedId == R.id.radioButtonSortRecovered){
                    radioSortGroupBy1.setOnCheckedChangeListener(null);
                    sortBy = radioSortByRecovered.getText().toString().toLowerCase();
                } else if (checkedId == R.id.radioButtonSortDeaths){
                    radioSortGroupBy1.setOnCheckedChangeListener(null);
                    sortBy = radioSortByDeath.getText().toString().toLowerCase().replace("death", "deaths");
                }
            });

            btnSearch.setOnClickListener(view -> doSort(sortBy));
            dialogSort.show();
        }
    }

    private void doSort(String query) {
        if(query.isEmpty()){
            Toast.makeText(getApplicationContext(),"Harap pilih tipe continents / countries!!!", Toast.LENGTH_SHORT).show();
        }
        if(sortType.isEmpty()){
            Toast.makeText(getApplicationContext(),"Harap pilih jenis sorting data!!!", Toast.LENGTH_SHORT).show();
        } else if (sortType.equals("continents")){
            switch (query) {
                case "cases":
                    Intent sortContinentsByCase = new Intent(MainActivity.this, SortActivity.class);
                    sortContinentsByCase.putExtra("sortType", "continents");
                    sortContinentsByCase.putExtra("sortBy", "cases");
                    startActivity(sortContinentsByCase);
                    break;
                case "actives":
                    Intent sortContinentsByActive = new Intent(MainActivity.this, SortActivity.class);
                    sortContinentsByActive.putExtra("sortType", "continents");
                    sortContinentsByActive.putExtra("sortBy", "actives");
                    startActivity(sortContinentsByActive);
                    break;
                case "recovered":
                    Intent sortContinentsByRecovered = new Intent(MainActivity.this, SortActivity.class);
                    sortContinentsByRecovered.putExtra("sortType", "continents");
                    sortContinentsByRecovered.putExtra("sortBy", "recovered");
                    startActivity(sortContinentsByRecovered);
                    break;
                case "deaths":
                    Intent sortContinentsByDeath = new Intent(MainActivity.this, SortActivity.class);
                    sortContinentsByDeath.putExtra("sortType", "continents");
                    sortContinentsByDeath.putExtra("sortBy", "deaths");
                    startActivity(sortContinentsByDeath);
                    break;
            }

        } else if (sortType.equals("countries")){
            switch (query) {
                case "cases":
                    Intent sortCountryByCase = new Intent(MainActivity.this, SortActivity.class);
                    sortCountryByCase.putExtra("sortType", "countries");
                    sortCountryByCase.putExtra("sortBy", "cases");
                    startActivity(sortCountryByCase);
                    break;
                case "actives":
                    Intent sortCountryByActive = new Intent(MainActivity.this, SortActivity.class);
                    sortCountryByActive.putExtra("sortType", "countries");
                    sortCountryByActive.putExtra("sortBy", "actives");
                    startActivity(sortCountryByActive);
                    break;
                case "recovered":
                    Intent sortCountryByRecovered = new Intent(MainActivity.this, SortActivity.class);
                    sortCountryByRecovered.putExtra("sortType", "countries");
                    sortCountryByRecovered.putExtra("sortBy", "recovered");
                    startActivity(sortCountryByRecovered);
                    break;
                case "deaths":
                    Intent sortCountryByDeath = new Intent(MainActivity.this, SortActivity.class);
                    sortCountryByDeath.putExtra("sortType", "countries");
                    sortCountryByDeath.putExtra("sortBy", "deaths");
                    startActivity(sortCountryByDeath);
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.nav_search) {
            dialogSearch();
        } else if (item.getItemId() == R.id.nav_sort){
            dialogSort();
        }
        return super.onOptionsItemSelected(item);
    }
}