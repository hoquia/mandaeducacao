import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MatriculaFormService } from './matricula-form.service';
import { MatriculaService } from '../service/matricula.service';
import { IMatricula } from '../matricula.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IPlanoDesconto } from 'app/entities/plano-desconto/plano-desconto.model';
import { PlanoDescontoService } from 'app/entities/plano-desconto/service/plano-desconto.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { IEncarregadoEducacao } from 'app/entities/encarregado-educacao/encarregado-educacao.model';
import { EncarregadoEducacaoService } from 'app/entities/encarregado-educacao/service/encarregado-educacao.service';
import { IDiscente } from 'app/entities/discente/discente.model';
import { DiscenteService } from 'app/entities/discente/service/discente.service';

import { MatriculaUpdateComponent } from './matricula-update.component';

describe('Matricula Management Update Component', () => {
  let comp: MatriculaUpdateComponent;
  let fixture: ComponentFixture<MatriculaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let matriculaFormService: MatriculaFormService;
  let matriculaService: MatriculaService;
  let userService: UserService;
  let planoDescontoService: PlanoDescontoService;
  let turmaService: TurmaService;
  let encarregadoEducacaoService: EncarregadoEducacaoService;
  let discenteService: DiscenteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MatriculaUpdateComponent],
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
      .overrideTemplate(MatriculaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MatriculaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    matriculaFormService = TestBed.inject(MatriculaFormService);
    matriculaService = TestBed.inject(MatriculaService);
    userService = TestBed.inject(UserService);
    planoDescontoService = TestBed.inject(PlanoDescontoService);
    turmaService = TestBed.inject(TurmaService);
    encarregadoEducacaoService = TestBed.inject(EncarregadoEducacaoService);
    discenteService = TestBed.inject(DiscenteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Matricula query and add missing value', () => {
      const matricula: IMatricula = { id: 456 };
      const referencia: IMatricula = { id: 8700 };
      matricula.referencia = referencia;

      const matriculaCollection: IMatricula[] = [{ id: 73016 }];
      jest.spyOn(matriculaService, 'query').mockReturnValue(of(new HttpResponse({ body: matriculaCollection })));
      const additionalMatriculas = [referencia];
      const expectedCollection: IMatricula[] = [...additionalMatriculas, ...matriculaCollection];
      jest.spyOn(matriculaService, 'addMatriculaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricula });
      comp.ngOnInit();

      expect(matriculaService.query).toHaveBeenCalled();
      expect(matriculaService.addMatriculaToCollectionIfMissing).toHaveBeenCalledWith(
        matriculaCollection,
        ...additionalMatriculas.map(expect.objectContaining)
      );
      expect(comp.matriculasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call User query and add missing value', () => {
      const matricula: IMatricula = { id: 456 };
      const utilizador: IUser = { id: 72781 };
      matricula.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 76075 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricula });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlanoDesconto query and add missing value', () => {
      const matricula: IMatricula = { id: 456 };
      const categoriasMatriculas: IPlanoDesconto[] = [{ id: 19192 }];
      matricula.categoriasMatriculas = categoriasMatriculas;

      const planoDescontoCollection: IPlanoDesconto[] = [{ id: 4301 }];
      jest.spyOn(planoDescontoService, 'query').mockReturnValue(of(new HttpResponse({ body: planoDescontoCollection })));
      const additionalPlanoDescontos = [...categoriasMatriculas];
      const expectedCollection: IPlanoDesconto[] = [...additionalPlanoDescontos, ...planoDescontoCollection];
      jest.spyOn(planoDescontoService, 'addPlanoDescontoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricula });
      comp.ngOnInit();

      expect(planoDescontoService.query).toHaveBeenCalled();
      expect(planoDescontoService.addPlanoDescontoToCollectionIfMissing).toHaveBeenCalledWith(
        planoDescontoCollection,
        ...additionalPlanoDescontos.map(expect.objectContaining)
      );
      expect(comp.planoDescontosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Turma query and add missing value', () => {
      const matricula: IMatricula = { id: 456 };
      const turma: ITurma = { id: 46267 };
      matricula.turma = turma;

      const turmaCollection: ITurma[] = [{ id: 51978 }];
      jest.spyOn(turmaService, 'query').mockReturnValue(of(new HttpResponse({ body: turmaCollection })));
      const additionalTurmas = [turma];
      const expectedCollection: ITurma[] = [...additionalTurmas, ...turmaCollection];
      jest.spyOn(turmaService, 'addTurmaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricula });
      comp.ngOnInit();

      expect(turmaService.query).toHaveBeenCalled();
      expect(turmaService.addTurmaToCollectionIfMissing).toHaveBeenCalledWith(
        turmaCollection,
        ...additionalTurmas.map(expect.objectContaining)
      );
      expect(comp.turmasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EncarregadoEducacao query and add missing value', () => {
      const matricula: IMatricula = { id: 456 };
      const responsavelFinanceiro: IEncarregadoEducacao = { id: 229 };
      matricula.responsavelFinanceiro = responsavelFinanceiro;

      const encarregadoEducacaoCollection: IEncarregadoEducacao[] = [{ id: 56985 }];
      jest.spyOn(encarregadoEducacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: encarregadoEducacaoCollection })));
      const additionalEncarregadoEducacaos = [responsavelFinanceiro];
      const expectedCollection: IEncarregadoEducacao[] = [...additionalEncarregadoEducacaos, ...encarregadoEducacaoCollection];
      jest.spyOn(encarregadoEducacaoService, 'addEncarregadoEducacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricula });
      comp.ngOnInit();

      expect(encarregadoEducacaoService.query).toHaveBeenCalled();
      expect(encarregadoEducacaoService.addEncarregadoEducacaoToCollectionIfMissing).toHaveBeenCalledWith(
        encarregadoEducacaoCollection,
        ...additionalEncarregadoEducacaos.map(expect.objectContaining)
      );
      expect(comp.encarregadoEducacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Discente query and add missing value', () => {
      const matricula: IMatricula = { id: 456 };
      const discente: IDiscente = { id: 69826 };
      matricula.discente = discente;

      const discenteCollection: IDiscente[] = [{ id: 65565 }];
      jest.spyOn(discenteService, 'query').mockReturnValue(of(new HttpResponse({ body: discenteCollection })));
      const additionalDiscentes = [discente];
      const expectedCollection: IDiscente[] = [...additionalDiscentes, ...discenteCollection];
      jest.spyOn(discenteService, 'addDiscenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricula });
      comp.ngOnInit();

      expect(discenteService.query).toHaveBeenCalled();
      expect(discenteService.addDiscenteToCollectionIfMissing).toHaveBeenCalledWith(
        discenteCollection,
        ...additionalDiscentes.map(expect.objectContaining)
      );
      expect(comp.discentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const matricula: IMatricula = { id: 456 };
      const referencia: IMatricula = { id: 46231 };
      matricula.referencia = referencia;
      const utilizador: IUser = { id: 80801 };
      matricula.utilizador = utilizador;
      const categoriasMatriculas: IPlanoDesconto = { id: 51297 };
      matricula.categoriasMatriculas = [categoriasMatriculas];
      const turma: ITurma = { id: 63808 };
      matricula.turma = turma;
      const responsavelFinanceiro: IEncarregadoEducacao = { id: 550 };
      matricula.responsavelFinanceiro = responsavelFinanceiro;
      const discente: IDiscente = { id: 89191 };
      matricula.discente = discente;

      activatedRoute.data = of({ matricula });
      comp.ngOnInit();

      expect(comp.matriculasSharedCollection).toContain(referencia);
      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.planoDescontosSharedCollection).toContain(categoriasMatriculas);
      expect(comp.turmasSharedCollection).toContain(turma);
      expect(comp.encarregadoEducacaosSharedCollection).toContain(responsavelFinanceiro);
      expect(comp.discentesSharedCollection).toContain(discente);
      expect(comp.matricula).toEqual(matricula);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMatricula>>();
      const matricula = { id: 123 };
      jest.spyOn(matriculaFormService, 'getMatricula').mockReturnValue(matricula);
      jest.spyOn(matriculaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matricula });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: matricula }));
      saveSubject.complete();

      // THEN
      expect(matriculaFormService.getMatricula).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(matriculaService.update).toHaveBeenCalledWith(expect.objectContaining(matricula));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMatricula>>();
      const matricula = { id: 123 };
      jest.spyOn(matriculaFormService, 'getMatricula').mockReturnValue({ id: null });
      jest.spyOn(matriculaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matricula: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: matricula }));
      saveSubject.complete();

      // THEN
      expect(matriculaFormService.getMatricula).toHaveBeenCalled();
      expect(matriculaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMatricula>>();
      const matricula = { id: 123 };
      jest.spyOn(matriculaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matricula });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(matriculaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMatricula', () => {
      it('Should forward to matriculaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(matriculaService, 'compareMatricula');
        comp.compareMatricula(entity, entity2);
        expect(matriculaService.compareMatricula).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePlanoDesconto', () => {
      it('Should forward to planoDescontoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoDescontoService, 'comparePlanoDesconto');
        comp.comparePlanoDesconto(entity, entity2);
        expect(planoDescontoService.comparePlanoDesconto).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTurma', () => {
      it('Should forward to turmaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(turmaService, 'compareTurma');
        comp.compareTurma(entity, entity2);
        expect(turmaService.compareTurma).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEncarregadoEducacao', () => {
      it('Should forward to encarregadoEducacaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(encarregadoEducacaoService, 'compareEncarregadoEducacao');
        comp.compareEncarregadoEducacao(entity, entity2);
        expect(encarregadoEducacaoService.compareEncarregadoEducacao).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDiscente', () => {
      it('Should forward to discenteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(discenteService, 'compareDiscente');
        comp.compareDiscente(entity, entity2);
        expect(discenteService.compareDiscente).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
