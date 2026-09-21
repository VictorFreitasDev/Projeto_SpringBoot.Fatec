package br.com.feiraviva.factory;

import br.com.feiraviva.model.Carrinho;
import br.com.feiraviva.model.Endereco;
import br.com.feiraviva.model.ItemPedido;
import br.com.feiraviva.model.Pedido;
import br.com.feiraviva.model.PedidoStatus;
import br.com.feiraviva.strategy.CalculadoraFrete;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PedidoFactory {

    private final CalculadoraFrete calculadoraFrete;

    public PedidoFactory(CalculadoraFrete calculadoraFrete) {
        this.calculadoraFrete = calculadoraFrete;
    }

    public Pedido montar(Carrinho carrinho, Endereco endereco) {
        var pedido = new Pedido();

        pedido.setCliente(carrinho.getCliente());
        pedido.setEndereco(endereco);
        pedido.setStatus(PedidoStatus.CRIADO);

        BigDecimal subtotal = BigDecimal.ZERO;
        for (var item : carrinho.getItens()) {
            var produto = item.getProduto();
            // SNAPSHOT: congela o preço do momento da compra
            var itemPedido = new ItemPedido(pedido, produto,
                    item.getQuantidade(), item.getPrecoUnitario());
            pedido.getItens().add(itemPedido);
            subtotal = subtotal.add(itemPedido.getSubtotal());
        }

        var estrategia = carrinho.getEstrategiaFrete() == null ? "PADRAO" : carrinho.getEstrategiaFrete();
        var frete = calculadoraFrete.calcular(estrategia, subtotal);
        pedido.setSubtotal(subtotal);
        pedido.setFrete(frete);
        pedido.setTotal(subtotal.add(frete));

        return pedido;
    }
}
