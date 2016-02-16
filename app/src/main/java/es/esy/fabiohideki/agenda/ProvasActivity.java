package es.esy.fabiohideki.agenda;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import es.esy.fabiohideki.agenda.fragment.DetalhesProvaFragment;
import es.esy.fabiohideki.agenda.fragment.ListaProvasFragment;
import es.esy.fabiohideki.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (isTablet()) {
            transaction.replace(R.id.provas_detalhes, new DetalhesProvaFragment()).replace(R.id.provas_lista, new ListaProvasFragment());
            //Toast.makeText(this, "Virou tablet", Toast.LENGTH_LONG).show();
        } else {
            transaction.replace(R.id.provas_view, new ListaProvasFragment());
            //Toast.makeText(this, "Virou phone", Toast.LENGTH_LONG).show();
        }

        transaction.commit();
    }

    public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    public void selecionaProva(Prova selecionada) {
        FragmentManager manager = getFragmentManager();

        if (isTablet()) {
            DetalhesProvaFragment detalhesProva = (DetalhesProvaFragment) manager.findFragmentById(R.id.provas_detalhes);

            detalhesProva.populaCamposComDados(selecionada);
        } else {
            Bundle argumentosBundle = new Bundle();
            argumentosBundle.putSerializable("prova", selecionada);

            DetalhesProvaFragment detalhesProva = new DetalhesProvaFragment();
            detalhesProva.setArguments(argumentosBundle);

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.provas_view, detalhesProva);

            transaction.addToBackStack(null);

            transaction.commit();
        }

    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getFragmentManager();
        if (manager.getBackStackEntryCount() > 1) {
            manager.popBackStack();
        } else {
            finish();
        }
    }
}
