package br.com.ecommerce.marcel.philippe.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ecommerce.marcel.philippe.dto.UsuarioDTO;
import br.com.ecommerce.marcel.philippe.modelo.Usuario;
import br.com.ecommerce.marcel.philippe.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<UsuarioDTO> getAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarios.stream().map(UsuarioDTO::convert).collect(Collectors.toList());
	}

	public UsuarioDTO findById(long userId) {
		Optional<Usuario> usuario = usuarioRepository.findById(userId);

		if (usuario.isPresent()) {
			return UsuarioDTO.convert(usuario.get());
		}
		return null;
	}

	public UsuarioDTO save(UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioRepository.save(Usuario.convert(usuarioDTO));
		return UsuarioDTO.convert(usuario);
	}

	public UsuarioDTO delete(long usuarioId) {
		Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
		if (usuario.isPresent()) {
			usuarioRepository.delete(usuario.get());
			return UsuarioDTO.convert(usuario.get());
		}
		return null;
	}

	public UsuarioDTO findByCpf(String cpf) {
		Usuario usuario = usuarioRepository.findByCpf(cpf);
		if (usuario != null) {
			return UsuarioDTO.convert(usuario);
		}
		return null;
	}

	public List<UsuarioDTO> queryByName(String name) {
		List<Usuario> usuarios = usuarioRepository.queryByNomeLike(name);

		return usuarios.stream().map(UsuarioDTO::convert).collect(Collectors.toList());
	}
}
