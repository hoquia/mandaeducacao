import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FacturaFormService, FacturaFormGroup } from './factura-form.service';
import { IFactura } from '../factura.model';
import { FacturaService } from '../service/factura.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { MatriculaService } from 'app/entities/matricula/service/matricula.service';
import { IDocumentoComercial } from 'app/entities/documento-comercial/documento-comercial.model';
import { DocumentoComercialService } from 'app/entities/documento-comercial/service/documento-comercial.service';
import { EstadoDocumentoComercial } from 'app/entities/enumerations/estado-documento-comercial.model';

@Component({
  selector: 'app-factura-update',
  templateUrl: './factura-update.component.html',
})
export class FacturaUpdateComponent implements OnInit {
  isSaving = false;
  factura: IFactura | null = null;
  estadoDocumentoComercialValues = Object.keys(EstadoDocumentoComercial);

  facturasSharedCollection: IFactura[] = [];
  usersSharedCollection: IUser[] = [];
  lookupItemsSharedCollection: ILookupItem[] = [];
  matriculasSharedCollection: IMatricula[] = [];
  documentoComercialsSharedCollection: IDocumentoComercial[] = [];

  editForm: FacturaFormGroup = this.facturaFormService.createFacturaFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected facturaService: FacturaService,
    protected facturaFormService: FacturaFormService,
    protected userService: UserService,
    protected lookupItemService: LookupItemService,
    protected matriculaService: MatriculaService,
    protected documentoComercialService: DocumentoComercialService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFactura = (o1: IFactura | null, o2: IFactura | null): boolean => this.facturaService.compareFactura(o1, o2);

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareLookupItem = (o1: ILookupItem | null, o2: ILookupItem | null): boolean => this.lookupItemService.compareLookupItem(o1, o2);

  compareMatricula = (o1: IMatricula | null, o2: IMatricula | null): boolean => this.matriculaService.compareMatricula(o1, o2);

  compareDocumentoComercial = (o1: IDocumentoComercial | null, o2: IDocumentoComercial | null): boolean =>
    this.documentoComercialService.compareDocumentoComercial(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factura }) => {
      this.factura = factura;
      if (factura) {
        this.updateForm(factura);
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

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const factura = this.facturaFormService.getFactura(this.editForm);
    if (factura.id !== null) {
      this.subscribeToSaveResponse(this.facturaService.update(factura));
    } else {
      this.subscribeToSaveResponse(this.facturaService.create(factura));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFactura>>): void {
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

  protected updateForm(factura: IFactura): void {
    this.factura = factura;
    this.facturaFormService.resetForm(this.editForm, factura);

    this.facturasSharedCollection = this.facturaService.addFacturaToCollectionIfMissing<IFactura>(
      this.facturasSharedCollection,
      factura.referencia
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, factura.utilizador);
    this.lookupItemsSharedCollection = this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
      this.lookupItemsSharedCollection,
      factura.motivoAnulacao
    );
    this.matriculasSharedCollection = this.matriculaService.addMatriculaToCollectionIfMissing<IMatricula>(
      this.matriculasSharedCollection,
      factura.matricula
    );
    this.documentoComercialsSharedCollection =
      this.documentoComercialService.addDocumentoComercialToCollectionIfMissing<IDocumentoComercial>(
        this.documentoComercialsSharedCollection,
        factura.documentoComercial
      );
  }

  protected loadRelationshipsOptions(): void {
    this.facturaService
      .query()
      .pipe(map((res: HttpResponse<IFactura[]>) => res.body ?? []))
      .pipe(
        map((facturas: IFactura[]) => this.facturaService.addFacturaToCollectionIfMissing<IFactura>(facturas, this.factura?.referencia))
      )
      .subscribe((facturas: IFactura[]) => (this.facturasSharedCollection = facturas));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.factura?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.lookupItemService
      .query()
      .pipe(map((res: HttpResponse<ILookupItem[]>) => res.body ?? []))
      .pipe(
        map((lookupItems: ILookupItem[]) =>
          this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(lookupItems, this.factura?.motivoAnulacao)
        )
      )
      .subscribe((lookupItems: ILookupItem[]) => (this.lookupItemsSharedCollection = lookupItems));

    this.matriculaService
      .query()
      .pipe(map((res: HttpResponse<IMatricula[]>) => res.body ?? []))
      .pipe(
        map((matriculas: IMatricula[]) =>
          this.matriculaService.addMatriculaToCollectionIfMissing<IMatricula>(matriculas, this.factura?.matricula)
        )
      )
      .subscribe((matriculas: IMatricula[]) => (this.matriculasSharedCollection = matriculas));

    this.documentoComercialService
      .query()
      .pipe(map((res: HttpResponse<IDocumentoComercial[]>) => res.body ?? []))
      .pipe(
        map((documentoComercials: IDocumentoComercial[]) =>
          this.documentoComercialService.addDocumentoComercialToCollectionIfMissing<IDocumentoComercial>(
            documentoComercials,
            this.factura?.documentoComercial
          )
        )
      )
      .subscribe((documentoComercials: IDocumentoComercial[]) => (this.documentoComercialsSharedCollection = documentoComercials));
  }
}
