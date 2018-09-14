package com.ghostship.cities;

import android.app.Application;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public final class App extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    AppCenter.start(this, "d3b74233-f7a4-426a-bc66-6591f94129ae",
        Analytics.class, Crashes.class);
  }
}
