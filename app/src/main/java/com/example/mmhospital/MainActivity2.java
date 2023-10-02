package com.example.mmhospital;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mmhospital.databinding.ActivityMain2Binding;
import com.squareup.picasso.Picasso;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    TextView name,email;

    ImageView img;

    SharedPreferences sh;
    private ActivityMain2Binding binding;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());



        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        View headerView=navigationView.getHeaderView(0);

        name = headerView.findViewById(R.id.textView60);
        email = headerView.findViewById(R.id.textView61);
        img = headerView.findViewById(R.id.imageView);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        name.setText(sh.getString("p_name",""));
        email.setText(sh.getString("email",""));

//        String image = jsonObj.getString("photo");
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":8000" + sh.getString("photo", "");;
        Picasso.with(getApplicationContext()).load(url).transform(new CircleTransform()).into(img);//circle



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
//        Toast.makeText(this, "Hhiiiiiiiiiii", Toast.LENGTH_SHORT).show();
        if(id==R.id.nav_home){
            Intent i=new Intent(getApplicationContext(),MainActivity2.class);
            startActivity(i);
        } else if (id==R.id.update) {
            Intent i=new Intent(getApplicationContext(),update_profile.class);
            startActivity(i);
            
        }
        else if (id==R.id.schedule) {
            Intent i=new Intent(getApplicationContext(),view_schedule.class);
            startActivity(i);

        }
        else if (id==R.id.booking) {
            Intent i=new Intent(getApplicationContext(),view_booking.class);
            startActivity(i);

        }
        else if (id==R.id.doctor) {
            Intent i=new Intent(getApplicationContext(),view_doctor.class);
            startActivity(i);

        }

        else if (id==R.id.changepass) {
            Intent i=new Intent(getApplicationContext(),change_password.class);
            startActivity(i);

        }
        else if (id==R.id.logout) {
            Intent i=new Intent(getApplicationContext(),login.class);
            startActivity(i);

        }

        return true;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),MainActivity2.class);
        startActivity(i);
    }
}