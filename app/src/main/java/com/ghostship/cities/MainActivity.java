package com.ghostship.cities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import com.ghostship.cities.ItemsAdapter.OnItemClickListener;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  private GlView surfaceView;
  private RecyclerView recyclerView;
  private FloatingActionButton fabView;
  private ItemsAdapter itemsAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    surfaceView = (GlView) findViewById(R.id.surface_view);
    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    fabView = (FloatingActionButton) findViewById(R.id.fab);

    itemsAdapter = new ItemsAdapter();

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(itemsAdapter);

    itemsAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(String itemData) {
        float angle;

        switch (itemData) {
          case "Side 1":
            angle = 51f;
            break;
          case "Side 2":
            angle = 102f;
            break;
          case "Side 3":
            angle = 153f;
            break;
          case "Side 4":
            angle = 204f;
            break;
          case "Side 5":
            angle = 255f;
            break;
          case "Side 6":
            angle = 306f;
            break;
          default:
            angle = 0;
        }

        surfaceView.setAngle(angle);
      }
    });

    fabView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        surfaceView.setAngle(0);
      }
    });

    setData();
  }

  @Override
  protected void onPause() {
    super.onPause();
    surfaceView.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    surfaceView.onResume();
  }

  private void setData() {
    ArrayList<String> data = new ArrayList<>();
    data.add("Side 1");
    data.add("Side 2");
    data.add("Side 3");
    data.add("Side 4");
    data.add("Side 5");
    data.add("Side 6");

    itemsAdapter.setItems(data);
  }
}
