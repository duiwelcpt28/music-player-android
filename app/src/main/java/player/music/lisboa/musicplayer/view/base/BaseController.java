package player.music.lisboa.musicplayer.view.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;

import player.music.lisboa.musicplayer.view.library.LibraryController;

/**
 * Based on the BaseController from the Conductor Demo
 */
public abstract class BaseController extends RefWatchingController {

	// navigation drawer is always enabled unless specified. ie.AlbumDetailController
	private boolean navigationState = true;

	protected BaseController() {
	}

	protected BaseController(Bundle args) {
		super(args);
		setHasOptionsMenu(true);
	}

	protected BaseController(Bundle args, boolean navigationState) {
		this(args);
		this.navigationState = navigationState;
	}

	// Note: This is just a quick demo of how an ActionBar *can* be accessed, not necessarily how it *should*
	// be accessed. In a production app, this would use Dagger instead.
	protected ActionBar getActionBar() {
		ActionBarProvider actionBarProvider = ((ActionBarProvider) getActivity());
		return actionBarProvider != null ? actionBarProvider.getSupportActionBar() : null;
	}

	@Override
	protected void onAttach(@NonNull View view) {
		setTitle();
		toggleNavigationDrawer();
		super.onAttach(view);
	}

	private void setTitle() {
		Controller parentController = getParentController();
		while (parentController != null) {
			if (parentController instanceof BaseController && ((BaseController) parentController).getTitle() != null) {
				return;
			}
			parentController = parentController.getParentController();
		}

		String title = getTitle();
		ActionBar actionBar = getActionBar();
		if (title != null && actionBar != null) {
			actionBar.setTitle(title);
		}
	}

	protected String getTitle() {
		return null;
	}

	private void toggleNavigationDrawer(){
		if(getParentController() instanceof RootController){
			((RootController) getParentController()).toggleNavigationDrawer(navigationState);
		}
	}

	public interface ActionBarProvider {
		ActionBar getSupportActionBar();
	}
}
