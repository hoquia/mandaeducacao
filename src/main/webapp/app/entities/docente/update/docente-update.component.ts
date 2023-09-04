import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DocenteFormService, DocenteFormGroup } from './docente-form.service';
import { IDocente } from '../docente.model';
import { DocenteService } from '../service/docente.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
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
import { Sexo } from 'app/entities/enumerations/sexo.model';

@Component({
  selector: 'app-docente-update',
  templateUrl: './docente-update.component.html',
})
export class DocenteUpdateComponent implements OnInit {
  isSaving = false;
  docente: IDocente | null = null;
  sexoValues = Object.keys(Sexo);

  lookupItemsSharedCollection: ILookupItem[] = [];
  responsavelTurnosSharedCollection: IResponsavelTurno[] = [];
  responsavelAreaFormacaosSharedCollection: IResponsavelAreaFormacao[] = [];
  responsavelCursosSharedCollection: IResponsavelCurso[] = [];
  responsavelDisciplinasSharedCollection: IResponsavelDisciplina[] = [];
  responsavelTurmasSharedCollection: IResponsavelTurma[] = [];

  editForm: DocenteFormGroup = this.docenteFormService.createDocenteFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected docenteService: DocenteService,
    protected docenteFormService: DocenteFormService,
    protected lookupItemService: LookupItemService,
    protected responsavelTurnoService: ResponsavelTurnoService,
    protected responsavelAreaFormacaoService: ResponsavelAreaFormacaoService,
    protected responsavelCursoService: ResponsavelCursoService,
    protected responsavelDisciplinaService: ResponsavelDisciplinaService,
    protected responsavelTurmaService: ResponsavelTurmaService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLookupItem = (o1: ILookupItem | null, o2: ILookupItem | null): boolean => this.lookupItemService.compareLookupItem(o1, o2);

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
    this.activatedRoute.data.subscribe(({ docente }) => {
      this.docente = docente;
      if (docente) {
        this.updateForm(docente);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('longonkeloApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const docente = this.docenteFormService.getDocente(this.editForm);
    if (docente.id !== null) {
      this.subscribeToSaveResponse(this.docenteService.update(docente));
    } else {
      this.subscribeToSaveResponse(this.docenteService.create(docente));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocente>>): void {
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

  protected updateForm(docente: IDocente): void {
    this.docente = docente;
    this.docenteFormService.resetForm(this.editForm, docente);

    this.lookupItemsSharedCollection = this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
      this.lookupItemsSharedCollection,
      docente.nacionalidade,
      docente.naturalidade,
      docente.tipoDocumento,
      docente.grauAcademico,
      docente.categoriaProfissional,
      docente.unidadeOrganica,
      docente.estadoCivil
    );
    this.responsavelTurnosSharedCollection = this.responsavelTurnoService.addResponsavelTurnoToCollectionIfMissing<IResponsavelTurno>(
      this.responsavelTurnosSharedCollection,
      docente.responsavelTurno
    );
    this.responsavelAreaFormacaosSharedCollection =
      this.responsavelAreaFormacaoService.addResponsavelAreaFormacaoToCollectionIfMissing<IResponsavelAreaFormacao>(
        this.responsavelAreaFormacaosSharedCollection,
        docente.responsavelAreaFormacao
      );
    this.responsavelCursosSharedCollection = this.responsavelCursoService.addResponsavelCursoToCollectionIfMissing<IResponsavelCurso>(
      this.responsavelCursosSharedCollection,
      docente.responsavelCurso
    );
    this.responsavelDisciplinasSharedCollection =
      this.responsavelDisciplinaService.addResponsavelDisciplinaToCollectionIfMissing<IResponsavelDisciplina>(
        this.responsavelDisciplinasSharedCollection,
        docente.responsavelDisciplina
      );
    this.responsavelTurmasSharedCollection = this.responsavelTurmaService.addResponsavelTurmaToCollectionIfMissing<IResponsavelTurma>(
      this.responsavelTurmasSharedCollection,
      docente.responsavelTurma
    );
  }

  protected loadRelationshipsOptions(): void {
    this.lookupItemService
      .query()
      .pipe(map((res: HttpResponse<ILookupItem[]>) => res.body ?? []))
      .pipe(
        map((lookupItems: ILookupItem[]) =>
          this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
            lookupItems,
            this.docente?.nacionalidade,
            this.docente?.naturalidade,
            this.docente?.tipoDocumento,
            this.docente?.grauAcademico,
            this.docente?.categoriaProfissional,
            this.docente?.unidadeOrganica,
            this.docente?.estadoCivil
          )
        )
      )
      .subscribe((lookupItems: ILookupItem[]) => (this.lookupItemsSharedCollection = lookupItems));

    this.responsavelTurnoService
      .query()
      .pipe(map((res: HttpResponse<IResponsavelTurno[]>) => res.body ?? []))
      .pipe(
        map((responsavelTurnos: IResponsavelTurno[]) =>
          this.responsavelTurnoService.addResponsavelTurnoToCollectionIfMissing<IResponsavelTurno>(
            responsavelTurnos,
            this.docente?.responsavelTurno
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
            this.docente?.responsavelAreaFormacao
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
            this.docente?.responsavelCurso
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
            this.docente?.responsavelDisciplina
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
            this.docente?.responsavelTurma
          )
        )
      )
      .subscribe((responsavelTurmas: IResponsavelTurma[]) => (this.responsavelTurmasSharedCollection = responsavelTurmas));
  }
}
