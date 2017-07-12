package com.ghostship.cities;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by scottkeller on 5/18/17.
 */

public class GlView extends GLSurfaceView {

  private static final float TOUCH_SCALE_FACTOR = 180.0f / 320;

  private final SurfaceRenderer renderer = new SurfaceRenderer();

  private float mPreviousX;
  private float mPreviousY;

  public GlView(Context context) {
    this(context, null);
  }

  public GlView(Context context, AttributeSet attrs) {
    super(context, attrs);

    setEGLContextClientVersion(2);
    setRenderer(renderer);
    setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    // MotionEvent reports input details from the touch screen
    // and other input controls. In this case, you are only
    // interested in events where the touch position changed.

    float x = event.getX();
    float y = event.getY();

    switch (event.getAction()) {
      case MotionEvent.ACTION_MOVE:

        float dx = x - mPreviousX;
        float dy = y - mPreviousY;

        // reverse direction of rotation above the mid-line
        if (y > getHeight() / 2) {
          dx = dx * -1;
        }

        // reverse direction of rotation to left of the mid-line
        if (x < getWidth() / 2) {
          dy = dy * -1;
        }

        float newAngle = renderer.getAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR);
        renderer.setAngle(newAngle % 360f);
        requestRender();
    }

    mPreviousX = x;
    mPreviousY = y;
    return true;
  }

  public void setAngle(float angle) {
    renderer.setAngle(angle);
    requestRender();
  }
}
