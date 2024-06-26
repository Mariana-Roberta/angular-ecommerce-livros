package com.pom.spring_ecommerce_livros.service;

import com.pom.spring_ecommerce_livros.model.Carrinho;
import com.pom.spring_ecommerce_livros.model.FactoryProduto;
import com.pom.spring_ecommerce_livros.model.ItemCarrinho;
import com.pom.spring_ecommerce_livros.model.Livro;
import com.pom.spring_ecommerce_livros.model.Produto;
import com.pom.spring_ecommerce_livros.repository.CarrinhoRepository;
import com.pom.spring_ecommerce_livros.repository.ItemCarrinhoRepository;
import com.pom.spring_ecommerce_livros.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemCarrinhoService {

  @Autowired
  private ItemCarrinhoRepository itemCarrinhoRepository;

  @Autowired
  private CarrinhoRepository carrinhoRepository;

  @Autowired
  private LivroRepository livroRepository;

  public List<ItemCarrinho> findAll() {
    return itemCarrinhoRepository.findAll();
  }

  public ItemCarrinho findById(Long itemCarrinhoId) {
    return itemCarrinhoRepository.findById(itemCarrinhoId).orElseThrow();
  }

  public List<ItemCarrinho> findByUserId(Long userId) {
    return itemCarrinhoRepository.findByUserId(userId);
  }

  public ItemCarrinho addItemToCarrinho(Long carrinhoId, Long livroId, Integer quantidade, Double valor) {
    Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElseThrow();
    Livro livro = livroRepository.findById(livroId).orElseThrow();

    // Verifica se o livro já está no carrinho
    ItemCarrinho itemExistente = itemCarrinhoRepository.findByCarrinhoIdAndLivroId(carrinhoId, livroId);

    if (itemExistente != null) {
      // Se o item já existe, atualiza a quantidade
      itemExistente.setQuantidade(quantidade);
      itemExistente.setValor(valor);
      return itemCarrinhoRepository.save(itemExistente);
    } else {
      // Se o item não existe, cria um novo item no carrinho
      ItemCarrinho newItem = new ItemCarrinho();
      newItem.setCarrinho(carrinho);
      newItem.setLivro(livro);
      newItem.setQuantidade(quantidade);
      newItem.setValor(valor);
      return itemCarrinhoRepository.save(newItem);
    }
  }

  public void deleteItemFromCarrinhoById(Long id) {
    itemCarrinhoRepository.deleteById(id);
  }

  public List<ItemCarrinho> findAllByLivroId(Long livroId) {
    return itemCarrinhoRepository.findAllByLivroId(livroId);
  }

  public List<ItemCarrinho> findAllByCarrinhoId(Long carrinhoId) {
    return itemCarrinhoRepository.findAllByCarrinhoId(carrinhoId);
  }

  public ItemCarrinho findByCarrinhoIdAndLivroId(Long carrinhoId, Long livroId) {
    return itemCarrinhoRepository.findByCarrinhoIdAndLivroId(carrinhoId, livroId);
  }

  public ItemCarrinho updateItemQuantidade(Long carrinhoId, Long livroId, Integer quantidade) {
    ItemCarrinho itemCarrinho = itemCarrinhoRepository.findByCarrinhoIdAndLivroId(carrinhoId, livroId);
    if (itemCarrinho != null) {
      itemCarrinho.setQuantidade(quantidade);
      return itemCarrinhoRepository.save(itemCarrinho);
    }
    return null; // Trate o caso em que o item não foi encontrado
  }

  public ItemCarrinho save(ItemCarrinho itemCarrinho) {
    return itemCarrinhoRepository.save(itemCarrinho);
  }

  public boolean removeItem(Long itemId) {
    Optional<ItemCarrinho> item = itemCarrinhoRepository.findById(itemId);
    if (item.isPresent()) {
      itemCarrinhoRepository.deleteById(itemId);
      return true;
    }
    return false;
  }
}
