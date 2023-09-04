import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AnoLectivoFormService, AnoLectivoFormGroup } from './ano-lectivo-form.service';
import { IAnoLectivo } from '../ano-lectivo.model';
import { AnoLectivoService } from '../service/ano-lectivo.service';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { INivelEnsino } from 'app/entities/nivel-ensino/nivel-ensino.model';
import { NivelEnsinoService } from 'app/entities/nivel-ensino/service/nivel-ensino.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { IHorario } from 'app/entities/horario/horario.model';
import { HorarioService } from 'app/entities/horario/service/horario.service';
import { IPlanoAula } from 'app/entities/plano-aula/plano-aula.model';
import { PlanoAulaService } from 'app/entities/plano-aula/service/plano-aula.service';
import { ILicao } from 'app/entities/licao/licao.model';
import { LicaoService } from 'app/entities/licao/service/licao.service';
import { IProcessoSelectivoMatricula } from 'app/entities/processo-selectivo-matricula/processo-selectivo-matricula.model';
import { ProcessoSelectivoMatriculaService } from 'app/entities/processo-selectivo-matricula/service/processo-selectivo-matricula.service';
import { IOcorrencia } from 'app/entities/ocorrencia/ocorrencia.model';
import { OcorrenciaService } from 'app/entities/ocorrencia/service/ocorrencia.service';
import { INotasPeriodicaDisciplina } from 'app/entities/notas-periodica-disciplina/notas-periodica-disciplina.model';
import { NotasPeriodicaDisciplinaService } from 'app/entities/notas-periodica-disciplina/service/notas-periodica-disciplina.service';
import { INotasGeralDisciplina } from 'app/entities/notas-geral-disciplina/notas-geral-disciplina.model';
import { NotasGeralDisciplinaService } from 'app/entities/notas-geral-disciplina/service/notas-geral-disciplina.service';
import { IDissertacaoFinalCurso } from 'app/entities/dissertacao-final-curso/dissertacao-final-curso.model';
import { DissertacaoFinalCursoService } from 'app/entities/dissertacao-final-curso/service/dissertacao-final-curso.service';
import { IFactura } from 'app/entities/factura/factura.model';
import { FacturaService } from 'app/entities/factura/service/factura.service';
import { IRecibo } from 'app/entities/recibo/recibo.model';
import { ReciboService } from 'app/entities/recibo/service/recibo.service';
import { IResponsavelTurno } from 'app/entities/responsavel-turno/responsavel-turno.model';
import { ResponsavelTurnoService } from 'app/entities/responsavel-turno/service/responsavel-turno.service';
import { IResponsavelAreaFormacao } from 'app/entities/responsavel-area-formacao/responsavel-area-formacao.model';
import { ResponsavelAreaFormacaoService } from 'app/entities/responsavel-area-formacao/service/responsavel-area-formacao.service';
import { IResponsavelCurso } from 'app/entities/responsavel-curso/responsavel-curso.model';
import { ResponsavelCursoService } from 'app/entities/responsavel-curso/service/responsavel-curso.service';
import { IResponsavelDisciplina } from 'app/entities/responsavel-disciplina/responsavel-disciplina.model';
import { ResponsavelDisciplinaService } from 'app/entities/responsavel-disciplina/service/responsavel-disciplina.service';
import { IResponsavelTurma } from 'app/entities/responsavel-turma/responsavel-turma.model';
import { ResponsavelTurmaService } from 'app/entities/responsavel-turma/service/responsavel-turma.service';

@Component({
  selector: 'app-ano-lectivo-update',
  templateUrl: './ano-lectivo-update.component.html',
})
export class AnoLectivoUpdateComponent implements OnInit {
  isSaving = false;
  anoLectivo: IAnoLectivo | null = null;

