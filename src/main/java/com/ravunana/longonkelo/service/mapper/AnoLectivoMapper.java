package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.AnoLectivo;
import com.ravunana.longonkelo.domain.DissertacaoFinalCurso;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.Factura;
import com.ravunana.longonkelo.domain.Horario;
import com.ravunana.longonkelo.domain.Licao;
import com.ravunana.longonkelo.domain.NivelEnsino;
import com.ravunana.longonkelo.domain.NotasGeralDisciplina;
import com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina;
import com.ravunana.longonkelo.domain.Ocorrencia;
import com.ravunana.longonkelo.domain.PlanoAula;
import com.ravunana.longonkelo.domain.ProcessoSelectivoMatricula;
import com.ravunana.longonkelo.domain.Recibo;
import com.ravunana.longonkelo.domain.ResponsavelAreaFormacao;
import com.ravunana.longonkelo.domain.ResponsavelCurso;
import com.ravunana.longonkelo.domain.ResponsavelDisciplina;
import com.ravunana.longonkelo.domain.ResponsavelTurma;
import com.ravunana.longonkelo.domain.ResponsavelTurno;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.AnoLectivoDTO;
import com.ravunana.longonkelo.service.dto.DissertacaoFinalCursoDTO;
import com.ravunana.longonkelo.service.dto.DocenteDTO;
import com.ravunana.longonkelo.service.dto.FacturaDTO;
import com.ravunana.longonkelo.service.dto.HorarioDTO;
import com.ravunana.longonkelo.service.dto.LicaoDTO;
import com.ravunana.longonkelo.service.dto.NivelEnsinoDTO;
import com.ravunana.longonkelo.service.dto.NotasGeralDisciplinaDTO;
import com.ravunana.longonkelo.service.dto.NotasPeriodicaDisciplinaDTO;
import com.ravunana.longonkelo.service.dto.OcorrenciaDTO;
import com.ravunana.longonkelo.service.dto.PlanoAulaDTO;
import com.ravunana.longonkelo.service.dto.ProcessoSelectivoMatriculaDTO;
import com.ravunana.longonkelo.service.dto.ReciboDTO;
import com.ravunana.longonkelo.service.dto.ResponsavelAreaFormacaoDTO;
import com.ravunana.longonkelo.service.dto.ResponsavelCursoDTO;
import com.ravunana.longonkelo.service.dto.ResponsavelDisciplinaDTO;
import com.ravunana.longonkelo.service.dto.ResponsavelTurmaDTO;
import com.ravunana.longonkelo.service.dto.ResponsavelTurnoDTO;
import com.ravunana.longonkelo.service.dto.TurmaDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnoLectivo} and its DTO {@link AnoLectivoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnoLectivoMapper extends EntityMapper<AnoLectivoDTO, AnoLectivo> {
    @Mapping(target = "directorGeral", source = "directorGeral", qualifiedByName = "docenteNome")
    @Mapping(target = "subDirectorPdagogico", source = "subDirectorPdagogico", qualifiedByName = "docenteNome")
    @Mapping(target = "subDirectorAdministrativo", source = "subDirectorAdministrativo", qualifiedByName = "docenteNome")
    @Mapping(target = "responsavelSecretariaGeral", source = "responsavelSecretariaGeral", qualifiedByName = "docenteNome")
    @Mapping(target = "responsavelSecretariaPedagogico", source = "responsavelSecretariaPedagogico", qualifiedByName = "docenteNome")
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "nivesEnsinos", source = "nivesEnsinos", qualifiedByName = "nivelEnsinoNomeSet")
    @Mapping(target = "turma", source = "turma", qualifiedByName = "turmaId")
    @Mapping(target = "horario", source = "horario", qualifiedByName = "horarioId")
    @Mapping(target = "planoAula", source = "planoAula", qualifiedByName = "planoAulaId")
    @Mapping(target = "licao", source = "licao", qualifiedByName = "licaoId")
    @Mapping(target = "processoSelectivoMatricula", source = "processoSelectivoMatricula", qualifiedByName = "processoSelectivoMatriculaId")
    @Mapping(target = "ocorrencia", source = "ocorrencia", qualifiedByName = "ocorrenciaId")
    @Mapping(target = "notasPeriodicaDisciplina", source = "notasPeriodicaDisciplina", qualifiedByName = "notasPeriodicaDisciplinaId")
    @Mapping(target = "notasGeralDisciplina", source = "notasGeralDisciplina", qualifiedByName = "notasGeralDisciplinaId")
    @Mapping(target = "dissertacaoFinalCurso", source = "dissertacaoFinalCurso", qualifiedByName = "dissertacaoFinalCursoId")
    @Mapping(target = "factura", source = "factura", qualifiedByName = "facturaId")
    @Mapping(target = "recibo", source = "recibo", qualifiedByName = "reciboId")
    @Mapping(target = "responsavelTurno", source = "responsavelTurno", qualifiedByName = "responsavelTurnoId")
    @Mapping(target = "responsavelAreaFormacao", source = "responsavelAreaFormacao", qualifiedByName = "responsavelAreaFormacaoId")
    @Mapping(target = "responsavelCurso", source = "responsavelCurso", qualifiedByName = "responsavelCursoId")
    @Mapping(target = "responsavelDisciplina", source = "responsavelDisciplina", qualifiedByName = "responsavelDisciplinaId")
    @Mapping(target = "responsavelTurma", source = "responsavelTurma", qualifiedByName = "responsavelTurmaId")
    AnoLectivoDTO toDto(AnoLectivo s);

    @Mapping(target = "removeNivesEnsino", ignore = true)
    AnoLectivo toEntity(AnoLectivoDTO anoLectivoDTO);

    @Named("docenteNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DocenteDTO toDtoDocenteNome(Docente docente);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("nivelEnsinoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    NivelEnsinoDTO toDtoNivelEnsinoNome(NivelEnsino nivelEnsino);

    @Named("nivelEnsinoNomeSet")
    default Set<NivelEnsinoDTO> toDtoNivelEnsinoNomeSet(Set<NivelEnsino> nivelEnsino) {
        return nivelEnsino.stream().map(this::toDtoNivelEnsinoNome).collect(Collectors.toSet());
    }

    @Named("turmaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TurmaDTO toDtoTurmaId(Turma turma);

    @Named("horarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HorarioDTO toDtoHorarioId(Horario horario);

    @Named("planoAulaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PlanoAulaDTO toDtoPlanoAulaId(PlanoAula planoAula);

    @Named("licaoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LicaoDTO toDtoLicaoId(Licao licao);

    @Named("processoSelectivoMatriculaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProcessoSelectivoMatriculaDTO toDtoProcessoSelectivoMatriculaId(ProcessoSelectivoMatricula processoSelectivoMatricula);

    @Named("ocorrenciaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OcorrenciaDTO toDtoOcorrenciaId(Ocorrencia ocorrencia);

    @Named("notasPeriodicaDisciplinaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NotasPeriodicaDisciplinaDTO toDtoNotasPeriodicaDisciplinaId(NotasPeriodicaDisciplina notasPeriodicaDisciplina);

    @Named("notasGeralDisciplinaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NotasGeralDisciplinaDTO toDtoNotasGeralDisciplinaId(NotasGeralDisciplina notasGeralDisciplina);

    @Named("dissertacaoFinalCursoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DissertacaoFinalCursoDTO toDtoDissertacaoFinalCursoId(DissertacaoFinalCurso dissertacaoFinalCurso);

    @Named("facturaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FacturaDTO toDtoFacturaId(Factura factura);

    @Named("reciboId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReciboDTO toDtoReciboId(Recibo recibo);

    @Named("responsavelTurnoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResponsavelTurnoDTO toDtoResponsavelTurnoId(ResponsavelTurno responsavelTurno);

    @Named("responsavelAreaFormacaoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResponsavelAreaFormacaoDTO toDtoResponsavelAreaFormacaoId(ResponsavelAreaFormacao responsavelAreaFormacao);

    @Named("responsavelCursoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResponsavelCursoDTO toDtoResponsavelCursoId(ResponsavelCurso responsavelCurso);

    @Named("responsavelDisciplinaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResponsavelDisciplinaDTO toDtoResponsavelDisciplinaId(ResponsavelDisciplina responsavelDisciplina);

    @Named("responsavelTurmaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResponsavelTurmaDTO toDtoResponsavelTurmaId(ResponsavelTurma responsavelTurma);
}
