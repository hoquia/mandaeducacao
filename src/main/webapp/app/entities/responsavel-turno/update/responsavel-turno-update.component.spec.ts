import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResponsavelTurnoFormService } from './responsavel-turno-form.service';
import { ResponsavelTurnoService } from '../service/responsavel-turno.service';
import { IResponsavelTurno } from '../responsavel-turno.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ITurno } from 'app/entities/turno/turno.model';
import { TurnoService } from 'app/entities/turno/service/turno.service';

import { ResponsavelTurnoUpdateComponent } from './responsavel-turno-update.component';

describe('ResponsavelTurno Management Update Component', () => {
  let comp: ResponsavelTurnoUpdateComponent;
  let fixture: ComponentFixture<ResponsavelTurnoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let responsavelTurnoFormService: ResponsavelTurnoFormService;
  let responsavelTurnoService: ResponsavelTurnoService;
  let userService: UserService;
  let turnoService: TurnoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResponsavelTurnoUpdateComponent],
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
      .overrideTemplate(ResponsavelTurnoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResponsavelTurnoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    responsavelTurnoFormService = TestBed.inject(ResponsavelTurnoFormService);
    responsavelTurnoService = TestBed.inject(ResponsavelTurnoService);
    userService = TestBed.inject(UserService);
    turnoService = TestBed.inject(TurnoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const responsavelTurno: IResponsavelTurno = { id: 456 };
      const utilizador: IUser = { id: 89907 };
      responsavelTurno.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 47683 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ responsavelTurno });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Turno query and add missing value', () => {
      const responsavelTurno: IResponsavelTurno = { id: 456 };
      const turno: ITurno = { id: 51644 };
      responsavelTurno.turno = turno;

      const turnoCollection: ITurno[] = [{ id: 96256 }];
      jest.spyOn(turnoService, 'query').mockReturnValue(of(new HttpResponse({ body: turnoCollection })));
      const additionalTurnos = [turno];
      const expectedCollection: ITurno[] = [...additionalTurnos, ...turnoCollection];
      jest.spyOn(turnoService, 'addTurnoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ responsavelTurno });
      comp.ngOnInit();

      expect(turnoService.query).toHaveBeenCalled();
      expect(turnoService.addTurnoToCollectionIfMissing).toHaveBeenCalledWith(
        turnoCollection,
        ...additionalTurnos.map(expect.objectContaining)
      );
      expect(comp.turnosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const responsavelTurno: IResponsavelTurno = { id: 456 };
      const utilizador: IUser = { id: 85535 };
      responsavelTurno.utilizador = utilizador;
      const turno: ITurno = { id: 19672 };
      responsavelTurno.turno = turno;

      activatedRoute.data = of({ responsavelTurno });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.turnosSharedCollection).toContain(turno);
      expect(comp.responsavelTurno).toEqual(responsavelTurno);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResponsavelTurno>>();
      const responsavelTurno = { id: 123 };
      jest.spyOn(responsavelTurnoFormService, 'getResponsavelTurno').mockReturnValue(responsavelTurno);
      jest.spyOn(responsavelTurnoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelTurno });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: responsavelTurno }));
      saveSubject.complete();

      // THEN
      expect(responsavelTurnoFormService.getResponsavelTurno).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(responsavelTurnoService.update).toHaveBeenCalledWith(expect.objectContaining(responsavelTurno));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResponsavelTurno>>();
      const responsavelTurno = { id: 123 };
      jest.spyOn(responsavelTurnoFormService, 'getResponsavelTurno').mockReturnValue({ id: null });
      jest.spyOn(responsavelTurnoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelTurno: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: responsavelTurno }));
      saveSubject.complete();

      // THEN
      expect(responsavelTurnoFormService.getResponsavelTurno).toHaveBeenCalled();
      expect(responsavelTurnoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResponsavelTurno>>();
      const responsavelTurno = { id: 123 };
      jest.spyOn(responsavelTurnoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelTurno });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(responsavelTurnoService.update).toHaveBeenCalled();
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

    describe('compareTurno', () => {
      it('Should forward to turnoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(turnoService, 'compareTurno');
        comp.compareTurno(entity, entity2);
        expect(turnoService.compareTurno).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
