package player.music.lisboa.musicplayer.view.settings;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by Lisboa on 20-Jul-17.
 */

public class SettingsPresenter implements MvpPresenter<SettingsView> {

	private static final String TAG = "SettingsPresenter";

	private SettingsView settingsView;

	@Override
	public void attachView(SettingsView view) {
		this.settingsView = view;
		Log.d(TAG, "attachView with:" + this);
	}

	@Override
	public void detachView(boolean retainInstance) {
		if(!retainInstance){
			this.settingsView = null;
		}
	}
}
