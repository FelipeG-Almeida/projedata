package org.example.dao;

import org.example.model.Funcionario;
import org.example.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FuncionarioDAO {
    private static final Connection conn;

    static {
        try {
            conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela...
    public void popularBanco() {
        String sql = "INSERT INTO funcionarios (nome, data_nascimento, salario, funcao) VALUES (?, ?, ?, ?)";

        List<Funcionario> funcionarios = Arrays.asList(
                new Funcionario("Maria", LocalDate.of(2000, 10, 18), 2009.44, "Operador"),
                new Funcionario("João", LocalDate.of(1990, 5, 12), 2284.38, "Operador"),
                new Funcionario("Caio", LocalDate.of(1961, 5, 2), 9836.14, "Coordenador"),
                new Funcionario("Miguel", LocalDate.of(1988, 10, 14), 19119.88, "Diretor"),
                new Funcionario("Alice", LocalDate.of(1995, 1, 5), 2234.68, "Recepcionista"),
                new Funcionario("Heitor", LocalDate.of(1999, 11, 19), 1582.72, "Operador"),
                new Funcionario("Arthur", LocalDate.of(1993, 3, 31), 4071.84, "Contador"),
                new Funcionario("Laura", LocalDate.of(1994, 7, 8), 3017.45, "Gerente"),
                new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), 1606.85, "Eletricista"),
                new Funcionario("Helena", LocalDate.of(1996, 9, 2), 2799.93, "Gerente")
        );

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Funcionario funcionario : funcionarios) {
                stmt.setString(1, funcionario.getNome());
                stmt.setDate(2, java.sql.Date.valueOf(funcionario.getDataNascimento()));
                stmt.setDouble(3, funcionario.getSalario());
                stmt.setString(4, funcionario.getFuncao());
                stmt.executeUpdate();
            }
            System.out.println("Banco populado com sucesso!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3.3 – Imprimir todos os funcionários com todas suas informações
    public List<Funcionario> listarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                funcionarios.add(new Funcionario(
                        rs.getString("nome"),
                        rs.getDate("data_nascimento").toLocalDate(),
                        rs.getDouble("salario"),
                        rs.getString("funcao")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return funcionarios;
    }

    // 3.2 – Remover o funcionário “João” da lista
    public void removerPorNome(String nome) {
        String sql = "DELETE FROM funcionarios WHERE nome = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.executeUpdate();
            System.out.println("Funcionário(a) " + nome + " removido com sucesso!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
    public void aumentarSalarioTodos(Double porcentagem) {
        String sql = "UPDATE funcionarios SET salario = salario * ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, 1 + (porcentagem / 100));
            stmt.executeUpdate();
            System.out.println("\nSalários aumentados em " + porcentagem + "% com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
