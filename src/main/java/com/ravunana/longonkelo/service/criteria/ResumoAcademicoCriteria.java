package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.ResumoAcademico} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.ResumoAcademicoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /resumo-academicos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResumoAcademicoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter temaProjecto;

    private DoubleFilter notaProjecto;

    private StringFilter localEstagio;

    private DoubleFilter notaEstagio;

    private DoubleFilter mediaFinalDisciplina;

    private DoubleFilter classificacaoFinal;

    private StringFilter numeroGrupo;

    private StringFilter mesaDefesa;

    private StringFilter livroRegistro;

    private StringFilter numeroFolha;

    private StringFilter chefeSecretariaPedagogica;

    private StringFilter subDirectorPedagogico;

    private StringFilter directorGeral;

    private StringFilter tutorProjecto;

    private StringFilter juriMesa;

    private StringFilter empresaEstagio;

    private StringFilter hash;

    private LongFilter utilizadorId;

    private LongFilter ultimaTurmaMatriculadaId;

    private LongFilter discenteId;

    private LongFilter situacaoId;

    private Boolean distinct;

    public ResumoAcademicoCriteria() {}

    public ResumoAcademicoCriteria(ResumoAcademicoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.temaProjecto = other.temaProjecto == null ? null : other.temaProjecto.copy();
        this.notaProjecto = other.notaProjecto == null ? null : other.notaProjecto.copy();
        this.localEstagio = other.localEstagio == null ? null : other.localEstagio.copy();
        this.notaEstagio = other.notaEstagio == null ? null : other.notaEstagio.copy();
        this.mediaFinalDisciplina = other.mediaFinalDisciplina == null ? null : other.mediaFinalDisciplina.copy();
        this.classificacaoFinal = other.classificacaoFinal == null ? null : other.classificacaoFinal.copy();
        this.numeroGrupo = other.numeroGrupo == null ? null : other.numeroGrupo.copy();
        this.mesaDefesa = other.mesaDefesa == null ? null : other.mesaDefesa.copy();
        this.livroRegistro = other.livroRegistro == null ? null : other.livroRegistro.copy();
        this.numeroFolha = other.numeroFolha == null ? null : other.numeroFolha.copy();
        this.chefeSecretariaPedagogica = other.chefeSecretariaPedagogica == null ? null : other.chefeSecretariaPedagogica.copy();
        this.subDirectorPedagogico = other.subDirectorPedagogico == null ? null : other.subDirectorPedagogico.copy();
        this.directorGeral = other.directorGeral == null ? null : other.directorGeral.copy();
        this.tutorProjecto = other.tutorProjecto == null ? null : other.tutorProjecto.copy();
        this.juriMesa = other.juriMesa == null ? null : other.juriMesa.copy();
        this.empresaEstagio = other.empresaEstagio == null ? null : other.empresaEstagio.copy();
        this.hash = other.hash == null ? null : other.hash.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.ultimaTurmaMatriculadaId = other.ultimaTurmaMatriculadaId == null ? null : other.ultimaTurmaMatriculadaId.copy();
        this.discenteId = other.discenteId == null ? null : other.discenteId.copy();
        this.situacaoId = other.situacaoId == null ? null : other.situacaoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ResumoAcademicoCriteria copy() {
        return new ResumoAcademicoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTemaProjecto() {
        return temaProjecto;
    }

    public StringFilter temaProjecto() {
        if (temaProjecto == null) {
            temaProjecto = new StringFilter();
        }
        return temaProjecto;
    }

    public void setTemaProjecto(StringFilter temaProjecto) {
        this.temaProjecto = temaProjecto;
    }

    public DoubleFilter getNotaProjecto() {
        return notaProjecto;
    }

    public DoubleFilter notaProjecto() {
        if (notaProjecto == null) {
            notaProjecto = new DoubleFilter();
        }
        return notaProjecto;
    }

    public void setNotaProjecto(DoubleFilter notaProjecto) {
        this.notaProjecto = notaProjecto;
    }

    public StringFilter getLocalEstagio() {
        return localEstagio;
    }

    public StringFilter localEstagio() {
        if (localEstagio == null) {
            localEstagio = new StringFilter();
        }
        return localEstagio;
    }

    public void setLocalEstagio(StringFilter localEstagio) {
        this.localEstagio = localEstagio;
    }

    public DoubleFilter getNotaEstagio() {
        return notaEstagio;
    }

    public DoubleFilter notaEstagio() {
        if (notaEstagio == null) {
            notaEstagio = new DoubleFilter();
        }
        return notaEstagio;
    }

    public void setNotaEstagio(DoubleFilter notaEstagio) {
        this.notaEstagio = notaEstagio;
    }

    public DoubleFilter getMediaFinalDisciplina() {
        return mediaFinalDisciplina;
    }

    public DoubleFilter mediaFinalDisciplina() {
        if (mediaFinalDisciplina == null) {
            mediaFinalDisciplina = new DoubleFilter();
        }
        return mediaFinalDisciplina;
    }

    public void setMediaFinalDisciplina(DoubleFilter mediaFinalDisciplina) {
        this.mediaFinalDisciplina = mediaFinalDisciplina;
    }

    public DoubleFilter getClassificacaoFinal() {
        return classificacaoFinal;
    }

    public DoubleFilter classificacaoFinal() {
        if (classificacaoFinal == null) {
            classificacaoFinal = new DoubleFilter();
        }
        return classificacaoFinal;
    }

    public void setClassificacaoFinal(DoubleFilter classificacaoFinal) {
        this.classificacaoFinal = classificacaoFinal;
    }

    public StringFilter getNumeroGrupo() {
        return numeroGrupo;
    }

    public StringFilter numeroGrupo() {
        if (numeroGrupo == null) {
            numeroGrupo = new StringFilter();
        }
        return numeroGrupo;
    }

    public void setNumeroGrupo(StringFilter numeroGrupo) {
        this.numeroGrupo = numeroGrupo;
    }

    public StringFilter getMesaDefesa() {
        return mesaDefesa;
    }

    public StringFilter mesaDefesa() {
        if (mesaDefesa == null) {
            mesaDefesa = new StringFilter();
        }
        return mesaDefesa;
    }

    public void setMesaDefesa(StringFilter mesaDefesa) {
        this.mesaDefesa = mesaDefesa;
    }

    public StringFilter getLivroRegistro() {
        return livroRegistro;
    }

    public StringFilter livroRegistro() {
        if (livroRegistro == null) {
            livroRegistro = new StringFilter();
        }
        return livroRegistro;
    }

    public void setLivroRegistro(StringFilter livroRegistro) {
        this.livroRegistro = livroRegistro;
    }

    public StringFilter getNumeroFolha() {
        return numeroFolha;
    }

    public StringFilter numeroFolha() {
        if (numeroFolha == null) {
            numeroFolha = new StringFilter();
        }
        return numeroFolha;
    }

    public void setNumeroFolha(StringFilter numeroFolha) {
        this.numeroFolha = numeroFolha;
    }

    public StringFilter getChefeSecretariaPedagogica() {
        return chefeSecretariaPedagogica;
    }

    public StringFilter chefeSecretariaPedagogica() {
        if (chefeSecretariaPedagogica == null) {
            chefeSecretariaPedagogica = new StringFilter();
        }
        return chefeSecretariaPedagogica;
    }

    public void setChefeSecretariaPedagogica(StringFilter chefeSecretariaPedagogica) {
        this.chefeSecretariaPedagogica = chefeSecretariaPedagogica;
    }

    public StringFilter getSubDirectorPedagogico() {
        return subDirectorPedagogico;
    }

    public StringFilter subDirectorPedagogico() {
        if (subDirectorPedagogico == null) {
            subDirectorPedagogico = new StringFilter();
        }
        return subDirectorPedagogico;
    }

    public void setSubDirectorPedagogico(StringFilter subDirectorPedagogico) {
        this.subDirectorPedagogico = subDirectorPedagogico;
    }

    public StringFilter getDirectorGeral() {
        return directorGeral;
    }

    public StringFilter directorGeral() {
        if (directorGeral == null) {
            directorGeral = new StringFilter();
        }
        return directorGeral;
    }

    public void setDirectorGeral(StringFilter directorGeral) {
        this.directorGeral = directorGeral;
    }

    public StringFilter getTutorProjecto() {
        return tutorProjecto;
    }

    public StringFilter tutorProjecto() {
        if (tutorProjecto == null) {
            tutorProjecto = new StringFilter();
        }
        return tutorProjecto;
    }

    public void setTutorProjecto(StringFilter tutorProjecto) {
        this.tutorProjecto = tutorProjecto;
    }

    public StringFilter getJuriMesa() {
        return juriMesa;
    }

    public StringFilter juriMesa() {
        if (juriMesa == null) {
            juriMesa = new StringFilter();
        }
        return juriMesa;
    }

    public void setJuriMesa(StringFilter juriMesa) {
        this.juriMesa = juriMesa;
    }

    public StringFilter getEmpresaEstagio() {
        return empresaEstagio;
    }

    public StringFilter empresaEstagio() {
        if (empresaEstagio == null) {
            empresaEstagio = new StringFilter();
        }
        return empresaEstagio;
    }

    public void setEmpresaEstagio(StringFilter empresaEstagio) {
        this.empresaEstagio = empresaEstagio;
    }

    public StringFilter getHash() {
        return hash;
    }

    public StringFilter hash() {
        if (hash == null) {
            hash = new StringFilter();
        }
        return hash;
    }

    public void setHash(StringFilter hash) {
        this.hash = hash;
    }

    public LongFilter getUtilizadorId() {
        return utilizadorId;
    }

    public LongFilter utilizadorId() {
        if (utilizadorId == null) {
            utilizadorId = new LongFilter();
        }
        return utilizadorId;
    }

    public void setUtilizadorId(LongFilter utilizadorId) {
        this.utilizadorId = utilizadorId;
    }

    public LongFilter getUltimaTurmaMatriculadaId() {
        return ultimaTurmaMatriculadaId;
    }

    public LongFilter ultimaTurmaMatriculadaId() {
        if (ultimaTurmaMatriculadaId == null) {
            ultimaTurmaMatriculadaId = new LongFilter();
        }
        return ultimaTurmaMatriculadaId;
    }

    public void setUltimaTurmaMatriculadaId(LongFilter ultimaTurmaMatriculadaId) {
        this.ultimaTurmaMatriculadaId = ultimaTurmaMatriculadaId;
    }

    public LongFilter getDiscenteId() {
        return discenteId;
    }

    public LongFilter discenteId() {
        if (discenteId == null) {
            discenteId = new LongFilter();
        }
        return discenteId;
    }

    public void setDiscenteId(LongFilter discenteId) {
        this.discenteId = discenteId;
    }

    public LongFilter getSituacaoId() {
        return situacaoId;
    }

    public LongFilter situacaoId() {
        if (situacaoId == null) {
            situacaoId = new LongFilter();
        }
        return situacaoId;
    }

    public void setSituacaoId(LongFilter situacaoId) {
        this.situacaoId = situacaoId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ResumoAcademicoCriteria that = (ResumoAcademicoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(temaProjecto, that.temaProjecto) &&
            Objects.equals(notaProjecto, that.notaProjecto) &&
            Objects.equals(localEstagio, that.localEstagio) &&
            Objects.equals(notaEstagio, that.notaEstagio) &&
            Objects.equals(mediaFinalDisciplina, that.mediaFinalDisciplina) &&
            Objects.equals(classificacaoFinal, that.classificacaoFinal) &&
            Objects.equals(numeroGrupo, that.numeroGrupo) &&
            Objects.equals(mesaDefesa, that.mesaDefesa) &&
            Objects.equals(livroRegistro, that.livroRegistro) &&
            Objects.equals(numeroFolha, that.numeroFolha) &&
            Objects.equals(chefeSecretariaPedagogica, that.chefeSecretariaPedagogica) &&
            Objects.equals(subDirectorPedagogico, that.subDirectorPedagogico) &&
            Objects.equals(directorGeral, that.directorGeral) &&
            Objects.equals(tutorProjecto, that.tutorProjecto) &&
            Objects.equals(juriMesa, that.juriMesa) &&
            Objects.equals(empresaEstagio, that.empresaEstagio) &&
            Objects.equals(hash, that.hash) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(ultimaTurmaMatriculadaId, that.ultimaTurmaMatriculadaId) &&
            Objects.equals(discenteId, that.discenteId) &&
            Objects.equals(situacaoId, that.situacaoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            temaProjecto,
            notaProjecto,
            localEstagio,
            notaEstagio,
            mediaFinalDisciplina,
            classificacaoFinal,
            numeroGrupo,
            mesaDefesa,
            livroRegistro,
            numeroFolha,
            chefeSecretariaPedagogica,
            subDirectorPedagogico,
            directorGeral,
            tutorProjecto,
            juriMesa,
            empresaEstagio,
            hash,
            utilizadorId,
            ultimaTurmaMatriculadaId,
            discenteId,
            situacaoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResumoAcademicoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (temaProjecto != null ? "temaProjecto=" + temaProjecto + ", " : "") +
            (notaProjecto != null ? "notaProjecto=" + notaProjecto + ", " : "") +
            (localEstagio != null ? "localEstagio=" + localEstagio + ", " : "") +
            (notaEstagio != null ? "notaEstagio=" + notaEstagio + ", " : "") +
            (mediaFinalDisciplina != null ? "mediaFinalDisciplina=" + mediaFinalDisciplina + ", " : "") +
            (classificacaoFinal != null ? "classificacaoFinal=" + classificacaoFinal + ", " : "") +
            (numeroGrupo != null ? "numeroGrupo=" + numeroGrupo + ", " : "") +
            (mesaDefesa != null ? "mesaDefesa=" + mesaDefesa + ", " : "") +
            (livroRegistro != null ? "livroRegistro=" + livroRegistro + ", " : "") +
            (numeroFolha != null ? "numeroFolha=" + numeroFolha + ", " : "") +
            (chefeSecretariaPedagogica != null ? "chefeSecretariaPedagogica=" + chefeSecretariaPedagogica + ", " : "") +
            (subDirectorPedagogico != null ? "subDirectorPedagogico=" + subDirectorPedagogico + ", " : "") +
            (directorGeral != null ? "directorGeral=" + directorGeral + ", " : "") +
            (tutorProjecto != null ? "tutorProjecto=" + tutorProjecto + ", " : "") +
            (juriMesa != null ? "juriMesa=" + juriMesa + ", " : "") +
            (empresaEstagio != null ? "empresaEstagio=" + empresaEstagio + ", " : "") +
            (hash != null ? "hash=" + hash + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (ultimaTurmaMatriculadaId != null ? "ultimaTurmaMatriculadaId=" + ultimaTurmaMatriculadaId + ", " : "") +
            (discenteId != null ? "discenteId=" + discenteId + ", " : "") +
            (situacaoId != null ? "situacaoId=" + situacaoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
