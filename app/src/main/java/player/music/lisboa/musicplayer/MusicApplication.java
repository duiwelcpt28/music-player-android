package player.music.lisboa.musicplayer;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import mortar.MortarScope;
import player.music.lisboa.musicplayer.dagger.component.DaggerMusicApplicationComponent;
import player.music.lisboa.musicplayer.dagger.component.MusicApplicationComponent;
import player.music.lisboa.musicplayer.dagger.module.MusicApplicationModule;
import player.music.lisboa.musicplayer.dagger.module.NetworkModule;
import player.music.lisboa.musicplayer.dagger.module.RepositoryModule;

/**
 * Created by Lisboa on 10-Jul-17.
 */

public class MusicApplication extends Application {

	private MusicApplicationComponent applicationComponent;
	private MortarScope rootScope;
	public static RefWatcher refWatcher;

	@Override
	public void onCreate() {
		super.onCreate();
		applicationComponent = initDagger();

		if (BuildConfig.DEBUG) {
			if (LeakCanary.isInAnalyzerProcess(this)) {
				// This process is dedicated to LeakCanary for heap analysis.
				// You should not init your app in this process.
				return;
			}
			refWatcher = LeakCanary.install(this);
			//Fabric.with(this, new Crashlytics());
		}
	}

	@Override
	public Object getSystemService(String name) {
		if (rootScope == null) rootScope = MortarScope.buildRootScope().build("Root");

		return rootScope.hasService(name) ? rootScope.getService(name) : super.getSystemService(name);
	}

	public MusicApplicationComponent getAppComponent() {
		return applicationComponent;
	}

	protected MusicApplicationComponent initDagger() {
		return DaggerMusicApplicationComponent.builder()
				.musicApplicationModule(new MusicApplicationModule(this))
				.repositoryModule(new RepositoryModule())
				.networkModule(new NetworkModule())
				.build();
	}

}