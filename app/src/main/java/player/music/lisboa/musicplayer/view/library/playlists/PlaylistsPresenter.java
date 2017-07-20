package player.music.lisboa.musicplayer.view.library.playlists;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by Lisboa on 20-Jul-17.
 */

public class PlaylistsPresenter implements MvpPresenter<PlaylistsView> {

	private static final String TAG = "PlaylistsPresenter";

	private PlaylistsView playlistsView;


	@Override
	public void attachView(PlaylistsView view) {
		this.playlistsView = view;
		Log.d(TAG, "attachView with:" + this);
	}

	@Override
	public void detachView(boolean retainInstance) {
		if(!retainInstance){
			this.playlistsView = null;
		}
	}
}
