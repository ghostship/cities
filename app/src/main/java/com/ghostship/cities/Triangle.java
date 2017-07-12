package com.ghostship.cities;

import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by scottkeller on 5/18/17.
 */

final class Triangle {

  private static final int COORDS_PER_VERTEX = 3;

  // Verticies of the triangle
  private static final float VERTICES[] = {
      0.0f, 0.622008459f, 0.0f, // top
      -0.5f, -0.311004243f, 0.0f, // bottom left
      0.5f, -0.311004243f, 0.0f  // bottom right
  };

  // Set color with red, green, blue and alpha (opacity) values
  private static final float COLOR[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};

  // Shader for rendering the vertices
  private static final String VERTEX_SHADER =
      // This matrix member variable provides a hook to manipulate
      // the coordinates of the objects that use this vertex shader
      "uniform mat4 uMVPMatrix;" +
          "attribute vec4 vPosition;" +
          "void main() {" +
          // the matrix must be included as a modifier of gl_Position
          // Note that the uMVPMatrix factor *must be first* in order
          // for the matrix multiplication product to be correct.
          "  gl_Position = uMVPMatrix * vPosition;" +
          "}";

  // Shader for rendering the face
  private static final String FRAGMENT_SHADER =
      "precision mediump float;" +
          "uniform vec4 vColor;" +
          "void main() {" +
          "  gl_FragColor = vColor;" +
          "}";

  private final int vertexCount = VERTICES.length / COORDS_PER_VERTEX;
  private final int vertexStride = COORDS_PER_VERTEX * 4;

  private FloatBuffer vertexBuffer;  // Buffer for vertex-array
  private int program;

  private int positionHandle;
  private int colorHandle;
  private int mvpMatrixHandle;

  Triangle() {
    // Setup vertex-array buffer
    ByteBuffer bb = ByteBuffer.allocateDirect(VERTICES.length * 4);
    // use the device hardware's native byte order
    bb.order(ByteOrder.nativeOrder());

    // Create a floating point buffer from the ByteBuffer
    vertexBuffer = bb.asFloatBuffer();
    // Add the coordinates to the FloatBuffer
    vertexBuffer.put(VERTICES);
    // Set the buffer to read the first coordinate
    vertexBuffer.position(0);

    int vertexShader = SurfaceRenderer.loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER);
    int fragmentShader = SurfaceRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);

    program = GLES20.glCreateProgram();
    GLES20.glAttachShader(program, vertexShader);
    GLES20.glAttachShader(program, fragmentShader);
    GLES20.glLinkProgram(program);
  }

  public void draw(float[] mvpMatrix) {
    // Add program to OpenGL ES environment
    GLES20.glUseProgram(program);

    // Get handle to vertex shader's vPosition member
    positionHandle = GLES20.glGetAttribLocation(program, "vPosition");

    // Enable a handle to the triangle vertices
    GLES20.glEnableVertexAttribArray(positionHandle);

    // Prepare the triangle coordinate data
    GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false,
        vertexStride, vertexBuffer);

    // Get handle to fragment shader's vColor member
    colorHandle = GLES20.glGetUniformLocation(program, "vColor");

    // Set color for drawing the triangle
    GLES20.glUniform4fv(colorHandle, 1, COLOR, 0);

    // Get handle to shape's transformation matrix
    mvpMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");

    // Pass the projection and view transformation to the shader
    GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

    // Draw the triangle
    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

    // Disable vertex array
    GLES20.glDisableVertexAttribArray(positionHandle);
  }
}
