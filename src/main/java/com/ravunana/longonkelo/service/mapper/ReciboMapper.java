package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.DocumentoComercial;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.Recibo;
import com.ravunana.longonkelo.domain.Transacao;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.DocumentoComercialDTO;
import com.ravunana.longonkelo.service.dto.MatriculaDTO;
import com.ravunana.longonkelo.service.dto.ReciboDTO;
import com.ravunana.longonkelo.service.dto.TransacaoDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Recibo} and its DTO {@link ReciboDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReciboMapper extends EntityMapper<ReciboDTO, Recibo> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "matricula", source = "matricula", qualifiedByName = "matriculaNumeroMatricula")
    @Mapping(target = "documentoComercial", source = "documentoComercial", qualifiedByName = "documentoComercialSiglaInterna")
    @Mapping(target = "transacao", source = "transacao")
    ReciboDTO toDto(Recibo s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("matriculaNumeroMatricula")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeroMatricula", source = "numeroMatricula")
    MatriculaDTO toDtoMatriculaNumeroMatricula(Matricula matricula);

    @Named("documentoComercialSiglaInterna")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "siglaInterna", source = "siglaInterna")
    DocumentoComercialDTO toDtoDocumentoComercialSiglaInterna(DocumentoComercial documentoComercial);

    @Named("transacaoReferencia")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "referencia", source = "referencia")
    TransacaoDTO toDtoTransacaoReferencia(Transacao transacao);
}
