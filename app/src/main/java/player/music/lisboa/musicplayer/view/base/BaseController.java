package player.music.lisboa.musicplayer.view.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.bluelinelabs.conductor.Controller;

import player.music.lisboa.musicplayer.dagger.component.ControllerComponent;
import player.music.lisboa.musicplayer.view.root.RootController;

/**
 * Based on the BaseController from the Conductor Demo
 */
public abstract class BaseController extends RefWatchingController {

	// navigation drawer is always enabled unless specified. ie.AlbumDetailController
	private boolean navigationState = true;
	private boolean toolbarHideState = true;
	protected ControllerComponent controllerComponent;

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

	protected BaseController(Bundle args, boolean navigationState, boolean toolbarHideState) {
		this(args, navigationState);
		this.toolbarHideState = toolbarHideState;
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
		setNavigationState();
		setToolbarHideState();
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

	private void setToolbarHideState(){
		if(getParentController() instanceof RootController){
			((RootController) getParentController()).toggleToolbarHide(toolbarHideState);
		}
	}

	private void setNavigationState(){
		if(getParentController() instanceof RootController){
			((RootController) getParentController()).toggleNavigationDrawer(navigationState);
		}
	}

	public interface ActionBarProvider {
		ActionBar getSupportActionBar();
	}
}
