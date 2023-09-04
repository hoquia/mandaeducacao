import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResponsavelTurmaFormService } from './responsavel-turma-form.service';
import { ResponsavelTurmaService } from '../service/responsavel-turma.service';
import { IResponsavelTurma } from '../responsavel-turma.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';

import { ResponsavelTurmaUpdateComponent } from './responsavel-turma-update.component';

describe('ResponsavelTurma Management Update Component', () => {
  let comp: ResponsavelTurmaUpdateComponent;
  let fixture: ComponentFixture<ResponsavelTurmaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let responsavelTurmaFormService: ResponsavelTurmaFormService;
  let responsavelTurmaService: ResponsavelTurmaService;
  let userService: UserService;
  let turmaService: TurmaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResponsavelTurmaUpdateComponent],
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
      .overrideTemplate(ResponsavelTurmaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResponsavelTurmaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    responsavelTurmaFormService = TestBed.inject(ResponsavelTurmaFormService);
    responsavelTurmaService = TestBed.inject(ResponsavelTurmaService);
    userService = TestBed.inject(UserService);
    turmaService = TestBed.inject(TurmaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const responsavelTurma: IResponsavelTurma = { id: 456 };
      const utilizador: IUser = { id: 43515 };
      responsavelTurma.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 29648 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ responsavelTurma });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Turma query and add missing value', () => {
      const responsavelTurma: IResponsavelTurma = { id: 456 };
      const turma: ITurma = { id: 47040 };
      responsavelTurma.turma = turma;

      const turmaCollection: ITurma[] = [{ id: 67137 }];
      jest.spyOn(turmaService, 'query').mockReturnValue(of(new HttpResponse({ body: turmaCollection })));
      const additionalTurmas = [turma];
      const expectedCollection: ITurma[] = [...additionalTurmas, ...turmaCollection];
      jest.spyOn(turmaService, 'addTurmaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ responsavelTurma });
      comp.ngOnInit();

      expect(turmaService.query).toHaveBeenCalled();
      expect(turmaService.addTurmaToCollectionIfMissing).toHaveBeenCalledWith(
        turmaCollection,
        ...additionalTurmas.map(expect.objectContaining)
      );
      expect(comp.turmasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const responsavelTurma: IResponsavelTurma = { id: 456 };
      const utilizador: IUser = { id: 5899 };
      responsavelTurma.utilizador = utilizador;
      const turma: ITurma = { id: 2873 };
      responsavelTurma.turma = turma;

      activatedRoute.data = of({ responsavelTurma });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.turmasSharedCollection).toContain(turma);
      expect(comp.responsavelTurma).toEqual(responsavelTurma);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResponsavelTurma>>();
      const responsavelTurma = { id: 123 };
      jest.spyOn(responsavelTurmaFormService, 'getResponsavelTurma').mockReturnValue(responsavelTurma);
      jest.spyOn(responsavelTurmaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelTurma });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: responsavelTurma }));
      saveSubject.complete();

      // THEN
      expect(responsavelTurmaFormService.getResponsavelTurma).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(responsavelTurmaService.update).toHaveBeenCalledWith(expect.objectContaining(responsavelTurma));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResponsavelTurma>>();
      const responsavelTurma = { id: 123 };
      jest.spyOn(responsavelTurmaFormService, 'getResponsavelTurma').mockReturnValue({ id: null });
      jest.spyOn(responsavelTurmaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelTurma: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: responsavelTurma }));
      saveSubject.complete();

      // THEN
      expect(responsavelTurmaFormService.getResponsavelTurma).toHaveBeenCalled();
      expect(responsavelTurmaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResponsavelTurma>>();
      const responsavelTurma = { id: 123 };
      jest.spyOn(responsavelTurmaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelTurma });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(responsavelTurmaService.update).toHaveBeenCalled();
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
  });
});
