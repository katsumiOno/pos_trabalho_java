package br.biblioteca.livros;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.biblioteca.livros.beans.Autor;
import br.biblioteca.livros.beans.Livro;
import br.biblioteca.livros.repository.LivroRepository;

@RunWith(SpringRunner.class)
@SpringBootTest

public class LivrosApplicationTests {

	@Autowired
	LivroRepository livroRepository;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void buscaLivrossCadastrados() {
		
		Page<Livro> livros = this.livroRepository.findAll(new PageRequest(0, 10));
		assertThat(livros.getTotalElements()).isGreaterThan(1L);
		
	}

	@Test
	public void buscaLivroJava() {
		
		Livro livroNaoEncontrado = this.livroRepository.findByNome("Python");
		assertThat(livroNaoEncontrado).isNull();
		
		Livro livroEncontrado = this.livroRepository.findByNome("JAVA");
		assertThat(livroEncontrado).isNotNull();
	}
	
	@Test
	public void alteraLivroCadastrado() {
		Livro alteraLivro = this.livroRepository.findByNome("PHP");
		alteraLivro.setNome("Node");
		
		this.livroRepository.save(alteraLivro);
		
		Livro alteraLivroVerifica = this.livroRepository.findByNome("Node");
		assertThat(alteraLivroVerifica).isNotNull();
	}
	
	@Test
	public void criaLivro() {
		Livro livro = new Livro();
		livro.setNome("go");
		livro.setQuantidade(60);
		livro.setQuantidadePaginas(600);
		livro.setIsbn("teste");
		
		this.livroRepository.save(livro);
		
		Livro livroGo = this.livroRepository.findByNome("go");
		assertThat(livroGo).isNotNull();		
	}
	
	@Test
	public void excluiLivro() {
		Livro livroC = this.livroRepository.findByNome("C#");
		this.livroRepository.delete(livroC);
		
		Livro livroCExcluido = this.livroRepository.findByNome("C#");
		assertThat(livroCExcluido).isNull();
	}
}
