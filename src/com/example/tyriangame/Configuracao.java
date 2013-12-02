package com.example.tyriangame;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Configuracao extends PreferenceActivity {
	@Override
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
	}
}

