package br.com.ecommerce.marcel.philippe.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.ecommerce.marcel.philippe.modelo.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByCpf(String cpf);
	
	List<Usuario> queryByNomeLike(String nome);

}
