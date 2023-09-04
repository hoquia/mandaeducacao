package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.AnoLectivo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnoLectivoDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer ano;

    @NotNull
    private LocalDate inicio;

    @NotNull
    private LocalDate fim;

    @NotNull
    private String descricao;

    private ZonedDateTime timestam;

    private Boolean isActual;

    private DocenteDTO directorGeral;

    private DocenteDTO subDirectorPdagogico;

    private DocenteDTO subDirectorAdministrativo;

    private DocenteDTO responsavelSecretariaGeral;

    private DocenteDTO responsavelSecretariaPedagogico;

    private UserDTO utilizador;

    private Set<NivelEnsinoDTO> nivesEnsinos = new HashSet<>();

    private TurmaDTO turma;

    private HorarioDTO horario;

    private PlanoAulaDTO planoAula;

    private LicaoDTO licao;

    private ProcessoSelectivoMatriculaDTO processoSelectivoMatricula;

    private OcorrenciaDTO ocorrencia;

    private NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplina;

    private NotasGeralDisciplinaDTO notasGeralDisciplina;

    private DissertacaoFinalCursoDTO dissertacaoFinalCurso;

    private FacturaDTO factura;

    private ReciboDTO recibo;

    private ResponsavelTurnoDTO responsavelTurno;

    private ResponsavelAreaFormacaoDTO responsavelAreaFormacao;

    private ResponsavelCursoDTO responsavelCurso;

    private ResponsavelDisciplinaDTO responsavelDisciplina;

    private ResponsavelTurmaDTO responsavelTurma;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ZonedDateTime getTimestam() {
        return timestam;
    }

    public void setTimestam(ZonedDateTime timestam) {
        this.timestam = timestam;
    }

    public Boolean getIsActual() {
        return isActual;
    }

    public void setIsActual(Boolean isActual) {
        this.isActual = isActual;
    }

    public DocenteDTO getDirectorGeral() {
        return directorGeral;
    }

    public void setDirectorGeral(DocenteDTO directorGeral) {
        this.directorGeral = directorGeral;
    }

    public DocenteDTO getSubDirectorPdagogico() {
        return subDirectorPdagogico;
    }

    public void setSubDirectorPdagogico(DocenteDTO subDirectorPdagogico) {
        this.subDirectorPdagogico = subDirectorPdagogico;
    }

    public DocenteDTO getSubDirectorAdministrativo() {
        return subDirectorAdministrativo;
    }

    public void setSubDirectorAdministrativo(DocenteDTO subDirectorAdministrativo) {
        this.subDirectorAdministrativo = subDirectorAdministrativo;
    }

    public DocenteDTO getResponsavelSecretariaGeral() {
        return responsavelSecretariaGeral;
    }

    public void setResponsavelSecretariaGeral(DocenteDTO responsavelSecretariaGeral) {
        this.responsavelSecretariaGeral = responsavelSecretariaGeral;
    }

    public DocenteDTO getResponsavelSecretariaPedagogico() {
        return responsavelSecretariaPedagogico;
    }

    public void setResponsavelSecretariaPedagogico(DocenteDTO responsavelSecretariaPedagogico) {
        this.responsavelSecretariaPedagogico = responsavelSecretariaPedagogico;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    public Set<NivelEnsinoDTO> getNivesEnsinos() {
        return nivesEnsinos;
    }

    public void setNivesEnsinos(Set<NivelEnsinoDTO> nivesEnsinos) {
        this.nivesEnsinos = nivesEnsinos;
    }

    public TurmaDTO getTurma() {
        return turma;
    }

    public void setTurma(TurmaDTO turma) {
        this.turma = turma;
    }

    public HorarioDTO getHorario() {
        return horario;
    }

    public void setHorario(HorarioDTO horario) {
        this.horario = horario;
    }

    public PlanoAulaDTO getPlanoAula() {
        return planoAula;
    }

    public void setPlanoAula(PlanoAulaDTO planoAula) {
        this.planoAula = planoAula;
    }

    public LicaoDTO getLicao() {
        return licao;
    }

    public void setLicao(LicaoDTO licao) {
        this.licao = licao;
    }

    public ProcessoSelectivoMatriculaDTO getProcessoSelectivoMatricula() {
        return processoSelectivoMatricula;
    }

    public void setProcessoSelectivoMatricula(ProcessoSelectivoMatriculaDTO processoSelectivoMatricula) {
        this.processoSelectivoMatricula = processoSelectivoMatricula;
    }

    public OcorrenciaDTO getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(OcorrenciaDTO ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public NotasPeriodicaDisciplinaDTO getNotasPeriodicaDisciplina() {
        return notasPeriodicaDisciplina;
    }

    public void setNotasPeriodicaDisciplina(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplina) {
        this.notasPeriodicaDisciplina = notasPeriodicaDisciplina;
    }

    public NotasGeralDisciplinaDTO getNotasGeralDisciplina() {
        return notasGeralDisciplina;
    }

    public void setNotasGeralDisciplina(NotasGeralDisciplinaDTO notasGeralDisciplina) {
        this.notasGeralDisciplina = notasGeralDisciplina;
    }

    public DissertacaoFinalCursoDTO getDissertacaoFinalCurso() {
        return dissertacaoFinalCurso;
    }

    public void setDissertacaoFinalCurso(DissertacaoFinalCursoDTO dissertacaoFinalCurso) {
        this.dissertacaoFinalCurso = dissertacaoFinalCurso;
    }

    public FacturaDTO getFactura() {
        return factura;
    }

    public void setFactura(FacturaDTO factura) {
        this.factura = factura;
    }

    public ReciboDTO getRecibo() {
        return recibo;
    }

    public void setRecibo(ReciboDTO recibo) {
        this.recibo = recibo;
    }

    public ResponsavelTurnoDTO getResponsavelTurno() {
        return responsavelTurno;
    }

    public void setResponsavelTurno(ResponsavelTurnoDTO responsavelTurno) {
        this.responsavelTurno = responsavelTurno;
    }

    public ResponsavelAreaFormacaoDTO getResponsavelAreaFormacao() {
        return responsavelAreaFormacao;
    }

    public void setResponsavelAreaFormacao(ResponsavelAreaFormacaoDTO responsavelAreaFormacao) {
        this.responsavelAreaFormacao = responsavelAreaFormacao;
    }

    public ResponsavelCursoDTO getResponsavelCurso() {
        return responsavelCurso;
    }

    public void setResponsavelCurso(ResponsavelCursoDTO responsavelCurso) {
        this.responsavelCurso = responsavelCurso;
    }

    public ResponsavelDisciplinaDTO getResponsavelDisciplina() {
        return responsavelDisciplina;
    }

    public void setResponsavelDisciplina(ResponsavelDisciplinaDTO responsavelDisciplina) {
        this.responsavelDisciplina = responsavelDisciplina;
    }

    public ResponsavelTurmaDTO getResponsavelTurma() {
        return responsavelTurma;
    }

    public void setResponsavelTurma(ResponsavelTurmaDTO responsavelTurma) {
        this.responsavelTurma = responsavelTurma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnoLectivoDTO)) {
            return false;
        }

        AnoLectivoDTO anoLectivoDTO = (AnoLectivoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, anoLectivoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnoLectivoDTO{" +
            "id=" + getId() +
            ", ano=" + getAno() +
            ", inicio='" + getInicio() + "'" +
            ", fim='" + getFim() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", timestam='" + getTimestam() + "'" +
            ", isActual='" + getIsActual() + "'" +
            ", directorGeral=" + getDirectorGeral() +
            ", subDirectorPdagogico=" + getSubDirectorPdagogico() +
            ", subDirectorAdministrativo=" + getSubDirectorAdministrativo() +
            ", responsavelSecretariaGeral=" + getResponsavelSecretariaGeral() +
            ", responsavelSecretariaPedagogico=" + getResponsavelSecretariaPedagogico() +
            ", utilizador=" + getUtilizador() +
            ", nivesEnsinos=" + getNivesEnsinos() +
            ", turma=" + getTurma() +
            ", horario=" + getHorario() +
            ", planoAula=" + getPlanoAula() +
            ", licao=" + getLicao() +
            ", processoSelectivoMatricula=" + getProcessoSelectivoMatricula() +
            ", ocorrencia=" + getOcorrencia() +
            ", notasPeriodicaDisciplina=" + getNotasPeriodicaDisciplina() +
            ", notasGeralDisciplina=" + getNotasGeralDisciplina() +
            ", dissertacaoFinalCurso=" + getDissertacaoFinalCurso() +
            ", factura=" + getFactura() +
            ", recibo=" + getRecibo() +
            ", responsavelTurno=" + getResponsavelTurno() +
            ", responsavelAreaFormacao=" + getResponsavelAreaFormacao() +
            ", responsavelCurso=" + getResponsavelCurso() +
            ", responsavelDisciplina=" + getResponsavelDisciplina() +
            ", responsavelTurma=" + getResponsavelTurma() +
            "}";
    }
}
