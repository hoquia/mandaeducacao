import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'instituicao-ensino',
        data: { pageTitle: 'longonkeloApp.instituicaoEnsino.home.title' },
        loadChildren: () => import('./instituicao-ensino/instituicao-ensino.module').then(m => m.InstituicaoEnsinoModule),
      },
      {
        path: 'provedor-notificacao',
        data: { pageTitle: 'longonkeloApp.provedorNotificacao.home.title' },
        loadChildren: () => import('./provedor-notificacao/provedor-notificacao.module').then(m => m.ProvedorNotificacaoModule),
      },
      {
        path: 'lookup',
        data: { pageTitle: 'longonkeloApp.lookup.home.title' },
        loadChildren: () => import('./lookup/lookup.module').then(m => m.LookupModule),
      },
      {
        path: 'lookup-item',
        data: { pageTitle: 'longonkeloApp.lookupItem.home.title' },
        loadChildren: () => import('./lookup-item/lookup-item.module').then(m => m.LookupItemModule),
      },
      {
        path: 'turno',
        data: { pageTitle: 'longonkeloApp.turno.home.title' },
        loadChildren: () => import('./turno/turno.module').then(m => m.TurnoModule),
      },
      {
        path: 'responsavel-turno',
        data: { pageTitle: 'longonkeloApp.responsavelTurno.home.title' },
        loadChildren: () => import('./responsavel-turno/responsavel-turno.module').then(m => m.ResponsavelTurnoModule),
      },
      {
        path: 'nivel-ensino',
        data: { pageTitle: 'longonkeloApp.nivelEnsino.home.title' },
        loadChildren: () => import('./nivel-ensino/nivel-ensino.module').then(m => m.NivelEnsinoModule),
      },
      {
        path: 'ano-lectivo',
        data: { pageTitle: 'longonkeloApp.anoLectivo.home.title' },
        loadChildren: () => import('./ano-lectivo/ano-lectivo.module').then(m => m.AnoLectivoModule),
      },
      {
        path: 'area-formacao',
        data: { pageTitle: 'longonkeloApp.areaFormacao.home.title' },
        loadChildren: () => import('./area-formacao/area-formacao.module').then(m => m.AreaFormacaoModule),
      },
      {
        path: 'responsavel-area-formacao',
        data: { pageTitle: 'longonkeloApp.responsavelAreaFormacao.home.title' },
        loadChildren: () =>
          import('./responsavel-area-formacao/responsavel-area-formacao.module').then(m => m.ResponsavelAreaFormacaoModule),
      },
      {
        path: 'curso',
        data: { pageTitle: 'longonkeloApp.curso.home.title' },
        loadChildren: () => import('./curso/curso.module').then(m => m.CursoModule),
      },
      {
        path: 'responsavel-curso',
        data: { pageTitle: 'longonkeloApp.responsavelCurso.home.title' },
        loadChildren: () => import('./responsavel-curso/responsavel-curso.module').then(m => m.ResponsavelCursoModule),
      },
      {
        path: 'classe',
        data: { pageTitle: 'longonkeloApp.classe.home.title' },
        loadChildren: () => import('./classe/classe.module').then(m => m.ClasseModule),
      },
      {
        path: 'disciplina',
        data: { pageTitle: 'longonkeloApp.disciplina.home.title' },
        loadChildren: () => import('./disciplina/disciplina.module').then(m => m.DisciplinaModule),
      },
      {
        path: 'responsavel-disciplina',
        data: { pageTitle: 'longonkeloApp.responsavelDisciplina.home.title' },
        loadChildren: () => import('./responsavel-disciplina/responsavel-disciplina.module').then(m => m.ResponsavelDisciplinaModule),
      },
      {
        path: 'docente',
        data: { pageTitle: 'longonkeloApp.docente.home.title' },
        loadChildren: () => import('./docente/docente.module').then(m => m.DocenteModule),
      },
      {
        path: 'formacao-docente',
        data: { pageTitle: 'longonkeloApp.formacaoDocente.home.title' },
        loadChildren: () => import('./formacao-docente/formacao-docente.module').then(m => m.FormacaoDocenteModule),
      },
      {
        path: 'encarregado-educacao',
        data: { pageTitle: 'longonkeloApp.encarregadoEducacao.home.title' },
        loadChildren: () => import('./encarregado-educacao/encarregado-educacao.module').then(m => m.EncarregadoEducacaoModule),
      },
      {
        path: 'discente',
        data: { pageTitle: 'longonkeloApp.discente.home.title' },
        loadChildren: () => import('./discente/discente.module').then(m => m.DiscenteModule),
      },
      {
        path: 'anexo-discente',
        data: { pageTitle: 'longonkeloApp.anexoDiscente.home.title' },
        loadChildren: () => import('./anexo-discente/anexo-discente.module').then(m => m.AnexoDiscenteModule),
      },
      {
        path: 'endereco-discente',
        data: { pageTitle: 'longonkeloApp.enderecoDiscente.home.title' },
        loadChildren: () => import('./endereco-discente/endereco-discente.module').then(m => m.EnderecoDiscenteModule),
      },
      {
        path: 'plano-curricular',
        data: { pageTitle: 'longonkeloApp.planoCurricular.home.title' },
        loadChildren: () => import('./plano-curricular/plano-curricular.module').then(m => m.PlanoCurricularModule),
      },
      {
        path: 'disciplina-curricular',
        data: { pageTitle: 'longonkeloApp.disciplinaCurricular.home.title' },
        loadChildren: () => import('./disciplina-curricular/disciplina-curricular.module').then(m => m.DisciplinaCurricularModule),
      },
      {
        path: 'estado-disciplina-curricular',
        data: { pageTitle: 'longonkeloApp.estadoDisciplinaCurricular.home.title' },
        loadChildren: () =>
          import('./estado-disciplina-curricular/estado-disciplina-curricular.module').then(m => m.EstadoDisciplinaCurricularModule),
      },
      {
        path: 'periodo-horario',
        data: { pageTitle: 'longonkeloApp.periodoHorario.home.title' },
        loadChildren: () => import('./periodo-horario/periodo-horario.module').then(m => m.PeriodoHorarioModule),
      },
      {
        path: 'turma',
        data: { pageTitle: 'longonkeloApp.turma.home.title' },
        loadChildren: () => import('./turma/turma.module').then(m => m.TurmaModule),
      },
      {
        path: 'responsavel-turma',
        data: { pageTitle: 'longonkeloApp.responsavelTurma.home.title' },
        loadChildren: () => import('./responsavel-turma/responsavel-turma.module').then(m => m.ResponsavelTurmaModule),
      },
      {
        path: 'horario',
        data: { pageTitle: 'longonkeloApp.horario.home.title' },
        loadChildren: () => import('./horario/horario.module').then(m => m.HorarioModule),
      },
      {
        path: 'plano-aula',
        data: { pageTitle: 'longonkeloApp.planoAula.home.title' },
        loadChildren: () => import('./plano-aula/plano-aula.module').then(m => m.PlanoAulaModule),
      },
      {
        path: 'detalhe-plano-aula',
        data: { pageTitle: 'longonkeloApp.detalhePlanoAula.home.title' },
        loadChildren: () => import('./detalhe-plano-aula/detalhe-plano-aula.module').then(m => m.DetalhePlanoAulaModule),
      },
      {
        path: 'licao',
        data: { pageTitle: 'longonkeloApp.licao.home.title' },
        loadChildren: () => import('./licao/licao.module').then(m => m.LicaoModule),
      },
      {
        path: 'historico-saude',
        data: { pageTitle: 'longonkeloApp.historicoSaude.home.title' },
        loadChildren: () => import('./historico-saude/historico-saude.module').then(m => m.HistoricoSaudeModule),
      },
      {
        path: 'processo-selectivo-matricula',
        data: { pageTitle: 'longonkeloApp.processoSelectivoMatricula.home.title' },
        loadChildren: () =>
          import('./processo-selectivo-matricula/processo-selectivo-matricula.module').then(m => m.ProcessoSelectivoMatriculaModule),
      },
      {
        path: 'matricula',
        data: { pageTitle: 'longonkeloApp.matricula.home.title' },
        loadChildren: () => import('./matricula/matricula.module').then(m => m.MatriculaModule),
      },
      {
        path: 'transferencia-turma',
        data: { pageTitle: 'longonkeloApp.transferenciaTurma.home.title' },
        loadChildren: () => import('./transferencia-turma/transferencia-turma.module').then(m => m.TransferenciaTurmaModule),
      },
      {
        path: 'medida-disciplinar',
        data: { pageTitle: 'longonkeloApp.medidaDisciplinar.home.title' },
        loadChildren: () => import('./medida-disciplinar/medida-disciplinar.module').then(m => m.MedidaDisciplinarModule),
      },
      {
        path: 'categoria-ocorrencia',
        data: { pageTitle: 'longonkeloApp.categoriaOcorrencia.home.title' },
        loadChildren: () => import('./categoria-ocorrencia/categoria-ocorrencia.module').then(m => m.CategoriaOcorrenciaModule),
      },
      {
        path: 'ocorrencia',
        data: { pageTitle: 'longonkeloApp.ocorrencia.home.title' },
        loadChildren: () => import('./ocorrencia/ocorrencia.module').then(m => m.OcorrenciaModule),
      },
      {
        path: 'periodo-lancamento-nota',
        data: { pageTitle: 'longonkeloApp.periodoLancamentoNota.home.title' },
        loadChildren: () => import('./periodo-lancamento-nota/periodo-lancamento-nota.module').then(m => m.PeriodoLancamentoNotaModule),
      },
      {
        path: 'notas-periodica-disciplina',
        data: { pageTitle: 'longonkeloApp.notasPeriodicaDisciplina.home.title' },
        loadChildren: () =>
          import('./notas-periodica-disciplina/notas-periodica-disciplina.module').then(m => m.NotasPeriodicaDisciplinaModule),
      },
      {
        path: 'notas-geral-disciplina',
        data: { pageTitle: 'longonkeloApp.notasGeralDisciplina.home.title' },
        loadChildren: () => import('./notas-geral-disciplina/notas-geral-disciplina.module').then(m => m.NotasGeralDisciplinaModule),
      },
      {
        path: 'estado-dissertacao',
        data: { pageTitle: 'longonkeloApp.estadoDissertacao.home.title' },
        loadChildren: () => import('./estado-dissertacao/estado-dissertacao.module').then(m => m.EstadoDissertacaoModule),
      },
      {
        path: 'campo-actuacao-dissertacao',
        data: { pageTitle: 'longonkeloApp.campoActuacaoDissertacao.home.title' },
        loadChildren: () =>
          import('./campo-actuacao-dissertacao/campo-actuacao-dissertacao.module').then(m => m.CampoActuacaoDissertacaoModule),
      },
      {
        path: 'natureza-trabalho',
        data: { pageTitle: 'longonkeloApp.naturezaTrabalho.home.title' },
        loadChildren: () => import('./natureza-trabalho/natureza-trabalho.module').then(m => m.NaturezaTrabalhoModule),
      },
      {
        path: 'dissertacao-final-curso',
        data: { pageTitle: 'longonkeloApp.dissertacaoFinalCurso.home.title' },
        loadChildren: () => import('./dissertacao-final-curso/dissertacao-final-curso.module').then(m => m.DissertacaoFinalCursoModule),
      },
      {
        path: 'resumo-academico',
        data: { pageTitle: 'longonkeloApp.resumoAcademico.home.title' },
        loadChildren: () => import('./resumo-academico/resumo-academico.module').then(m => m.ResumoAcademicoModule),
      },
      {
        path: 'documento-comercial',
        data: { pageTitle: 'longonkeloApp.documentoComercial.home.title' },
        loadChildren: () => import('./documento-comercial/documento-comercial.module').then(m => m.DocumentoComercialModule),
      },
      {
        path: 'serie-documento',
        data: { pageTitle: 'longonkeloApp.serieDocumento.home.title' },
        loadChildren: () => import('./serie-documento/serie-documento.module').then(m => m.SerieDocumentoModule),
      },
      {
        path: 'sequencia-documento',
        data: { pageTitle: 'longonkeloApp.sequenciaDocumento.home.title' },
        loadChildren: () => import('./sequencia-documento/sequencia-documento.module').then(m => m.SequenciaDocumentoModule),
      },
      {
        path: 'conta',
        data: { pageTitle: 'longonkeloApp.conta.home.title' },
        loadChildren: () => import('./conta/conta.module').then(m => m.ContaModule),
      },
      {
        path: 'plano-multa',
        data: { pageTitle: 'longonkeloApp.planoMulta.home.title' },
        loadChildren: () => import('./plano-multa/plano-multa.module').then(m => m.PlanoMultaModule),
      },
      {
        path: 'categoria-emolumento',
        data: { pageTitle: 'longonkeloApp.categoriaEmolumento.home.title' },
        loadChildren: () => import('./categoria-emolumento/categoria-emolumento.module').then(m => m.CategoriaEmolumentoModule),
      },
      {
        path: 'imposto',
        data: { pageTitle: 'longonkeloApp.imposto.home.title' },
        loadChildren: () => import('./imposto/imposto.module').then(m => m.ImpostoModule),
      },
      {
        path: 'emolumento',
        data: { pageTitle: 'longonkeloApp.emolumento.home.title' },
        loadChildren: () => import('./emolumento/emolumento.module').then(m => m.EmolumentoModule),
      },
      {
        path: 'preco-emolumento',
        data: { pageTitle: 'longonkeloApp.precoEmolumento.home.title' },
        loadChildren: () => import('./preco-emolumento/preco-emolumento.module').then(m => m.PrecoEmolumentoModule),
      },
      {
        path: 'plano-desconto',
        data: { pageTitle: 'longonkeloApp.planoDesconto.home.title' },
        loadChildren: () => import('./plano-desconto/plano-desconto.module').then(m => m.PlanoDescontoModule),
      },
      {
        path: 'meio-pagamento',
        data: { pageTitle: 'longonkeloApp.meioPagamento.home.title' },
        loadChildren: () => import('./meio-pagamento/meio-pagamento.module').then(m => m.MeioPagamentoModule),
      },
      {
        path: 'transacao',
        data: { pageTitle: 'longonkeloApp.transacao.home.title' },
        loadChildren: () => import('./transacao/transacao.module').then(m => m.TransacaoModule),
      },
      {
        path: 'factura',
        data: { pageTitle: 'longonkeloApp.factura.home.title' },
        loadChildren: () => import('./factura/factura.module').then(m => m.FacturaModule),
      },
      {
        path: 'item-factura',
        data: { pageTitle: 'longonkeloApp.itemFactura.home.title' },
        loadChildren: () => import('./item-factura/item-factura.module').then(m => m.ItemFacturaModule),
      },
      {
        path: 'resumo-imposto-factura',
        data: { pageTitle: 'longonkeloApp.resumoImpostoFactura.home.title' },
        loadChildren: () => import('./resumo-imposto-factura/resumo-imposto-factura.module').then(m => m.ResumoImpostoFacturaModule),
      },
      {
        path: 'recibo',
        data: { pageTitle: 'longonkeloApp.recibo.home.title' },
        loadChildren: () => import('./recibo/recibo.module').then(m => m.ReciboModule),
      },
      {
        path: 'aplicacao-recibo',
        data: { pageTitle: 'longonkeloApp.aplicacaoRecibo.home.title' },
        loadChildren: () => import('./aplicacao-recibo/aplicacao-recibo.module').then(m => m.AplicacaoReciboModule),
      },
      {
        path: 'transferencia-saldo',
        data: { pageTitle: 'longonkeloApp.transferenciaSaldo.home.title' },
        loadChildren: () => import('./transferencia-saldo/transferencia-saldo.module').then(m => m.TransferenciaSaldoModule),
      },
      {
        path: 'longonkelo-historico',
        data: { pageTitle: 'longonkeloApp.longonkeloHistorico.home.title' },
        loadChildren: () => import('./longonkelo-historico/longonkelo-historico.module').then(m => m.LongonkeloHistoricoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
