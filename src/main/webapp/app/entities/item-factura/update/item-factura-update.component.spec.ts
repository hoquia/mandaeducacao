import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ItemFacturaFormService } from './item-factura-form.service';
import { ItemFacturaService } from '../service/item-factura.service';
import { IItemFactura } from '../item-factura.model';
import { IFactura } from 'app/entities/factura/factura.model';
import { FacturaService } from 'app/entities/factura/service/factura.service';
import { IEmolumento } from 'app/entities/emolumento/emolumento.model';
import { EmolumentoService } from 'app/entities/emolumento/service/emolumento.service';

import { ItemFacturaUpdateComponent } from './item-factura-update.component';

describe('ItemFactura Management Update Component', () => {
  let comp: ItemFacturaUpdateComponent;
  let fixture: ComponentFixture<ItemFacturaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let itemFacturaFormService: ItemFacturaFormService;
  let itemFacturaService: ItemFacturaService;
  let facturaService: FacturaService;
  let emolumentoService: EmolumentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ItemFacturaUpdateComponent],
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
      .overrideTemplate(ItemFacturaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ItemFacturaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    itemFacturaFormService = TestBed.inject(ItemFacturaFormService);
    itemFacturaService = TestBed.inject(ItemFacturaService);
    facturaService = TestBed.inject(FacturaService);
    emolumentoService = TestBed.inject(EmolumentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Factura query and add missing value', () => {
      const itemFactura: IItemFactura = { id: 456 };
      const factura: IFactura = { id: 55338 };
      itemFactura.factura = factura;

      const facturaCollection: IFactura[] = [{ id: 43887 }];
      jest.spyOn(facturaService, 'query').mockReturnValue(of(new HttpResponse({ body: facturaCollection })));
      const additionalFacturas = [factura];
      const expectedCollection: IFactura[] = [...additionalFacturas, ...facturaCollection];
      jest.spyOn(facturaService, 'addFacturaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ itemFactura });
      comp.ngOnInit();

      expect(facturaService.query).toHaveBeenCalled();
      expect(facturaService.addFacturaToCollectionIfMissing).toHaveBeenCalledWith(
        facturaCollection,
        ...additionalFacturas.map(expect.objectContaining)
      );
      expect(comp.facturasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Emolumento query and add missing value', () => {
      const itemFactura: IItemFactura = { id: 456 };
      const emolumento: IEmolumento = { id: 69860 };
      itemFactura.emolumento = emolumento;

      const emolumentoCollection: IEmolumento[] = [{ id: 67525 }];
      jest.spyOn(emolumentoService, 'query').mockReturnValue(of(new HttpResponse({ body: emolumentoCollection })));
      const additionalEmolumentos = [emolumento];
      const expectedCollection: IEmolumento[] = [...additionalEmolumentos, ...emolumentoCollection];
      jest.spyOn(emolumentoService, 'addEmolumentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ itemFactura });
      comp.ngOnInit();

      expect(emolumentoService.query).toHaveBeenCalled();
      expect(emolumentoService.addEmolumentoToCollectionIfMissing).toHaveBeenCalledWith(
        emolumentoCollection,
        ...additionalEmolumentos.map(expect.objectContaining)
      );
      expect(comp.emolumentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const itemFactura: IItemFactura = { id: 456 };
      const factura: IFactura = { id: 13768 };
      itemFactura.factura = factura;
      const emolumento: IEmolumento = { id: 795 };
      itemFactura.emolumento = emolumento;

      activatedRoute.data = of({ itemFactura });
      comp.ngOnInit();

      expect(comp.facturasSharedCollection).toContain(factura);
      expect(comp.emolumentosSharedCollection).toContain(emolumento);
      expect(comp.itemFactura).toEqual(itemFactura);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IItemFactura>>();
      const itemFactura = { id: 123 };
      jest.spyOn(itemFacturaFormService, 'getItemFactura').mockReturnValue(itemFactura);
      jest.spyOn(itemFacturaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ itemFactura });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: itemFactura }));
      saveSubject.complete();

      // THEN
      expect(itemFacturaFormService.getItemFactura).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(itemFacturaService.update).toHaveBeenCalledWith(expect.objectContaining(itemFactura));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IItemFactura>>();
      const itemFactura = { id: 123 };
      jest.spyOn(itemFacturaFormService, 'getItemFactura').mockReturnValue({ id: null });
      jest.spyOn(itemFacturaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ itemFactura: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: itemFactura }));
      saveSubject.complete();

      // THEN
      expect(itemFacturaFormService.getItemFactura).toHaveBeenCalled();
      expect(itemFacturaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IItemFactura>>();
      const itemFactura = { id: 123 };
      jest.spyOn(itemFacturaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ itemFactura });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(itemFacturaService.update).toHaveBeenCalled();
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

    describe('compareEmolumento', () => {
      it('Should forward to emolumentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(emolumentoService, 'compareEmolumento');
        comp.compareEmolumento(entity, entity2);
        expect(emolumentoService.compareEmolumento).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
