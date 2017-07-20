package player.music.lisboa.musicplayer.view.root;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerChangeType;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.hannesdorfmann.mosby3.mvp.conductor.delegate.MvpConductorDelegateCallback;
import com.hannesdorfmann.mosby3.mvp.conductor.delegate.MvpConductorLifecycleListener;

import javax.inject.Inject;

import butterknife.BindView;
import player.music.lisboa.musicplayer.MusicApplication;
import player.music.lisboa.musicplayer.R;
import player.music.lisboa.musicplayer.dagger.component.DaggerControllerComponent;
import player.music.lisboa.musicplayer.dagger.module.PresenterModule;
import player.music.lisboa.musicplayer.util.anim.SlideAnimation;
import player.music.lisboa.musicplayer.view.base.BaseController;
import player.music.lisboa.musicplayer.view.library.LibraryController;
import player.music.lisboa.musicplayer.view.settings.SettingsController;

import static android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS;
import static android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL;
import static player.music.lisboa.musicplayer.view.library.LibraryController.LIBRARY_TAG;
import static player.music.lisboa.musicplayer.view.settings.SettingsController.SETTINGS_TAG;

/**
 * Created by Lisboa on 17-Jul-17.
 */

public class RootController extends BaseController implements NavigationView.OnNavigationItemSelectedListener,
		RootView, MvpConductorDelegateCallback<RootView, RootPresenter> {

	private static final String TAG = "RootController";

	private static final String KEY_MINI_PLAYER = "RootController.miniPlayer";

	@BindView(R.id.controller_container)
	ViewGroup container;
	@BindView(R.id.controller_nested_scroll)
	NestedScrollView nestedScrollView;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.drawer_layout)
	DrawerLayout drawerLayout;
	@BindView(R.id.nav_view)
	NavigationView navigationView;
	@BindView(R.id.mini_player)
	RelativeLayout miniPlayer;
	@BindView(R.id.coordinator)
	CoordinatorLayout coordinatorLayout;

	private Router router;
	private ActionBarDrawerToggle drawerToggle;

	@Inject
	RootPresenter rootPresenter;

	public RootController() {
		DaggerControllerComponent.builder()
				.musicApplicationComponent(MusicApplication.getAppComponent())
				.presenterModule(new PresenterModule())
				.build().inject(this);

		// only add lifecycle for mosby after dagger injection
		addLifecycleListener(new MvpConductorLifecycleListener<>(this));
		setRetainViewMode(RetainViewMode.RETAIN_DETACH);
	}

	@Override
	protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		return inflater.inflate(R.layout.controller_root, container, false);
	}

	@Override
	protected void onChangeEnded(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerChangeType changeType) {
		super.onChangeEnded(changeHandler, changeType);
		if (changeType == ControllerChangeType.PUSH_ENTER) {
			if (!router.hasRootController())
				router.setRoot(RouterTransaction.with(new LibraryController()).tag(LIBRARY_TAG));
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

	private void setupNavigationDrawer() {
		drawerToggle = new ActionBarDrawerToggle(
				getActivity(), drawerLayout, toolbar,
				R.string.navigation_drawer_open,
				R.string.navigation_drawer_close);

		drawerLayout.addDrawerListener(drawerToggle);
		drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "DrawerToggle:pressed back");
				handleBack();
			}
		});
		drawerToggle.syncState();
		navigationView.setNavigationItemSelectedListener(this);
		navigationView.setCheckedItem(R.id.nav_library);
	}

	private void setupUI() {
		((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
		setupNavigationDrawer();

		router = getChildRouter(container, null);
	}

	/**
	 * From the BaseController we can alter the state of the navDrawer to enable/disable it
	 * This will enable/disable the menu button and the swiping gesture
	 * When disabling the menu button will be replaced with the back arrow
	 * <p>
	 * ie. A detailed info page like AlbumDetailController won't have access to navDrawer
	 *
	 * @param navState new state of Navigation Drawer
	 */
	public void toggleNavigationDrawer(boolean navState) {
		Log.d(TAG, "Toggle Navigation drawer:" + navState);

		if (navState) {
			drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
			drawerToggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED);
			drawerToggle.setDrawerIndicatorEnabled(true);
		} else {
			drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
			drawerToggle.setDrawerIndicatorEnabled(false);
			drawerToggle.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
		}
		drawerToggle.syncState();
	}

	/**
	 * From the BaseController we can alter the state of the toolbar to enable/disable hiding
	 * This will enable/disable the toolbar behavior so it doesn't close when scrolling
	 * <p>
	 * ie. A detailed info page like AlbumDetailController won't have access to navDrawer
	 *
	 * @param hideState new state of Toolbar scrolling behavior
	 */
	public void toggleToolbarHide(boolean hideState) {
		Log.d(TAG, "Toggle toolbar hide:" + hideState);

		AppBarLayout.LayoutParams toolbarLayoutParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
		if (hideState) {
			toolbarLayoutParams.setScrollFlags(SCROLL_FLAG_SCROLL | SCROLL_FLAG_ENTER_ALWAYS);
		} else {
			toolbarLayoutParams.setScrollFlags(0);
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
				changeController(new LibraryController(), LIBRARY_TAG);
				break;

			case R.id.nav_settings:
				changeController(new SettingsController(), SETTINGS_TAG);
				break;
		}
		item.setChecked(true);
		return true;
	}

	/**
	 * Ensure that when moving to a controller it doesn't already exist in the backstack
	 *
	 * @param controller controller being pushed
	 * @param tag        tag to verify if a controller is in backstack
	 */
	private void changeController(Controller controller, String tag) {
		if (router.getControllerWithTag(tag) == null) {
			router.pushController(RouterTransaction.with(controller).tag(tag));
		} else {
			router.popToTag(tag);
		}

		drawerLayout.closeDrawer(GravityCompat.START);
	}

	@Override
	public void showMiniPlayer() {
		if (miniPlayer.getVisibility() == View.GONE) {
			miniPlayer.setVisibility(View.VISIBLE);
			Animation animation = new SlideAnimation(miniPlayer, 0,
					getResources().getDimensionPixelSize(R.dimen.miniPlayerHeight));

			animation.setInterpolator(new AccelerateDecelerateInterpolator());
			animation.setDuration(250);
			miniPlayer.setAnimation(animation);
			miniPlayer.startAnimation(animation);

			// resize scroll view/container of child controller after animation finishes
			miniPlayer.setLayoutAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// container for internal controllers will occupy remaining space
					nestedScrollView.getLayoutParams().height = nestedScrollView.getMeasuredHeight()
							- (getResources().getDimensionPixelSize(R.dimen.miniPlayerHeight));
					nestedScrollView.requestLayout();
				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}
			});
		}
	}

	// MOSBY

	@NonNull
	@Override
	public RootPresenter createPresenter() {
		return rootPresenter;
	}

	@Nullable
	@Override
	public RootPresenter getPresenter() {
		return rootPresenter;
	}

	@Override
	public void setPresenter(@NonNull RootPresenter presenter) {
		logPresenter(presenter);
		this.rootPresenter = presenter;
	}

	@NonNull
	@Override
	public RootView getMvpView() {
		return this;
	}

}
