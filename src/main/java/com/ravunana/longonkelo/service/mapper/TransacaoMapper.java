package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Conta;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.MeioPagamento;
import com.ravunana.longonkelo.domain.Transacao;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.ContaDTO;
import com.ravunana.longonkelo.service.dto.LookupItemDTO;
import com.ravunana.longonkelo.service.dto.MatriculaDTO;
import com.ravunana.longonkelo.service.dto.MeioPagamentoDTO;
import com.ravunana.longonkelo.service.dto.TransacaoDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transacao} and its DTO {@link TransacaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransacaoMapper extends EntityMapper<TransacaoDTO, Transacao> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "moeda", source = "moeda", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "matricula", source = "matricula", qualifiedByName = "matriculaNumeroMatricula")
    @Mapping(target = "meioPagamento", source = "meioPagamento", qualifiedByName = "meioPagamentoNome")
    @Mapping(target = "conta", source = "conta", qualifiedByName = "contaTitulo")
    TransacaoDTO toDto(Transacao s);

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

    @Named("matriculaNumeroMatricula")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeroMatricula", source = "numeroMatricula")
    MatriculaDTO toDtoMatriculaNumeroMatricula(Matricula matricula);

    @Named("meioPagamentoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    MeioPagamentoDTO toDtoMeioPagamentoNome(MeioPagamento meioPagamento);

    @Named("contaTitulo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titulo", source = "titulo")
    ContaDTO toDtoContaTitulo(Conta conta);
}
