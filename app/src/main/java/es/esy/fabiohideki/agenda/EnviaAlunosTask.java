package es.esy.fabiohideki.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import es.esy.fabiohideki.agenda.Converter.AlunoConverter;
import es.esy.fabiohideki.agenda.dao.AlunoDao;
import es.esy.fabiohideki.agenda.modelo.Aluno;
import es.esy.fabiohideki.agenda.support.WebClient;

/**
 * Created by Fabio on 02/02/2016.
 */
public class EnviaAlunosTask extends AsyncTask<Object, Object, String> {

    private final Context context;
    private ProgressDialog progressDialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = ProgressDialog.show(context, "Aguarde...", "Envio de dados para a web", true, true);
    }

    @Override
    protected String doInBackground(Object[] params) {

        AlunoDao alunoDao = new AlunoDao(context);
        List<Aluno> alunos = alunoDao.buscaAlunos();
        alunoDao.close();

        String json = new AlunoConverter().toJson(alunos);

        WebClient webClient = new WebClient();
        String resposta = webClient.post(json);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resposta;
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();

        progressDialog.dismiss();

        super.onPostExecute(s);
    }


}
