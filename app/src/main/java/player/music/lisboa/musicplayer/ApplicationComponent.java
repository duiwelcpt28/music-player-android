package player.music.lisboa.musicplayer;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by Lisboa on 15-Jul-17.
 */

@Singleton
@Component(modules = {AndroidInjectionModule.class, ApplicationModule.class})
public interface ApplicationComponent {
}
