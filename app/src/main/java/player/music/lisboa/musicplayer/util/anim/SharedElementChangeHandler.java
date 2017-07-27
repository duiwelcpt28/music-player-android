package player.music.lisboa.musicplayer.util.anim;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeScroll;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.changehandler.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lisboa on 21-Jul-17.
 */

public class SharedElementChangeHandler extends SharedElementTransitionChangeHandler {

	private static final String KEY_WAIT_FOR_TRANSITION_NAMES = "SharedElementTransitionChangeHandler.names";

	private final ArrayList<String> names;

	public SharedElementChangeHandler() {
		names = new ArrayList<>();
	}

	public SharedElementChangeHandler(@NonNull List<String> waitForTransitionNames) {
		names = new ArrayList<>(waitForTransitionNames);
	}

	public SharedElementChangeHandler(String... sharedElementNames) {
		this();
		Collections.addAll(names, sharedElementNames);
	}

	@Override
	public void saveToBundle(@NonNull Bundle bundle) {
		bundle.putStringArrayList(KEY_WAIT_FOR_TRANSITION_NAMES, names);
	}

	@Override
	public void restoreFromBundle(@NonNull Bundle bundle) {
		List<String> savedNames = bundle.getStringArrayList(KEY_WAIT_FOR_TRANSITION_NAMES);
		if (savedNames != null) {
			names.addAll(savedNames);
		}
	}

	@Nullable
	public Transition getExitTransition(@NonNull ViewGroup container, @Nullable View from, @Nullable View to, boolean isPush) {
		if (isPush) {
			return new Explode();
		} else {
			return new Slide(Gravity.BOTTOM);
		}
	}

	@Override
	@Nullable
	public Transition getSharedElementTransition(@NonNull ViewGroup container, @Nullable View from, @Nullable View to, boolean isPush) {
		return new TransitionSet()
				.addTransition(new ChangeBounds())
				.addTransition(new ChangeClipBounds())
				.addTransition(new ChangeTransform());
	}

	@Override
	@Nullable
	public Transition getEnterTransition(@NonNull ViewGroup container, @Nullable View from, @Nullable View to, boolean isPush) {
		if (isPush) {
			return new Slide(Gravity.BOTTOM);
		} else {
			return new Explode();
		}
	}

	@Override
	public void configureSharedElements(@NonNull ViewGroup container, @Nullable View from, @Nullable View to, boolean isPush) {
		for (String name : names) {
			addSharedElement(name);
			// this line seems to be causing the album title and cover to be switched on exit transiton
			// making the title on top of the cover in the AlbumsController
			//waitOnSharedElementNamed(name);
		}
	}

}