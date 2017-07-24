package player.music.lisboa.musicplayer.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * Created by Lisboa on 24-Jul-17.
 */

@SuppressLint("AppCompatCustomView")
public class FadingImageView extends ImageView {

	private FadeSide mFadeSide;
	private Context context;

	public enum FadeSide {
		BOTTOM_SIDE
	}

	public FadingImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;

		init();
	}

	public FadingImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		init();
	}

	public FadingImageView(Context context) {
		super(context);
		this.context = context;

		init();
	}

	private void init() {
		this.setVerticalFadingEdgeEnabled(true);
		this.setEdgeLength(25);
		this.setFadeDirection(FadeSide.BOTTOM_SIDE);
	}

	public void setFadeDirection(FadeSide side) {
		this.mFadeSide = side;
	}

	public void setEdgeLength(int length) {
		this.setFadingEdgeLength(getPixels(length));
	}

	@Override
	protected float getBottomFadingEdgeStrength() {
		return mFadeSide.equals(FadeSide.BOTTOM_SIDE) ? 1.0f : 0.0f;
	}

	@Override
	public boolean hasOverlappingRendering() {
		return true;
	}

	@Override
	public boolean onSetAlpha(int alpha) {
		return true;
	}

	private int getPixels(int dipValue) {
		Resources r = context.getResources();

		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dipValue, r.getDisplayMetrics());
	}
}