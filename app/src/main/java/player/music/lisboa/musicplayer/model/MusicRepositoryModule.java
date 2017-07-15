package player.music.lisboa.musicplayer.model;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Lisboa on 15-Jul-17.
 */

@Module
abstract class MusicRepositoryModule {

    @Singleton
    @Binds
    abstract MusicDataSource provideMusicDataSource(MusicDataSource dataSource);

}
