package com.ravunana.longonkelo.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.ravunana.longonkelo.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.ravunana.longonkelo.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.ravunana.longonkelo.domain.User.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Authority.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.User.class.getName() + ".authorities");
            createCache(cm, com.ravunana.longonkelo.domain.InstituicaoEnsino.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.InstituicaoEnsino.class.getName() + ".instituicaoEnsinos");
            createCache(cm, com.ravunana.longonkelo.domain.InstituicaoEnsino.class.getName() + ".provedorNotificacaos");
            createCache(cm, com.ravunana.longonkelo.domain.ProvedorNotificacao.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Lookup.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Lookup.class.getName() + ".lookupItems");
            createCache(cm, com.ravunana.longonkelo.domain.LookupItem.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Turno.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Turno.class.getName() + ".responsavelTurnos");
            createCache(cm, com.ravunana.longonkelo.domain.Turno.class.getName() + ".precoEmolumentos");
            createCache(cm, com.ravunana.longonkelo.domain.Turno.class.getName() + ".periodoHorarios");
            createCache(cm, com.ravunana.longonkelo.domain.Turno.class.getName() + ".turmas");
            createCache(cm, com.ravunana.longonkelo.domain.ResponsavelTurno.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.ResponsavelTurno.class.getName() + ".responsavels");
            createCache(cm, com.ravunana.longonkelo.domain.ResponsavelTurno.class.getName() + ".anoLectivos");
            createCache(cm, com.ravunana.longonkelo.domain.NivelEnsino.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.NivelEnsino.class.getName() + ".nivelEnsinos");
            createCache(cm, com.ravunana.longonkelo.domain.NivelEnsino.class.getName() + ".areaFormacaos");
            createCache(cm, com.ravunana.longonkelo.domain.NivelEnsino.class.getName() + ".anoLectivos");
            createCache(cm, com.ravunana.longonkelo.domain.NivelEnsino.class.getName() + ".classes");
            createCache(cm, com.ravunana.longonkelo.domain.AnoLectivo.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.AnoLectivo.class.getName() + ".nivesEnsinos");
            createCache(cm, com.ravunana.longonkelo.domain.AreaFormacao.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.AreaFormacao.class.getName() + ".cursos");
            createCache(cm, com.ravunana.longonkelo.domain.AreaFormacao.class.getName() + ".dissertacaoFinalCursos");
            createCache(cm, com.ravunana.longonkelo.domain.AreaFormacao.class.getName() + ".responsaveis");
            createCache(cm, com.ravunana.longonkelo.domain.AreaFormacao.class.getName() + ".precoEmolumentos");
            createCache(cm, com.ravunana.longonkelo.domain.ResponsavelAreaFormacao.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.ResponsavelAreaFormacao.class.getName() + ".responsavels");
            createCache(cm, com.ravunana.longonkelo.domain.ResponsavelAreaFormacao.class.getName() + ".anoLectivos");
            createCache(cm, com.ravunana.longonkelo.domain.Curso.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Curso.class.getName() + ".planoCurriculars");
            createCache(cm, com.ravunana.longonkelo.domain.Curso.class.getName() + ".responsaveis");
            createCache(cm, com.ravunana.longonkelo.domain.Curso.class.getName() + ".precoEmolumentos");
            createCache(cm, com.ravunana.longonkelo.domain.Curso.class.getName() + ".camposActuacaos");
            createCache(cm, com.ravunana.longonkelo.domain.ResponsavelCurso.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.ResponsavelCurso.class.getName() + ".responsavels");
            createCache(cm, com.ravunana.longonkelo.domain.ResponsavelCurso.class.getName() + ".anoLectivos");
            createCache(cm, com.ravunana.longonkelo.domain.Classe.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Classe.class.getName() + ".planoCurriculars");
            createCache(cm, com.ravunana.longonkelo.domain.Classe.class.getName() + ".precoEmolumentos");
            createCache(cm, com.ravunana.longonkelo.domain.Classe.class.getName() + ".nivesEnsinos");
            createCache(cm, com.ravunana.longonkelo.domain.Classe.class.getName() + ".periodosLancamentoNotas");
            createCache(cm, com.ravunana.longonkelo.domain.Disciplina.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Disciplina.class.getName() + ".responsaveis");
            createCache(cm, com.ravunana.longonkelo.domain.Disciplina.class.getName() + ".disciplinaCurriculars");
            createCache(cm, com.ravunana.longonkelo.domain.ResponsavelDisciplina.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.ResponsavelDisciplina.class.getName() + ".responsavels");
            createCache(cm, com.ravunana.longonkelo.domain.ResponsavelDisciplina.class.getName() + ".anoLectivos");
            createCache(cm, com.ravunana.longonkelo.domain.Docente.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Docente.class.getName() + ".ocorrencias");
            createCache(cm, com.ravunana.longonkelo.domain.Docente.class.getName() + ".horarios");
            createCache(cm, com.ravunana.longonkelo.domain.Docente.class.getName() + ".planoAulas");
            createCache(cm, com.ravunana.longonkelo.domain.Docente.class.getName() + ".notasPeriodicaDisciplinas");
            createCache(cm, com.ravunana.longonkelo.domain.Docente.class.getName() + ".notasGeralDisciplinas");
            createCache(cm, com.ravunana.longonkelo.domain.Docente.class.getName() + ".dissertacaoFinalCursos");
            createCache(cm, com.ravunana.longonkelo.domain.Docente.class.getName() + ".categoriaOcorrencias");
            createCache(cm, com.ravunana.longonkelo.domain.Docente.class.getName() + ".formacoes");
            createCache(cm, com.ravunana.longonkelo.domain.FormacaoDocente.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.EncarregadoEducacao.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.EncarregadoEducacao.class.getName() + ".discentes");
            createCache(cm, com.ravunana.longonkelo.domain.EncarregadoEducacao.class.getName() + ".matriculas");
            createCache(cm, com.ravunana.longonkelo.domain.Discente.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Discente.class.getName() + ".enderecos");
            createCache(cm, com.ravunana.longonkelo.domain.Discente.class.getName() + ".processosSelectivos");
            createCache(cm, com.ravunana.longonkelo.domain.Discente.class.getName() + ".anexoDiscentes");
            createCache(cm, com.ravunana.longonkelo.domain.Discente.class.getName() + ".matriculas");
            createCache(cm, com.ravunana.longonkelo.domain.Discente.class.getName() + ".resumoAcademicos");
            createCache(cm, com.ravunana.longonkelo.domain.Discente.class.getName() + ".historicosSaudes");
            createCache(cm, com.ravunana.longonkelo.domain.Discente.class.getName() + ".dissertacaoFinalCursos");
            createCache(cm, com.ravunana.longonkelo.domain.AnexoDiscente.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.EnderecoDiscente.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.PlanoCurricular.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.PlanoCurricular.class.getName() + ".turmas");
            createCache(cm, com.ravunana.longonkelo.domain.PlanoCurricular.class.getName() + ".disciplinasCurriculars");
            createCache(cm, com.ravunana.longonkelo.domain.DisciplinaCurricular.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.DisciplinaCurricular.class.getName() + ".disciplinaCurriculars");
            createCache(cm, com.ravunana.longonkelo.domain.DisciplinaCurricular.class.getName() + ".horarios");
            createCache(cm, com.ravunana.longonkelo.domain.DisciplinaCurricular.class.getName() + ".planoAulas");
            createCache(cm, com.ravunana.longonkelo.domain.DisciplinaCurricular.class.getName() + ".notasGeralDisciplinas");
            createCache(cm, com.ravunana.longonkelo.domain.DisciplinaCurricular.class.getName() + ".notasPeriodicaDisciplinas");
            createCache(cm, com.ravunana.longonkelo.domain.DisciplinaCurricular.class.getName() + ".planosCurriculars");
            createCache(cm, com.ravunana.longonkelo.domain.DisciplinaCurricular.class.getName() + ".estados");
            createCache(cm, com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular.class.getName() + ".estadoDisciplinaCurriculars");
            createCache(cm, com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular.class.getName() + ".notasPeriodicaDisciplinas");
            createCache(cm, com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular.class.getName() + ".notasGeralDisciplinas");
            createCache(cm, com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular.class.getName() + ".resumoAcademicos");
            createCache(cm, com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular.class.getName() + ".disciplinasCurriculars");
            createCache(cm, com.ravunana.longonkelo.domain.PeriodoHorario.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.PeriodoHorario.class.getName() + ".horarios");
            createCache(cm, com.ravunana.longonkelo.domain.Turma.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Turma.class.getName() + ".turmas");
            createCache(cm, com.ravunana.longonkelo.domain.Turma.class.getName() + ".horarios");
            createCache(cm, com.ravunana.longonkelo.domain.Turma.class.getName() + ".notasPeriodicaDisciplinas");
            createCache(cm, com.ravunana.longonkelo.domain.Turma.class.getName() + ".processoSelectivoMatriculas");
            createCache(cm, com.ravunana.longonkelo.domain.Turma.class.getName() + ".planoAulas");
            createCache(cm, com.ravunana.longonkelo.domain.Turma.class.getName() + ".matriculas");
            createCache(cm, com.ravunana.longonkelo.domain.Turma.class.getName() + ".resumoAcademicos");
            createCache(cm, com.ravunana.longonkelo.domain.Turma.class.getName() + ".responsaveis");
            createCache(cm, com.ravunana.longonkelo.domain.Turma.class.getName() + ".dissertacaoFinalCursos");
            createCache(cm, com.ravunana.longonkelo.domain.Turma.class.getName() + ".anoLectivos");
            createCache(cm, com.ravunana.longonkelo.domain.ResponsavelTurma.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.ResponsavelTurma.class.getName() + ".responsavels");
            createCache(cm, com.ravunana.longonkelo.domain.ResponsavelTurma.class.getName() + ".anoLectivos");
            createCache(cm, com.ravunana.longonkelo.domain.Horario.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Horario.class.getName() + ".horarios");
            createCache(cm, com.ravunana.longonkelo.domain.Horario.class.getName() + ".licaos");
            createCache(cm, com.ravunana.longonkelo.domain.Horario.class.getName() + ".anoLectivos");
            createCache(cm, com.ravunana.longonkelo.domain.PlanoAula.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.PlanoAula.class.getName() + ".detalhes");
            createCache(cm, com.ravunana.longonkelo.domain.PlanoAula.class.getName() + ".licaos");
            createCache(cm, com.ravunana.longonkelo.domain.PlanoAula.class.getName() + ".anoLectivos");
            createCache(cm, com.ravunana.longonkelo.domain.DetalhePlanoAula.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Licao.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Licao.class.getName() + ".ocorrencias");
            createCache(cm, com.ravunana.longonkelo.domain.Licao.class.getName() + ".anoLectivos");
            createCache(cm, com.ravunana.longonkelo.domain.HistoricoSaude.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.ProcessoSelectivoMatricula.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.ProcessoSelectivoMatricula.class.getName() + ".anoLectivos");
            createCache(cm, com.ravunana.longonkelo.domain.Matricula.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Matricula.class.getName() + ".matriculas");
            createCache(cm, com.ravunana.longonkelo.domain.Matricula.class.getName() + ".facturas");
            createCache(cm, com.ravunana.longonkelo.domain.Matricula.class.getName() + ".transacoes");
            createCache(cm, com.ravunana.longonkelo.domain.Matricula.class.getName() + ".recibos");
            createCache(cm, com.ravunana.longonkelo.domain.Matricula.class.getName() + ".notasPeriodicaDisciplinas");
            createCache(cm, com.ravunana.longonkelo.domain.Matricula.class.getName() + ".notasGeralDisciplinas");
            createCache(cm, com.ravunana.longonkelo.domain.Matricula.class.getName() + ".transferenciaTurmas");
            createCache(cm, com.ravunana.longonkelo.domain.Matricula.class.getName() + ".ocorrencias");
            createCache(cm, com.ravunana.longonkelo.domain.Matricula.class.getName() + ".categoriasMatriculas");
            createCache(cm, com.ravunana.longonkelo.domain.TransferenciaTurma.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.MedidaDisciplinar.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.MedidaDisciplinar.class.getName() + ".categoriaOcorrencias");
            createCache(cm, com.ravunana.longonkelo.domain.CategoriaOcorrencia.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.CategoriaOcorrencia.class.getName() + ".categoriaOcorrencias");
            createCache(cm, com.ravunana.longonkelo.domain.CategoriaOcorrencia.class.getName() + ".ocorrencias");
            createCache(cm, com.ravunana.longonkelo.domain.Ocorrencia.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Ocorrencia.class.getName() + ".ocorrencias");
            createCache(cm, com.ravunana.longonkelo.domain.Ocorrencia.class.getName() + ".anoLectivos");
            createCache(cm, com.ravunana.longonkelo.domain.PeriodoLancamentoNota.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.PeriodoLancamentoNota.class.getName() + ".classes");
            createCache(cm, com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina.class.getName() + ".anoLectivos");
            createCache(cm, com.ravunana.longonkelo.domain.NotasGeralDisciplina.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.NotasGeralDisciplina.class.getName() + ".anoLectivos");
            createCache(cm, com.ravunana.longonkelo.domain.EstadoDissertacao.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.EstadoDissertacao.class.getName() + ".dissertacaoFinalCursos");
            createCache(cm, com.ravunana.longonkelo.domain.CampoActuacaoDissertacao.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.CampoActuacaoDissertacao.class.getName() + ".cursos");
            createCache(cm, com.ravunana.longonkelo.domain.NaturezaTrabalho.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.NaturezaTrabalho.class.getName() + ".dissertacaoFinalCursos");
            createCache(cm, com.ravunana.longonkelo.domain.DissertacaoFinalCurso.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.DissertacaoFinalCurso.class.getName() + ".anoLectivos");
            createCache(cm, com.ravunana.longonkelo.domain.ResumoAcademico.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.DocumentoComercial.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.DocumentoComercial.class.getName() + ".serieDocumentos");
            createCache(cm, com.ravunana.longonkelo.domain.DocumentoComercial.class.getName() + ".facturas");
            createCache(cm, com.ravunana.longonkelo.domain.DocumentoComercial.class.getName() + ".recibos");
            createCache(cm, com.ravunana.longonkelo.domain.SerieDocumento.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.SerieDocumento.class.getName() + ".sequenciaDocumentos");
            createCache(cm, com.ravunana.longonkelo.domain.SequenciaDocumento.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Conta.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Conta.class.getName() + ".transacoes");
            createCache(cm, com.ravunana.longonkelo.domain.PlanoMulta.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.PlanoMulta.class.getName() + ".categoriaEmolumentos");
            createCache(cm, com.ravunana.longonkelo.domain.PlanoMulta.class.getName() + ".emolumentos");
            createCache(cm, com.ravunana.longonkelo.domain.PlanoMulta.class.getName() + ".precoEmolumentos");
            createCache(cm, com.ravunana.longonkelo.domain.CategoriaEmolumento.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.CategoriaEmolumento.class.getName() + ".emolumentos");
            createCache(cm, com.ravunana.longonkelo.domain.CategoriaEmolumento.class.getName() + ".planosDescontos");
            createCache(cm, com.ravunana.longonkelo.domain.Imposto.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Imposto.class.getName() + ".emolumentos");
            createCache(cm, com.ravunana.longonkelo.domain.Emolumento.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Emolumento.class.getName() + ".itemFacturas");
            createCache(cm, com.ravunana.longonkelo.domain.Emolumento.class.getName() + ".emolumentos");
            createCache(cm, com.ravunana.longonkelo.domain.Emolumento.class.getName() + ".precosEmolumentos");
            createCache(cm, com.ravunana.longonkelo.domain.PrecoEmolumento.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.PlanoDesconto.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.PlanoDesconto.class.getName() + ".categoriasEmolumentos");
            createCache(cm, com.ravunana.longonkelo.domain.PlanoDesconto.class.getName() + ".matriculas");
            createCache(cm, com.ravunana.longonkelo.domain.MeioPagamento.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.MeioPagamento.class.getName() + ".transacaos");
            createCache(cm, com.ravunana.longonkelo.domain.Transacao.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Transacao.class.getName() + ".recibos");
            createCache(cm, com.ravunana.longonkelo.domain.Transacao.class.getName() + ".transferenciaSaldos");
            createCache(cm, com.ravunana.longonkelo.domain.Factura.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Factura.class.getName() + ".facturas");
            createCache(cm, com.ravunana.longonkelo.domain.Factura.class.getName() + ".itemsFacturas");
            createCache(cm, com.ravunana.longonkelo.domain.Factura.class.getName() + ".aplicacoesFacturas");
            createCache(cm, com.ravunana.longonkelo.domain.Factura.class.getName() + ".resumosImpostos");
            createCache(cm, com.ravunana.longonkelo.domain.Factura.class.getName() + ".anoLectivos");
            createCache(cm, com.ravunana.longonkelo.domain.ItemFactura.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.ResumoImpostoFactura.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Recibo.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.Recibo.class.getName() + ".aplicacoesRecibos");
            createCache(cm, com.ravunana.longonkelo.domain.Recibo.class.getName() + ".anoLectivos");
            createCache(cm, com.ravunana.longonkelo.domain.AplicacaoRecibo.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.TransferenciaSaldo.class.getName());
            createCache(cm, com.ravunana.longonkelo.domain.TransferenciaSaldo.class.getName() + ".transacoes");
            createCache(cm, com.ravunana.longonkelo.domain.LongonkeloHistorico.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
