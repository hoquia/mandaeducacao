import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../provedor-notificacao.test-samples';

import { ProvedorNotificacaoFormService } from './provedor-notificacao-form.service';

describe('ProvedorNotificacao Form Service', () => {
  let service: ProvedorNotificacaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProvedorNotificacaoFormService);
  });

  describe('Service methods', () => {
    describe('createProvedorNotificacaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProvedorNotificacaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            telefone: expect.any(Object),
            email: expect.any(Object),
            link: expect.any(Object),
            token: expect.any(Object),
            username: expect.any(Object),
            password: expect.any(Object),
            hash: expect.any(Object),
            isPadrao: expect.any(Object),
            instituicao: expect.any(Object),
          })
        );
      });

      it('passing IProvedorNotificacao should create a new form with FormGroup', () => {
        const formGroup = service.createProvedorNotificacaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            telefone: expect.any(Object),
            email: expect.any(Object),
            link: expect.any(Object),
            token: expect.any(Object),
            username: expect.any(Object),
            password: expect.any(Object),
            hash: expect.any(Object),
            isPadrao: expect.any(Object),
            instituicao: expect.any(Object),
          })
        );
      });
    });

    describe('getProvedorNotificacao', () => {
      it('should return NewProvedorNotificacao for default ProvedorNotificacao initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProvedorNotificacaoFormGroup(sampleWithNewData);

        const provedorNotificacao = service.getProvedorNotificacao(formGroup) as any;

        expect(provedorNotificacao).toMatchObject(sampleWithNewData);
      });

      it('should return NewProvedorNotificacao for empty ProvedorNotificacao initial value', () => {
        const formGroup = service.createProvedorNotificacaoFormGroup();

        const provedorNotificacao = service.getProvedorNotificacao(formGroup) as any;

        expect(provedorNotificacao).toMatchObject({});
      });

      it('should return IProvedorNotificacao', () => {
        const formGroup = service.createProvedorNotificacaoFormGroup(sampleWithRequiredData);

        const provedorNotificacao = service.getProvedorNotificacao(formGroup) as any;

        expect(provedorNotificacao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProvedorNotificacao should not enable id FormControl', () => {
        const formGroup = service.createProvedorNotificacaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProvedorNotificacao should disable id FormControl', () => {
        const formGroup = service.createProvedorNotificacaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
