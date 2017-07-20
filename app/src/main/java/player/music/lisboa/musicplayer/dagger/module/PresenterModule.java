package player.music.lisboa.musicplayer.dagger.module;

import dagger.Module;
import dagger.Provides;
import player.music.lisboa.musicplayer.dagger.scope.ControllerScope;
import player.music.lisboa.musicplayer.model.MusicRepository;
import player.music.lisboa.musicplayer.view.library.albumdetail.AlbumDetailController;
import player.music.lisboa.musicplayer.view.library.albumdetail.AlbumDetailPresenter;
import player.music.lisboa.musicplayer.view.library.albums.AlbumsPresenter;
import player.music.lisboa.musicplayer.view.library.artists.ArtistsController;
import player.music.lisboa.musicplayer.view.library.artists.ArtistsPresenter;
import player.music.lisboa.musicplayer.view.library.playlists.PlaylistsPresenter;
import player.music.lisboa.musicplayer.view.root.RootPresenter;
import player.music.lisboa.musicplayer.view.settings.SettingsPresenter;

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

	@Provides
	@ControllerScope
	SettingsPresenter provideSettingsPresenter(){
		return new SettingsPresenter();
	}

	@Provides
	@ControllerScope
	ArtistsPresenter provideArtistsPresenter(){
		return new ArtistsPresenter();
	}

	@Provides
	@ControllerScope
	PlaylistsPresenter providePlaylistsPresenter(){
		return new PlaylistsPresenter();
	}

	@Provides
	@ControllerScope
	AlbumDetailPresenter provideAlbumDetailPresenter(){
		return new AlbumDetailPresenter();
	}

}
