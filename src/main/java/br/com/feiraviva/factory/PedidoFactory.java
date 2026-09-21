package br.com.feiraviva.factory;

import br.com.feiraviva.model.Carrinho;
import br.com.feiraviva.model.Endereco;
import br.com.feiraviva.model.ItemPedido;
import br.com.feiraviva.model.Pedido;
import br.com.feiraviva.model.PedidoStatus;
import br.com.feiraviva.service.CarrinhoService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PedidoFactory {

    private final CarrinhoService carrinhoService;

    public PedidoFactory(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
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

        var frete = carrinhoService.calcularFrete(subtotal);
        pedido.setSubtotal(subtotal);
        pedido.setFrete(frete);
        pedido.setTotal(subtotal.add(frete));

        return pedido;
    }
}