  docentesSharedCollection: IDocente[] = [];
  usersSharedCollection: IUser[] = [];
  nivelEnsinosSharedCollection: INivelEnsino[] = [];
  turmasSharedCollection: ITurma[] = [];
  horariosSharedCollection: IHorario[] = [];
  planoAulasSharedCollection: IPlanoAula[] = [];
  licaosSharedCollection: ILicao[] = [];
  processoSelectivoMatriculasSharedCollection: IProcessoSelectivoMatricula[] = [];
  ocorrenciasSharedCollection: IOcorrencia[] = [];
  notasPeriodicaDisciplinasSharedCollection: INotasPeriodicaDisciplina[] = [];
  notasGeralDisciplinasSharedCollection: INotasGeralDisciplina[] = [];
  dissertacaoFinalCursosSharedCollection: IDissertacaoFinalCurso[] = [];
  facturasSharedCollection: IFactura[] = [];
  recibosSharedCollection: IRecibo[] = [];
  responsavelTurnosSharedCollection: IResponsavelTurno[] = [];
  responsavelAreaFormacaosSharedCollection: IResponsavelAreaFormacao[] = [];
  responsavelCursosSharedCollection: IResponsavelCurso[] = [];
  responsavelDisciplinasSharedCollection: IResponsavelDisciplina[] = [];
  responsavelTurmasSharedCollection: IResponsavelTurma[] = [];

  editForm: AnoLectivoFormGroup = this.anoLectivoFormService.createAnoLectivoFormGroup();

