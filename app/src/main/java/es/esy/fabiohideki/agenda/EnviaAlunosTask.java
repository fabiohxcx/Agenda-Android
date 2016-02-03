package es.esy.fabiohideki.agenda;

import android.os.AsyncTask;

/**
 * Created by Fabio on 02/02/2016.
 */
public class EnviaAlunosTask extends AsyncTask<Object, Object, String> {



    @Override
    protected String doInBackground(Object[] params) {

/*        AlunoDao alunoDao = new AlunoDao(this);
        List<Aluno> alunos = alunoDao.buscaAlunos();
        alunoDao.close();

        String json = new AlunoConverter().toJson(alunos);

        WebClient webClient = new WebClient();
        String resposta = webClient.post(json);*/

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
       // Toast.makeText(context, s, Toast.LENGTH_LONG).show();

        super.onPostExecute(s);
    }


}
