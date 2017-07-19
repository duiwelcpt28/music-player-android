package player.music.lisboa.musicplayer.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.lang.annotation.Annotation;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import player.music.lisboa.musicplayer.dagger.scope.MusicApplicationScope;

/**
 * Created by Lisboa on 18-Jul-17.
 */

@GlideModule
public class MyGlideModule extends AppGlideModule {

	@Override public void applyOptions(Context context, GlideBuilder builder) {
		// Apply options to the builder here.
		builder.setDefaultRequestOptions(RequestOptions.formatOf(DecodeFormat.PREFER_ARGB_8888));
	}

}
