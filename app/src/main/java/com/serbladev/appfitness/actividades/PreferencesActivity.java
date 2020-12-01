package com.serbladev.appfitness.actividades;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.serbladev.appfitness.R;

public class PreferencesActivity extends FragmentActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content,
                    new FragmentDePreferencias()).commit();
        }
        public static class FragmentDePreferencias extends PreferenceFragmentCompat {
            @Override
            public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
// Load the preferences from an XML resource
                setPreferencesFromResource(R.xml.preferences_layout_xml, rootKey);
            }
        }
    }
