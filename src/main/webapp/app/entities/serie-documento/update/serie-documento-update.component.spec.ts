import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SerieDocumentoFormService } from './serie-documento-form.service';
import { SerieDocumentoService } from '../service/serie-documento.service';
import { ISerieDocumento } from '../serie-documento.model';
import { IDocumentoComercial } from 'app/entities/documento-comercial/documento-comercial.model';
import { DocumentoComercialService } from 'app/entities/documento-comercial/service/documento-comercial.service';

import { SerieDocumentoUpdateComponent } from './serie-documento-update.component';

describe('SerieDocumento Management Update Component', () => {
  let comp: SerieDocumentoUpdateComponent;
  let fixture: ComponentFixture<SerieDocumentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let serieDocumentoFormService: SerieDocumentoFormService;
  let serieDocumentoService: SerieDocumentoService;
  let documentoComercialService: DocumentoComercialService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SerieDocumentoUpdateComponent],
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
      .overrideTemplate(SerieDocumentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SerieDocumentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    serieDocumentoFormService = TestBed.inject(SerieDocumentoFormService);
    serieDocumentoService = TestBed.inject(SerieDocumentoService);
    documentoComercialService = TestBed.inject(DocumentoComercialService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DocumentoComercial query and add missing value', () => {
      const serieDocumento: ISerieDocumento = { id: 456 };
      const tipoDocumento: IDocumentoComercial = { id: 82663 };
      serieDocumento.tipoDocumento = tipoDocumento;

      const documentoComercialCollection: IDocumentoComercial[] = [{ id: 21098 }];
      jest.spyOn(documentoComercialService, 'query').mockReturnValue(of(new HttpResponse({ body: documentoComercialCollection })));
      const additionalDocumentoComercials = [tipoDocumento];
      const expectedCollection: IDocumentoComercial[] = [...additionalDocumentoComercials, ...documentoComercialCollection];
      jest.spyOn(documentoComercialService, 'addDocumentoComercialToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ serieDocumento });
      comp.ngOnInit();

      expect(documentoComercialService.query).toHaveBeenCalled();
      expect(documentoComercialService.addDocumentoComercialToCollectionIfMissing).toHaveBeenCalledWith(
        documentoComercialCollection,
        ...additionalDocumentoComercials.map(expect.objectContaining)
      );
      expect(comp.documentoComercialsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const serieDocumento: ISerieDocumento = { id: 456 };
      const tipoDocumento: IDocumentoComercial = { id: 12386 };
      serieDocumento.tipoDocumento = tipoDocumento;

      activatedRoute.data = of({ serieDocumento });
      comp.ngOnInit();

      expect(comp.documentoComercialsSharedCollection).toContain(tipoDocumento);
      expect(comp.serieDocumento).toEqual(serieDocumento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISerieDocumento>>();
      const serieDocumento = { id: 123 };
      jest.spyOn(serieDocumentoFormService, 'getSerieDocumento').mockReturnValue(serieDocumento);
      jest.spyOn(serieDocumentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ serieDocumento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: serieDocumento }));
      saveSubject.complete();

      // THEN
      expect(serieDocumentoFormService.getSerieDocumento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(serieDocumentoService.update).toHaveBeenCalledWith(expect.objectContaining(serieDocumento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISerieDocumento>>();
      const serieDocumento = { id: 123 };
      jest.spyOn(serieDocumentoFormService, 'getSerieDocumento').mockReturnValue({ id: null });
      jest.spyOn(serieDocumentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ serieDocumento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: serieDocumento }));
      saveSubject.complete();

      // THEN
      expect(serieDocumentoFormService.getSerieDocumento).toHaveBeenCalled();
      expect(serieDocumentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISerieDocumento>>();
      const serieDocumento = { id: 123 };
      jest.spyOn(serieDocumentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ serieDocumento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(serieDocumentoService.update).toHaveBeenCalled();
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
