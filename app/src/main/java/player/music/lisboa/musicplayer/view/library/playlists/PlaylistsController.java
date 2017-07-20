package player.music.lisboa.musicplayer.view.library.playlists;

import android.os.Bundle;
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
import player.music.lisboa.musicplayer.util.BundleBuilder;
import player.music.lisboa.musicplayer.view.base.BaseController;

/**
 * Created by Lisboa on 18-Jul-17.
 */

public class PlaylistsController extends BaseController implements PlaylistsView,
		MvpConductorDelegateCallback<PlaylistsView, PlaylistsPresenter>{

	private static final String KEY_TITLE = "PlaylistsController.title";

	@BindView(R.id.tv_title) TextView tvTitle;

	@Inject
	PlaylistsPresenter playlistsPresenter;

	public PlaylistsController() {
		this(new BundleBuilder(new Bundle())
				.putString(KEY_TITLE, "Playlists")
				.build());

		DaggerControllerComponent.builder()
				.musicApplicationComponent(MusicApplication.getAppComponent())
				.presenterModule(new PresenterModule())
				.build().inject(this);

		addLifecycleListener(new MvpConductorLifecycleListener<>(this));
	}

	public PlaylistsController(Bundle args) {
		super(args);
	}

	@Override
	protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		return inflater.inflate(R.layout.controller_playlists, container, false);
	}

	@Override
	protected void onViewBound(@NonNull View view) {
		super.onViewBound(view);

		tvTitle.setText(getArgs().getString(KEY_TITLE));
	}

	@NonNull
	@Override
	public PlaylistsPresenter createPresenter() {
		return playlistsPresenter;
	}

	@Nullable
	@Override
	public PlaylistsPresenter getPresenter() {
		return playlistsPresenter;
	}

	@Override
	public void setPresenter(@NonNull PlaylistsPresenter presenter) {
		this.playlistsPresenter = presenter;
	}

	@NonNull
	@Override
	public PlaylistsView getMvpView() {
		return this;
	}
}
