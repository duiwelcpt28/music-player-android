package player.music.lisboa.musicplayer.view.library.albumdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import player.music.lisboa.musicplayer.R;
import player.music.lisboa.musicplayer.util.BundleBuilder;
import player.music.lisboa.musicplayer.view.base.BaseController;
import player.music.lisboa.musicplayer.view.base.RootController;

/**
 * Created by Lisboa on 17-Jul-17.
 */

public class AlbumDetailController extends BaseController {

	private static final String ALBUM_TITLE = "AlbumDetail.title";
	private static final String KEY_BG_COLOR = "ChildController.bgColor";

	@BindView(R.id.tv_title)
	TextView tvTitle;

	public AlbumDetailController() {
		super(null, false);
	}

	public AlbumDetailController(String albumName) {
		this(new BundleBuilder(new Bundle())
				.putString(ALBUM_TITLE, albumName)
				.build());
	}

	private AlbumDetailController(Bundle args) {
		super(args, false);
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

}