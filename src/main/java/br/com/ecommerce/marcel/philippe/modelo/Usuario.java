package br.com.ecommerce.marcel.philippe.modelo;

import java.time.LocalDate;

import br.com.ecommerce.marcel.philippe.dto.UsuarioDTO;
import br.com.ecommerce.marcel.philippe.utils.DataUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nome;
	private String cpf;
	private String endereco;
	private String email;
	private String telefone;
	private LocalDate dataCadastro;
	private String key;

	public static Usuario convert(UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario();
		usuario.setNome(usuarioDTO.getNome());
		usuario.setEndereco(usuarioDTO.getEndereco());
		usuario.setCpf(usuarioDTO.getCpf());
		usuario.setEmail(usuarioDTO.getEmail());
		usuario.setTelefone(usuarioDTO.getTelefone());
		usuario.setDataCadastro(DataUtil.transformarStringEmLocalDate(usuarioDTO.getDataCadastro()));
		usuario.setKey(usuarioDTO.getKey());
		return usuario;
	}
}
