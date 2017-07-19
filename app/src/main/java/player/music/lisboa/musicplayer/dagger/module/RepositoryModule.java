package player.music.lisboa.musicplayer.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import player.music.lisboa.musicplayer.dagger.scope.MusicApplicationScope;
import player.music.lisboa.musicplayer.model.MusicRepository;

/**
 * Created by Lisboa on 18-Jul-17.
 */

@Module
public class RepositoryModule {

	@Provides
	@MusicApplicationScope
	public MusicRepository getMusicRepository(){
		return new MusicRepository();
	}

}
