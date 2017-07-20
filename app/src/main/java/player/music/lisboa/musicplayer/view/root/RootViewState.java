package player.music.lisboa.musicplayer.view.root;

import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState;

/**
 * Created by Lisboa on 20-Jul-17.
 */

public class RootViewState implements ViewState<RootView> {

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
		if(currentViewState == STATE_SHOW_MINI_PLAYER){
			view.showMiniPlayer();
		}else if(currentViewState == STATE_SHOW_BOTTOM_SHEET){
			view.showBottomPlayer();
		}
	}

}
