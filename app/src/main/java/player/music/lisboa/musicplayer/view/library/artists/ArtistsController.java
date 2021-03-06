package player.music.lisboa.musicplayer.view.library.artists;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import player.music.lisboa.musicplayer.dagger.component.MusicApplicationComponent;
import player.music.lisboa.musicplayer.dagger.module.PresenterModule;
import player.music.lisboa.musicplayer.util.BundleBuilder;
import player.music.lisboa.musicplayer.view.base.BaseController;

/**
 * Created by Lisboa on 15-Jul-17.
 */

public class ArtistsController extends BaseController implements ArtistsView,
		MvpConductorDelegateCallback<ArtistsView, ArtistsPresenter>{

	private static final String KEY_TITLE = "ChildController.title";
	private static final String KEY_BG_COLOR = "ChildController.bgColor";

	@BindView(R.id.tv_title)
	TextView tvTitle;

	@Inject
	ArtistsPresenter artistsPresenter;

	public ArtistsController() {
		this(new BundleBuilder(new Bundle())
				.putString(KEY_TITLE, "Artists")
				.build());

		DaggerControllerComponent.builder()
				.musicApplicationComponent(MusicApplication.getAppComponent())
				.presenterModule(new PresenterModule())
				.build().inject(this);

		addLifecycleListener(new MvpConductorLifecycleListener<>(this));
	}

	public ArtistsController(Bundle args) {
		super(args);
	}

	@NonNull
	@Override
	protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		return inflater.inflate(R.layout.controller_artists, container, false);
	}

	@Override
	protected void onViewBound(@NonNull View view) {
		super.onViewBound(view);

		tvTitle.setText(getArgs().getString(KEY_TITLE));
	}

	// MOSBY

	@NonNull
	@Override
	public ArtistsPresenter createPresenter() {
		return artistsPresenter;
	}

	@Nullable
	@Override
	public ArtistsPresenter getPresenter() {
		return artistsPresenter;
	}

	@Override
	public void setPresenter(@NonNull ArtistsPresenter presenter) {
		this.artistsPresenter = presenter;
	}

	@NonNull
	@Override
	public ArtistsView getMvpView() {
		return this;
	}
}