package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.Transacao;
import com.ravunana.longonkelo.domain.TransferenciaSaldo;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.DiscenteDTO;
import com.ravunana.longonkelo.service.dto.LookupItemDTO;
import com.ravunana.longonkelo.service.dto.TransacaoDTO;
import com.ravunana.longonkelo.service.dto.TransferenciaSaldoDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransferenciaSaldo} and its DTO {@link TransferenciaSaldoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransferenciaSaldoMapper extends EntityMapper<TransferenciaSaldoDTO, TransferenciaSaldo> {
    @Mapping(target = "de", source = "de", qualifiedByName = "discenteNome")
    @Mapping(target = "para", source = "para", qualifiedByName = "discenteNome")
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "motivoTransferencia", source = "motivoTransferencia", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "transacoes", source = "transacoes", qualifiedByName = "transacaoReferenciaSet")
    TransferenciaSaldoDTO toDto(TransferenciaSaldo s);

    @Mapping(target = "removeTransacoes", ignore = true)
    TransferenciaSaldo toEntity(TransferenciaSaldoDTO transferenciaSaldoDTO);

    @Named("discenteNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DiscenteDTO toDtoDiscenteNome(Discente discente);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("lookupItemDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    LookupItemDTO toDtoLookupItemDescricao(LookupItem lookupItem);

    @Named("transacaoReferencia")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "referencia", source = "referencia")
    TransacaoDTO toDtoTransacaoReferencia(Transacao transacao);

    @Named("transacaoReferenciaSet")
    default Set<TransacaoDTO> toDtoTransacaoReferenciaSet(Set<Transacao> transacao) {
        return transacao.stream().map(this::toDtoTransacaoReferencia).collect(Collectors.toSet());
    }
}
