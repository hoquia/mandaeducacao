import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DissertacaoFinalCursoFormService } from './dissertacao-final-curso-form.service';
import { DissertacaoFinalCursoService } from '../service/dissertacao-final-curso.service';
import { IDissertacaoFinalCurso } from '../dissertacao-final-curso.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';
import { IAreaFormacao } from 'app/entities/area-formacao/area-formacao.model';
import { AreaFormacaoService } from 'app/entities/area-formacao/service/area-formacao.service';
import { IDiscente } from 'app/entities/discente/discente.model';
import { DiscenteService } from 'app/entities/discente/service/discente.service';
import { IEstadoDissertacao } from 'app/entities/estado-dissertacao/estado-dissertacao.model';
import { EstadoDissertacaoService } from 'app/entities/estado-dissertacao/service/estado-dissertacao.service';
import { INaturezaTrabalho } from 'app/entities/natureza-trabalho/natureza-trabalho.model';
import { NaturezaTrabalhoService } from 'app/entities/natureza-trabalho/service/natureza-trabalho.service';

import { DissertacaoFinalCursoUpdateComponent } from './dissertacao-final-curso-update.component';

describe('DissertacaoFinalCurso Management Update Component', () => {
  let comp: DissertacaoFinalCursoUpdateComponent;
  let fixture: ComponentFixture<DissertacaoFinalCursoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dissertacaoFinalCursoFormService: DissertacaoFinalCursoFormService;
  let dissertacaoFinalCursoService: DissertacaoFinalCursoService;
  let userService: UserService;
  let turmaService: TurmaService;
  let docenteService: DocenteService;
  let areaFormacaoService: AreaFormacaoService;
  let discenteService: DiscenteService;
  let estadoDissertacaoService: EstadoDissertacaoService;
  let naturezaTrabalhoService: NaturezaTrabalhoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DissertacaoFinalCursoUpdateComponent],
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
      .overrideTemplate(DissertacaoFinalCursoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DissertacaoFinalCursoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dissertacaoFinalCursoFormService = TestBed.inject(DissertacaoFinalCursoFormService);
    dissertacaoFinalCursoService = TestBed.inject(DissertacaoFinalCursoService);
    userService = TestBed.inject(UserService);
    turmaService = TestBed.inject(TurmaService);
    docenteService = TestBed.inject(DocenteService);
    areaFormacaoService = TestBed.inject(AreaFormacaoService);
    discenteService = TestBed.inject(DiscenteService);
    estadoDissertacaoService = TestBed.inject(EstadoDissertacaoService);
    naturezaTrabalhoService = TestBed.inject(NaturezaTrabalhoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const dissertacaoFinalCurso: IDissertacaoFinalCurso = { id: 456 };
      const utilizador: IUser = { id: 5381 };
      dissertacaoFinalCurso.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 39386 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dissertacaoFinalCurso });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Turma query and add missing value', () => {
      const dissertacaoFinalCurso: IDissertacaoFinalCurso = { id: 456 };
      const turma: ITurma = { id: 44363 };
      dissertacaoFinalCurso.turma = turma;

      const turmaCollection: ITurma[] = [{ id: 76939 }];
      jest.spyOn(turmaService, 'query').mockReturnValue(of(new HttpResponse({ body: turmaCollection })));
      const additionalTurmas = [turma];
      const expectedCollection: ITurma[] = [...additionalTurmas, ...turmaCollection];
      jest.spyOn(turmaService, 'addTurmaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dissertacaoFinalCurso });
      comp.ngOnInit();

      expect(turmaService.query).toHaveBeenCalled();
      expect(turmaService.addTurmaToCollectionIfMissing).toHaveBeenCalledWith(
        turmaCollection,
        ...additionalTurmas.map(expect.objectContaining)
      );
      expect(comp.turmasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Docente query and add missing value', () => {
      const dissertacaoFinalCurso: IDissertacaoFinalCurso = { id: 456 };
      const orientador: IDocente = { id: 55037 };
      dissertacaoFinalCurso.orientador = orientador;

      const docenteCollection: IDocente[] = [{ id: 22402 }];
      jest.spyOn(docenteService, 'query').mockReturnValue(of(new HttpResponse({ body: docenteCollection })));
      const additionalDocentes = [orientador];
      const expectedCollection: IDocente[] = [...additionalDocentes, ...docenteCollection];
      jest.spyOn(docenteService, 'addDocenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dissertacaoFinalCurso });
      comp.ngOnInit();

      expect(docenteService.query).toHaveBeenCalled();
      expect(docenteService.addDocenteToCollectionIfMissing).toHaveBeenCalledWith(
        docenteCollection,
        ...additionalDocentes.map(expect.objectContaining)
      );
      expect(comp.docentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AreaFormacao query and add missing value', () => {
      const dissertacaoFinalCurso: IDissertacaoFinalCurso = { id: 456 };
      const especialidade: IAreaFormacao = { id: 41878 };
      dissertacaoFinalCurso.especialidade = especialidade;

      const areaFormacaoCollection: IAreaFormacao[] = [{ id: 84982 }];
      jest.spyOn(areaFormacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: areaFormacaoCollection })));
      const additionalAreaFormacaos = [especialidade];
      const expectedCollection: IAreaFormacao[] = [...additionalAreaFormacaos, ...areaFormacaoCollection];
      jest.spyOn(areaFormacaoService, 'addAreaFormacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dissertacaoFinalCurso });
      comp.ngOnInit();

      expect(areaFormacaoService.query).toHaveBeenCalled();
      expect(areaFormacaoService.addAreaFormacaoToCollectionIfMissing).toHaveBeenCalledWith(
        areaFormacaoCollection,
        ...additionalAreaFormacaos.map(expect.objectContaining)
      );
      expect(comp.areaFormacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Discente query and add missing value', () => {
      const dissertacaoFinalCurso: IDissertacaoFinalCurso = { id: 456 };
      const discente: IDiscente = { id: 12703 };
      dissertacaoFinalCurso.discente = discente;

      const discenteCollection: IDiscente[] = [{ id: 16613 }];
      jest.spyOn(discenteService, 'query').mockReturnValue(of(new HttpResponse({ body: discenteCollection })));
      const additionalDiscentes = [discente];
      const expectedCollection: IDiscente[] = [...additionalDiscentes, ...discenteCollection];
      jest.spyOn(discenteService, 'addDiscenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dissertacaoFinalCurso });
      comp.ngOnInit();

      expect(discenteService.query).toHaveBeenCalled();
      expect(discenteService.addDiscenteToCollectionIfMissing).toHaveBeenCalledWith(
        discenteCollection,
        ...additionalDiscentes.map(expect.objectContaining)
      );
      expect(comp.discentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EstadoDissertacao query and add missing value', () => {
      const dissertacaoFinalCurso: IDissertacaoFinalCurso = { id: 456 };
      const estado: IEstadoDissertacao = { id: 89095 };
      dissertacaoFinalCurso.estado = estado;

      const estadoDissertacaoCollection: IEstadoDissertacao[] = [{ id: 72849 }];
      jest.spyOn(estadoDissertacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: estadoDissertacaoCollection })));
      const additionalEstadoDissertacaos = [estado];
      const expectedCollection: IEstadoDissertacao[] = [...additionalEstadoDissertacaos, ...estadoDissertacaoCollection];
      jest.spyOn(estadoDissertacaoService, 'addEstadoDissertacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dissertacaoFinalCurso });
      comp.ngOnInit();

      expect(estadoDissertacaoService.query).toHaveBeenCalled();
      expect(estadoDissertacaoService.addEstadoDissertacaoToCollectionIfMissing).toHaveBeenCalledWith(
        estadoDissertacaoCollection,
        ...additionalEstadoDissertacaos.map(expect.objectContaining)
      );
      expect(comp.estadoDissertacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NaturezaTrabalho query and add missing value', () => {
      const dissertacaoFinalCurso: IDissertacaoFinalCurso = { id: 456 };
      const natureza: INaturezaTrabalho = { id: 64307 };
      dissertacaoFinalCurso.natureza = natureza;

      const naturezaTrabalhoCollection: INaturezaTrabalho[] = [{ id: 8484 }];
      jest.spyOn(naturezaTrabalhoService, 'query').mockReturnValue(of(new HttpResponse({ body: naturezaTrabalhoCollection })));
      const additionalNaturezaTrabalhos = [natureza];
      const expectedCollection: INaturezaTrabalho[] = [...additionalNaturezaTrabalhos, ...naturezaTrabalhoCollection];
      jest.spyOn(naturezaTrabalhoService, 'addNaturezaTrabalhoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dissertacaoFinalCurso });
      comp.ngOnInit();

      expect(naturezaTrabalhoService.query).toHaveBeenCalled();
      expect(naturezaTrabalhoService.addNaturezaTrabalhoToCollectionIfMissing).toHaveBeenCalledWith(
        naturezaTrabalhoCollection,
        ...additionalNaturezaTrabalhos.map(expect.objectContaining)
      );
      expect(comp.naturezaTrabalhosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dissertacaoFinalCurso: IDissertacaoFinalCurso = { id: 456 };
      const utilizador: IUser = { id: 70391 };
      dissertacaoFinalCurso.utilizador = utilizador;
      const turma: ITurma = { id: 82486 };
      dissertacaoFinalCurso.turma = turma;
      const orientador: IDocente = { id: 13796 };
      dissertacaoFinalCurso.orientador = orientador;
      const especialidade: IAreaFormacao = { id: 81367 };
      dissertacaoFinalCurso.especialidade = especialidade;
      const discente: IDiscente = { id: 41349 };
      dissertacaoFinalCurso.discente = discente;
      const estado: IEstadoDissertacao = { id: 65957 };
      dissertacaoFinalCurso.estado = estado;
      const natureza: INaturezaTrabalho = { id: 23501 };
      dissertacaoFinalCurso.natureza = natureza;

      activatedRoute.data = of({ dissertacaoFinalCurso });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.turmasSharedCollection).toContain(turma);
      expect(comp.docentesSharedCollection).toContain(orientador);
      expect(comp.areaFormacaosSharedCollection).toContain(especialidade);
      expect(comp.discentesSharedCollection).toContain(discente);
      expect(comp.estadoDissertacaosSharedCollection).toContain(estado);
      expect(comp.naturezaTrabalhosSharedCollection).toContain(natureza);
      expect(comp.dissertacaoFinalCurso).toEqual(dissertacaoFinalCurso);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDissertacaoFinalCurso>>();
      const dissertacaoFinalCurso = { id: 123 };
      jest.spyOn(dissertacaoFinalCursoFormService, 'getDissertacaoFinalCurso').mockReturnValue(dissertacaoFinalCurso);
      jest.spyOn(dissertacaoFinalCursoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dissertacaoFinalCurso });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dissertacaoFinalCurso }));
      saveSubject.complete();

      // THEN
      expect(dissertacaoFinalCursoFormService.getDissertacaoFinalCurso).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dissertacaoFinalCursoService.update).toHaveBeenCalledWith(expect.objectContaining(dissertacaoFinalCurso));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDissertacaoFinalCurso>>();
      const dissertacaoFinalCurso = { id: 123 };
      jest.spyOn(dissertacaoFinalCursoFormService, 'getDissertacaoFinalCurso').mockReturnValue({ id: null });
      jest.spyOn(dissertacaoFinalCursoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dissertacaoFinalCurso: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dissertacaoFinalCurso }));
      saveSubject.complete();

      // THEN
      expect(dissertacaoFinalCursoFormService.getDissertacaoFinalCurso).toHaveBeenCalled();
      expect(dissertacaoFinalCursoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDissertacaoFinalCurso>>();
      const dissertacaoFinalCurso = { id: 123 };
      jest.spyOn(dissertacaoFinalCursoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dissertacaoFinalCurso });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dissertacaoFinalCursoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareDocente', () => {
      it('Should forward to docenteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(docenteService, 'compareDocente');
        comp.compareDocente(entity, entity2);
        expect(docenteService.compareDocente).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAreaFormacao', () => {
      it('Should forward to areaFormacaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(areaFormacaoService, 'compareAreaFormacao');
        comp.compareAreaFormacao(entity, entity2);
        expect(areaFormacaoService.compareAreaFormacao).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareEstadoDissertacao', () => {
      it('Should forward to estadoDissertacaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(estadoDissertacaoService, 'compareEstadoDissertacao');
        comp.compareEstadoDissertacao(entity, entity2);
        expect(estadoDissertacaoService.compareEstadoDissertacao).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareNaturezaTrabalho', () => {
      it('Should forward to naturezaTrabalhoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(naturezaTrabalhoService, 'compareNaturezaTrabalho');
        comp.compareNaturezaTrabalho(entity, entity2);
        expect(naturezaTrabalhoService.compareNaturezaTrabalho).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
