package me.dio.sacola.service;

import lombok.RequiredArgsConstructor;
import me.dio.sacola.enumerate.FormaPagamento;
import me.dio.sacola.model.Item;
import me.dio.sacola.model.Restaurante;
import me.dio.sacola.model.Sacola;
import me.dio.sacola.repository.ItemRespository;
import me.dio.sacola.repository.ProdutoRepository;
import me.dio.sacola.repository.SacolaRepository;
import me.dio.sacola.resource.dto.ItemDto;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl  implements SacolaService{
    private final SacolaRepository sacolaRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemRespository itemRespository;
    @Override
    public Item incluirItemNaSacola(ItemDto itemDto) {
    Sacola sacola =  verSacola(itemDto.getSacolaId());

    if(sacola.isFechada()){
    throw new RuntimeException("Esta sacola esta fechada");
}
        Item itemParaSerInserido = Item.builder()
                .quantidade(itemDto.getQuantidade())
                .sacola(sacola)
                .produto(produtoRepository.findById(itemDto.getProdutoId()).orElseThrow(
                        () -> {
                            throw new RuntimeException("Este produto não existe!");
                        }
                ))
                .build();

        List<Item> itensDaScola = sacola.getItens();
        if(itensDaScola.isEmpty()) {
            itensDaScola.add(itemParaSerInserido);
        } else {
            Restaurante restauranteAtual = itensDaScola.get(0).getProduto().getRestaurante();
            Restaurante restauranteDoItemParaAdicionar = itemParaSerInserido.getProduto().getRestaurante();
            if(restauranteAtual.equals(restauranteDoItemParaAdicionar)) {
                itensDaScola.add(itemParaSerInserido);
            } else {
                throw new RuntimeException("Não é possivel adicionar o produto de restaurantes diferentes");
            }
        }
        List<Double> valorDosItens = new ArrayList<>();

        for(Item itemDaSacola: itensDaScola){
            double valorTotalItem =
            itemDaSacola.getProduto().getValorUnitario() * itemDaSacola.getQuantidade();
            valorDosItens.add(valorTotalItem);
        }

        /*Double valorTotaSacola = 0.0;
        for(Double valorDeCadaItem : valorDosItens){
            valorTotaSacola += valorDeCadaItem;
        }*/

        double valorTotalSacola = valorDosItens.stream()
                .mapToDouble(valorTotalDeCadaItem -> valorTotalDeCadaItem)
                .sum();

        sacola.setValor_total(valorTotalSacola);
        sacolaRepository.save(sacola);
        return itemParaSerInserido;
    }

    @Override
    public Sacola verSacola(Long id) {
        return sacolaRepository.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("Essa sacola não existe!");
                }
        );
    }

    /*if(numeroformaPagamento ==0){
    sacola.setFormaPagamento(FormaPagamento.DINHEIRO);
    }else{
    sacola.setFormaPagamento(FormaPagamento.MAQUINETA)
    }*/

    @Override
    public Sacola fecharSacola(Long id, int numeroformaPagamento) {
        Sacola sacola = verSacola(id);
        if (sacola.getItens().isEmpty()){
            throw new RuntimeException("Inclua itens na sacola!");
        }
        FormaPagamento formaPagamento =
                numeroformaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;
        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechada(true);
        return sacolaRepository.save(sacola);
    }


}
