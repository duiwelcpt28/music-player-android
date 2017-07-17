package player.music.lisboa.musicplayer;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import mortar.MortarScope;

/**
 * Created by Lisboa on 10-Jul-17.
 */

public class MusicApplication extends Application {

    private ApplicationComponent applicationComponent;
    private MortarScope rootScope;
    public static RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = initDagger();

        if(BuildConfig.DEBUG) {
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

    public ApplicationComponent getAppComponent() {
        return applicationComponent;
    }

    protected ApplicationComponent initDagger() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

}