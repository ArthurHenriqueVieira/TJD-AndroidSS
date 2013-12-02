package com.example.tyriangame;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.Button;

public class MainMenu extends Activity {
	
	//Muda a velocidade do acelerometro
	static float velocidadeAcele;
	
	//Cria os botoes.
	Button btn_sair;
	Button btn_ajuda;
	Button btn_ranking;
	Button btn_configuracoes;
	Button btn_jogo;
	
	static Sons som;
	
	//Cria um nivel de dificuldade
	public static float dificuldade;
	
	Context contexto;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		btn_sair = (Button)findViewById(R.id.btn_sair);
		btn_ajuda = (Button)findViewById(R.id.btn_ajuda);
		btn_ranking = (Button)findViewById(R.id.btn_ranking);
		btn_configuracoes = (Button)findViewById(R.id.btn_configuracoes);
		btn_jogo = (Button)findViewById(R.id.btn_novoJogo);
		
		contexto = this;
		
		SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(contexto);
		int volume = Integer.parseInt(preferencias.getString("volume", ""));
		
		//Cria um media Player
		som = new Sons(this, volume);
		som.tocarMusicaDeFundo();
		
		if(btn_ajuda != null) {
			
			btn_ajuda.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent();
					i.setClass(getApplicationContext(), Ajuda.class);
        			startActivity(i);
				}
			});
		}
		
		if(btn_ranking != null) {
			
			btn_ranking.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent();
					i.setClass(getApplicationContext(), Ranking.class);
        			startActivity(i);
				}
			});
		}
		if(btn_configuracoes != null) {
			
			btn_configuracoes.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent();
					i.setClass(getApplicationContext(), Configuracao.class);
        			startActivity(i);
				}
			});
		}
		if(btn_jogo != null) {
			
			btn_jogo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					AlertDialog.Builder dificult = new AlertDialog.Builder(contexto);
					
					dificult.setMessage("Escolha a dificuldade");
					dificult.setPositiveButton("Fácil",
				            new DialogInterface.OnClickListener() {
				                public void onClick(DialogInterface dialog, int id) {
				                    dialog.cancel();
				                    dificuldade = 1f;
				                    Intent i = new Intent();
									i.setClass(getApplicationContext(), Jogo.class);
				        			startActivity(i);
				                }
				            });
					dificult.setNeutralButton("Médio",
				            new DialogInterface.OnClickListener() {
				                public void onClick(DialogInterface dialog, int id) {
				                    dialog.cancel();
				                    dificuldade = 2f;
				                    Intent i = new Intent();
									i.setClass(getApplicationContext(), Jogo.class);
				        			startActivity(i);
				                }
				            });
					dificult.setNegativeButton("Difícil",
				            new DialogInterface.OnClickListener() {
				                public void onClick(DialogInterface dialog, int id) {
				                    dialog.cancel();
				                    dificuldade = 6f;
				                    Intent i = new Intent();
									i.setClass(getApplicationContext(), Jogo.class);
				        			startActivity(i);
				                }
				            });
					AlertDialog alert = dificult.create();
					alert.show();
				}
			});
		}
		
		//Botão que fecha o jogo
		if(btn_sair != null) {
			
			btn_sair.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					finish();
					System.exit(0);
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	public void onResume() {
		super.onResume();
		
		som.tocarMusicaDeFundo();
	}
	
	public void onPause(){
		super.onPause();
		
		som.pausarMusicaDeFundo();
	}
}
