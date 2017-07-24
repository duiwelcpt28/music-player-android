package player.music.lisboa.musicplayer.view.library.albumdetail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvp.conductor.delegate.MvpConductorDelegateCallback;
import com.hannesdorfmann.mosby3.mvp.conductor.delegate.MvpConductorLifecycleListener;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import player.music.lisboa.musicplayer.MusicApplication;
import player.music.lisboa.musicplayer.R;
import player.music.lisboa.musicplayer.dagger.component.DaggerControllerComponent;
import player.music.lisboa.musicplayer.dagger.module.PresenterModule;
import player.music.lisboa.musicplayer.model.Album;
import player.music.lisboa.musicplayer.util.BlurringView;
import player.music.lisboa.musicplayer.util.BundleBuilder;
import player.music.lisboa.musicplayer.util.FadingImageView;
import player.music.lisboa.musicplayer.view.base.BaseController;
import player.music.lisboa.musicplayer.view.root.RootController;

/**
 * Created by Lisboa on 17-Jul-17.
 */

public class AlbumDetailController extends BaseController implements AlbumDetailView,
		MvpConductorDelegateCallback<AlbumDetailView, AlbumDetailPresenter> {

	private static final String ALBUM_TITLE = "AlbumDetail.title";
	private static final String KEY_BG_COLOR = "AlbumDetail.bgColor";
	private static final String KEY_FROM_POSITION = "AlbumDetail.position";

	@BindView(R.id.recycler_view)
	RecyclerView recyclerView;
	@BindView(R.id.background)
	ImageView background;
	@BindView(R.id.blurring_view)
	BlurringView blurringView;

	@Inject
	AlbumDetailPresenter detailPresenter;

	private Album album;
	private int fromPosition;

	public AlbumDetailController(Album album, int position) {
		this(new BundleBuilder(new Bundle())
				.putString(ALBUM_TITLE, album.getName())
				.putInt(KEY_FROM_POSITION, position)
				.build());

		DaggerControllerComponent.builder()
				.musicApplicationComponent(MusicApplication.getAppComponent())
				.presenterModule(new PresenterModule())
				.build().inject(this);

		addLifecycleListener(new MvpConductorLifecycleListener<>(this));
	}

	public AlbumDetailController(Bundle args) {
		super(args, false, true);
		album = new Album(getArgs().getString(ALBUM_TITLE));
		fromPosition = getArgs().getInt(KEY_FROM_POSITION);
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

		recyclerView.setHasFixedSize(true);
		recyclerView.setNestedScrollingEnabled(false);
		recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
		recyclerView.setAdapter(new AlbumDetailsAdapter(LayoutInflater.from(view.getContext()),
				album.getName(),
				getResources().getString(R.string.transition_tag_image_indexed, fromPosition),
				getResources().getString(R.string.transition_tag_title_indexed, fromPosition)));

		blurringView.setBlurredView(background);
		background.setImageResource(R.drawable.alterbridge);
		blurringView.invalidate();

		((RootController) getParentController()).setBanner(R.drawable.alterbridge,
				getResources().getString(R.string.transition_tag_image_indexed, fromPosition));
	}

	@Override
	protected String getTitle() {
		return getArgs().getString(ALBUM_TITLE);
	}

	@Override
	public boolean handleBack() {
		((RootController) getParentController()).removeBanner();
		return super.handleBack();
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

	class AlbumDetailsAdapter extends RecyclerView.Adapter<AlbumDetailsAdapter.ViewHolder>{

		private static final int VIEW_TYPE_HEADER = 0;
		private static final int VIEW_TYPE_SONGS = 1;

		private LayoutInflater inflater;
		private String title;
		private String imageViewTransitionName;
		private String textViewTransitionName;

		private List<String> songs;

		AlbumDetailsAdapter(LayoutInflater inflater, String title, String imageViewTransitionName, String textViewTransitionName){
			this.inflater = inflater;
			this.title = title;
			this.imageViewTransitionName = imageViewTransitionName;
			this.textViewTransitionName = textViewTransitionName;

			songs = new LinkedList<>();
			for(int i = 0; i < 20; i++){
				songs.add("Song " + i);
			}
		}

		@Override
		public int getItemViewType(int position) {
			if (position == 0) {
				return VIEW_TYPE_HEADER;
			} else {
				return VIEW_TYPE_SONGS;
			}
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			if (viewType == VIEW_TYPE_HEADER) {
				return new AlbumHeaderHolder(inflater.inflate(R.layout.controller_album_detail_header, parent, false));
			} else {
				return new AlbumSongHolder(inflater.inflate(R.layout.controller_album_detail_songs, parent, false));
			}
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			if (getItemViewType(position) == VIEW_TYPE_HEADER) {
				((AlbumHeaderHolder)holder).bind(title, imageViewTransitionName, textViewTransitionName);
			} else {
				((AlbumSongHolder)holder).bind(songs.get(position - 1));
			}
		}

		@Override
		public int getItemCount() {
			return 1 + songs.size();
		}

		class ViewHolder extends RecyclerView.ViewHolder{

			public ViewHolder(View itemView) {
				super(itemView);
				ButterKnife.bind(this, itemView);
			}
		}

		class AlbumHeaderHolder extends ViewHolder{

			/*@BindView(R.id.image_view)
			ImageView imageView;*/
			@BindView(R.id.text_view)
			TextView textView;

			AlbumHeaderHolder(View itemView) {
				super(itemView);
			}

			void bind(String title, String imageTransitionName, String textViewTransitionName) {
				//imageView.setImageResource(R.drawable.alterbridge);

				textView.setText(title);

				//ViewCompat.setTransitionName(imageView, imageTransitionName);
				ViewCompat.setTransitionName(textView, textViewTransitionName);
			}

		}

		class AlbumSongHolder extends ViewHolder{

			@BindView(R.id.text_view)
			TextView textView;

			AlbumSongHolder(View itemView) {
				super(itemView);
			}

			void bind(String song) {
				textView.setText(song);
			}

		}

	}

}