package player.music.lisboa.musicplayer.view.library.albumdetail;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by Lisboa on 20-Jul-17.
 */

public class AlbumDetailPresenter implements MvpPresenter<AlbumDetailView> {

	private static final String TAG = "AlbumDetailPresenter";

	private AlbumDetailView detailView;

	@Override
	public void attachView(AlbumDetailView view) {
		this.detailView = view;
		Log.d(TAG, "attachView with:" + this);

	}

	@Override
	public void detachView(boolean retainInstance) {
		if(!retainInstance){
			this.detailView = null;
		}
	}
}
