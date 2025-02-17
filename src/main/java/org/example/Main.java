package org.example;

import org.example.dao.FuncionarioDAO;
import org.example.model.Funcionario;
import org.example.services.FuncionarioService;
import org.example.util.DatabaseSetup;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Comentei os requisitos/ações com o respectivo número, ex:
        // "3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela acima."
        // Algumas lógicas não estão dentro completamente do arquivo Main, caso seja necessário verificar
        // Basta dar um ctrl + F no projeto e pesquisar pelo ponto em questão.

        DatabaseSetup.setupDatabase();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        FuncionarioService funcionarioService = new FuncionarioService();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);

        // 3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela...
        funcionarioDAO.popularBanco();

        // 3.2 – Remover o funcionário “João” da lista
        funcionarioDAO.removerPorNome("João");

        // 3.3 – Imprimir todos os funcionários com todas suas informações
        funcionarioDAO.listarFuncionarios().forEach(System.out::println);

        // 3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
        funcionarioDAO.aumentarSalarioTodos(10.00);
        funcionarioDAO.listarFuncionarios().forEach(System.out::println);

        // 3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.
        List<Funcionario> funcionarios = funcionarioDAO.listarFuncionarios();
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarioService.agruparPorFuncao(funcionarios);

        // 3.6 – Imprimir os funcionários, agrupados por função.
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\nFunção: " + funcao);
            lista.forEach(f -> System.out.println(" - " + f.getNome()));
        });

        // 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
        StringBuilder aniversariantes = new StringBuilder("\nFuncionários que fazem aniversário no mês 10 e 12: ");

        funcionarios.forEach(funcionario -> {
            int mesAniversario = funcionario.getDataNascimento().getMonthValue();
            if (mesAniversario == 10 || mesAniversario == 12) {
                aniversariantes.append(funcionario.getNome()).append(", ");
            }
        });

        if (aniversariantes.toString().endsWith(", ")) {
            aniversariantes.setLength(aniversariantes.length() - 2);
        }
        System.out.println(aniversariantes);

        // 3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparingInt(f -> f.getDataNascimento().getYear())).orElse(null);
        System.out.println("\nFuncionário mais velho: " + maisVelho.getNome() + ", idade: " + (2025 - maisVelho.getDataNascimento().getYear()));

        // 3.10 – Imprimir a lista de funcionários por ordem alfabética.
        funcionarios.sort(Comparator.comparing(Funcionario::getNome));
        System.out.println("\nFuncionários ordenados alfabeticamente");
        funcionarios.forEach(System.out::println);

        // 3.11 – Imprimir o total dos salários dos funcionários.
        Double total = funcionarios.stream()
                .mapToDouble(Funcionario::getSalario)
                .sum();
        System.out.println("\nTotal dos salários: R$ " + numberFormat.format(total));

        // 3.12 – Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.
        System.out.println("\nSalários mínimo por funcionário");
        funcionarios.forEach(funcionario -> {
            Double salarios = funcionario.getSalario() / 1212;
            System.out.println(funcionario.getNome() + " recebe um total de " + numberFormat.format(salarios) + " salário(s) mínimo(s)");
        });
    }
}