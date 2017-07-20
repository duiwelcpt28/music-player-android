package player.music.lisboa.musicplayer.view.library.albums;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

/**
 * Created by Lisboa on 20-Jul-17.
 */

public interface AlbumsView extends MvpView{

	void showAlbums(List<String> albums);

}
