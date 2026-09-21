package br.com.feiraviva.strategy;

import br.com.feiraviva.repository.EnderecoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("CEP_SIMULADO")
public class FreteCepSimuladoStrategy implements StrategyFrete {

    private final EnderecoRepository enderecoRepository;
    private final HttpServletRequest request;

    public FreteCepSimuladoStrategy(EnderecoRepository enderecoRepository, HttpServletRequest request) {
        this.enderecoRepository = enderecoRepository;
        this.request = request;
    }

    @Override
    public BigDecimal calcular(BigDecimal subtotal) {
        String clienteIdStr = request.getParameter("clienteId");
        if (clienteIdStr != null && !clienteIdStr.isBlank()) {
            Long clienteId = Long.valueOf(clienteIdStr);
            var enderecos = enderecoRepository.findByClienteId(clienteId);
            if (!enderecos.isEmpty()) {
                String cep = enderecos.get(0).getCep();
                if (cep != null && cep.startsWith("0")) {
                    return new BigDecimal("8.00");
                }
            }
        }
        return new BigDecimal("12.00");
    }
}
