package es.esy.fabiohideki.agenda.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import es.esy.fabiohideki.agenda.R;
import es.esy.fabiohideki.agenda.modelo.Aluno;

/**
 * Created by Fabio on 28/01/2016.
 */
public class ListaAlunosAdapter extends BaseAdapter {
    private final Activity activity;
    private final List<Aluno> alunos;

    public ListaAlunosAdapter(Activity activity, List<Aluno> alunos) {
        this.activity = activity;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.item, parent, false);

        if (position % 2 == 0) {
            view.setBackgroundColor(ContextCompat.getColor(activity, R.color.linha_par));
        } else {
            view.setBackgroundColor(ContextCompat.getColor(activity, R.color.linha_impar));
        }

        Aluno aluno = alunos.get(position);

        TextView nome = (TextView) view.findViewById(R.id.item_nome);
        nome.setText(aluno.getNome());

        TextView telefone = (TextView) view.findViewById(R.id.item_telefone);
        telefone.setText("tel: " + aluno.getTelefone());

        TextView site = (TextView) view.findViewById(R.id.item_site);

        if (site != null) {
            site.setText("site: " + aluno.getSite());
        }

        Bitmap bm;

        if (aluno.getCaminhoFoto() != null) {

            try {
                String localImagemReduzido = aluno.getCaminhoFoto().split("\\.jpg")[0] + "_reduzido.jpg";
                bm = BitmapFactory.decodeFile(localImagemReduzido);
            } catch (Exception e) {
                bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
                bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
            }

        } else {
            bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
            bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
        }

        ImageView imagem = (ImageView) view.findViewById(R.id.item_foto);
        imagem.setImageBitmap(bm);

        return view;
    }
}