  constructor(
    protected anoLectivoService: AnoLectivoService,
    protected anoLectivoFormService: AnoLectivoFormService,
    protected docenteService: DocenteService,
    protected userService: UserService,
    protected nivelEnsinoService: NivelEnsinoService,
    protected turmaService: TurmaService,
    protected horarioService: HorarioService,
    protected planoAulaService: PlanoAulaService,
    protected licaoService: LicaoService,
    protected processoSelectivoMatriculaService: ProcessoSelectivoMatriculaService,
    protected ocorrenciaService: OcorrenciaService,
    protected notasPeriodicaDisciplinaService: NotasPeriodicaDisciplinaService,
    protected notasGeralDisciplinaService: NotasGeralDisciplinaService,
    protected dissertacaoFinalCursoService: DissertacaoFinalCursoService,
    protected facturaService: FacturaService,
    protected reciboService: ReciboService,
    protected responsavelTurnoService: ResponsavelTurnoService,
    protected responsavelAreaFormacaoService: ResponsavelAreaFormacaoService,
    protected responsavelCursoService: ResponsavelCursoService,
    protected responsavelDisciplinaService: ResponsavelDisciplinaService,
    protected responsavelTurmaService: ResponsavelTurmaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDocente = (o1: IDocente | null, o2: IDocente | null): boolean => this.docenteService.compareDocente(o1, o2);

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareNivelEnsino = (o1: INivelEnsino | null, o2: INivelEnsino | null): boolean => this.nivelEnsinoService.compareNivelEnsino(o1, o2);

  compareTurma = (o1: ITurma | null, o2: ITurma | null): boolean => this.turmaService.compareTurma(o1, o2);

  compareHorario = (o1: IHorario | null, o2: IHorario | null): boolean => this.horarioService.compareHorario(o1, o2);

  comparePlanoAula = (o1: IPlanoAula | null, o2: IPlanoAula | null): boolean => this.planoAulaService.comparePlanoAula(o1, o2);

  compareLicao = (o1: ILicao | null, o2: ILicao | null): boolean => this.licaoService.compareLicao(o1, o2);

  compareProcessoSelectivoMatricula = (o1: IProcessoSelectivoMatricula | null, o2: IProcessoSelectivoMatricula | null): boolean =>
    this.processoSelectivoMatriculaService.compareProcessoSelectivoMatricula(o1, o2);

  compareOcorrencia = (o1: IOcorrencia | null, o2: IOcorrencia | null): boolean => this.ocorrenciaService.compareOcorrencia(o1, o2);

  compareNotasPeriodicaDisciplina = (o1: INotasPeriodicaDisciplina | null, o2: INotasPeriodicaDisciplina | null): boolean =>
    this.notasPeriodicaDisciplinaService.compareNotasPeriodicaDisciplina(o1, o2);

  compareNotasGeralDisciplina = (o1: INotasGeralDisciplina | null, o2: INotasGeralDisciplina | null): boolean =>
    this.notasGeralDisciplinaService.compareNotasGeralDisciplina(o1, o2);

  compareDissertacaoFinalCurso = (o1: IDissertacaoFinalCurso | null, o2: IDissertacaoFinalCurso | null): boolean =>
    this.dissertacaoFinalCursoService.compareDissertacaoFinalCurso(o1, o2);

  compareFactura = (o1: IFactura | null, o2: IFactura | null): boolean => this.facturaService.compareFactura(o1, o2);

  compareRecibo = (o1: IRecibo | null, o2: IRecibo | null): boolean => this.reciboService.compareRecibo(o1, o2);

  compareResponsavelTurno = (o1: IResponsavelTurno | null, o2: IResponsavelTurno | null): boolean =>
    this.responsavelTurnoService.compareResponsavelTurno(o1, o2);

  compareResponsavelAreaFormacao = (o1: IResponsavelAreaFormacao | null, o2: IResponsavelAreaFormacao | null): boolean =>
    this.responsavelAreaFormacaoService.compareResponsavelAreaFormacao(o1, o2);

  compareResponsavelCurso = (o1: IResponsavelCurso | null, o2: IResponsavelCurso | null): boolean =>
    this.responsavelCursoService.compareResponsavelCurso(o1, o2);

  compareResponsavelDisciplina = (o1: IResponsavelDisciplina | null, o2: IResponsavelDisciplina | null): boolean =>
    this.responsavelDisciplinaService.compareResponsavelDisciplina(o1, o2);

  compareResponsavelTurma = (o1: IResponsavelTurma | null, o2: IResponsavelTurma | null): boolean =>
    this.responsavelTurmaService.compareResponsavelTurma(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anoLectivo }) => {
      this.anoLectivo = anoLectivo;
      if (anoLectivo) {
        this.updateForm(anoLectivo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const anoLectivo = this.anoLectivoFormService.getAnoLectivo(this.editForm);
    if (anoLectivo.id !== null) {
      this.subscribeToSaveResponse(this.anoLectivoService.update(anoLectivo));
    } else {
      this.subscribeToSaveResponse(this.anoLectivoService.create(anoLectivo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnoLectivo>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(anoLectivo: IAnoLectivo): void {
    this.anoLectivo = anoLectivo;
    this.anoLectivoFormService.resetForm(this.editForm, anoLectivo);

    this.docentesSharedCollection = this.docenteService.addDocenteToCollectionIfMissing<IDocente>(
      this.docentesSharedCollection,
      anoLectivo.directorGeral,
      anoLectivo.subDirectorPdagogico,
      anoLectivo.subDirectorAdministrativo,
      anoLectivo.responsavelSecretariaGeral,
      anoLectivo.responsavelSecretariaPedagogico
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, anoLectivo.utilizador);
    this.nivelEnsinosSharedCollection = this.nivelEnsinoService.addNivelEnsinoToCollectionIfMissing<INivelEnsino>(
      this.nivelEnsinosSharedCollection,
      ...(anoLectivo.nivesEnsinos ?? [])
    );
    this.turmasSharedCollection = this.turmaService.addTurmaToCollectionIfMissing<ITurma>(this.turmasSharedCollection, anoLectivo.turma);
    this.horariosSharedCollection = this.horarioService.addHorarioToCollectionIfMissing<IHorario>(
      this.horariosSharedCollection,
      anoLectivo.horario
    );
    this.planoAulasSharedCollection = this.planoAulaService.addPlanoAulaToCollectionIfMissing<IPlanoAula>(
      this.planoAulasSharedCollection,
      anoLectivo.planoAula
    );
    this.licaosSharedCollection = this.licaoService.addLicaoToCollectionIfMissing<ILicao>(this.licaosSharedCollection, anoLectivo.licao);
    this.processoSelectivoMatriculasSharedCollection =
      this.processoSelectivoMatriculaService.addProcessoSelectivoMatriculaToCollectionIfMissing<IProcessoSelectivoMatricula>(
        this.processoSelectivoMatriculasSharedCollection,
        anoLectivo.processoSelectivoMatricula
      );
    this.ocorrenciasSharedCollection = this.ocorrenciaService.addOcorrenciaToCollectionIfMissing<IOcorrencia>(
      this.ocorrenciasSharedCollection,
      anoLectivo.ocorrencia
    );
    this.notasPeriodicaDisciplinasSharedCollection =
      this.notasPeriodicaDisciplinaService.addNotasPeriodicaDisciplinaToCollectionIfMissing<INotasPeriodicaDisciplina>(
        this.notasPeriodicaDisciplinasSharedCollection,
        anoLectivo.notasPeriodicaDisciplina
      );
    this.notasGeralDisciplinasSharedCollection =
      this.notasGeralDisciplinaService.addNotasGeralDisciplinaToCollectionIfMissing<INotasGeralDisciplina>(
        this.notasGeralDisciplinasSharedCollection,
        anoLectivo.notasGeralDisciplina
      );
    this.dissertacaoFinalCursosSharedCollection =
      this.dissertacaoFinalCursoService.addDissertacaoFinalCursoToCollectionIfMissing<IDissertacaoFinalCurso>(
        this.dissertacaoFinalCursosSharedCollection,
        anoLectivo.dissertacaoFinalCurso
      );
    this.facturasSharedCollection = this.facturaService.addFacturaToCollectionIfMissing<IFactura>(
      this.facturasSharedCollection,
      anoLectivo.factura
    );
    this.recibosSharedCollection = this.reciboService.addReciboToCollectionIfMissing<IRecibo>(
      this.recibosSharedCollection,
      anoLectivo.recibo
    );
    this.responsavelTurnosSharedCollection = this.responsavelTurnoService.addResponsavelTurnoToCollectionIfMissing<IResponsavelTurno>(
      this.responsavelTurnosSharedCollection,
      anoLectivo.responsavelTurno
    );
    this.responsavelAreaFormacaosSharedCollection =
      this.responsavelAreaFormacaoService.addResponsavelAreaFormacaoToCollectionIfMissing<IResponsavelAreaFormacao>(
        this.responsavelAreaFormacaosSharedCollection,
        anoLectivo.responsavelAreaFormacao
      );
    this.responsavelCursosSharedCollection = this.responsavelCursoService.addResponsavelCursoToCollectionIfMissing<IResponsavelCurso>(
      this.responsavelCursosSharedCollection,
      anoLectivo.responsavelCurso
    );
    this.responsavelDisciplinasSharedCollection =
      this.responsavelDisciplinaService.addResponsavelDisciplinaToCollectionIfMissing<IResponsavelDisciplina>(
        this.responsavelDisciplinasSharedCollection,
        anoLectivo.responsavelDisciplina
      );
    this.responsavelTurmasSharedCollection = this.responsavelTurmaService.addResponsavelTurmaToCollectionIfMissing<IResponsavelTurma>(
      this.responsavelTurmasSharedCollection,
      anoLectivo.responsavelTurma
    );
  }

  protected loadRelationshipsOptions(): void {
    this.docenteService
      .query()
      .pipe(map((res: HttpResponse<IDocente[]>) => res.body ?? []))
      .pipe(
        map((docentes: IDocente[]) =>
          this.docenteService.addDocenteToCollectionIfMissing<IDocente>(
            docentes,
            this.anoLectivo?.directorGeral,
            this.anoLectivo?.subDirectorPdagogico,
            this.anoLectivo?.subDirectorAdministrativo,
            this.anoLectivo?.responsavelSecretariaGeral,
            this.anoLectivo?.responsavelSecretariaPedagogico
          )
        )
      )
      .subscribe((docentes: IDocente[]) => (this.docentesSharedCollection = docentes));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.anoLectivo?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.nivelEnsinoService
      .query()
      .pipe(map((res: HttpResponse<INivelEnsino[]>) => res.body ?? []))
      .pipe(
        map((nivelEnsinos: INivelEnsino[]) =>
          this.nivelEnsinoService.addNivelEnsinoToCollectionIfMissing<INivelEnsino>(nivelEnsinos, ...(this.anoLectivo?.nivesEnsinos ?? []))
        )
      )
      .subscribe((nivelEnsinos: INivelEnsino[]) => (this.nivelEnsinosSharedCollection = nivelEnsinos));

    this.turmaService
      .query()
      .pipe(map((res: HttpResponse<ITurma[]>) => res.body ?? []))
      .pipe(map((turmas: ITurma[]) => this.turmaService.addTurmaToCollectionIfMissing<ITurma>(turmas, this.anoLectivo?.turma)))
      .subscribe((turmas: ITurma[]) => (this.turmasSharedCollection = turmas));

    this.horarioService
      .query()
      .pipe(map((res: HttpResponse<IHorario[]>) => res.body ?? []))
      .pipe(
        map((horarios: IHorario[]) => this.horarioService.addHorarioToCollectionIfMissing<IHorario>(horarios, this.anoLectivo?.horario))
      )
      .subscribe((horarios: IHorario[]) => (this.horariosSharedCollection = horarios));

    this.planoAulaService
      .query()
      .pipe(map((res: HttpResponse<IPlanoAula[]>) => res.body ?? []))
      .pipe(
        map((planoAulas: IPlanoAula[]) =>
          this.planoAulaService.addPlanoAulaToCollectionIfMissing<IPlanoAula>(planoAulas, this.anoLectivo?.planoAula)
        )
      )
      .subscribe((planoAulas: IPlanoAula[]) => (this.planoAulasSharedCollection = planoAulas));

    this.licaoService
      .query()
      .pipe(map((res: HttpResponse<ILicao[]>) => res.body ?? []))
      .pipe(map((licaos: ILicao[]) => this.licaoService.addLicaoToCollectionIfMissing<ILicao>(licaos, this.anoLectivo?.licao)))
      .subscribe((licaos: ILicao[]) => (this.licaosSharedCollection = licaos));

    this.processoSelectivoMatriculaService
      .query()
      .pipe(map((res: HttpResponse<IProcessoSelectivoMatricula[]>) => res.body ?? []))
      .pipe(
        map((processoSelectivoMatriculas: IProcessoSelectivoMatricula[]) =>
          this.processoSelectivoMatriculaService.addProcessoSelectivoMatriculaToCollectionIfMissing<IProcessoSelectivoMatricula>(
            processoSelectivoMatriculas,
            this.anoLectivo?.processoSelectivoMatricula
          )
        )
      )
      .subscribe(
        (processoSelectivoMatriculas: IProcessoSelectivoMatricula[]) =>
          (this.processoSelectivoMatriculasSharedCollection = processoSelectivoMatriculas)
      );

    this.ocorrenciaService
      .query()
      .pipe(map((res: HttpResponse<IOcorrencia[]>) => res.body ?? []))
      .pipe(
        map((ocorrencias: IOcorrencia[]) =>
          this.ocorrenciaService.addOcorrenciaToCollectionIfMissing<IOcorrencia>(ocorrencias, this.anoLectivo?.ocorrencia)
        )
      )
      .subscribe((ocorrencias: IOcorrencia[]) => (this.ocorrenciasSharedCollection = ocorrencias));

    this.notasPeriodicaDisciplinaService
      .query()
      .pipe(map((res: HttpResponse<INotasPeriodicaDisciplina[]>) => res.body ?? []))
      .pipe(
        map((notasPeriodicaDisciplinas: INotasPeriodicaDisciplina[]) =>
          this.notasPeriodicaDisciplinaService.addNotasPeriodicaDisciplinaToCollectionIfMissing<INotasPeriodicaDisciplina>(
            notasPeriodicaDisciplinas,
            this.anoLectivo?.notasPeriodicaDisciplina
          )
        )
      )
      .subscribe(
        (notasPeriodicaDisciplinas: INotasPeriodicaDisciplina[]) =>
          (this.notasPeriodicaDisciplinasSharedCollection = notasPeriodicaDisciplinas)
      );

    this.notasGeralDisciplinaService
      .query()
      .pipe(map((res: HttpResponse<INotasGeralDisciplina[]>) => res.body ?? []))
      .pipe(
        map((notasGeralDisciplinas: INotasGeralDisciplina[]) =>
          this.notasGeralDisciplinaService.addNotasGeralDisciplinaToCollectionIfMissing<INotasGeralDisciplina>(
            notasGeralDisciplinas,
            this.anoLectivo?.notasGeralDisciplina
          )
        )
      )
      .subscribe((notasGeralDisciplinas: INotasGeralDisciplina[]) => (this.notasGeralDisciplinasSharedCollection = notasGeralDisciplinas));

    this.dissertacaoFinalCursoService
      .query()
      .pipe(map((res: HttpResponse<IDissertacaoFinalCurso[]>) => res.body ?? []))
      .pipe(
        map((dissertacaoFinalCursos: IDissertacaoFinalCurso[]) =>
          this.dissertacaoFinalCursoService.addDissertacaoFinalCursoToCollectionIfMissing<IDissertacaoFinalCurso>(
            dissertacaoFinalCursos,
            this.anoLectivo?.dissertacaoFinalCurso
          )
        )
      )
      .subscribe(
        (dissertacaoFinalCursos: IDissertacaoFinalCurso[]) => (this.dissertacaoFinalCursosSharedCollection = dissertacaoFinalCursos)
      );

    this.facturaService
      .query()
      .pipe(map((res: HttpResponse<IFactura[]>) => res.body ?? []))
      .pipe(
        map((facturas: IFactura[]) => this.facturaService.addFacturaToCollectionIfMissing<IFactura>(facturas, this.anoLectivo?.factura))
      )
      .subscribe((facturas: IFactura[]) => (this.facturasSharedCollection = facturas));

    this.reciboService
      .query()
      .pipe(map((res: HttpResponse<IRecibo[]>) => res.body ?? []))
      .pipe(map((recibos: IRecibo[]) => this.reciboService.addReciboToCollectionIfMissing<IRecibo>(recibos, this.anoLectivo?.recibo)))
      .subscribe((recibos: IRecibo[]) => (this.recibosSharedCollection = recibos));

    this.responsavelTurnoService
      .query()
      .pipe(map((res: HttpResponse<IResponsavelTurno[]>) => res.body ?? []))
      .pipe(
        map((responsavelTurnos: IResponsavelTurno[]) =>
          this.responsavelTurnoService.addResponsavelTurnoToCollectionIfMissing<IResponsavelTurno>(
            responsavelTurnos,
            this.anoLectivo?.responsavelTurno
          )
        )
      )
      .subscribe((responsavelTurnos: IResponsavelTurno[]) => (this.responsavelTurnosSharedCollection = responsavelTurnos));

    this.responsavelAreaFormacaoService
      .query()
      .pipe(map((res: HttpResponse<IResponsavelAreaFormacao[]>) => res.body ?? []))
      .pipe(
        map((responsavelAreaFormacaos: IResponsavelAreaFormacao[]) =>
          this.responsavelAreaFormacaoService.addResponsavelAreaFormacaoToCollectionIfMissing<IResponsavelAreaFormacao>(
            responsavelAreaFormacaos,
            this.anoLectivo?.responsavelAreaFormacao
          )
        )
      )
      .subscribe(
        (responsavelAreaFormacaos: IResponsavelAreaFormacao[]) => (this.responsavelAreaFormacaosSharedCollection = responsavelAreaFormacaos)
      );

    this.responsavelCursoService
      .query()
      .pipe(map((res: HttpResponse<IResponsavelCurso[]>) => res.body ?? []))
      .pipe(
        map((responsavelCursos: IResponsavelCurso[]) =>
          this.responsavelCursoService.addResponsavelCursoToCollectionIfMissing<IResponsavelCurso>(
            responsavelCursos,
            this.anoLectivo?.responsavelCurso
          )
        )
      )
      .subscribe((responsavelCursos: IResponsavelCurso[]) => (this.responsavelCursosSharedCollection = responsavelCursos));

    this.responsavelDisciplinaService
      .query()
      .pipe(map((res: HttpResponse<IResponsavelDisciplina[]>) => res.body ?? []))
      .pipe(
        map((responsavelDisciplinas: IResponsavelDisciplina[]) =>
          this.responsavelDisciplinaService.addResponsavelDisciplinaToCollectionIfMissing<IResponsavelDisciplina>(
            responsavelDisciplinas,
            this.anoLectivo?.responsavelDisciplina
          )
        )
      )
      .subscribe(
        (responsavelDisciplinas: IResponsavelDisciplina[]) => (this.responsavelDisciplinasSharedCollection = responsavelDisciplinas)
      );

    this.responsavelTurmaService
      .query()
      .pipe(map((res: HttpResponse<IResponsavelTurma[]>) => res.body ?? []))
      .pipe(
        map((responsavelTurmas: IResponsavelTurma[]) =>
          this.responsavelTurmaService.addResponsavelTurmaToCollectionIfMissing<IResponsavelTurma>(
            responsavelTurmas,
            this.anoLectivo?.responsavelTurma
          )
        )
      )
      .subscribe((responsavelTurmas: IResponsavelTurma[]) => (this.responsavelTurmasSharedCollection = responsavelTurmas));
  }
}
