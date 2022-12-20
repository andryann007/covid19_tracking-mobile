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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    private FirebaseAuth firebaseAuth;

    private DrawerLayout drawerLayout;

    TextView textName, textEmail;

    private ArrayList<String> queryArrayList;

    String [] searchQueryType = {"Continent", "Country"};
    String [] sortQueryType = {"Cases", "Deaths", "Recovered"};

    private String selectedQuery;

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

    /*private void setDropdownSearchText(){
        queryArrayList.clear();
        queryArrayList.add(Arrays.toString(searchQueryType));

        String [] queryArray = new String [2];

        System.arraycopy(searchQueryType, 0, queryArray, 0, 2);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Select Search Type")
                .setItems(queryArray, (dialog, which) -> {
                    selectedQuery = queryArrayList.get(which);

                    searchType.setText(selectedQuery);
                }).show();
    }

    private void setDropdownSortType(){
        queryArrayList.clear();
        queryArrayList.add(Arrays.toString(searchQueryType));

        String [] queryArray = new String [2];

        System.arraycopy(searchQueryType, 0, queryArray, 0, 2);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Select Search Type")
                .setItems(queryArray, (dialog, which) -> {
                    selectedQuery = queryArrayList.get(which);

                    sortType.setText(selectedQuery);
                }).show();
    }

    private void setDropdownSortBy(){
        queryArrayList.clear();
        queryArrayList.add(Arrays.toString(sortQueryType));

        String [] queryArray = new String [3];

        System.arraycopy(sortQueryType, 0, queryArray, 0, 3);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Select Sort By")
                .setItems(queryArray, (dialog, which) -> {
                    selectedQuery = queryArrayList.get(which);

                    sortBy.setText(selectedQuery);
                }).show();
    }*/

    private void dialogSearch() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_search, null);

        TextView searchType = v.findViewById(R.id.textSearchType);
        EditText inputSearch = v.findViewById(R.id.textSearchBy);
        Button btnSearch = v.findViewById(R.id.btnSearch);

        builder.setView(v);

        AlertDialog dialogSearch = builder.create();
        if (dialogSearch.getWindow() != null) {
            dialogSearch.getWindow().setBackgroundDrawable(new ColorDrawable(0));

            btnSearch.setOnClickListener(view -> doSearch(inputSearch.getText().toString()));

            inputSearch.setOnEditorActionListener((v1, actionid, event) -> {
                if (actionid == EditorInfo.IME_ACTION_GO) {
                    //searchType.setOnClickListener(view -> setDropdownSearchText());
                    doSearch(inputSearch.getText().toString());
                }
                return false;
            });
            dialogSearch.show();
        }
    }

    private void doSearch(String query) {
        if(query.isEmpty()){
            Toast.makeText(MainActivity.this,"Tidak ada inputan!!!", Toast.LENGTH_SHORT).show();
        }
        /*if(searchType.getText().toString().isEmpty()){
            Toast.makeText(MainActivity.this,"Harap pilih tipe search!!!", Toast.LENGTH_SHORT).show();
        } else if (searchType.getText().toString().equals("Continent")){
            Intent i = new Intent(MainActivity.this, SearchContinentsActivity.class);
            i.putExtra("searchType", searchType.getText().toString().toLowerCase());
            i.putExtra("searchQuery", query);
            startActivity(i);
        } else if (searchType.getText().toString().equals("Country")){
            Intent i = new Intent(MainActivity.this, SearchCountryActivity.class);
            i.putExtra("searchType", searchType.getText().toString().toLowerCase());
            i.putExtra("searchQuery", query);
            startActivity(i);
        }*/
    }

    private void dialogSort() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_sort, null);

        Button btnSearch = v.findViewById(R.id.btnSort);
        TextView sortType = v.findViewById(R.id.textSortType);
        TextView sortBy = v.findViewById(R.id.textSortBy);

        builder.setView(v);

        AlertDialog dialogSort = builder.create();
        if (dialogSort.getWindow() != null) {
            dialogSort.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            //sortType.setOnClickListener(view -> setDropdownSortType());
            //sortBy.setOnClickListener(view -> setDropdownSortBy());
            btnSearch.setOnClickListener(view -> doSort(sortBy.getText().toString()));

            dialogSort.show();
        }
    }

    private void doSort(String query) {
        if(query.isEmpty()){
            Toast.makeText(MainActivity.this,"Tidak ada inputan!!!", Toast.LENGTH_SHORT).show();
        }
        /*if(sortType.getText().toString().isEmpty()){
            Toast.makeText(MainActivity.this,"Harap pilih tipe search!!!", Toast.LENGTH_SHORT).show();
        } else if (sortType.getText().toString().equals("Continent")){
            Intent i = new Intent(MainActivity.this, SortContinentsActivity.class);
            i.putExtra("sortType", sortType.getText().toString().toLowerCase());
            i.putExtra("sortQuery", query);
            startActivity(i);
        } else if (sortType.getText().toString().equals("Country")){
            Intent i = new Intent(MainActivity.this, SortCountriesActivity.class);
            i.putExtra("sortType", sortType.getText().toString().toLowerCase());
            i.putExtra("sortQuery", query);
            startActivity(i);
        } */
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