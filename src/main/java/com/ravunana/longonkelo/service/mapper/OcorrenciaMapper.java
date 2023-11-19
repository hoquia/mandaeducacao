package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.CategoriaOcorrencia;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.Licao;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.Ocorrencia;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.CategoriaOcorrenciaDTO;
import com.ravunana.longonkelo.service.dto.DocenteDTO;
import com.ravunana.longonkelo.service.dto.LicaoDTO;
import com.ravunana.longonkelo.service.dto.MatriculaDTO;
import com.ravunana.longonkelo.service.dto.OcorrenciaDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ocorrencia} and its DTO {@link OcorrenciaDTO}.
 */
@Mapper(componentModel = "spring")
public interface OcorrenciaMapper extends EntityMapper<OcorrenciaDTO, Ocorrencia> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "referencia", source = "referencia", qualifiedByName = "ocorrenciaId")
    @Mapping(target = "docente", source = "docente", qualifiedByName = "docenteNome")
    @Mapping(target = "matricula", source = "matricula")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "categoriaOcorrenciaDescricao")
    @Mapping(target = "licao", source = "licao", qualifiedByName = "licaoNumero")
    OcorrenciaDTO toDto(Ocorrencia s);

    @Named("ocorrenciaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OcorrenciaDTO toDtoOcorrenciaId(Ocorrencia ocorrencia);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("docenteNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DocenteDTO toDtoDocenteNome(Docente docente);

    @Named("matriculaNumeroMatricula")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeroMatricula", source = "numeroMatricula")
    MatriculaDTO toDtoMatriculaNumeroMatricula(Matricula matricula);

    @Named("categoriaOcorrenciaDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    CategoriaOcorrenciaDTO toDtoCategoriaOcorrenciaDescricao(CategoriaOcorrencia categoriaOcorrencia);

    @Named("licaoNumero")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numero", source = "numero")
    LicaoDTO toDtoLicaoNumero(Licao licao);
}
