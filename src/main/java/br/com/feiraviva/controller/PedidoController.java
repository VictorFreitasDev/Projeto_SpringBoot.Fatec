package br.com.feiraviva.controller;

import br.com.feiraviva.dto.*;
import br.com.feiraviva.security.ClienteAutenticado;
import br.com.feiraviva.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedido", description = "Processamento e consultas de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Finalizar pedido",
            description = "Converte o carrinho atual do cliente em um pedido confirmado.")
    @ApiResponse(responseCode = "201", description = "Pedido gerado com sucesso")
    @ApiResponse(responseCode = "400", description = "Carrinho vazio ou dados inválidos")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    public PedidoResponseDTO finalizar(
            @AuthenticationPrincipal ClienteAutenticado cliente,
            @Valid @RequestBody PedidoRequestDTO dto) {
        return pedidoService.finalizar(cliente.id(), dto);
    }

    @GetMapping
    @Operation(summary = "Histórico de pedidos",
            description = "Retorna a lista completa de pedidos realizados por um cliente.")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    public List<PedidoResponseDTO> historico(
            @AuthenticationPrincipal ClienteAutenticado cliente) {
        return pedidoService.historico(cliente.id());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido específico",
            description = "Retorna os detalhes de um pedido pertencente ao cliente.")
    @ApiResponse(responseCode = "200", description = "Pedido retornado com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente ou Pedido não encontrado")
    public PedidoResponseDTO buscar(
            @AuthenticationPrincipal ClienteAutenticado cliente,
            @Parameter(description = "ID do pedido", required = true, example = "100")
            @PathVariable Long id) {
        return pedidoService.buscar(cliente.id(), id);
    }

    @PostMapping("/{id}/cancelamento")
    @Operation(summary = "Cancelar pedido",
            description = "Altera o status do pedido para cancelado (se as regras de negócio permitirem).")
    @ApiResponse(responseCode = "200", description = "Pedido cancelado com sucesso")
    @ApiResponse(responseCode = "400", description = "Não é possível cancelar no status atual")
    @ApiResponse(responseCode = "404", description = "Cliente ou Pedido não encontrado")
    public PedidoResponseDTO cancelar(
            @AuthenticationPrincipal ClienteAutenticado cliente,
            @Parameter(description = "ID do pedido", required = true, example = "100")
            @PathVariable Long id) {
        return pedidoService.cancelar(cliente.id(), id);
    }
}
