package player.music.lisboa.musicplayer.view.settings;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvp.conductor.delegate.MvpConductorDelegateCallback;
import com.hannesdorfmann.mosby3.mvp.conductor.delegate.MvpConductorLifecycleListener;

import javax.inject.Inject;

import butterknife.BindView;
import player.music.lisboa.musicplayer.MusicApplication;
import player.music.lisboa.musicplayer.R;
import player.music.lisboa.musicplayer.dagger.component.DaggerControllerComponent;
import player.music.lisboa.musicplayer.dagger.module.PresenterModule;
import player.music.lisboa.musicplayer.view.base.BaseController;

/**
 * Created by Lisboa on 20-Jul-17.
 */

public class SettingsController extends BaseController implements SettingsView,
		MvpConductorDelegateCallback<SettingsView, SettingsPresenter> {

	public static final String SETTINGS_TAG = "Settings";

	@BindView(R.id.tv_title)
	TextView textView;

	@Inject
	SettingsPresenter settingsPresenter;

	public SettingsController(){
		super(null, true, false);

		DaggerControllerComponent.builder()
				.musicApplicationComponent(MusicApplication.getAppComponent())
				.presenterModule(new PresenterModule())
				.build().inject(this);

		addLifecycleListener(new MvpConductorLifecycleListener<>(this));
	}

	@Override
	protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		return inflater.inflate(R.layout.controller_settings, container, false);
	}

	@Override
	protected void onViewBound(@NonNull View view) {
		super.onViewBound(view);

		textView.setText("Settings");
	}

	@Override
	protected String getTitle() {
		return "Settings";
	}

	// MOSBY

	@NonNull
	@Override
	public SettingsPresenter createPresenter() {
		return settingsPresenter;
	}

	@Nullable
	@Override
	public SettingsPresenter getPresenter() {
		return settingsPresenter;
	}

	@Override
	public void setPresenter(@NonNull SettingsPresenter presenter) {
		logPresenter(presenter);
		this.settingsPresenter = presenter;
	}

	@NonNull
	@Override
	public SettingsView getMvpView() {
		return this;
	}
}
