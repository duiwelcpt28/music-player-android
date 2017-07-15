package player.music.lisboa.musicplayer.model;

import javax.inject.Singleton;

import dagger.Component;
import player.music.lisboa.musicplayer.ApplicationModule;

/**
 * Created by Lisboa on 15-Jul-17.
 */

@Singleton
@Component(modules = {MusicRepositoryModule.class, ApplicationModule.class})
public interface MusicRepositoryComponent {

    MusicRepository getMusicRepository();

}
