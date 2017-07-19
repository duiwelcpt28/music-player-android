package player.music.lisboa.musicplayer.view.library.albums;


import dagger.Component;
import player.music.lisboa.musicplayer.dagger.component.MusicApplicationComponent;
import player.music.lisboa.musicplayer.dagger.scope.ControllerScope;

/**
 * Created by Lisboa on 19-Jul-17.
 */

@ControllerScope
@Component(dependencies = MusicApplicationComponent.class, modules = AlbumsPresenterModule.class)
public interface AlbumsComponent {

	void inject(AlbumsController controller);

}
