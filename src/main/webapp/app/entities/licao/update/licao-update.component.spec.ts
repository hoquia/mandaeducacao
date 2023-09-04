import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LicaoFormService } from './licao-form.service';
import { LicaoService } from '../service/licao.service';
import { ILicao } from '../licao.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IPlanoAula } from 'app/entities/plano-aula/plano-aula.model';
import { PlanoAulaService } from 'app/entities/plano-aula/service/plano-aula.service';
import { IHorario } from 'app/entities/horario/horario.model';
import { HorarioService } from 'app/entities/horario/service/horario.service';

import { LicaoUpdateComponent } from './licao-update.component';

describe('Licao Management Update Component', () => {
  let comp: LicaoUpdateComponent;
  let fixture: ComponentFixture<LicaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let licaoFormService: LicaoFormService;
  let licaoService: LicaoService;
  let userService: UserService;
  let planoAulaService: PlanoAulaService;
  let horarioService: HorarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LicaoUpdateComponent],
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
      .overrideTemplate(LicaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LicaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    licaoFormService = TestBed.inject(LicaoFormService);
    licaoService = TestBed.inject(LicaoService);
    userService = TestBed.inject(UserService);
    planoAulaService = TestBed.inject(PlanoAulaService);
    horarioService = TestBed.inject(HorarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const licao: ILicao = { id: 456 };
      const utilizador: IUser = { id: 99499 };
      licao.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 88200 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ licao });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlanoAula query and add missing value', () => {
      const licao: ILicao = { id: 456 };
      const planoAula: IPlanoAula = { id: 45448 };
      licao.planoAula = planoAula;

      const planoAulaCollection: IPlanoAula[] = [{ id: 12038 }];
      jest.spyOn(planoAulaService, 'query').mockReturnValue(of(new HttpResponse({ body: planoAulaCollection })));
      const additionalPlanoAulas = [planoAula];
      const expectedCollection: IPlanoAula[] = [...additionalPlanoAulas, ...planoAulaCollection];
      jest.spyOn(planoAulaService, 'addPlanoAulaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ licao });
      comp.ngOnInit();

      expect(planoAulaService.query).toHaveBeenCalled();
      expect(planoAulaService.addPlanoAulaToCollectionIfMissing).toHaveBeenCalledWith(
        planoAulaCollection,
        ...additionalPlanoAulas.map(expect.objectContaining)
      );
      expect(comp.planoAulasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Horario query and add missing value', () => {
      const licao: ILicao = { id: 456 };
      const horario: IHorario = { id: 33531 };
      licao.horario = horario;

      const horarioCollection: IHorario[] = [{ id: 41187 }];
      jest.spyOn(horarioService, 'query').mockReturnValue(of(new HttpResponse({ body: horarioCollection })));
      const additionalHorarios = [horario];
      const expectedCollection: IHorario[] = [...additionalHorarios, ...horarioCollection];
      jest.spyOn(horarioService, 'addHorarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ licao });
      comp.ngOnInit();

      expect(horarioService.query).toHaveBeenCalled();
      expect(horarioService.addHorarioToCollectionIfMissing).toHaveBeenCalledWith(
        horarioCollection,
        ...additionalHorarios.map(expect.objectContaining)
      );
      expect(comp.horariosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const licao: ILicao = { id: 456 };
      const utilizador: IUser = { id: 1785 };
      licao.utilizador = utilizador;
      const planoAula: IPlanoAula = { id: 56995 };
      licao.planoAula = planoAula;
      const horario: IHorario = { id: 54091 };
      licao.horario = horario;

      activatedRoute.data = of({ licao });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.planoAulasSharedCollection).toContain(planoAula);
      expect(comp.horariosSharedCollection).toContain(horario);
      expect(comp.licao).toEqual(licao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILicao>>();
      const licao = { id: 123 };
      jest.spyOn(licaoFormService, 'getLicao').mockReturnValue(licao);
      jest.spyOn(licaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ licao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: licao }));
      saveSubject.complete();

      // THEN
      expect(licaoFormService.getLicao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(licaoService.update).toHaveBeenCalledWith(expect.objectContaining(licao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILicao>>();
      const licao = { id: 123 };
      jest.spyOn(licaoFormService, 'getLicao').mockReturnValue({ id: null });
      jest.spyOn(licaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ licao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: licao }));
      saveSubject.complete();

      // THEN
      expect(licaoFormService.getLicao).toHaveBeenCalled();
      expect(licaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILicao>>();
      const licao = { id: 123 };
      jest.spyOn(licaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ licao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(licaoService.update).toHaveBeenCalled();
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

    describe('comparePlanoAula', () => {
      it('Should forward to planoAulaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoAulaService, 'comparePlanoAula');
        comp.comparePlanoAula(entity, entity2);
        expect(planoAulaService.comparePlanoAula).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareHorario', () => {
      it('Should forward to horarioService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(horarioService, 'compareHorario');
        comp.compareHorario(entity, entity2);
        expect(horarioService.compareHorario).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
