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

import br.biblioteca.livros.beans.Autor;
import br.biblioteca.livros.repository.AutorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest

public class AutorApplicationTests {

	@Autowired
	AutorRepository autorRepository;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void buscaAutorsCadastrados() {
		
		Page<Autor> autors = this.autorRepository.findAll(new PageRequest(0, 10));
		assertThat(autors.getTotalElements()).isGreaterThan(1L);
		
	}

	@Test
	public void buscaAutorKatsumi() {
		
		Autor autorNaoEncontrado = this.autorRepository.findByNome("Joao");
		assertThat(autorNaoEncontrado).isNull();
		
		Autor autorEncontrado = this.autorRepository.findByNome("Marcio Katsumi");
		assertThat(autorEncontrado).isNotNull();
		assertThat(autorEncontrado.getNome()).isEqualTo("Marcio Katsumi");		
	}
	
	@Test
	public void alteraAutorCadastrado() {
		Autor alteraAutor = this.autorRepository.findByNome("Marcelo Mikio");
		alteraAutor.setNome("Marcelo Mikio Matsunaga");
		
		this.autorRepository.save(alteraAutor);
		
		Autor alteraAutorVerifica = this.autorRepository.findByNome("Marcelo Mikio Matsunaga");
		assertThat(alteraAutorVerifica.getNome()).isEqualTo("Marcelo Mikio Matsunaga");
	}
	
	@Test
	public void criaAutor() {
		Autor joao = new Autor();
		joao.setNome("joao");
		
		this.autorRepository.save(joao);
		
		Autor autorJoao = this.autorRepository.findByNome("joao");
		assertThat(autorJoao.getNome()).isEqualTo("joao");
		
	}
	
	@Test
	public void excluiAutor() {
		Autor autorJoao = this.autorRepository.findByNome("joao");
		this.autorRepository.delete(autorJoao);
		
		Autor autorJoaoExcluido = this.autorRepository.findByNome("joao");
		assertThat(autorJoaoExcluido).isNull();
	}
}
