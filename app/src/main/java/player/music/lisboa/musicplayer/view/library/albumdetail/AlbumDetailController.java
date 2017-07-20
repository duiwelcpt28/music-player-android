package player.music.lisboa.musicplayer.view.library.albumdetail;

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
 * Created by Lisboa on 17-Jul-17.
 */

public class AlbumDetailController extends BaseController implements AlbumDetailView,
		MvpConductorDelegateCallback<AlbumDetailView, AlbumDetailPresenter> {

	private static final String ALBUM_TITLE = "AlbumDetail.title";
	private static final String KEY_BG_COLOR = "ChildController.bgColor";

	@BindView(R.id.tv_title)
	TextView tvTitle;

	@Inject
	AlbumDetailPresenter detailPresenter;

	public AlbumDetailController(String albumName) {
		this(new BundleBuilder(new Bundle())
				.putString(ALBUM_TITLE, albumName)
				.build());

		DaggerControllerComponent.builder()
				.musicApplicationComponent(MusicApplication.getAppComponent())
				.presenterModule(new PresenterModule())
				.build().inject(this);

		addLifecycleListener(new MvpConductorLifecycleListener<>(this));
	}

	public AlbumDetailController(Bundle args) {
		super(args, false, false);
	}

	@NonNull
	@Override
	protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		return inflater.inflate(R.layout.controller_album_detail, container, false);
	}

	@Override
	protected void onAttach(@NonNull View view) {
		super.onAttach(view);
	}

	@Override
	protected void onViewBound(@NonNull View view) {
		super.onViewBound(view);

		tvTitle.setText(getArgs().getString(ALBUM_TITLE));
	}

	@Override
	protected String getTitle() {
		return getArgs().getString(ALBUM_TITLE);
	}

	//MOSBY

	@NonNull
	@Override
	public AlbumDetailPresenter createPresenter() {
		return detailPresenter;
	}

	@Nullable
	@Override
	public AlbumDetailPresenter getPresenter() {
		return detailPresenter;
	}

	@Override
	public void setPresenter(@NonNull AlbumDetailPresenter presenter) {
		this.detailPresenter = presenter;
	}

	@NonNull
	@Override
	public AlbumDetailView getMvpView() {
		return this;
	}
}