import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PrecoEmolumentoFormService } from './preco-emolumento-form.service';
import { PrecoEmolumentoService } from '../service/preco-emolumento.service';
import { IPrecoEmolumento } from '../preco-emolumento.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IEmolumento } from 'app/entities/emolumento/emolumento.model';
import { EmolumentoService } from 'app/entities/emolumento/service/emolumento.service';
import { IAreaFormacao } from 'app/entities/area-formacao/area-formacao.model';
import { AreaFormacaoService } from 'app/entities/area-formacao/service/area-formacao.service';
import { ICurso } from 'app/entities/curso/curso.model';
import { CursoService } from 'app/entities/curso/service/curso.service';
import { IClasse } from 'app/entities/classe/classe.model';
import { ClasseService } from 'app/entities/classe/service/classe.service';
import { ITurno } from 'app/entities/turno/turno.model';
import { TurnoService } from 'app/entities/turno/service/turno.service';
import { IPlanoMulta } from 'app/entities/plano-multa/plano-multa.model';
import { PlanoMultaService } from 'app/entities/plano-multa/service/plano-multa.service';

import { PrecoEmolumentoUpdateComponent } from './preco-emolumento-update.component';

describe('PrecoEmolumento Management Update Component', () => {
  let comp: PrecoEmolumentoUpdateComponent;
  let fixture: ComponentFixture<PrecoEmolumentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let precoEmolumentoFormService: PrecoEmolumentoFormService;
  let precoEmolumentoService: PrecoEmolumentoService;
  let userService: UserService;
  let emolumentoService: EmolumentoService;
  let areaFormacaoService: AreaFormacaoService;
  let cursoService: CursoService;
  let classeService: ClasseService;
  let turnoService: TurnoService;
  let planoMultaService: PlanoMultaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PrecoEmolumentoUpdateComponent],
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
      .overrideTemplate(PrecoEmolumentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrecoEmolumentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    precoEmolumentoFormService = TestBed.inject(PrecoEmolumentoFormService);
    precoEmolumentoService = TestBed.inject(PrecoEmolumentoService);
    userService = TestBed.inject(UserService);
    emolumentoService = TestBed.inject(EmolumentoService);
    areaFormacaoService = TestBed.inject(AreaFormacaoService);
    cursoService = TestBed.inject(CursoService);
    classeService = TestBed.inject(ClasseService);
    turnoService = TestBed.inject(TurnoService);
    planoMultaService = TestBed.inject(PlanoMultaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const precoEmolumento: IPrecoEmolumento = { id: 456 };
      const utilizador: IUser = { id: 88739 };
      precoEmolumento.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 85955 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ precoEmolumento });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Emolumento query and add missing value', () => {
      const precoEmolumento: IPrecoEmolumento = { id: 456 };
      const emolumento: IEmolumento = { id: 81629 };
      precoEmolumento.emolumento = emolumento;

      const emolumentoCollection: IEmolumento[] = [{ id: 41004 }];
      jest.spyOn(emolumentoService, 'query').mockReturnValue(of(new HttpResponse({ body: emolumentoCollection })));
      const additionalEmolumentos = [emolumento];
      const expectedCollection: IEmolumento[] = [...additionalEmolumentos, ...emolumentoCollection];
      jest.spyOn(emolumentoService, 'addEmolumentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ precoEmolumento });
      comp.ngOnInit();

      expect(emolumentoService.query).toHaveBeenCalled();
      expect(emolumentoService.addEmolumentoToCollectionIfMissing).toHaveBeenCalledWith(
        emolumentoCollection,
        ...additionalEmolumentos.map(expect.objectContaining)
      );
      expect(comp.emolumentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AreaFormacao query and add missing value', () => {
      const precoEmolumento: IPrecoEmolumento = { id: 456 };
      const areaFormacao: IAreaFormacao = { id: 86732 };
      precoEmolumento.areaFormacao = areaFormacao;

      const areaFormacaoCollection: IAreaFormacao[] = [{ id: 384 }];
      jest.spyOn(areaFormacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: areaFormacaoCollection })));
      const additionalAreaFormacaos = [areaFormacao];
      const expectedCollection: IAreaFormacao[] = [...additionalAreaFormacaos, ...areaFormacaoCollection];
      jest.spyOn(areaFormacaoService, 'addAreaFormacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ precoEmolumento });
      comp.ngOnInit();

      expect(areaFormacaoService.query).toHaveBeenCalled();
      expect(areaFormacaoService.addAreaFormacaoToCollectionIfMissing).toHaveBeenCalledWith(
        areaFormacaoCollection,
        ...additionalAreaFormacaos.map(expect.objectContaining)
      );
      expect(comp.areaFormacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Curso query and add missing value', () => {
      const precoEmolumento: IPrecoEmolumento = { id: 456 };
      const curso: ICurso = { id: 19472 };
      precoEmolumento.curso = curso;

      const cursoCollection: ICurso[] = [{ id: 38494 }];
      jest.spyOn(cursoService, 'query').mockReturnValue(of(new HttpResponse({ body: cursoCollection })));
      const additionalCursos = [curso];
      const expectedCollection: ICurso[] = [...additionalCursos, ...cursoCollection];
      jest.spyOn(cursoService, 'addCursoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ precoEmolumento });
      comp.ngOnInit();

      expect(cursoService.query).toHaveBeenCalled();
      expect(cursoService.addCursoToCollectionIfMissing).toHaveBeenCalledWith(
        cursoCollection,
        ...additionalCursos.map(expect.objectContaining)
      );
      expect(comp.cursosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Classe query and add missing value', () => {
      const precoEmolumento: IPrecoEmolumento = { id: 456 };
      const classe: IClasse = { id: 29683 };
      precoEmolumento.classe = classe;

      const classeCollection: IClasse[] = [{ id: 41100 }];
      jest.spyOn(classeService, 'query').mockReturnValue(of(new HttpResponse({ body: classeCollection })));
      const additionalClasses = [classe];
      const expectedCollection: IClasse[] = [...additionalClasses, ...classeCollection];
      jest.spyOn(classeService, 'addClasseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ precoEmolumento });
      comp.ngOnInit();

      expect(classeService.query).toHaveBeenCalled();
      expect(classeService.addClasseToCollectionIfMissing).toHaveBeenCalledWith(
        classeCollection,
        ...additionalClasses.map(expect.objectContaining)
      );
      expect(comp.classesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Turno query and add missing value', () => {
      const precoEmolumento: IPrecoEmolumento = { id: 456 };
      const turno: ITurno = { id: 99076 };
      precoEmolumento.turno = turno;

      const turnoCollection: ITurno[] = [{ id: 67839 }];
      jest.spyOn(turnoService, 'query').mockReturnValue(of(new HttpResponse({ body: turnoCollection })));
      const additionalTurnos = [turno];
      const expectedCollection: ITurno[] = [...additionalTurnos, ...turnoCollection];
      jest.spyOn(turnoService, 'addTurnoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ precoEmolumento });
      comp.ngOnInit();

      expect(turnoService.query).toHaveBeenCalled();
      expect(turnoService.addTurnoToCollectionIfMissing).toHaveBeenCalledWith(
        turnoCollection,
        ...additionalTurnos.map(expect.objectContaining)
      );
      expect(comp.turnosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlanoMulta query and add missing value', () => {
      const precoEmolumento: IPrecoEmolumento = { id: 456 };
      const planoMulta: IPlanoMulta = { id: 62583 };
      precoEmolumento.planoMulta = planoMulta;

      const planoMultaCollection: IPlanoMulta[] = [{ id: 93968 }];
      jest.spyOn(planoMultaService, 'query').mockReturnValue(of(new HttpResponse({ body: planoMultaCollection })));
      const additionalPlanoMultas = [planoMulta];
      const expectedCollection: IPlanoMulta[] = [...additionalPlanoMultas, ...planoMultaCollection];
      jest.spyOn(planoMultaService, 'addPlanoMultaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ precoEmolumento });
      comp.ngOnInit();

      expect(planoMultaService.query).toHaveBeenCalled();
      expect(planoMultaService.addPlanoMultaToCollectionIfMissing).toHaveBeenCalledWith(
        planoMultaCollection,
        ...additionalPlanoMultas.map(expect.objectContaining)
      );
      expect(comp.planoMultasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const precoEmolumento: IPrecoEmolumento = { id: 456 };
      const utilizador: IUser = { id: 64420 };
      precoEmolumento.utilizador = utilizador;
      const emolumento: IEmolumento = { id: 82013 };
      precoEmolumento.emolumento = emolumento;
      const areaFormacao: IAreaFormacao = { id: 36676 };
      precoEmolumento.areaFormacao = areaFormacao;
      const curso: ICurso = { id: 84716 };
      precoEmolumento.curso = curso;
      const classe: IClasse = { id: 19454 };
      precoEmolumento.classe = classe;
      const turno: ITurno = { id: 5483 };
      precoEmolumento.turno = turno;
      const planoMulta: IPlanoMulta = { id: 53184 };
      precoEmolumento.planoMulta = planoMulta;

      activatedRoute.data = of({ precoEmolumento });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.emolumentosSharedCollection).toContain(emolumento);
      expect(comp.areaFormacaosSharedCollection).toContain(areaFormacao);
      expect(comp.cursosSharedCollection).toContain(curso);
      expect(comp.classesSharedCollection).toContain(classe);
      expect(comp.turnosSharedCollection).toContain(turno);
      expect(comp.planoMultasSharedCollection).toContain(planoMulta);
      expect(comp.precoEmolumento).toEqual(precoEmolumento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrecoEmolumento>>();
      const precoEmolumento = { id: 123 };
      jest.spyOn(precoEmolumentoFormService, 'getPrecoEmolumento').mockReturnValue(precoEmolumento);
      jest.spyOn(precoEmolumentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ precoEmolumento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: precoEmolumento }));
      saveSubject.complete();

      // THEN
      expect(precoEmolumentoFormService.getPrecoEmolumento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(precoEmolumentoService.update).toHaveBeenCalledWith(expect.objectContaining(precoEmolumento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrecoEmolumento>>();
      const precoEmolumento = { id: 123 };
      jest.spyOn(precoEmolumentoFormService, 'getPrecoEmolumento').mockReturnValue({ id: null });
      jest.spyOn(precoEmolumentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ precoEmolumento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: precoEmolumento }));
      saveSubject.complete();

      // THEN
      expect(precoEmolumentoFormService.getPrecoEmolumento).toHaveBeenCalled();
      expect(precoEmolumentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrecoEmolumento>>();
      const precoEmolumento = { id: 123 };
      jest.spyOn(precoEmolumentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ precoEmolumento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(precoEmolumentoService.update).toHaveBeenCalled();
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

    describe('compareEmolumento', () => {
      it('Should forward to emolumentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(emolumentoService, 'compareEmolumento');
        comp.compareEmolumento(entity, entity2);
        expect(emolumentoService.compareEmolumento).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareCurso', () => {
      it('Should forward to cursoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cursoService, 'compareCurso');
        comp.compareCurso(entity, entity2);
        expect(cursoService.compareCurso).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareClasse', () => {
      it('Should forward to classeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(classeService, 'compareClasse');
        comp.compareClasse(entity, entity2);
        expect(classeService.compareClasse).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTurno', () => {
      it('Should forward to turnoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(turnoService, 'compareTurno');
        comp.compareTurno(entity, entity2);
        expect(turnoService.compareTurno).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePlanoMulta', () => {
      it('Should forward to planoMultaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoMultaService, 'comparePlanoMulta');
        comp.comparePlanoMulta(entity, entity2);
        expect(planoMultaService.comparePlanoMulta).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
