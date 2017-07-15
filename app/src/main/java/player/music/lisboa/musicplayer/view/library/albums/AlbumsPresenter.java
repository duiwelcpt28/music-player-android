package player.music.lisboa.musicplayer.view.library.albums;

import javax.inject.Inject;

import player.music.lisboa.musicplayer.model.MusicRepository;

/**
 * Created by Lisboa on 15-Jul-17.
 */

final class AlbumsPresenter {

    private final MusicRepository musicRepository;

    @Inject
    AlbumsPresenter(MusicRepository musicRepository){
        this.musicRepository = musicRepository;
    }

    @Inject
    void setupListeners() {
        //mTasksView.setPresenter(this);
    }

}
