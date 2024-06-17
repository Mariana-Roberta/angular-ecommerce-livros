package com.pom.spring_ecommerce_livros.service;

import com.pom.spring_ecommerce_livros.model.Livro;
import com.pom.spring_ecommerce_livros.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> findAll() {
        return livroRepository.findAll();
    }

    public Livro findById(Long id) {
      return this.livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    public Livro save(Livro livro) {
        return livroRepository.save(livro);
    }

    public void delete(Long id){
      this.livroRepository.deleteById(id);
    }

    public List<Livro> searchBooks(String term) {
      return livroRepository.searchBooks(term);
    }

  public Livro findBookByName(String nome) {
    return livroRepository.findByTituloContainingIgnoreCase(nome).stream().findFirst().orElse(null);
  }

  public Livro updateBook(Long id, Livro bookDetails) {
    Livro book = livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    book.setTitulo(bookDetails.getTitulo());
    book.setAutor(bookDetails.getAutor());
    book.setSinopse(bookDetails.getSinopse());
    book.setValor(bookDetails.getValor());
    book.setQuantidade(bookDetails.getQuantidade());
    return livroRepository.save(book);
  }
}
