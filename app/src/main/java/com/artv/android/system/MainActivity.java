package com.artv.android.system;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.artv.android.R;

public class MainActivity extends Activity {

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
    }

}
