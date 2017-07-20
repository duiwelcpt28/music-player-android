package player.music.lisboa.musicplayer.dagger.module;

import dagger.Module;
import dagger.Provides;
import player.music.lisboa.musicplayer.dagger.scope.ControllerScope;
import player.music.lisboa.musicplayer.model.MusicRepository;
import player.music.lisboa.musicplayer.view.library.albums.AlbumsPresenter;
import player.music.lisboa.musicplayer.view.root.RootPresenter;

/**
 * Created by Lisboa on 20-Jul-17.
 */

@Module
public class PresenterModule {

	@Provides
	@ControllerScope
	RootPresenter provideRootPresenter(){
		return new RootPresenter();
	}

	@Provides
	@ControllerScope
	AlbumsPresenter provideAlbumsPresenter(MusicRepository musicRepository){
		return new AlbumsPresenter(musicRepository);
	}

}
