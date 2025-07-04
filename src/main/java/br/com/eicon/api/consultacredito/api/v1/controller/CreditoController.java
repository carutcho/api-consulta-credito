package br.com.eicon.api.consultacredito.api.v1.controller;

import br.com.eicon.api.consultacredito.api.v1.dto.CreditoResponseDto;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

@Api(value = "/v1/creditos", produces = "application/json", tags = "Creditos",
        description = "API para consulta de créditos constituídos")
public interface CreditoController {

    @ApiOperation(value = "Listar todas as NFS-es", response = CreditoResponseDto.class, httpMethod = "GET")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 500, message = "Erro interno")
    })
    ResponseEntity<Collection<CreditoResponseDto>> listarTodasNfe();

    @ApiOperation(value = "Listar créditos por NFS-e", response = CreditoResponseDto.class, httpMethod = "GET")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 500, message = "Erro interno")
    })
    ResponseEntity<Collection<CreditoResponseDto>> buscarPorNfse(
            @ApiParam(value = "Número identificador da NFS-e", required = true)
            @PathVariable String numeroNfse);

    @ApiOperation(value = "Buscar crédito por número", response = CreditoResponseDto.class, httpMethod = "GET")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "Crédito não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno")
    })
    ResponseEntity<CreditoResponseDto> buscarPorNumero(
            @ApiParam(value = "Número do crédito constituído", required = true)
            @PathVariable String numeroCredito);
}