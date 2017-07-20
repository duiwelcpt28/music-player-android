package player.music.lisboa.musicplayer.view.library.artists;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by Lisboa on 20-Jul-17.
 */

public class ArtistsPresenter implements MvpPresenter<ArtistsView> {

	private static final String TAG = "ArtistsPresenter";

	private ArtistsView artistsView;

	@Override
	public void attachView(ArtistsView view) {
		this.artistsView = view;
		Log.d(TAG, "attachView with:" + this);
	}

	@Override
	public void detachView(boolean retainInstance) {
		if(!retainInstance){
			this.artistsView = null;
		}
	}
}
