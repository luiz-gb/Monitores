package org.example;
import java.util.ArrayList;
import java.util.List;

public class CentralDeInformacoes {
    private List<Aluno> listaAlunos = new ArrayList<>();
    private List<EditalMonitoria> listaEditais = new ArrayList<>();

    public List<EditalMonitoria> getListaEditais() {
        return listaEditais;
    }

    public void setListaEditais(List<EditalMonitoria> listaEditais) {
        this.listaEditais = listaEditais;
    }

    public List<Aluno> getTodosAlunos () {
        return listaAlunos;
    }

    public void setTodosAlunos (List<Aluno> listaAlunos) {
        this.listaAlunos = listaAlunos;
    }

    public boolean adicionarAluno(Aluno aluno) {
        try {
            if (this.recuperarAlunoPorMatricula(aluno.getMatricula()) != null) {
                throw new Exception("Já existe um aluno com essa matrícula!");
            }

            this.listaAlunos.add(aluno);
        }
        catch (Exception erro) {
            erro.printStackTrace();
            return false;
        }

        return true;
    }

    public Aluno recuperarAlunoPorMatricula (String matricula) {
        for (int i = 0; i < listaAlunos.size();  i++) {
            if (listaAlunos.get(i).getMatricula().equals(matricula)) {
                return listaAlunos.get(i);
            }
        }

        return null;
    }

    public boolean adicionarEdital(EditalMonitoria edital) {
        try {
            if (recuperarEditalPorId(edital.getId()) != null) {
                throw new Exception("Já existe um edital com essa matrícula!");
            }

            listaEditais.add(edital);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public EditalMonitoria recuperarEditalPorId(long id) {
        if (listaEditais.size() > 0) {
            for (EditalMonitoria edital : listaEditais) {
                if (edital.getId() == id) {
                    return edital;
                }
            }
        }
        return null;
    }

    public List<Disciplina> recuperarInscricoesDeUmAlunoEmUmEdital (String matriculaAluno, long idEdital) {
        if (recuperarEditalPorId(idEdital) != null && recuperarAlunoPorMatricula(matriculaAluno) != null) {
            EditalMonitoria edital = recuperarEditalPorId(idEdital);
            List<Disciplina> listaDisciplinasEncontradas = new ArrayList<>();

            for (Disciplina disciplina : edital.getListaDisciplinas()) {
                if (disciplina.verificarAlunoExiste(matriculaAluno)) listaDisciplinasEncontradas.add(disciplina);
            }

            return listaDisciplinasEncontradas;
        }

        return null;
    }

    public int quantidadeEditaisCadastrados () {
        return this.listaEditais.size();
    }
}
