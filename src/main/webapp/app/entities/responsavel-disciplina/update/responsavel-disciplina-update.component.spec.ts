import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResponsavelDisciplinaFormService } from './responsavel-disciplina-form.service';
import { ResponsavelDisciplinaService } from '../service/responsavel-disciplina.service';
import { IResponsavelDisciplina } from '../responsavel-disciplina.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDisciplina } from 'app/entities/disciplina/disciplina.model';
import { DisciplinaService } from 'app/entities/disciplina/service/disciplina.service';

import { ResponsavelDisciplinaUpdateComponent } from './responsavel-disciplina-update.component';

describe('ResponsavelDisciplina Management Update Component', () => {
  let comp: ResponsavelDisciplinaUpdateComponent;
  let fixture: ComponentFixture<ResponsavelDisciplinaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let responsavelDisciplinaFormService: ResponsavelDisciplinaFormService;
  let responsavelDisciplinaService: ResponsavelDisciplinaService;
  let userService: UserService;
  let disciplinaService: DisciplinaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResponsavelDisciplinaUpdateComponent],
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
      .overrideTemplate(ResponsavelDisciplinaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResponsavelDisciplinaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    responsavelDisciplinaFormService = TestBed.inject(ResponsavelDisciplinaFormService);
    responsavelDisciplinaService = TestBed.inject(ResponsavelDisciplinaService);
    userService = TestBed.inject(UserService);
    disciplinaService = TestBed.inject(DisciplinaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const responsavelDisciplina: IResponsavelDisciplina = { id: 456 };
      const utilizador: IUser = { id: 54642 };
      responsavelDisciplina.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 90831 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ responsavelDisciplina });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Disciplina query and add missing value', () => {
      const responsavelDisciplina: IResponsavelDisciplina = { id: 456 };
      const disciplina: IDisciplina = { id: 37669 };
      responsavelDisciplina.disciplina = disciplina;

      const disciplinaCollection: IDisciplina[] = [{ id: 78402 }];
      jest.spyOn(disciplinaService, 'query').mockReturnValue(of(new HttpResponse({ body: disciplinaCollection })));
      const additionalDisciplinas = [disciplina];
      const expectedCollection: IDisciplina[] = [...additionalDisciplinas, ...disciplinaCollection];
      jest.spyOn(disciplinaService, 'addDisciplinaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ responsavelDisciplina });
      comp.ngOnInit();

      expect(disciplinaService.query).toHaveBeenCalled();
      expect(disciplinaService.addDisciplinaToCollectionIfMissing).toHaveBeenCalledWith(
        disciplinaCollection,
        ...additionalDisciplinas.map(expect.objectContaining)
      );
      expect(comp.disciplinasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const responsavelDisciplina: IResponsavelDisciplina = { id: 456 };
      const utilizador: IUser = { id: 14917 };
      responsavelDisciplina.utilizador = utilizador;
      const disciplina: IDisciplina = { id: 27336 };
      responsavelDisciplina.disciplina = disciplina;

      activatedRoute.data = of({ responsavelDisciplina });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.disciplinasSharedCollection).toContain(disciplina);
      expect(comp.responsavelDisciplina).toEqual(responsavelDisciplina);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResponsavelDisciplina>>();
      const responsavelDisciplina = { id: 123 };
      jest.spyOn(responsavelDisciplinaFormService, 'getResponsavelDisciplina').mockReturnValue(responsavelDisciplina);
      jest.spyOn(responsavelDisciplinaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelDisciplina });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: responsavelDisciplina }));
      saveSubject.complete();

      // THEN
      expect(responsavelDisciplinaFormService.getResponsavelDisciplina).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(responsavelDisciplinaService.update).toHaveBeenCalledWith(expect.objectContaining(responsavelDisciplina));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResponsavelDisciplina>>();
      const responsavelDisciplina = { id: 123 };
      jest.spyOn(responsavelDisciplinaFormService, 'getResponsavelDisciplina').mockReturnValue({ id: null });
      jest.spyOn(responsavelDisciplinaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelDisciplina: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: responsavelDisciplina }));
      saveSubject.complete();

      // THEN
      expect(responsavelDisciplinaFormService.getResponsavelDisciplina).toHaveBeenCalled();
      expect(responsavelDisciplinaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResponsavelDisciplina>>();
      const responsavelDisciplina = { id: 123 };
      jest.spyOn(responsavelDisciplinaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelDisciplina });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(responsavelDisciplinaService.update).toHaveBeenCalled();
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

    describe('compareDisciplina', () => {
      it('Should forward to disciplinaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(disciplinaService, 'compareDisciplina');
        comp.compareDisciplina(entity, entity2);
        expect(disciplinaService.compareDisciplina).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
