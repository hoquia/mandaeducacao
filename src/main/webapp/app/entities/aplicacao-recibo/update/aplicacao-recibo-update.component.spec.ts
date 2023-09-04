import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AplicacaoReciboFormService } from './aplicacao-recibo-form.service';
import { AplicacaoReciboService } from '../service/aplicacao-recibo.service';
import { IAplicacaoRecibo } from '../aplicacao-recibo.model';
import { IItemFactura } from 'app/entities/item-factura/item-factura.model';
import { ItemFacturaService } from 'app/entities/item-factura/service/item-factura.service';
import { IFactura } from 'app/entities/factura/factura.model';
import { FacturaService } from 'app/entities/factura/service/factura.service';
import { IRecibo } from 'app/entities/recibo/recibo.model';
import { ReciboService } from 'app/entities/recibo/service/recibo.service';

import { AplicacaoReciboUpdateComponent } from './aplicacao-recibo-update.component';

describe('AplicacaoRecibo Management Update Component', () => {
  let comp: AplicacaoReciboUpdateComponent;
  let fixture: ComponentFixture<AplicacaoReciboUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let aplicacaoReciboFormService: AplicacaoReciboFormService;
  let aplicacaoReciboService: AplicacaoReciboService;
  let itemFacturaService: ItemFacturaService;
  let facturaService: FacturaService;
  let reciboService: ReciboService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AplicacaoReciboUpdateComponent],
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
      .overrideTemplate(AplicacaoReciboUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AplicacaoReciboUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    aplicacaoReciboFormService = TestBed.inject(AplicacaoReciboFormService);
    aplicacaoReciboService = TestBed.inject(AplicacaoReciboService);
    itemFacturaService = TestBed.inject(ItemFacturaService);
    facturaService = TestBed.inject(FacturaService);
    reciboService = TestBed.inject(ReciboService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ItemFactura query and add missing value', () => {
      const aplicacaoRecibo: IAplicacaoRecibo = { id: 456 };
      const itemFactura: IItemFactura = { id: 8872 };
      aplicacaoRecibo.itemFactura = itemFactura;

      const itemFacturaCollection: IItemFactura[] = [{ id: 40334 }];
      jest.spyOn(itemFacturaService, 'query').mockReturnValue(of(new HttpResponse({ body: itemFacturaCollection })));
      const additionalItemFacturas = [itemFactura];
      const expectedCollection: IItemFactura[] = [...additionalItemFacturas, ...itemFacturaCollection];
      jest.spyOn(itemFacturaService, 'addItemFacturaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ aplicacaoRecibo });
      comp.ngOnInit();

      expect(itemFacturaService.query).toHaveBeenCalled();
      expect(itemFacturaService.addItemFacturaToCollectionIfMissing).toHaveBeenCalledWith(
        itemFacturaCollection,
        ...additionalItemFacturas.map(expect.objectContaining)
      );
      expect(comp.itemFacturasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Factura query and add missing value', () => {
      const aplicacaoRecibo: IAplicacaoRecibo = { id: 456 };
      const factura: IFactura = { id: 7622 };
      aplicacaoRecibo.factura = factura;

      const facturaCollection: IFactura[] = [{ id: 6567 }];
      jest.spyOn(facturaService, 'query').mockReturnValue(of(new HttpResponse({ body: facturaCollection })));
      const additionalFacturas = [factura];
      const expectedCollection: IFactura[] = [...additionalFacturas, ...facturaCollection];
      jest.spyOn(facturaService, 'addFacturaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ aplicacaoRecibo });
      comp.ngOnInit();

      expect(facturaService.query).toHaveBeenCalled();
      expect(facturaService.addFacturaToCollectionIfMissing).toHaveBeenCalledWith(
        facturaCollection,
        ...additionalFacturas.map(expect.objectContaining)
      );
      expect(comp.facturasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Recibo query and add missing value', () => {
      const aplicacaoRecibo: IAplicacaoRecibo = { id: 456 };
      const recibo: IRecibo = { id: 15104 };
      aplicacaoRecibo.recibo = recibo;

      const reciboCollection: IRecibo[] = [{ id: 80581 }];
      jest.spyOn(reciboService, 'query').mockReturnValue(of(new HttpResponse({ body: reciboCollection })));
      const additionalRecibos = [recibo];
      const expectedCollection: IRecibo[] = [...additionalRecibos, ...reciboCollection];
      jest.spyOn(reciboService, 'addReciboToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ aplicacaoRecibo });
      comp.ngOnInit();

      expect(reciboService.query).toHaveBeenCalled();
      expect(reciboService.addReciboToCollectionIfMissing).toHaveBeenCalledWith(
        reciboCollection,
        ...additionalRecibos.map(expect.objectContaining)
      );
      expect(comp.recibosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const aplicacaoRecibo: IAplicacaoRecibo = { id: 456 };
      const itemFactura: IItemFactura = { id: 83534 };
      aplicacaoRecibo.itemFactura = itemFactura;
      const factura: IFactura = { id: 15130 };
      aplicacaoRecibo.factura = factura;
      const recibo: IRecibo = { id: 83341 };
      aplicacaoRecibo.recibo = recibo;

      activatedRoute.data = of({ aplicacaoRecibo });
      comp.ngOnInit();

      expect(comp.itemFacturasSharedCollection).toContain(itemFactura);
      expect(comp.facturasSharedCollection).toContain(factura);
      expect(comp.recibosSharedCollection).toContain(recibo);
      expect(comp.aplicacaoRecibo).toEqual(aplicacaoRecibo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAplicacaoRecibo>>();
      const aplicacaoRecibo = { id: 123 };
      jest.spyOn(aplicacaoReciboFormService, 'getAplicacaoRecibo').mockReturnValue(aplicacaoRecibo);
      jest.spyOn(aplicacaoReciboService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ aplicacaoRecibo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: aplicacaoRecibo }));
      saveSubject.complete();

      // THEN
      expect(aplicacaoReciboFormService.getAplicacaoRecibo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(aplicacaoReciboService.update).toHaveBeenCalledWith(expect.objectContaining(aplicacaoRecibo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAplicacaoRecibo>>();
      const aplicacaoRecibo = { id: 123 };
      jest.spyOn(aplicacaoReciboFormService, 'getAplicacaoRecibo').mockReturnValue({ id: null });
      jest.spyOn(aplicacaoReciboService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ aplicacaoRecibo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: aplicacaoRecibo }));
      saveSubject.complete();

      // THEN
      expect(aplicacaoReciboFormService.getAplicacaoRecibo).toHaveBeenCalled();
      expect(aplicacaoReciboService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAplicacaoRecibo>>();
      const aplicacaoRecibo = { id: 123 };
      jest.spyOn(aplicacaoReciboService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ aplicacaoRecibo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(aplicacaoReciboService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareItemFactura', () => {
      it('Should forward to itemFacturaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(itemFacturaService, 'compareItemFactura');
        comp.compareItemFactura(entity, entity2);
        expect(itemFacturaService.compareItemFactura).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFactura', () => {
      it('Should forward to facturaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(facturaService, 'compareFactura');
        comp.compareFactura(entity, entity2);
        expect(facturaService.compareFactura).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareRecibo', () => {
      it('Should forward to reciboService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(reciboService, 'compareRecibo');
        comp.compareRecibo(entity, entity2);
        expect(reciboService.compareRecibo).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
