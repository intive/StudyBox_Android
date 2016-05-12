package com.blstream.studybox.components;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Transition;
import android.view.MenuItem;
import android.widget.TextView;

import com.blstream.studybox.R;
import com.blstream.studybox.activities.BaseExamActivity;
import com.blstream.studybox.activities.LoginActivity;
import com.blstream.studybox.auth.login.LoginManager;
import com.blstream.studybox.database.DataHelper;
import com.blstream.studybox.database.DataProvider;
import com.blstream.studybox.debugger.DebugHelper;
import com.blstream.studybox.model.database.Decks;

public class DrawerAdapter implements NavigationView.OnNavigationItemSelectedListener, DataProvider.OnDecksReceivedListener<Decks> {

    private static final int HEADER_INDEX = 0;
    private static final String TAG_IN_EXAM = "inExam";
    private static final String TAG_DECK_NAME = "deckName";
    private static final String TAG_DECK_ID = "deckId";
    private static final String TAG_IS_RANDOM_EXAM = "isRandomExam";

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private Context context;
    private Activity activity;
    private LoginManager login;
    private ActionBarDrawerToggle drawerToggle;
    private DataHelper dataHelper = new DataHelper(context);

    public DrawerAdapter(Context context, NavigationView navigationView, DrawerLayout drawerLayout, Toolbar toolbar) {
        this.context = context;
        this.navigationView = navigationView;
        this.drawerLayout = drawerLayout;
        this.toolbar = toolbar;
        try {
            this.activity = (Activity) context;
        } catch (ClassCastException e) {
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

    public void randomDeckDrawerItem(boolean state) {
        navigationView.getMenu().findItem(R.id.random_deck).setChecked(state);
    }

    public void detachDrawer() {
        drawerLayout.removeDrawerListener(drawerToggle);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent;
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
            case R.id.random_deck:
                dataHelper.fetchRandomDeck(this);
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

        return true;
    }

    @Override
    public void OnDecksReceived(Decks decks) {
        if (decks.getFlashcardsCount() != 0) {
            String deckId = decks.getDeckId();
            String deckName = decks.getName();
            startExam(deckId, deckName, true, true);
        }else{
            dataHelper.fetchRandomDeck(this);
        }
    }

    private void startExam(final String deckId, final String deckName, boolean isExam, boolean isRandomDeckExam) {
        Intent intent = new Intent(context, BaseExamActivity.class);
        intent.putExtra(TAG_DECK_ID, deckId);
        intent.putExtra(TAG_DECK_NAME, deckName);
        intent.putExtra(TAG_IN_EXAM, isExam);
        intent.putExtra(TAG_IS_RANDOM_EXAM, isRandomDeckExam);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpTransition();
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context);
            context.startActivity(intent, options.toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpTransition() {
        Activity activity = (Activity) context;
        Transition exitTrans = new Explode();
        activity.getWindow().setExitTransition(exitTrans);
    }
}
