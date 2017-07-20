package player.music.lisboa.musicplayer.dagger.component;

import dagger.Component;
import player.music.lisboa.musicplayer.dagger.module.PresenterModule;
import player.music.lisboa.musicplayer.dagger.scope.ControllerScope;
import player.music.lisboa.musicplayer.view.library.albums.AlbumsController;
import player.music.lisboa.musicplayer.view.root.RootController;

/**
 * Created by Lisboa on 20-Jul-17.
 */
@ControllerScope
@Component(dependencies = MusicApplicationComponent.class, modules = PresenterModule.class)
public interface ControllerComponent {

	void inject(AlbumsController controller);

	void inject(RootController rootController);

}
