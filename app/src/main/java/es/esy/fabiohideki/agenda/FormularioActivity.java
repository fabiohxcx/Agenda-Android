package es.esy.fabiohideki.agenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import es.esy.fabiohideki.agenda.dao.AlunoDao;
import es.esy.fabiohideki.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    public static final int TIRA_FOTO = 123;
    private FormularioHelper helper;
    private String localArquivoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();

        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        helper = new FormularioHelper(this);

        if (aluno != null) {
            helper.preencheformulario(aluno);
        }

        Button foto = helper.getFotoButton();
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localArquivoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";

                Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri localFoto = Uri.fromFile(new File(localArquivoFoto));
                irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);

                startActivityForResult(irParaCamera, TIRA_FOTO);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TIRA_FOTO) {
            if (resultCode == Activity.RESULT_OK) {
                helper.carregaImagem(this.localArquivoFoto);

                //Cria Imagem miniatura usada na listView###########################
                Bitmap imagemFoto = BitmapFactory.decodeFile(localArquivoFoto);
                Bitmap imagemFotoReduzida_lista = Bitmap.createScaledBitmap(imagemFoto, 100, 100, true);
                String partes[] = localArquivoFoto.split("\\.jpg");
                String localImagemReduzido = partes[0] + "_reduzido.jpg";

                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(localImagemReduzido);
                    imagemFotoReduzida_lista.compress(Bitmap.CompressFormat.JPEG, 85, out);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //###################################################################

            } else {
                this.localArquivoFoto = null;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_formulario_salvar:

                Aluno aluno = helper.pegaAluno();

                AlunoDao dao = new AlunoDao(FormularioActivity.this);

                if (aluno.getId() != null) {
                    dao.altera(aluno);
                } else {
                    dao.insere(aluno);
                }

                dao.close();

                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
