import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResumoImpostoFacturaFormService } from './resumo-imposto-factura-form.service';
import { ResumoImpostoFacturaService } from '../service/resumo-imposto-factura.service';
import { IResumoImpostoFactura } from '../resumo-imposto-factura.model';
import { IFactura } from 'app/entities/factura/factura.model';
import { FacturaService } from 'app/entities/factura/service/factura.service';

import { ResumoImpostoFacturaUpdateComponent } from './resumo-imposto-factura-update.component';

describe('ResumoImpostoFactura Management Update Component', () => {
  let comp: ResumoImpostoFacturaUpdateComponent;
  let fixture: ComponentFixture<ResumoImpostoFacturaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let resumoImpostoFacturaFormService: ResumoImpostoFacturaFormService;
  let resumoImpostoFacturaService: ResumoImpostoFacturaService;
  let facturaService: FacturaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResumoImpostoFacturaUpdateComponent],
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
      .overrideTemplate(ResumoImpostoFacturaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResumoImpostoFacturaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    resumoImpostoFacturaFormService = TestBed.inject(ResumoImpostoFacturaFormService);
    resumoImpostoFacturaService = TestBed.inject(ResumoImpostoFacturaService);
    facturaService = TestBed.inject(FacturaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Factura query and add missing value', () => {
      const resumoImpostoFactura: IResumoImpostoFactura = { id: 456 };
      const factura: IFactura = { id: 84251 };
      resumoImpostoFactura.factura = factura;

      const facturaCollection: IFactura[] = [{ id: 57107 }];
      jest.spyOn(facturaService, 'query').mockReturnValue(of(new HttpResponse({ body: facturaCollection })));
      const additionalFacturas = [factura];
      const expectedCollection: IFactura[] = [...additionalFacturas, ...facturaCollection];
      jest.spyOn(facturaService, 'addFacturaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ resumoImpostoFactura });
      comp.ngOnInit();

      expect(facturaService.query).toHaveBeenCalled();
      expect(facturaService.addFacturaToCollectionIfMissing).toHaveBeenCalledWith(
        facturaCollection,
        ...additionalFacturas.map(expect.objectContaining)
      );
      expect(comp.facturasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const resumoImpostoFactura: IResumoImpostoFactura = { id: 456 };
      const factura: IFactura = { id: 22297 };
      resumoImpostoFactura.factura = factura;

      activatedRoute.data = of({ resumoImpostoFactura });
      comp.ngOnInit();

      expect(comp.facturasSharedCollection).toContain(factura);
      expect(comp.resumoImpostoFactura).toEqual(resumoImpostoFactura);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResumoImpostoFactura>>();
      const resumoImpostoFactura = { id: 123 };
      jest.spyOn(resumoImpostoFacturaFormService, 'getResumoImpostoFactura').mockReturnValue(resumoImpostoFactura);
      jest.spyOn(resumoImpostoFacturaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumoImpostoFactura });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumoImpostoFactura }));
      saveSubject.complete();

      // THEN
      expect(resumoImpostoFacturaFormService.getResumoImpostoFactura).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(resumoImpostoFacturaService.update).toHaveBeenCalledWith(expect.objectContaining(resumoImpostoFactura));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResumoImpostoFactura>>();
      const resumoImpostoFactura = { id: 123 };
      jest.spyOn(resumoImpostoFacturaFormService, 'getResumoImpostoFactura').mockReturnValue({ id: null });
      jest.spyOn(resumoImpostoFacturaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumoImpostoFactura: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumoImpostoFactura }));
      saveSubject.complete();

      // THEN
      expect(resumoImpostoFacturaFormService.getResumoImpostoFactura).toHaveBeenCalled();
      expect(resumoImpostoFacturaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResumoImpostoFactura>>();
      const resumoImpostoFactura = { id: 123 };
      jest.spyOn(resumoImpostoFacturaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumoImpostoFactura });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(resumoImpostoFacturaService.update).toHaveBeenCalled();
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
  });
});
