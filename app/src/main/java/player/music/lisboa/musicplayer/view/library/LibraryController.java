package player.music.lisboa.musicplayer.view.library;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.support.RouterPagerAdapter;

import java.util.Locale;

import butterknife.BindView;
import player.music.lisboa.musicplayer.R;
import player.music.lisboa.musicplayer.view.base.BaseController;
import player.music.lisboa.musicplayer.view.library.albums.AlbumsController;
import player.music.lisboa.musicplayer.view.library.artists.ArtistsController;
import player.music.lisboa.musicplayer.view.library.genre.GenresController;

/**
 * Created by Lisboa on 15-Jul-17.
 */

public class LibraryController extends BaseController {

    private static final String[] TABS = new String[]{"Albums", "Artists", "Genres"};

    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;

    private final RouterPagerAdapter pagerAdapter;

    public LibraryController() {
        setHasOptionsMenu(true);

        pagerAdapter = new RouterPagerAdapter(this) {
            @Override
            public void configureRouter(@NonNull Router router, int position) {
                if (!router.hasRootController()) {
                    Controller page;
                    switch(position){
                        case 0: page = new AlbumsController();
                            break;
                        case 1: page = new ArtistsController();
                            break;
                        case 2: page = new GenresController();
                            break;
                        default: page = new AlbumsController();
                    }

                    router.setRoot(RouterTransaction.with(page));
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return TABS[position];
            }
        };
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_library, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.library_menu_items, menu);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        if (!getActivity().isChangingConfigurations()) {
            viewPager.setAdapter(null);
        }
        tabLayout.setupWithViewPager(null);
        super.onDestroyView(view);
    }

    @Override
    protected String getTitle() {
        return "Library";
    }

}
