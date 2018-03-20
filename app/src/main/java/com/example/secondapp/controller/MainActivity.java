package com.example.secondapp.controller;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.secondapp.controller.fragments.AboutUsActivity;
import com.example.secondapp.R;
import com.example.secondapp.controller.fragments.CarsByBranches;
import com.example.secondapp.controller.fragments.showAllBranches;
import com.example.secondapp.models.backend.Consts;
import com.example.secondapp.models.entities.Client;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout draw;
    private ActionBarDrawerToggle mToogle;
    private NavigationView nevigation;
    private TextView userName;
    private TextView email;
    private Client client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setupDrawerContent(nevigation);

    }

    private void setupDrawerContent(NavigationView navigationV) {
        navigationV.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.aboutUs:
                fragmentClass = AboutUsActivity.class;
                break;
            case R.id.branches:
                fragmentClass = CarsByBranches.class;
                break;
            case R.id.allBranches:
                fragmentClass = showAllBranches.class;
                break;


            default:
                fragmentClass = null;
        }

        if (fragmentClass != null) {
            try {

                fragment = (Fragment)fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fargment, fragment).commit();
            menuItem.setChecked(true);
            setTitle(menuItem.getTitle());
            draw.closeDrawers();
        }
    }

    private void findViews()
    {
        draw = (DrawerLayout)findViewById(R.id.drawer);
        nevigation = (NavigationView)findViewById(R.id.nevigation);
        mToogle = new ActionBarDrawerToggle(this,draw,R.string.open,R.string.close);
        draw.addDrawerListener(mToogle);
        mToogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userName =(TextView)findViewById(R.id.userName);
        email = (TextView)findViewById(R.id.userMail);
        new AsyncTask<Void,Void,Client>()
        {
            private ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new  ProgressDialog(MainActivity.this);
                dialog.setMessage("please wait ...");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected void onPostExecute(Client client) {
                super.onPostExecute(client);
                if(client != null)//need change to if(client != null && client != -1)
                {
                    userName.setText(client.getFname()+" " + client.getLname());
                    email.setText(client.getEmail());
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Have a problem to login....", Toast.LENGTH_LONG).show();
                }



            }

            @Override
            protected Client doInBackground(Void... voids) {
                try{
                    SharedPreferences sharedPreferences = getSharedPreferences("LoginActivity", MODE_PRIVATE);
                    client = Consts.getMyClient(sharedPreferences.getLong("password",-1));
                    return client;
                }
                catch (Exception error)
                {
                    return null;
                }
            }
        }.execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToogle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
