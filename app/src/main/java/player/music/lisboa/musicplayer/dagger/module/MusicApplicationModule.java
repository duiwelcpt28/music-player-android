package player.music.lisboa.musicplayer.dagger.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import player.music.lisboa.musicplayer.dagger.scope.MusicApplicationScope;

/**
 * Created by Lisboa on 15-Jul-17.
 */

@Module
public final class MusicApplicationModule {

	private Context context;

	public MusicApplicationModule(Context context) {
		this.context = context;
	}

	@Provides
	@MusicApplicationScope
	public Context provideContext() {
		return context;
	}

}
