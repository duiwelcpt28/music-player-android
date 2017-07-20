package player.music.lisboa.musicplayer.dagger.component;

import dagger.Component;
import player.music.lisboa.musicplayer.dagger.module.PresenterModule;
import player.music.lisboa.musicplayer.dagger.scope.ControllerScope;
import player.music.lisboa.musicplayer.view.library.albumdetail.AlbumDetailController;
import player.music.lisboa.musicplayer.view.library.albums.AlbumsController;
import player.music.lisboa.musicplayer.view.library.artists.ArtistsController;
import player.music.lisboa.musicplayer.view.library.playlists.PlaylistsController;
import player.music.lisboa.musicplayer.view.root.RootController;
import player.music.lisboa.musicplayer.view.settings.SettingsController;

/**
 * call inject(controller) here to provide injections from PresenterModule
 */
@ControllerScope
@Component(dependencies = MusicApplicationComponent.class, modules = PresenterModule.class)
public interface ControllerComponent {

	void inject(AlbumsController controller);

	void inject(RootController rootController);

	void inject(SettingsController settingsController);

	void inject(ArtistsController artistsController);

	void inject(AlbumDetailController albumDetailController);

	void inject(PlaylistsController playlistsController);

}
