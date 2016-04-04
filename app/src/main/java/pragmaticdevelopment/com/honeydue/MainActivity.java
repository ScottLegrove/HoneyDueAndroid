package pragmaticdevelopment.com.honeydue;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
            ListFragment.OnFragmentInteractionListener,
            AddListFragment.OnFragmentInteractionListener,
            UserSettingsFragment.OnFragmentInteractionListener,
            AlertDialogNewListFragment.newListDialogListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("Lists");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ListFragment lf = ListFragment.newInstance();
        ft.add(R.id.fragFrame, lf, "lists" );
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_list) {
            AlertDialogNewListFragment aDialogNewListFragment = new AlertDialogNewListFragment();
            aDialogNewListFragment.show(getSupportFragmentManager(), "addList");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        // Get currently loaded fragment
        Fragment f = fm.findFragmentById(R.id.fragFrame);

        // Check to ensure fragment isn't currently loaded
        if (id == R.id.nav_lists && !(f instanceof ListFragment)) {
            getSupportActionBar().setTitle("Lists");
            ListFragment lf = ListFragment.newInstance();
            ft.replace(R.id.fragFrame, lf, "lists");
        } else if (id == R.id.nav_settings && !(f instanceof UserSettingsFragment)) {
            getSupportActionBar().setTitle("User Settings");
            UserSettingsFragment usf = UserSettingsFragment.newInstance();
            ft.replace(R.id.fragFrame, usf, "userSettings");
        }

        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDialogPositiveClick(AlertDialogNewListFragment dialog){

    }

    @Override
    public void onDialogNegativeClick(AlertDialogNewListFragment dialog){

    }
}
