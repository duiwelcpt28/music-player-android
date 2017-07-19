package player.music.lisboa.musicplayer.dagger.module;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import player.music.lisboa.musicplayer.dagger.scope.MusicApplicationScope;

/**
 * Created by Lisboa on 18-Jul-17.
 */

@Module(includes = {MusicApplicationModule.class})
public class GlideModule {

	@Provides
	@MusicApplicationScope
	public Glide getGlide(Context context){
		return new GlideBuilder()
				.setDefaultRequestOptions(RequestOptions.formatOf(DecodeFormat.PREFER_ARGB_8888))
				.build(context);
	}

}
