package player.music.lisboa.musicplayer.view.library.playlists;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import player.music.lisboa.musicplayer.R;
import player.music.lisboa.musicplayer.util.BundleBuilder;
import player.music.lisboa.musicplayer.view.base.BaseController;

/**
 * Created by Lisboa on 18-Jul-17.
 */

public class PlaylistsController extends BaseController {

	private static final String KEY_TITLE = "PlaylistsController.title";

	@BindView(R.id.tv_title) TextView tvTitle;

	public PlaylistsController() {
		this(new BundleBuilder(new Bundle())
				.putString(KEY_TITLE, "Playlists")
				.build());
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

}
