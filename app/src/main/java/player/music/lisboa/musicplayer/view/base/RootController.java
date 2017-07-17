package player.music.lisboa.musicplayer.view.base;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerChangeType;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

import butterknife.BindView;
import player.music.lisboa.musicplayer.R;
import player.music.lisboa.musicplayer.view.library.LibraryController;

/**
 * Created by Lisboa on 17-Jul-17.
 */

public class RootController extends BaseController implements NavigationView.OnNavigationItemSelectedListener {

	private static final String TAG = "RootController";

	@BindView(R.id.controller_container) ViewGroup container;
	@BindView(R.id.toolbar) Toolbar toolbar;
	@BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
	@BindView(R.id.nav_view) NavigationView navigationView;

	private Router router;
	private ActionBarDrawerToggle drawerToggle;

	@Override
	protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		return inflater.inflate(R.layout.controller_root, container, false);
	}

	@Override
	protected void onChangeEnded(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerChangeType changeType) {
		super.onChangeEnded(changeHandler, changeType);
		if(changeType == ControllerChangeType.PUSH_ENTER){
			if(!router.hasRootController())
				router.setRoot(RouterTransaction.with(new LibraryController()).tag("Home"));
		}
	}

	@Override
	protected void onViewBound(@NonNull View view) {
		super.onViewBound(view);

		setupUI();
	}

	@Override
	public boolean handleBack() {
		if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
			drawerLayout.closeDrawer(GravityCompat.START);
			return true;
		}
		return router.handleBack();
	}

	private void setupNavigationDrawer(){
		drawerToggle = new ActionBarDrawerToggle(
				getActivity(), drawerLayout, toolbar,
				R.string.navigation_drawer_open,
				R.string.navigation_drawer_close);

		drawerLayout.addDrawerListener(drawerToggle);
		drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				handleBack();
			}
		});
		drawerToggle.syncState();
		navigationView.setNavigationItemSelectedListener(this);
	}

	private void setupUI() {
		((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
		setupNavigationDrawer();

		router = getChildRouter(container, null);
	}

	/**
	 * From the BaseController we can alter the state of the navDrawer to enalbe/disable
	 * This will enable/disable the menu button and the swiping gesture
	 * When disabling the menu button will be replaced with the back arrow
	 * <p>
	 * ie. A detailed info page like AlbumDetailController won't have access to navDrawer
	 *
	 * @param navState new state of Navigation Drawer
	 */
	void toggleNavigationDrawer(boolean navState) {
		Log.d(TAG, "Toggle Navigation drawer:" + navState);

		if (navState) {
			drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
			drawerToggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED);
			drawerToggle.setDrawerIndicatorEnabled(true);
			drawerToggle.syncState();
		} else {
			drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
			// when navigation drawer is disabled make sure to show back arrow indicator
			drawerToggle.setDrawerIndicatorEnabled(false);
			drawerToggle.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
			drawerToggle.syncState();
		}
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		if (item.isChecked()) {
			drawerLayout.closeDrawer(GravityCompat.START);
			return false;
		}
		switch (item.getItemId()) {
			case R.id.nav_library:
				break;

			case R.id.nav_settings:
				break;
		}
		drawerLayout.closeDrawer(GravityCompat.START);
		return true;
	}

}
