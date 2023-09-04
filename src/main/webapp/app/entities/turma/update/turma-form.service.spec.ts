import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../turma.test-samples';

import { TurmaFormService } from './turma-form.service';

describe('Turma Form Service', () => {
  let service: TurmaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TurmaFormService);
  });

  describe('Service methods', () => {
    describe('createTurmaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTurmaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            chaveComposta: expect.any(Object),
            tipoTurma: expect.any(Object),
            sala: expect.any(Object),
            descricao: expect.any(Object),
            lotacao: expect.any(Object),
            confirmado: expect.any(Object),
            abertura: expect.any(Object),
            encerramento: expect.any(Object),
            criterioDescricao: expect.any(Object),
            criterioOrdenacaoNumero: expect.any(Object),
            fazInscricaoDepoisMatricula: expect.any(Object),
            isDisponivel: expect.any(Object),
            utilizador: expect.any(Object),
            referencia: expect.any(Object),
            planoCurricular: expect.any(Object),
            turno: expect.any(Object),
          })
        );
      });

      it('passing ITurma should create a new form with FormGroup', () => {
        const formGroup = service.createTurmaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            chaveComposta: expect.any(Object),
            tipoTurma: expect.any(Object),
            sala: expect.any(Object),
            descricao: expect.any(Object),
            lotacao: expect.any(Object),
            confirmado: expect.any(Object),
            abertura: expect.any(Object),
            encerramento: expect.any(Object),
            criterioDescricao: expect.any(Object),
            criterioOrdenacaoNumero: expect.any(Object),
            fazInscricaoDepoisMatricula: expect.any(Object),
            isDisponivel: expect.any(Object),
            utilizador: expect.any(Object),
            referencia: expect.any(Object),
            planoCurricular: expect.any(Object),
            turno: expect.any(Object),
          })
        );
      });
    });

    describe('getTurma', () => {
      it('should return NewTurma for default Turma initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTurmaFormGroup(sampleWithNewData);

        const turma = service.getTurma(formGroup) as any;

        expect(turma).toMatchObject(sampleWithNewData);
      });

      it('should return NewTurma for empty Turma initial value', () => {
        const formGroup = service.createTurmaFormGroup();

        const turma = service.getTurma(formGroup) as any;

        expect(turma).toMatchObject({});
      });

      it('should return ITurma', () => {
        const formGroup = service.createTurmaFormGroup(sampleWithRequiredData);

        const turma = service.getTurma(formGroup) as any;

        expect(turma).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITurma should not enable id FormControl', () => {
        const formGroup = service.createTurmaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTurma should disable id FormControl', () => {
        const formGroup = service.createTurmaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
