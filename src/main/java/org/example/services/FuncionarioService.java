package org.example.services;

import org.example.model.Funcionario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuncionarioService {
    public Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        Map<String, List<Funcionario>> mapa = new HashMap<>();

        for (Funcionario funcionario : funcionarios) {
            String funcao = funcionario.getFuncao();
            mapa.computeIfAbsent(funcao, k -> new ArrayList<>()).add(funcionario);
        }
        return mapa;
    }
}
