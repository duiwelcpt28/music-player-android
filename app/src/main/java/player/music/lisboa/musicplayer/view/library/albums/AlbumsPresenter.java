package player.music.lisboa.musicplayer.view.library.albums;

import javax.inject.Inject;

import player.music.lisboa.musicplayer.model.MusicRepository;

/**
 * Created by Lisboa on 15-Jul-17.
 */

public class AlbumsPresenter{

	private final MusicRepository musicRepository;
	private final AlbumsController view;

	@Inject
	public AlbumsPresenter(AlbumsController view, MusicRepository musicRepository) {
		this.musicRepository = musicRepository;
		this.view = view;
		setupView();
	}

	public void setupView(){
		view.setPresenter(this);
	}

	public void subscribe(){
		musicRepository.test();
	}


}
