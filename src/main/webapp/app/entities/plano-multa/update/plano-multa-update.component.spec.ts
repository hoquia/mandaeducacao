import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PlanoMultaFormService } from './plano-multa-form.service';
import { PlanoMultaService } from '../service/plano-multa.service';
import { IPlanoMulta } from '../plano-multa.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { PlanoMultaUpdateComponent } from './plano-multa-update.component';

describe('PlanoMulta Management Update Component', () => {
  let comp: PlanoMultaUpdateComponent;
  let fixture: ComponentFixture<PlanoMultaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let planoMultaFormService: PlanoMultaFormService;
  let planoMultaService: PlanoMultaService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PlanoMultaUpdateComponent],
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
      .overrideTemplate(PlanoMultaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlanoMultaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    planoMultaFormService = TestBed.inject(PlanoMultaFormService);
    planoMultaService = TestBed.inject(PlanoMultaService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const planoMulta: IPlanoMulta = { id: 456 };
      const utilizador: IUser = { id: 90711 };
      planoMulta.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 57678 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ planoMulta });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const planoMulta: IPlanoMulta = { id: 456 };
      const utilizador: IUser = { id: 43257 };
      planoMulta.utilizador = utilizador;

      activatedRoute.data = of({ planoMulta });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.planoMulta).toEqual(planoMulta);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoMulta>>();
      const planoMulta = { id: 123 };
      jest.spyOn(planoMultaFormService, 'getPlanoMulta').mockReturnValue(planoMulta);
      jest.spyOn(planoMultaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoMulta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planoMulta }));
      saveSubject.complete();

      // THEN
      expect(planoMultaFormService.getPlanoMulta).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(planoMultaService.update).toHaveBeenCalledWith(expect.objectContaining(planoMulta));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoMulta>>();
      const planoMulta = { id: 123 };
      jest.spyOn(planoMultaFormService, 'getPlanoMulta').mockReturnValue({ id: null });
      jest.spyOn(planoMultaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoMulta: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planoMulta }));
      saveSubject.complete();

      // THEN
      expect(planoMultaFormService.getPlanoMulta).toHaveBeenCalled();
      expect(planoMultaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoMulta>>();
      const planoMulta = { id: 123 };
      jest.spyOn(planoMultaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoMulta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(planoMultaService.update).toHaveBeenCalled();
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
  });
});
