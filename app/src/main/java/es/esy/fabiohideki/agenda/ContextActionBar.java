package es.esy.fabiohideki.agenda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;

import es.esy.fabiohideki.agenda.dao.AlunoDao;
import es.esy.fabiohideki.agenda.modelo.Aluno;

/**
 * Created by Fabio on 25/01/2016.
 */
public class ContextActionBar implements android.support.v7.view.ActionMode.Callback {

    private Aluno alunoSelecionado;
    private ListaAlunosActivity activity;



    public ContextActionBar(ListaAlunosActivity activity, Aluno alunoSelecionado) {
        this.activity = activity;
        this.alunoSelecionado = alunoSelecionado;
    }

    @Override
    public boolean onCreateActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                new AlertDialog.Builder(activity).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deletar")
                        .setMessage("Deseja mesmo deletar?")
                        .setPositiveButton("Quero", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                AlunoDao dao = new AlunoDao(activity);
                                dao.deleta(alunoSelecionado);
                                dao.close();

                                activity.carregaListaAlunos();
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


        return true;
    }

    @Override
    public boolean onPrepareActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(android.support.v7.view.ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(android.support.v7.view.ActionMode mode) {

    }
}
