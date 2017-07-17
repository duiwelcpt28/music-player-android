package player.music.lisboa.musicplayer.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

import butterknife.BindView;
import butterknife.ButterKnife;
import player.music.lisboa.musicplayer.R;
import player.music.lisboa.musicplayer.view.base.BaseController;
import player.music.lisboa.musicplayer.view.base.RootController;

public class MainActivity extends AppCompatActivity implements BaseController.ActionBarProvider {

	@BindView(R.id.controller_container) ViewGroup container;

	private Router router;

    /*@Override
	public Object getSystemService(String name) {
        MortarScope activityScope = MortarScope.findChild(getApplicationContext(), getScopeName());

        if (activityScope == null) {
            activityScope = buildChild(getApplicationContext()) //
                    .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                    .withService(DaggerService.SERVICE_NAME, DaggerService.createComponent(Main.Component.class))
                    .build(getScopeName());
        }

        return activityScope.hasService(name) ? activityScope.getService(name) : super.getSystemService(name);
    }*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ButterKnife.bind(this);

		router = Conductor.attachRouter(this, container, savedInstanceState);
		if (!router.hasRootController()) {
			router.setRoot(RouterTransaction.with(new RootController()).tag("Root"));
		}
	}

	@Override
	public void onBackPressed() {
		if (!router.handleBack()) {
			super.onBackPressed();
		}
	}

	private String getScopeName() {
		return getClass().getName();
	}
}
