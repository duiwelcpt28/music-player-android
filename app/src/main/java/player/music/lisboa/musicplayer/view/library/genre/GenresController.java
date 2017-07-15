package player.music.lisboa.musicplayer.view.library.genre;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import player.music.lisboa.musicplayer.R;
import player.music.lisboa.musicplayer.util.BundleBuilder;
import player.music.lisboa.musicplayer.view.base.BaseController;

/**
 * Created by Lisboa on 15-Jul-17.
 */

public class GenresController extends BaseController {

    private static final String KEY_TITLE = "ChildController.title";
    private static final String KEY_BG_COLOR = "ChildController.bgColor";

    @BindView(R.id.tv_title)
    TextView tvTitle;

    public GenresController() {
        this(new BundleBuilder(new Bundle())
                .putString(KEY_TITLE, "Genres")
                .build());
    }
    public GenresController(Bundle args) {
        super(args);
    }

    @NonNull
    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_albums, container, false);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        tvTitle.setText(getArgs().getString(KEY_TITLE));
    }
}