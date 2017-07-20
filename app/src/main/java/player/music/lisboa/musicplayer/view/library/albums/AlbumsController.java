package player.music.lisboa.musicplayer.view.library.albums;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.hannesdorfmann.mosby3.conductor.viewstate.delegate.MvpViewStateConductorDelegateCallback;
import com.hannesdorfmann.mosby3.conductor.viewstate.delegate.MvpViewStateConductorLifecycleListener;
import com.hannesdorfmann.mosby3.mvp.conductor.delegate.MvpConductorDelegateCallback;
import com.hannesdorfmann.mosby3.mvp.conductor.delegate.MvpConductorLifecycleListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnItemClick;
import player.music.lisboa.musicplayer.MusicApplication;
import player.music.lisboa.musicplayer.R;
import player.music.lisboa.musicplayer.dagger.component.ControllerComponent;
import player.music.lisboa.musicplayer.dagger.component.DaggerControllerComponent;
import player.music.lisboa.musicplayer.dagger.module.PresenterModule;
import player.music.lisboa.musicplayer.util.BundleBuilder;
import player.music.lisboa.musicplayer.view.base.BaseController;
import player.music.lisboa.musicplayer.view.library.albumdetail.AlbumDetailController;
import player.music.lisboa.musicplayer.view.root.RootController;

/**
 * Created by Lisboa on 15-Jul-17.
 */

public class AlbumsController extends BaseController implements AlbumsView,
		MvpConductorDelegateCallback<AlbumsView, AlbumsPresenter> {

	private static final String TAG = "AlbumsController";

	private static final String KEY_TITLE = "AlbumsController.title";

	@BindView(R.id.list)
	ListView listView;

	@BindView(R.id.image_test)
	ImageView imageTest;

	private List<String> albums;
	private ArrayAdapter<String> listAlbumsAdapter;

	@Inject
	AlbumsPresenter albumsPresenter;

	public AlbumsController() {
		this(new BundleBuilder(new Bundle())
				.putString(KEY_TITLE, "Albums")
				.build());

		setRetainViewMode(RetainViewMode.RETAIN_DETACH);
	}

	public AlbumsController(Bundle args) {
		super(args);
		DaggerControllerComponent.builder()
					.musicApplicationComponent(MusicApplication.getAppComponent())
					.presenterModule(new PresenterModule())
					.build().inject(this);

		addLifecycleListener(new MvpConductorLifecycleListener<>(this));

		albums = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			albums.add("Album " + i);
		}
	}

	@NonNull
	@Override
	protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		return inflater.inflate(R.layout.controller_albums, container, false);
	}

	@Override
	protected void onViewBound(@NonNull View view) {
		super.onViewBound(view);
		listAlbumsAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, albums);
		listView.setAdapter(listAlbumsAdapter);

		albumsPresenter.subscribe();
	}

	@OnItemClick(R.id.list)
	void onAlbumClick(int position) {
		getParentController().getRouter()
				.pushController(RouterTransaction.with(new AlbumDetailController(listAlbumsAdapter.getItem(position)))
				.pushChangeHandler(new FadeChangeHandler()));

		((RootController)getParentController().getParentController()).showMiniPlayer();
	}

	@NonNull
	@Override
	public AlbumsPresenter createPresenter() {
		return albumsPresenter;
	}

	@Nullable
	@Override
	public AlbumsPresenter getPresenter() {
		return albumsPresenter;
	}

	@Override
	public void setPresenter(@NonNull AlbumsPresenter presenter) {
		logPresenter(presenter);
		this.albumsPresenter = presenter;
	}

	@NonNull
	@Override
	public AlbumsView getMvpView() {
		return this;
	}
}
