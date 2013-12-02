package com.example.tyriangame;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	
	/*
	 * Vamos definir algumas constantes para 
	 * os nomes das colunas
	 * */
	public static final String KEY_ROWID = "_id";
	public static final String KEY_ISBN = "isbn";
	public static final String KEY_TITLE = "title";
	public static final String KEY_PUBLISHER = "publisher";
	private static final String TAG = "DBAdapter";

	/*
	 * Vamos definir algumas constantes para 
	 * o nome do banco de dados, nome da tabela e 
	 * versão do banco de dados
	 * */
	private static final String DATABASE_NAME = "books";
	private static final String DATABASE_TABLE = "titles";
	private static final int DATABASE_VERSION = 2;

	private static final String DATABASE_CREATE = "create table titles (_id integer primary key autoincrement, "
			+ "isbn text not null, title text not null, "
			+ "publisher text not null);";

	private final Context context;

	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	/***
	 * Essa classe estática extende SQLiteOpenHelper para prover os mecanismos
	 * de abertura/fechamento/manipulação do banco de dados
	 * 
	 * @author Andre
	 * 
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {

		/***
		 * Construtor padrão
		 * 
		 * @param context
		 *            Contexto
		 */
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		/***
		 * Evento de criação do banco de dados, disparado quando se tenta abrir
		 * um banco que não existe
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		/***
		 * Evento de atualização do banco de dados, disparado quando se tenta
		 * abrir um banco de dados existente, mas o Helper informa que a versão
		 * do banco foi alterada, por isso temos que atualizar o banco que já
		 * está gravado em arquivo
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS titles");
			onCreate(db);
		}
	}

	/***
	 * Abre o Banco de dados
	 * 
	 * @return uma instância para o banco de dados SQLite
	 * @throws SQLException
	 *             Lança um SQLException caso ocorra algum erro de SQL
	 */
	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	/***
	 * Fecha o banco de dados
	 */
	public void close() {
		DBHelper.close();
	}

	/***
	 * Insere um livro no banco de dados
	 * 
	 * @param isbn
	 *            Número do ISBN
	 * @param title
	 *            Título da Obra
	 * @param publisher
	 *            Editora
	 * @return O ROWID da linha inserida
	 */
	public long insertTitle(String isbn, String title, String publisher) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ISBN, isbn);
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_PUBLISHER, publisher);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	/***
	 * Apaga um livro do banco de dados
	 * 
	 * @param rowId
	 *            ROWID da linha a ser apagada (usada como PK)
	 * @return O resultado da operação, true é sucesso, false é erro
	 */
	public boolean deleteTitle(long rowId) {
		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/***
	 * Lista todos os livros cadastrados no banco de dados
	 * 
	 * @return Um cursor com os registros
	 */
	public Cursor getAllTitles() {
		return db.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_ISBN,
				KEY_TITLE, KEY_PUBLISHER }, null, null, null, null, null);
	}

	/***
	 * Retorna um livro dado um ROWID
	 * 
	 * @param rowId
	 *            ROWID do registro a ser retornado
	 * @return um Cursor contendo um registro (ou nenhum no caso de não
	 *         encontrar)
	 * @throws SQLException
	 */
	public Cursor getTitle(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_ISBN, KEY_TITLE, KEY_PUBLISHER }, KEY_ROWID
				+ "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	/***
	 * Atualiza um livro
	 * 
	 * @param rowId
	 *            ROWID do livro
	 * @param isbn
	 *            Novo ISBD
	 * @param title
	 *            Novo Titulo
	 * @param publisher
	 *            Nova Editora
	 * @return O resultado da operação, true em caso de sucesso, false me caso
	 *         de erro
	 */
	public boolean updateTitle(long rowId, String isbn, String title,
			String publisher) {
		ContentValues args = new ContentValues();
		args.put(KEY_ISBN, isbn);
		args.put(KEY_TITLE, title);
		args.put(KEY_PUBLISHER, publisher);
		return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
}