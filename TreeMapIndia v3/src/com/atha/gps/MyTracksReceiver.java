/*
 * Copyright 2011 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.atha.gps;





import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.atha.treemapindia.R;

/**
 * A receiver to receive MyTracks notifications.
 * 
 * @author Jimmy Shih
 */
public class MyTracksReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    String action = intent.getAction();
    long trackId = intent.getLongExtra(context.getString(R.string.track_id_broadcast_extra), -1L);
    Toast.makeText(context, action + " " + trackId, Toast.LENGTH_LONG).show();
  }
}
