import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResponsavelCursoFormService } from './responsavel-curso-form.service';
import { ResponsavelCursoService } from '../service/responsavel-curso.service';
import { IResponsavelCurso } from '../responsavel-curso.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ICurso } from 'app/entities/curso/curso.model';
import { CursoService } from 'app/entities/curso/service/curso.service';

import { ResponsavelCursoUpdateComponent } from './responsavel-curso-update.component';

describe('ResponsavelCurso Management Update Component', () => {
  let comp: ResponsavelCursoUpdateComponent;
  let fixture: ComponentFixture<ResponsavelCursoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let responsavelCursoFormService: ResponsavelCursoFormService;
  let responsavelCursoService: ResponsavelCursoService;
  let userService: UserService;
  let cursoService: CursoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResponsavelCursoUpdateComponent],
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
      .overrideTemplate(ResponsavelCursoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResponsavelCursoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    responsavelCursoFormService = TestBed.inject(ResponsavelCursoFormService);
    responsavelCursoService = TestBed.inject(ResponsavelCursoService);
    userService = TestBed.inject(UserService);
    cursoService = TestBed.inject(CursoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const responsavelCurso: IResponsavelCurso = { id: 456 };
      const utilizador: IUser = { id: 85278 };
      responsavelCurso.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 21062 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ responsavelCurso });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Curso query and add missing value', () => {
      const responsavelCurso: IResponsavelCurso = { id: 456 };
      const curso: ICurso = { id: 22840 };
      responsavelCurso.curso = curso;

      const cursoCollection: ICurso[] = [{ id: 45388 }];
      jest.spyOn(cursoService, 'query').mockReturnValue(of(new HttpResponse({ body: cursoCollection })));
      const additionalCursos = [curso];
      const expectedCollection: ICurso[] = [...additionalCursos, ...cursoCollection];
      jest.spyOn(cursoService, 'addCursoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ responsavelCurso });
      comp.ngOnInit();

      expect(cursoService.query).toHaveBeenCalled();
      expect(cursoService.addCursoToCollectionIfMissing).toHaveBeenCalledWith(
        cursoCollection,
        ...additionalCursos.map(expect.objectContaining)
      );
      expect(comp.cursosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const responsavelCurso: IResponsavelCurso = { id: 456 };
      const utilizador: IUser = { id: 90420 };
      responsavelCurso.utilizador = utilizador;
      const curso: ICurso = { id: 20522 };
      responsavelCurso.curso = curso;

      activatedRoute.data = of({ responsavelCurso });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.cursosSharedCollection).toContain(curso);
      expect(comp.responsavelCurso).toEqual(responsavelCurso);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResponsavelCurso>>();
      const responsavelCurso = { id: 123 };
      jest.spyOn(responsavelCursoFormService, 'getResponsavelCurso').mockReturnValue(responsavelCurso);
      jest.spyOn(responsavelCursoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelCurso });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: responsavelCurso }));
      saveSubject.complete();

      // THEN
      expect(responsavelCursoFormService.getResponsavelCurso).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(responsavelCursoService.update).toHaveBeenCalledWith(expect.objectContaining(responsavelCurso));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResponsavelCurso>>();
      const responsavelCurso = { id: 123 };
      jest.spyOn(responsavelCursoFormService, 'getResponsavelCurso').mockReturnValue({ id: null });
      jest.spyOn(responsavelCursoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelCurso: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: responsavelCurso }));
      saveSubject.complete();

      // THEN
      expect(responsavelCursoFormService.getResponsavelCurso).toHaveBeenCalled();
      expect(responsavelCursoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResponsavelCurso>>();
      const responsavelCurso = { id: 123 };
      jest.spyOn(responsavelCursoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelCurso });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(responsavelCursoService.update).toHaveBeenCalled();
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

    describe('compareCurso', () => {
      it('Should forward to cursoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cursoService, 'compareCurso');
        comp.compareCurso(entity, entity2);
        expect(cursoService.compareCurso).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
