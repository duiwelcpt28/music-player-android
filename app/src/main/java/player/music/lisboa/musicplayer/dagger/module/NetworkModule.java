package player.music.lisboa.musicplayer.dagger.module;

import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import player.music.lisboa.musicplayer.dagger.scope.MusicApplicationScope;

/**
 * Created by Lisboa on 18-Jul-17.
 */

@Module
public class NetworkModule {

	@Provides
	@MusicApplicationScope
	public HttpLoggingInterceptor getLoggingInterceptor(){
		return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
			@Override
			public void log(String message) {
				Log.d("OK_HTTP", message);
			}
		});
	}

	@Provides
	@MusicApplicationScope
	public OkHttpClient getOkHttpClient(HttpLoggingInterceptor loggingInterceptor){
		return new OkHttpClient.Builder()
				.addInterceptor(loggingInterceptor)
				.build();

	}

}
