package com.blstream.studybox.components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.blstream.studybox.R;
import com.blstream.studybox.activities.ExamActivity;
import com.blstream.studybox.activities.LoginActivity;
import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.database.DataHelper;
import com.blstream.studybox.debugger.DebugHelper;
import com.blstream.studybox.auth.login.LoginManager;
import com.blstream.studybox.model.database.Decks;

import java.util.List;

import retrofit.RetrofitError;

public class DrawerAdapter implements NavigationView.OnNavigationItemSelectedListener, RequestListener<String> {

    //TODO
    //Delete Toast messages after providing better tests for drawer

    private static final int HEADER_INDEX = 0;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private Context context;
    private Activity activity;
    private LoginManager login;
    private ActionBarDrawerToggle drawerToggle;
    private Intent intent;
    private List<Decks> randomDeck;
    private DataHelper dataHelper = new DataHelper();

    public DrawerAdapter(Context context, NavigationView navigationView, DrawerLayout drawerLayout, Toolbar toolbar) {
        this.context = context;
        this.navigationView = navigationView;
        this.drawerLayout = drawerLayout;
        this.toolbar = toolbar;
        try {
            this.activity = (Activity)context;
        } catch(ClassCastException e){
            DebugHelper.logException(e, "Unable to cast context to Activity object type", "CastException");
        }
        this.login = new LoginManager(context);
    }

    public void attachDrawer() {
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().findItem(R.id.login).setVisible(!login.isUserLoggedIn());
        navigationView.getMenu().findItem(R.id.logout).setVisible(login.isUserLoggedIn());
        navigationView.getMenu().findItem(R.id.my_account).setVisible(login.isUserLoggedIn());
        navigationView.getMenu().findItem(R.id.my_decks).setVisible(login.isUserLoggedIn());

        TextView userName = (TextView) navigationView.getHeaderView(HEADER_INDEX).findViewById(R.id.user_name);
        TextView userEmail = (TextView) navigationView.getHeaderView(HEADER_INDEX).findViewById(R.id.user_email);
        if (userName != null) {
            userName.setText(login.getUserName());
        }
        if (userEmail != null) {
            userEmail.setText(login.getUserEmail());
        }

        drawerToggle = new ActionBarDrawerToggle(
                activity, drawerLayout, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    public void detachDrawer() {
        drawerLayout.removeDrawerListener(drawerToggle);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);

        int id = item.getItemId();
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (id) {
            case R.id.login:
                intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(intent);
                activity.finish();
                break;
            case R.id.my_account:
                break;
            case R.id.my_decks:
                break;
            case R.id.create_deck:
                break;
            case R.id.show_deck:
                dataHelper.downloadRandomDeck(this);
                break;
            case R.id.statistics:
                break;
            case R.id.logout:
                login.deleteUser();
                intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(intent);
                activity.finish();
                break;
        }

        // Only for testing
        Toast.makeText(context, "Selected: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onSuccess(String response) {
        randomDeck = dataHelper.getRandomDeck();
        Log.d("TAG", String.valueOf(randomDeck.get(0).getName()));
    }

    @Override
    public void onFailure(RetrofitError error) {
        Log.d("TAG", "ERROR");
    }
}
