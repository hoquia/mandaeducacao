import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SequenciaDocumentoFormService } from './sequencia-documento-form.service';
import { SequenciaDocumentoService } from '../service/sequencia-documento.service';
import { ISequenciaDocumento } from '../sequencia-documento.model';
import { ISerieDocumento } from 'app/entities/serie-documento/serie-documento.model';
import { SerieDocumentoService } from 'app/entities/serie-documento/service/serie-documento.service';

import { SequenciaDocumentoUpdateComponent } from './sequencia-documento-update.component';

describe('SequenciaDocumento Management Update Component', () => {
  let comp: SequenciaDocumentoUpdateComponent;
  let fixture: ComponentFixture<SequenciaDocumentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sequenciaDocumentoFormService: SequenciaDocumentoFormService;
  let sequenciaDocumentoService: SequenciaDocumentoService;
  let serieDocumentoService: SerieDocumentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SequenciaDocumentoUpdateComponent],
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
      .overrideTemplate(SequenciaDocumentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SequenciaDocumentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sequenciaDocumentoFormService = TestBed.inject(SequenciaDocumentoFormService);
    sequenciaDocumentoService = TestBed.inject(SequenciaDocumentoService);
    serieDocumentoService = TestBed.inject(SerieDocumentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SerieDocumento query and add missing value', () => {
      const sequenciaDocumento: ISequenciaDocumento = { id: 456 };
      const serie: ISerieDocumento = { id: 92271 };
      sequenciaDocumento.serie = serie;

      const serieDocumentoCollection: ISerieDocumento[] = [{ id: 59247 }];
      jest.spyOn(serieDocumentoService, 'query').mockReturnValue(of(new HttpResponse({ body: serieDocumentoCollection })));
      const additionalSerieDocumentos = [serie];
      const expectedCollection: ISerieDocumento[] = [...additionalSerieDocumentos, ...serieDocumentoCollection];
      jest.spyOn(serieDocumentoService, 'addSerieDocumentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sequenciaDocumento });
      comp.ngOnInit();

      expect(serieDocumentoService.query).toHaveBeenCalled();
      expect(serieDocumentoService.addSerieDocumentoToCollectionIfMissing).toHaveBeenCalledWith(
        serieDocumentoCollection,
        ...additionalSerieDocumentos.map(expect.objectContaining)
      );
      expect(comp.serieDocumentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const sequenciaDocumento: ISequenciaDocumento = { id: 456 };
      const serie: ISerieDocumento = { id: 67043 };
      sequenciaDocumento.serie = serie;

      activatedRoute.data = of({ sequenciaDocumento });
      comp.ngOnInit();

      expect(comp.serieDocumentosSharedCollection).toContain(serie);
      expect(comp.sequenciaDocumento).toEqual(sequenciaDocumento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISequenciaDocumento>>();
      const sequenciaDocumento = { id: 123 };
      jest.spyOn(sequenciaDocumentoFormService, 'getSequenciaDocumento').mockReturnValue(sequenciaDocumento);
      jest.spyOn(sequenciaDocumentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sequenciaDocumento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sequenciaDocumento }));
      saveSubject.complete();

      // THEN
      expect(sequenciaDocumentoFormService.getSequenciaDocumento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sequenciaDocumentoService.update).toHaveBeenCalledWith(expect.objectContaining(sequenciaDocumento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISequenciaDocumento>>();
      const sequenciaDocumento = { id: 123 };
      jest.spyOn(sequenciaDocumentoFormService, 'getSequenciaDocumento').mockReturnValue({ id: null });
      jest.spyOn(sequenciaDocumentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sequenciaDocumento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sequenciaDocumento }));
      saveSubject.complete();

      // THEN
      expect(sequenciaDocumentoFormService.getSequenciaDocumento).toHaveBeenCalled();
      expect(sequenciaDocumentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISequenciaDocumento>>();
      const sequenciaDocumento = { id: 123 };
      jest.spyOn(sequenciaDocumentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sequenciaDocumento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sequenciaDocumentoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSerieDocumento', () => {
      it('Should forward to serieDocumentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(serieDocumentoService, 'compareSerieDocumento');
        comp.compareSerieDocumento(entity, entity2);
        expect(serieDocumentoService.compareSerieDocumento).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
