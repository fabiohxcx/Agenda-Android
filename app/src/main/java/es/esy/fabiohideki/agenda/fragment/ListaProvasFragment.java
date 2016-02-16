package es.esy.fabiohideki.agenda.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import es.esy.fabiohideki.agenda.ProvasActivity;
import es.esy.fabiohideki.agenda.R;
import es.esy.fabiohideki.agenda.modelo.Prova;

/**
 * Created by Fabio on 11/02/2016.
 */
public class ListaProvasFragment extends Fragment {

    private ListView listViewProvas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layoutProvas = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        this.listViewProvas = (ListView) layoutProvas.findViewById(R.id.lista_provas_listview);

        Prova prova1 = new Prova("20/06/2015", "Matemática");
        prova1.setTopicos(Arrays.asList("Álgebra Linear", "Cálculo", "Estatística"));

        Prova prova2 = new Prova("25/07/2015", "Portugues");
        prova2.setTopicos(Arrays.asList("Complemento Nominal", "Orações subordinadas", "Análise sintática"));

        List<Prova> provas = Arrays.asList(prova1, prova2);

        this.listViewProvas.setAdapter(new ArrayAdapter<Prova>(getActivity(), android.R.layout.simple_list_item_1, provas));

        this.listViewProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Prova selecionada = (Prova) adapter.getItemAtPosition(position);

                ProvasActivity calendarioProvas = (ProvasActivity) getActivity();
                calendarioProvas.selecionaProva(selecionada);

                Toast.makeText(getActivity(), "Prova Selecionada: " + selecionada, Toast.LENGTH_SHORT).show();
            }
        });

        return layoutProvas;
    }
}
