package br.biblioteca.livros;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.biblioteca.livros.beans.Usuario;
import br.biblioteca.livros.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest

public class UsuarioApplicationTests {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void buscaUsuariosCadastrados() {
		
		Page<Usuario> usuarios = this.usuarioRepository.findAll(new PageRequest(0, 10));
		assertThat(usuarios.getTotalElements()).isGreaterThan(1L);
		
	}

	@Test
	public void buscaUsuarioKatsumi() {
		
		Usuario usuarioNaoEncontrado = this.usuarioRepository. findByUsername("marcio");
		assertThat(usuarioNaoEncontrado).isNull();
		
		Usuario usuarioEncontrado = this.usuarioRepository.findByUsername("katsumi");
		assertThat(usuarioEncontrado).isNotNull();
		assertThat(usuarioEncontrado.getUsername()).isEqualTo("katsumi");
		assertThat(usuarioEncontrado.getEmail()).isEqualTo("katsumi@email.com.br");
		
	}
	
	@Test
	public void alteraUsuarioCadastrado() {
		Usuario alteraUsuario = this.usuarioRepository.findByUsername("vittoria");
		alteraUsuario.setEmail("vittoria_zago@gmail.com.br");
		
		this.usuarioRepository.save(alteraUsuario);
		
		Usuario alteraUsuarioVerifica = this.usuarioRepository.findByUsername("vittoria");
		assertThat(alteraUsuarioVerifica.getEmail()).isEqualTo("vittoria_zago@gmail.com.br");
	}
	
	@Test
	public void criaUsuario() {
		Usuario joao = new Usuario();
		joao.setUsername("joao");
		joao.setEmail("joao@gmail.com");
		joao.setPassword("joao@123");
		
		this.usuarioRepository.save(joao);
		
		Usuario usuarioJoao = this.usuarioRepository.findByUsername("joao");
		assertThat(usuarioJoao.getEmail()).isEqualTo("joao@gmail.com");
		
	}
	
	@Test
	public void excluiUsuario() {
		Usuario usuarioJoao = this.usuarioRepository.findByUsername("joao");
		this.usuarioRepository.delete(usuarioJoao);
		
		Usuario usuarioJoaoExcluido = this.usuarioRepository.findByUsername("joao");
		assertThat(usuarioJoaoExcluido).isNull();
	}
}
