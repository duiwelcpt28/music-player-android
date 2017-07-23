package player.music.lisboa.musicplayer.view.library.albums;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import hugo.weaving.DebugLog;
import player.music.lisboa.musicplayer.MusicApplication;
import player.music.lisboa.musicplayer.R;
import player.music.lisboa.musicplayer.dagger.component.ControllerComponent;
import player.music.lisboa.musicplayer.dagger.component.DaggerControllerComponent;
import player.music.lisboa.musicplayer.dagger.module.PresenterModule;
import player.music.lisboa.musicplayer.model.Album;
import player.music.lisboa.musicplayer.util.BundleBuilder;
import player.music.lisboa.musicplayer.util.anim.ArcFadeMoveChangeHandler;
import player.music.lisboa.musicplayer.util.anim.SharedElementChangeHandler;
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

	@BindView(R.id.recycler_view)
	RecyclerView recyclerView;

	private AlbumAdapter listAlbumsAdapter;

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
	}

	@NonNull
	@Override
	protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		return inflater.inflate(R.layout.controller_albums, container, false);
	}

	@Override
	protected void onViewBound(@NonNull View view) {
		super.onViewBound(view);
	}

	private void onAlbumClick(Album album, int position) {
		String titleSharedElementName = getResources().getString(R.string.transition_tag_title_indexed, position);
		String imageSharedElementName = getResources().getString(R.string.transition_tag_image_indexed, position);

		getParentController().getRouter()
				.pushController(RouterTransaction
						.with(new AlbumDetailController(album, position))
						.pushChangeHandler(new SharedElementChangeHandler(titleSharedElementName, imageSharedElementName))
						.popChangeHandler(new SharedElementChangeHandler(titleSharedElementName, imageSharedElementName)));

		//((RootController)getParentController().getParentController()).showMiniPlayer();
	}

	@DebugLog
	@Override
	public void showAlbums(List<Album> albums) {
		listAlbumsAdapter = new AlbumAdapter(LayoutInflater.from(getView().getContext()), albums);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new GridLayoutManager(getView().getContext(), 2));
		recyclerView.setAdapter(listAlbumsAdapter);
	}

	// MOSBY

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


	class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

		private final LayoutInflater inflater;
		private List<Album> albums;

		AlbumAdapter(LayoutInflater inflater, List<Album> albums) {
			this.inflater = inflater;
			this.albums = albums;
		}

		@Override
		public AlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return new ViewHolder(inflater.inflate(R.layout.item_album_layout, parent, false));
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			holder.bind(albums.get(position), position);
		}

		@Override
		public int getItemCount() {
			return albums.size();
		}


		class ViewHolder extends RecyclerView.ViewHolder {

			@BindView(R.id.tv_title)
			TextView textView;
			@BindView(R.id.cover)
			ImageView imageView;

			private Album model;
			private int position;

			ViewHolder(View itemView) {
				super(itemView);
				ButterKnife.bind(this, itemView);
			}

			void bind(Album item, int position) {
				this.position = position;
				model = item;
				imageView.setImageResource(R.drawable.ic_album_black_24dp);
				textView.setText(item.getName());

				ViewCompat.setTransitionName(textView, getResources().getString(R.string.transition_tag_title_indexed, position));
				ViewCompat.setTransitionName(imageView, getResources().getString(R.string.transition_tag_image_indexed, position));
			}

			@OnClick(R.id.row_root)
			void onRowClick() {
				onAlbumClick(model, position);
			}

		}

	}

}
