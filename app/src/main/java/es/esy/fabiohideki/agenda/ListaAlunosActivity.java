package es.esy.fabiohideki.agenda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import es.esy.fabiohideki.agenda.adapter.ListaAlunosAdapter;
import es.esy.fabiohideki.agenda.dao.AlunoDao;
import es.esy.fabiohideki.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);


                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                intentVaiProFormulario.putExtra("aluno", aluno);
                startActivity(intentVaiProFormulario);
            }
        });


//        listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
//
//                Aluno alunoSelecionado = (Aluno) adapter.getItemAtPosition(position);
//
//                ContextActionBar actionBar = new ContextActionBar(ListaAlunosActivity.this, alunoSelecionado);
//                ListaAlunosActivity.this.startSupportActionMode(actionBar);
//
//                return true;
//            }
//        });


        Button fab = (Button) findViewById(R.id.lista_alunos_adicionar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });

        registerForContextMenu(listaAlunos);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        final Aluno alunoSelecionado = (Aluno) listaAlunos.getItemAtPosition(info.position);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                new AlertDialog.Builder(ListaAlunosActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deletar")
                        .setMessage("Deseja mesmo deletar?")
                        .setPositiveButton("Quero", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                AlunoDao dao = new AlunoDao(ListaAlunosActivity.this);
                                dao.deleta(alunoSelecionado);
                                dao.close();

                                carregaListaAlunos();
                            }
                        }).setNegativeButton("Não", null).show();


                return true;
            }
        });

        MenuItem ligar = menu.add("Ligar");
        ligar.setIcon(android.R.drawable.ic_menu_call);
        Intent intentLigar = new Intent(Intent.ACTION_CALL);
        intentLigar.setData(Uri.parse("tel:" + alunoSelecionado.getTelefone()));
        ligar.setIntent(intentLigar);

        MenuItem sms = menu.add("Enviar SMS");
        Intent intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.setData(Uri.parse("sms:" + alunoSelecionado.getTelefone()));
        intentSms.putExtra("sms_body", "Mensagem");
        sms.setIntent(intentSms);

        MenuItem mapa = menu.add("Achar no Mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        String endereco = alunoSelecionado.getEndereco();
        intentMapa.setData(Uri.parse("geo:0,0?z=14&q=" + Uri.encode(endereco)));
        mapa.setIntent(intentMapa);

        MenuItem site = menu.add("Navegar no Site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        intentSite.setData(Uri.parse("http:" + alunoSelecionado.getSite()));
        site.setIntent(intentSite);

        MenuItem share = menu.add("Compartilhar");
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_SUBJECT, "Assunto Compartilahdo");
        intentShare.putExtra(Intent.EXTRA_TEXT, "Texto Compartilhado");
        share.setIntent(intentShare);

    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaListaAlunos();
    }

    public void carregaListaAlunos() {
        AlunoDao dao = new AlunoDao(this);
        List<Aluno> alunos = dao.buscaAlunos();

        //ArrayAdapter<Aluno> adapterAlunos = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        //listaAlunos.setAdapter(adapterAlunos);
        ListaAlunosAdapter adapter = new ListaAlunosAdapter(this, alunos);

        listaAlunos.setAdapter(adapter);

    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
