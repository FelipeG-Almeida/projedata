package org.example.model;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class Funcionario extends Pessoa {
    private final String funcao;
    private final NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
    private final Double salario;

    public Funcionario(String nome, LocalDate dataNascimento, Double salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public Double getSalario() {
        return salario;
    }

    public String getFuncao() {
        return funcao;
    }

    @Override
    public String toString() {
        numberFormat.setMinimumFractionDigits(2);
        return super.toString() + ", Salário: R$ " + numberFormat.format(salario) + ", Função: " + funcao;
    }
}
