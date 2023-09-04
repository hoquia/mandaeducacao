import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FacturaFormService } from './factura-form.service';
import { FacturaService } from '../service/factura.service';
import { IFactura } from '../factura.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { MatriculaService } from 'app/entities/matricula/service/matricula.service';
import { IDocumentoComercial } from 'app/entities/documento-comercial/documento-comercial.model';
import { DocumentoComercialService } from 'app/entities/documento-comercial/service/documento-comercial.service';

import { FacturaUpdateComponent } from './factura-update.component';

describe('Factura Management Update Component', () => {
  let comp: FacturaUpdateComponent;
  let fixture: ComponentFixture<FacturaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let facturaFormService: FacturaFormService;
  let facturaService: FacturaService;
  let userService: UserService;
  let lookupItemService: LookupItemService;
  let matriculaService: MatriculaService;
  let documentoComercialService: DocumentoComercialService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FacturaUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(FacturaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FacturaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    facturaFormService = TestBed.inject(FacturaFormService);
    facturaService = TestBed.inject(FacturaService);
    userService = TestBed.inject(UserService);
    lookupItemService = TestBed.inject(LookupItemService);
    matriculaService = TestBed.inject(MatriculaService);
    documentoComercialService = TestBed.inject(DocumentoComercialService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Factura query and add missing value', () => {
      const factura: IFactura = { id: 456 };
      const referencia: IFactura = { id: 30214 };
      factura.referencia = referencia;

      const facturaCollection: IFactura[] = [{ id: 17824 }];
      jest.spyOn(facturaService, 'query').mockReturnValue(of(new HttpResponse({ body: facturaCollection })));
      const additionalFacturas = [referencia];
      const expectedCollection: IFactura[] = [...additionalFacturas, ...facturaCollection];
      jest.spyOn(facturaService, 'addFacturaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ factura });
      comp.ngOnInit();

      expect(facturaService.query).toHaveBeenCalled();
      expect(facturaService.addFacturaToCollectionIfMissing).toHaveBeenCalledWith(
        facturaCollection,
        ...additionalFacturas.map(expect.objectContaining)
      );
      expect(comp.facturasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call User query and add missing value', () => {
      const factura: IFactura = { id: 456 };
      const utilizador: IUser = { id: 32234 };
      factura.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 82034 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ factura });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LookupItem query and add missing value', () => {
      const factura: IFactura = { id: 456 };
      const motivoAnulacao: ILookupItem = { id: 51578 };
      factura.motivoAnulacao = motivoAnulacao;

      const lookupItemCollection: ILookupItem[] = [{ id: 21523 }];
      jest.spyOn(lookupItemService, 'query').mockReturnValue(of(new HttpResponse({ body: lookupItemCollection })));
      const additionalLookupItems = [motivoAnulacao];
      const expectedCollection: ILookupItem[] = [...additionalLookupItems, ...lookupItemCollection];
      jest.spyOn(lookupItemService, 'addLookupItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ factura });
      comp.ngOnInit();

      expect(lookupItemService.query).toHaveBeenCalled();
      expect(lookupItemService.addLookupItemToCollectionIfMissing).toHaveBeenCalledWith(
        lookupItemCollection,
        ...additionalLookupItems.map(expect.objectContaining)
      );
      expect(comp.lookupItemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Matricula query and add missing value', () => {
      const factura: IFactura = { id: 456 };
      const matricula: IMatricula = { id: 88516 };
      factura.matricula = matricula;

      const matriculaCollection: IMatricula[] = [{ id: 22486 }];
      jest.spyOn(matriculaService, 'query').mockReturnValue(of(new HttpResponse({ body: matriculaCollection })));
      const additionalMatriculas = [matricula];
      const expectedCollection: IMatricula[] = [...additionalMatriculas, ...matriculaCollection];
      jest.spyOn(matriculaService, 'addMatriculaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ factura });
      comp.ngOnInit();

      expect(matriculaService.query).toHaveBeenCalled();
      expect(matriculaService.addMatriculaToCollectionIfMissing).toHaveBeenCalledWith(
        matriculaCollection,
        ...additionalMatriculas.map(expect.objectContaining)
      );
      expect(comp.matriculasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DocumentoComercial query and add missing value', () => {
      const factura: IFactura = { id: 456 };
      const documentoComercial: IDocumentoComercial = { id: 1626 };
      factura.documentoComercial = documentoComercial;

      const documentoComercialCollection: IDocumentoComercial[] = [{ id: 84772 }];
      jest.spyOn(documentoComercialService, 'query').mockReturnValue(of(new HttpResponse({ body: documentoComercialCollection })));
      const additionalDocumentoComercials = [documentoComercial];
      const expectedCollection: IDocumentoComercial[] = [...additionalDocumentoComercials, ...documentoComercialCollection];
      jest.spyOn(documentoComercialService, 'addDocumentoComercialToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ factura });
      comp.ngOnInit();

      expect(documentoComercialService.query).toHaveBeenCalled();
      expect(documentoComercialService.addDocumentoComercialToCollectionIfMissing).toHaveBeenCalledWith(
        documentoComercialCollection,
        ...additionalDocumentoComercials.map(expect.objectContaining)
      );
      expect(comp.documentoComercialsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const factura: IFactura = { id: 456 };
      const referencia: IFactura = { id: 36393 };
      factura.referencia = referencia;
      const utilizador: IUser = { id: 44491 };
      factura.utilizador = utilizador;
      const motivoAnulacao: ILookupItem = { id: 84137 };
      factura.motivoAnulacao = motivoAnulacao;
      const matricula: IMatricula = { id: 74128 };
      factura.matricula = matricula;
      const documentoComercial: IDocumentoComercial = { id: 45918 };
      factura.documentoComercial = documentoComercial;

      activatedRoute.data = of({ factura });
      comp.ngOnInit();

      expect(comp.facturasSharedCollection).toContain(referencia);
      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.lookupItemsSharedCollection).toContain(motivoAnulacao);
      expect(comp.matriculasSharedCollection).toContain(matricula);
      expect(comp.documentoComercialsSharedCollection).toContain(documentoComercial);
      expect(comp.factura).toEqual(factura);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFactura>>();
      const factura = { id: 123 };
      jest.spyOn(facturaFormService, 'getFactura').mockReturnValue(factura);
      jest.spyOn(facturaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factura });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: factura }));
      saveSubject.complete();

      // THEN
      expect(facturaFormService.getFactura).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(facturaService.update).toHaveBeenCalledWith(expect.objectContaining(factura));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFactura>>();
      const factura = { id: 123 };
      jest.spyOn(facturaFormService, 'getFactura').mockReturnValue({ id: null });
      jest.spyOn(facturaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factura: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: factura }));
      saveSubject.complete();

      // THEN
      expect(facturaFormService.getFactura).toHaveBeenCalled();
      expect(facturaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFactura>>();
      const factura = { id: 123 };
      jest.spyOn(facturaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factura });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(facturaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFactura', () => {
      it('Should forward to facturaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(facturaService, 'compareFactura');
        comp.compareFactura(entity, entity2);
        expect(facturaService.compareFactura).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareLookupItem', () => {
      it('Should forward to lookupItemService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(lookupItemService, 'compareLookupItem');
        comp.compareLookupItem(entity, entity2);
        expect(lookupItemService.compareLookupItem).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMatricula', () => {
      it('Should forward to matriculaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(matriculaService, 'compareMatricula');
        comp.compareMatricula(entity, entity2);
        expect(matriculaService.compareMatricula).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDocumentoComercial', () => {
      it('Should forward to documentoComercialService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(documentoComercialService, 'compareDocumentoComercial');
        comp.compareDocumentoComercial(entity, entity2);
        expect(documentoComercialService.compareDocumentoComercial).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
