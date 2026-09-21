package br.com.feiraviva.controller;

import br.com.feiraviva.dto.*;
import br.com.feiraviva.service.CarrinhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
@Tag(name = "Carrinho", description = "Gerenciamento do carrinho de compras")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @GetMapping
    @Operation(summary = "Obter carrinho do cliente",
            description = "Retorna itens, cupom, estratégia de frete e totais calculados.")
    @ApiResponse(responseCode = "200", description = "Carrinho retornado (pode estar vazio)")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    public CarrinhoResponseDTO obter(
            @Parameter(description = "ID do cliente", required = true, example = "1")
            @RequestParam Long clienteId) {
        return carrinhoService.obter(clienteId);
    }

    @PostMapping("/itens")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adicionar item ao carrinho",
            description = "Aplica a regra R1 (estoque); soma quantidade se o produto já estiver no carrinho.")
    @ApiResponse(responseCode = "201", description = "Item adicionado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos (ex: quantidade < 1)")
    @ApiResponse(responseCode = "404", description = "Cliente ou produto não encontrado")
    @ApiResponse(responseCode = "409", description = "Produto sem estoque suficiente")
    public CarrinhoResponseDTO adicionar(
            @Parameter(description = "ID do cliente", required = true, example = "1")
            @RequestParam Long clienteId,
            @Valid @RequestBody ItemCarrinhoDTO dto) {
        return carrinhoService.adicionarItem(clienteId, dto);
    }

    @PutMapping("/itens/{itemId}")
    @Operation(summary = "Alterar quantidade de um item",
            description = "Atualiza a quantidade de um item específico no carrinho do cliente.")
    @ApiResponse(responseCode = "200", description = "Quantidade atualizada")
    @ApiResponse(responseCode = "404", description = "Cliente ou item não encontrado")
    @ApiResponse(responseCode = "409", description = "Produto sem estoque suficiente")
    public CarrinhoResponseDTO alterar(
            @Parameter(description = "ID do cliente", required = true, example = "1")
            @RequestParam Long clienteId,
            @Parameter(description = "ID do item no carrinho", required = true, example = "10")
            @PathVariable Long itemId,
            @Parameter(description = "Nova quantidade do produto", required = true, example = "3")
            @RequestParam int quantidade) {
        return carrinhoService.alterarQuantidade(clienteId, itemId, quantidade);
    }

    @DeleteMapping("/itens/{itemId}")
    @Operation(summary = "Remover item do carrinho",
            description = "Remove completamente um item do carrinho do cliente.")
    @ApiResponse(responseCode = "200", description = "Item removido com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente ou item não encontrado")
    public CarrinhoResponseDTO remover(
            @Parameter(description = "ID do cliente", required = true, example = "1")
            @RequestParam Long clienteId,
            @Parameter(description = "ID do item no carrinho", required = true, example = "10")
            @PathVariable Long itemId) {
        return carrinhoService.removerItem(clienteId, itemId);
    }

    @PostMapping("/cupom")
    @Operation(summary = "Aplicar cupom de desconto",
            description = "Aplica um cupom promocional válido ao carrinho.")
    @ApiResponse(responseCode = "200", description = "Cupom aplicado com sucesso")
    @ApiResponse(responseCode = "400", description = "Cupom inválido ou expirado")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    public CarrinhoResponseDTO aplicarCupom(
            @Parameter(description = "ID do cliente", required = true, example = "1")
            @RequestParam Long clienteId,
            @Parameter(description = "Código do cupom", required = true, example = "DESC10")
            @RequestParam String codigo) {
        return carrinhoService.aplicarCupom(clienteId, codigo);
    }

    @DeleteMapping("/cupom")
    @Operation(summary = "Remover cupom de desconto",
            description = "Remove o cupom atualmente aplicado no carrinho.")
    @ApiResponse(responseCode = "200", description = "Cupom removido")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    public CarrinhoResponseDTO removerCupom(
            @Parameter(description = "ID do cliente", required = true, example = "1")
            @RequestParam Long clienteId) {
        return carrinhoService.removerCupom(clienteId);
    }

    @PostMapping("/frete")
    @Operation(summary = "Definir estratégia de frete",
            description = "Define a modalidade de frete escolhida para o carrinho.")
    @ApiResponse(responseCode = "200", description = "Estratégia de frete definida com sucesso")
    @ApiResponse(responseCode = "400", description = "Tipo de frete inválido")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    public CarrinhoResponseDTO definirFrete(
            @Parameter(description = "ID do cliente", required = true, example = "1")
            @RequestParam Long clienteId,
            @Parameter(description = "Tipo de frete (ex: PAC, SEDEX)", required = true, example = "SEDEX")
            @RequestParam String tipo) {
        return carrinhoService.definirEstrategiaFrete(clienteId, tipo);
    }
}