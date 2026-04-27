package br.com.fiap.financas.repositories;

import br.com.fiap.financas.models.Despesas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancaRepository extends JpaRepository<Despesas, Long> {
}
