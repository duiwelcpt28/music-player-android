package player.music.lisboa.musicplayer.dagger.component;

import com.bumptech.glide.Glide;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import player.music.lisboa.musicplayer.dagger.module.MusicApplicationModule;
import player.music.lisboa.musicplayer.dagger.module.GlideModule;
import player.music.lisboa.musicplayer.dagger.module.NetworkModule;
import player.music.lisboa.musicplayer.dagger.module.RepositoryModule;
import player.music.lisboa.musicplayer.dagger.scope.MusicApplicationScope;
import player.music.lisboa.musicplayer.model.MusicRepository;
import player.music.lisboa.musicplayer.view.MainActivity;

/**
 * Created by Lisboa on 15-Jul-17.
 */

@MusicApplicationScope
@Component(modules = {MusicApplicationModule.class, NetworkModule.class, GlideModule.class, RepositoryModule.class})
public interface MusicApplicationComponent {

	Glide getGlide();

	OkHttpClient getHttpClient();

	MusicRepository getMusicRespository();

	void inject(MainActivity mainActivity);

}
