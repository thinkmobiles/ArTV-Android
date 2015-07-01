package com.artv.android.system;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.artv.android.R;
import com.artv.android.system.fragments.MediaPlayerFragment;

public class MainActivity extends Activity {
    private FrameLayout mFragmentContainer;

    @Override
    protected final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.btnCrash_AM)).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View v) {
                throw new RuntimeException("Test crash yo");
            }
        });

        mFragmentContainer = (FrameLayout) findViewById(R.id.flFragmentContainer_MA);

        getFragmentManager().beginTransaction().
                add(R.id.flFragmentContainer_MA,new MediaPlayerFragment()).commit();
    }

}
