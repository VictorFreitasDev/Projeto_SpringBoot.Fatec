package br.com.feiraviva.controller;

import br.com.feiraviva.dto.*;
import br.com.feiraviva.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
            @Parameter(description = "ID do cliente", required = true, example = "1")
            @RequestParam Long clienteId,
            @Valid @RequestBody PedidoRequestDTO dto) {
        return pedidoService.finalizar(clienteId, dto);
    }

    @GetMapping
    @Operation(summary = "Histórico de pedidos",
            description = "Retorna a lista completa de pedidos realizados por um cliente.")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    public List historico(
            @Parameter(description = "ID do cliente", required = true, example = "1")
            @RequestParam Long clienteId) {
        return pedidoService.historico(clienteId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido específico",
            description = "Retorna os detalhes de um pedido pertencente ao cliente.")
    @ApiResponse(responseCode = "200", description = "Pedido retornado com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente ou Pedido não encontrado")
    public PedidoResponseDTO buscar(
            @Parameter(description = "ID do cliente", required = true, example = "1")
            @RequestParam Long clienteId,
            @Parameter(description = "ID do pedido", required = true, example = "100")
            @PathVariable Long id) {
        return pedidoService.buscar(clienteId, id);
    }

    @PostMapping("/{id}/cancelamento")
    @Operation(summary = "Cancelar pedido",
            description = "Altera o status do pedido para cancelado (se as regras de negócio permitirem).")
    @ApiResponse(responseCode = "200", description = "Pedido cancelado com sucesso")
    @ApiResponse(responseCode = "400", description = "Não é possível cancelar no status atual")
    @ApiResponse(responseCode = "404", description = "Cliente ou Pedido não encontrado")
    public PedidoResponseDTO cancelar(
            @Parameter(description = "ID do cliente", required = true, example = "1")
            @RequestParam Long clienteId,
            @Parameter(description = "ID do pedido", required = true, example = "100")
            @PathVariable Long id) {
        return pedidoService.cancelar(clienteId, id);
    }
}