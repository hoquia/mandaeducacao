import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../meio-pagamento.test-samples';

import { MeioPagamentoFormService } from './meio-pagamento-form.service';

describe('MeioPagamento Form Service', () => {
  let service: MeioPagamentoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MeioPagamentoFormService);
  });

  describe('Service methods', () => {
    describe('createMeioPagamentoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMeioPagamentoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            imagem: expect.any(Object),
            codigo: expect.any(Object),
            nome: expect.any(Object),
            numeroDigitoReferencia: expect.any(Object),
            isPagamentoInstantanio: expect.any(Object),
            hash: expect.any(Object),
            link: expect.any(Object),
            token: expect.any(Object),
            username: expect.any(Object),
            password: expect.any(Object),
            formatoReferencia: expect.any(Object),
          })
        );
      });

      it('passing IMeioPagamento should create a new form with FormGroup', () => {
        const formGroup = service.createMeioPagamentoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            imagem: expect.any(Object),
            codigo: expect.any(Object),
            nome: expect.any(Object),
            numeroDigitoReferencia: expect.any(Object),
            isPagamentoInstantanio: expect.any(Object),
            hash: expect.any(Object),
            link: expect.any(Object),
            token: expect.any(Object),
            username: expect.any(Object),
            password: expect.any(Object),
            formatoReferencia: expect.any(Object),
          })
        );
      });
    });

    describe('getMeioPagamento', () => {
      it('should return NewMeioPagamento for default MeioPagamento initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMeioPagamentoFormGroup(sampleWithNewData);

        const meioPagamento = service.getMeioPagamento(formGroup) as any;

        expect(meioPagamento).toMatchObject(sampleWithNewData);
      });

      it('should return NewMeioPagamento for empty MeioPagamento initial value', () => {
        const formGroup = service.createMeioPagamentoFormGroup();

        const meioPagamento = service.getMeioPagamento(formGroup) as any;

        expect(meioPagamento).toMatchObject({});
      });

      it('should return IMeioPagamento', () => {
        const formGroup = service.createMeioPagamentoFormGroup(sampleWithRequiredData);

        const meioPagamento = service.getMeioPagamento(formGroup) as any;

        expect(meioPagamento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMeioPagamento should not enable id FormControl', () => {
        const formGroup = service.createMeioPagamentoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMeioPagamento should disable id FormControl', () => {
        const formGroup = service.createMeioPagamentoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
