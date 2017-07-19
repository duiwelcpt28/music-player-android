package player.music.lisboa.musicplayer.view.library.albums;

import dagger.Module;
import dagger.Provides;
import player.music.lisboa.musicplayer.view.base.BaseController;

/**
 * Created by Lisboa on 19-Jul-17.
 */

@Module
public class AlbumsPresenterModule {

	private final AlbumsController view;

	public AlbumsPresenterModule(AlbumsController view) {
		this.view = view;
	}

	@Provides
	AlbumsController provideAlbumsContractView() {
		return view;
	}

}
