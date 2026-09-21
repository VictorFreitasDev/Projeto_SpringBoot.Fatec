package br.com.feiraviva.controller;

import br.com.feiraviva.model.Produto;
import br.com.feiraviva.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produto", description = "Catálogo e consultas de produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    @Operation(summary = "Listar produtos",
            description = "Retorna a lista de todos os produtos disponíveis.")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    public List listar() {
        return produtoService.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID",
            description = "Retorna os detalhes de um produto específico com base no seu identificador.")
    @ApiResponse(responseCode = "200", description = "Produto encontrado e retornado")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public Produto buscar(
            @Parameter(description = "ID do produto", required = true, example = "50")
            @PathVariable Long id) {
        return produtoService.buscar(id);
    }
}