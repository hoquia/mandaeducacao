import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../item-factura.test-samples';

import { ItemFacturaFormService } from './item-factura-form.service';

describe('ItemFactura Form Service', () => {
  let service: ItemFacturaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ItemFacturaFormService);
  });

  describe('Service methods', () => {
    describe('createItemFacturaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createItemFacturaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quantidade: expect.any(Object),
            precoUnitario: expect.any(Object),
            desconto: expect.any(Object),
            multa: expect.any(Object),
            juro: expect.any(Object),
            precoTotal: expect.any(Object),
            estado: expect.any(Object),
            taxType: expect.any(Object),
            taxCountryRegion: expect.any(Object),
            taxCode: expect.any(Object),
            taxPercentage: expect.any(Object),
            taxExemptionReason: expect.any(Object),
            taxExemptionCode: expect.any(Object),
            factura: expect.any(Object),
            emolumento: expect.any(Object),
          })
        );
      });

      it('passing IItemFactura should create a new form with FormGroup', () => {
        const formGroup = service.createItemFacturaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quantidade: expect.any(Object),
            precoUnitario: expect.any(Object),
            desconto: expect.any(Object),
            multa: expect.any(Object),
            juro: expect.any(Object),
            precoTotal: expect.any(Object),
            estado: expect.any(Object),
            taxType: expect.any(Object),
            taxCountryRegion: expect.any(Object),
            taxCode: expect.any(Object),
            taxPercentage: expect.any(Object),
            taxExemptionReason: expect.any(Object),
            taxExemptionCode: expect.any(Object),
            factura: expect.any(Object),
            emolumento: expect.any(Object),
          })
        );
      });
    });

    describe('getItemFactura', () => {
      it('should return NewItemFactura for default ItemFactura initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createItemFacturaFormGroup(sampleWithNewData);

        const itemFactura = service.getItemFactura(formGroup) as any;

        expect(itemFactura).toMatchObject(sampleWithNewData);
      });

      it('should return NewItemFactura for empty ItemFactura initial value', () => {
        const formGroup = service.createItemFacturaFormGroup();

        const itemFactura = service.getItemFactura(formGroup) as any;

        expect(itemFactura).toMatchObject({});
      });

      it('should return IItemFactura', () => {
        const formGroup = service.createItemFacturaFormGroup(sampleWithRequiredData);

        const itemFactura = service.getItemFactura(formGroup) as any;

        expect(itemFactura).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IItemFactura should not enable id FormControl', () => {
        const formGroup = service.createItemFacturaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewItemFactura should disable id FormControl', () => {
        const formGroup = service.createItemFacturaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
