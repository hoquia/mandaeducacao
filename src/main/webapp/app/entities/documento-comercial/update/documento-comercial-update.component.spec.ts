import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DocumentoComercialFormService } from './documento-comercial-form.service';
import { DocumentoComercialService } from '../service/documento-comercial.service';
import { IDocumentoComercial } from '../documento-comercial.model';

import { DocumentoComercialUpdateComponent } from './documento-comercial-update.component';

describe('DocumentoComercial Management Update Component', () => {
  let comp: DocumentoComercialUpdateComponent;
  let fixture: ComponentFixture<DocumentoComercialUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let documentoComercialFormService: DocumentoComercialFormService;
  let documentoComercialService: DocumentoComercialService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DocumentoComercialUpdateComponent],
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
      .overrideTemplate(DocumentoComercialUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocumentoComercialUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    documentoComercialFormService = TestBed.inject(DocumentoComercialFormService);
    documentoComercialService = TestBed.inject(DocumentoComercialService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DocumentoComercial query and add missing value', () => {
      const documentoComercial: IDocumentoComercial = { id: 456 };
      const transformaEm: IDocumentoComercial = { id: 33 };
      documentoComercial.transformaEm = transformaEm;

      const documentoComercialCollection: IDocumentoComercial[] = [{ id: 38024 }];
      jest.spyOn(documentoComercialService, 'query').mockReturnValue(of(new HttpResponse({ body: documentoComercialCollection })));
      const additionalDocumentoComercials = [transformaEm];
      const expectedCollection: IDocumentoComercial[] = [...additionalDocumentoComercials, ...documentoComercialCollection];
      jest.spyOn(documentoComercialService, 'addDocumentoComercialToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ documentoComercial });
      comp.ngOnInit();

      expect(documentoComercialService.query).toHaveBeenCalled();
      expect(documentoComercialService.addDocumentoComercialToCollectionIfMissing).toHaveBeenCalledWith(
        documentoComercialCollection,
        ...additionalDocumentoComercials.map(expect.objectContaining)
      );
      expect(comp.documentoComercialsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const documentoComercial: IDocumentoComercial = { id: 456 };
      const transformaEm: IDocumentoComercial = { id: 12192 };
      documentoComercial.transformaEm = transformaEm;

      activatedRoute.data = of({ documentoComercial });
      comp.ngOnInit();

      expect(comp.documentoComercialsSharedCollection).toContain(transformaEm);
      expect(comp.documentoComercial).toEqual(documentoComercial);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentoComercial>>();
      const documentoComercial = { id: 123 };
      jest.spyOn(documentoComercialFormService, 'getDocumentoComercial').mockReturnValue(documentoComercial);
      jest.spyOn(documentoComercialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentoComercial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentoComercial }));
      saveSubject.complete();

      // THEN
      expect(documentoComercialFormService.getDocumentoComercial).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(documentoComercialService.update).toHaveBeenCalledWith(expect.objectContaining(documentoComercial));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentoComercial>>();
      const documentoComercial = { id: 123 };
      jest.spyOn(documentoComercialFormService, 'getDocumentoComercial').mockReturnValue({ id: null });
      jest.spyOn(documentoComercialService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentoComercial: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentoComercial }));
      saveSubject.complete();

      // THEN
      expect(documentoComercialFormService.getDocumentoComercial).toHaveBeenCalled();
      expect(documentoComercialService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentoComercial>>();
      const documentoComercial = { id: 123 };
      jest.spyOn(documentoComercialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentoComercial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(documentoComercialService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
