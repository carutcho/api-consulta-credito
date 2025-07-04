package br.com.eicon.api.consultacredito.api.v1.controller.impl;

import br.com.eicon.api.consultacredito.api.v1.controller.CreditoController;
import br.com.eicon.api.consultacredito.api.v1.dto.CreditoResponseDto;
import br.com.eicon.api.consultacredito.domain.exception.BusinessException;
import br.com.eicon.api.consultacredito.domain.exception.CodigoMensagem;
import br.com.eicon.api.consultacredito.domain.service.CreditoService;
import br.com.eicon.api.consultacredito.jpa.entity.Credito;
import br.com.eicon.api.consultacredito.domain.service.ConversorUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(path = "/v1/creditos")
@AllArgsConstructor
public class CreditoControllerImpl implements CreditoController {

    private final CreditoService creditoService;
    private final ReloadableResourceBundleMessageSource messageSource;

    @Override
    @GetMapping("/")
    public ResponseEntity<Collection<CreditoResponseDto>> listarTodasNfe() {
        Collection<Credito> lista = creditoService.buscarTodas();
        Collection<CreditoResponseDto> retorno = lista.stream()
                .map(c -> ConversorUtil.converter(c, CreditoResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(retorno);
    }

    @Override
    @GetMapping("/{numeroNfse}")
    public ResponseEntity<Collection<CreditoResponseDto>> buscarPorNfse(@PathVariable String numeroNfse) {
        Collection<Credito> lista = creditoService.buscarPorNumeroNfse(numeroNfse);
        Collection<CreditoResponseDto> retorno = lista.stream()
                .map(c -> ConversorUtil.converter(c, CreditoResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(retorno);
    }

    @Override
    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity buscarPorNumero(@PathVariable String numeroCredito) {
        try{
            Credito credito = creditoService.buscarPorNumeroCredito(numeroCredito);
            return ResponseEntity.ok(ConversorUtil.converter(credito, CreditoResponseDto.class));
        }catch (BusinessException e){
            String msg = messageSource.getMessage(
                    e.getCodigoMensagem().getValue(),
                    e.getParametros(),
                    LocaleContextHolder.getLocale()
            );

            if (e.getCodigoMensagem() == CodigoMensagem.RN_CREDITO_NAO_ENCONTRADO) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            } else {
                return ResponseEntity
                        .badRequest()
                        .body(msg);
            }
        }
    }
}