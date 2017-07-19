package player.music.lisboa.musicplayer.view.library.albums;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnItemClick;
import player.music.lisboa.musicplayer.MusicApplication;
import player.music.lisboa.musicplayer.R;
import player.music.lisboa.musicplayer.util.BundleBuilder;
import player.music.lisboa.musicplayer.view.base.BaseController;
import player.music.lisboa.musicplayer.view.library.albumdetail.AlbumDetailController;

/**
 * Created by Lisboa on 15-Jul-17.
 */

public class AlbumsController extends BaseController{

	private static final String TAG = "AlbumsController";

	private static final String KEY_TITLE = "AlbumsController.title";

	@BindView(R.id.list)
	ListView listView;

	private List<String> albums;
	private ArrayAdapter<String> listAlbumsAdapter;

	@Inject AlbumsPresenter albumsPresenter;

	public AlbumsController() {
		this(new BundleBuilder(new Bundle())
				.putString(KEY_TITLE, "Albums")
				.build());

		setRetainViewMode(RetainViewMode.RETAIN_DETACH);
	}

	public AlbumsController(Bundle args) {
		super(args);

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
		DaggerAlbumsComponent.builder()
				.musicApplicationComponent(((MusicApplication) getApplicationContext())
						.getAppComponent())
				.albumsPresenterModule(new AlbumsPresenterModule(this))
				.build()
				.inject(this);

		listAlbumsAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, albums);
		listView.setAdapter(listAlbumsAdapter);

		albumsPresenter.subscribe();
	}

	@OnItemClick(R.id.list)
	void onAlbumClick(int position) {
		getParentController().getRouter()
				.pushController(RouterTransaction
						.with(new AlbumDetailController(listAlbumsAdapter.getItem(position)))
						.pushChangeHandler(new FadeChangeHandler()));
	}

	void setPresenter(AlbumsPresenter presenter) {
		Log.d(TAG, "Presenter injected");
		this.albumsPresenter = presenter;
	}
}
