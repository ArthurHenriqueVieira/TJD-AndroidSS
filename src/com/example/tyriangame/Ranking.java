package com.example.tyriangame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class Ranking extends Activity {
	private Context contexto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ranking);
		contexto = this;
		
		ListView lvLivros = (ListView) findViewById(R.id.listViewLivros);
		DBAdapter adapter = new DBAdapter(this.getApplicationContext());
					
		adapter.open();
							
		Cursor c = adapter.getAllTitles();
		
		startManagingCursor(c);
		
		String[] columns = new String[] { "isbn", "title", "publisher" };
		int[] to = new int[] { R.id.txtIsbn, R.id.txtTitle, R.id.txtPublisher };
		
		ListAdapter listAdapter = new SimpleCursorAdapter(this, R.layout.linha, c, columns, to);
		
		lvLivros.setAdapter(listAdapter);
			
		lvLivros.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View parentView, int position, long id) {

			}
		});
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		Intent i = new Intent(contexto, MainMenu.class);
		startActivity(i);
	}
}
