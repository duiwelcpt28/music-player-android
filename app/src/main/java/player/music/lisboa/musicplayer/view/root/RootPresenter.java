package player.music.lisboa.musicplayer.view.root;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by Lisboa on 20-Jul-17.
 */

public class RootPresenter implements MvpPresenter<RootView> {

	private RootView view;

	@Override
	public void attachView(RootView view) {
		this.view = view;
		System.out.println("Presenter:onAttachView:" + this);
	}

	@Override
	public void detachView(boolean retainInstance) {
		if(!retainInstance){
			view = null;
		}
	}
}
