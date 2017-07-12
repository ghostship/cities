package com.ghostship.cities;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.util.Log;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by scottkeller on 5/18/17.
 */

final class SurfaceRenderer implements Renderer {

  // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
  private final float[] mvpMatrix = new float[16];

  private final float[] projectionMatrix = new float[16];
  private final float[] viewMatrix = new float[16];

  private Triangle triangle;
  private float[] rotationMatrix = new float[16];
  private volatile float angle;

  SurfaceRenderer() {
  }

  @Override
  public void onSurfaceCreated(GL10 unused, EGLConfig config) {
    // Set the background frame color
    GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

    this.triangle = new Triangle();
  }

  @Override
  public void onSurfaceChanged(GL10 unused, int width, int height) {
    // Set the viewport to cover the entire window
    GLES20.glViewport(0, 0, width, height);

    float ratio = (float) width / height;

    // this projection matrix is applied to object coordinates
    // in the onDrawFrame() method
    Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
  }

  @Override
  public void onDrawFrame(GL10 unused) {
    float[] scratch = new float[16];

    // Redraw background color
    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

    // Set the camera position (View matrix)
    Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

    // Calculate the projection and view transformation
    Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

    Log.e("s", angle + " ");
    // Create a rotation transformation for the triangle
    Matrix.setRotateM(rotationMatrix, 0, angle, 0, 0, 1.0f);

    // Combine the rotation matrix with the projection and camera view
    // Note that the mMVPMatrix factor *must be first* in order
    // for the matrix multiplication product to be correct.
    Matrix.multiplyMM(scratch, 0, mvpMatrix, 0, rotationMatrix, 0);

    triangle.draw(scratch);
  }

  static int loadShader(int type, String shaderCode) {

    // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
    // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
    int shader = GLES20.glCreateShader(type);

    // add the source code to the shader and compile it
    GLES20.glShaderSource(shader, shaderCode);
    GLES20.glCompileShader(shader);

    return shader;
  }

  float getAngle() {
    return angle;
  }

  void setAngle(float angle) {
    this.angle = angle;
  }
}
