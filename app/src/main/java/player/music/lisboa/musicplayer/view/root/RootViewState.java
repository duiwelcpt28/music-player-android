package player.music.lisboa.musicplayer.view.root;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState;

/**
 * Created by Lisboa on 20-Jul-17.
 */

public class RootViewState implements ViewState<RootView> {

	private static final String TAG = "RootViewState";

	/**
	 * Used to indicate that the mini player is shown
	 */
	int STATE_SHOW_MINI_PLAYER = 0;
	/**
	 * Used to indicate that the bottom sheet player is opened
	 */
	int STATE_SHOW_BOTTOM_SHEET = 1;

	private int currentViewState;

	public RootViewState(){
		this.currentViewState = -1;
	}

	public void setShowingMiniPlayer(){
		currentViewState = STATE_SHOW_MINI_PLAYER;
	}

	public void setShowingBottomSheet(){
		currentViewState = STATE_SHOW_BOTTOM_SHEET;
	}

	@Override
	public void apply(RootView view, boolean retained) {
		Log.d(TAG, "restoring view:" + view + "\n\twith state:" + currentViewState);
		if(currentViewState == STATE_SHOW_MINI_PLAYER){
			view.showMiniPlayer();
		}else if(currentViewState == STATE_SHOW_BOTTOM_SHEET){
			view.showBottomPlayer();
		}
	}

	@Override
	public String toString() {
		return "RootViewState{" +
				"currentViewState=" + currentViewState +
				'}';
	}
}
