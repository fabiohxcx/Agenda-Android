package es.esy.fabiohideki.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import es.esy.fabiohideki.agenda.modelo.Aluno;

/**
 * Created by Fabio on 14/01/2016.
 */
public class AlunoDao extends SQLiteOpenHelper {

    public static final int VERSION = 3;

    public AlunoDao(Context context) {
        super(context, "Agenda", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCriaTabela = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL);";
        db.execSQL(sqlCriaTabela);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT;";
        db.execSQL(sql);
    }

    public void insere(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosDoAluno(aluno);

        db.insert("Alunos", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosDoAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());
        return dados;
    }

    public List<Aluno> buscaAlunos() {

        String sql = "SELECT * FROM Alunos;";
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(sql, null);

        List<Aluno> alunos = new ArrayList<Aluno>();

        while (c.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);
        }

        c.close();

        return alunos;
    }

    public void deleta(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {aluno.getId().toString()};
        db.delete("alunos", "id = ?", params);
    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosDoAluno(aluno);
        String[] params = {(aluno.getId().toString())};
        db.update("alunos", dados, "id = ?", params);
    }

    public Aluno getAlunoPorId(Long id) {
        String[] args = {id.toString()};
        Cursor c = getWritableDatabase().rawQuery("SELECT * FROM Alunos " + "WHERE id=?;", args);
        c.moveToFirst();

        Aluno aluno = new Aluno();
        aluno.setId(c.getLong(c.getColumnIndex("id")));
        aluno.setNome(c.getString(c.getColumnIndex("nome")));
        aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
        aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
        aluno.setSite(c.getString(c.getColumnIndex("site")));
        aluno.setNota(c.getDouble(c.getColumnIndex("nota")));

        c.close();

        return aluno;
    }
}
