package player.music.lisboa.musicplayer.view.root;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by Lisboa on 20-Jul-17.
 */

public class RootPresenter implements MvpPresenter<RootView> {

	private static final String TAG = "RootPresenter";
	private RootView view;

	@Override
	public void attachView(RootView view) {
		this.view = view;
		Log.d(TAG, "attachView with:" + this);
	}

	@Override
	public void detachView(boolean retainInstance) {
		if(!retainInstance){
			view = null;
		}
	}
}
