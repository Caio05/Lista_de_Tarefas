/*Caio Castori de Oliveira RA 818234790*/
package br.usjt.ads20.listadetarefas;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private EditText txtTarefa;
    private ArrayList<Tarefa> tarefas;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTarefa = findViewById(R.id.tarefa);
        listView = findViewById(R.id.lista);
        tarefas = TarefaDAO.recuperarTodas(this);
        Log.d(Tarefa.TAG, "lista de tarefas " + tarefas);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TarefaDAO.apagarTarefa(tarefas.get(i).getId());
                tarefas.remove(i);
                setAdapter();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TarefaDAO.completarTarefa(tarefas.get(i).getId(),getApplicationContext());
                setAdapter();
                return;
            }
        });
    }


    public void setAdapter() {
        ArrayList<String> descricao = new ArrayList<>();
        //ordena o arraylist de tarefas
        Collections.sort(tarefas);

        for (Tarefa tarefa : tarefas) {
            descricao.add(tarefa.getTarefa());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, descricao);
        listView.setAdapter(adapter);
    }

    public void inserirTarefa(View view) {
        String descricao = txtTarefa.getText().toString();
        Tarefa tarefa = new Tarefa();
        tarefa.setTarefa(descricao);
        String id = TarefaDAO.inserir(tarefa);
        tarefa.setId(id);
        tarefas.add(tarefa);
        txtTarefa.setText("");
        setAdapter();
    }
}