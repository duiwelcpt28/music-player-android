package player.music.lisboa.musicplayer.view.library.albums;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import javax.inject.Inject;

import player.music.lisboa.musicplayer.model.MusicRepository;

/**
 * Created by Lisboa on 15-Jul-17.
 */

public class AlbumsPresenter implements MvpPresenter<AlbumsView>{

	private final MusicRepository musicRepository;
	private AlbumsView view;

	@Inject
	public AlbumsPresenter(MusicRepository musicRepository) {
		this.musicRepository = musicRepository;
	}

	public void subscribe(){
		musicRepository.test();
	}


	@Override
	public void attachView(AlbumsView view) {
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
