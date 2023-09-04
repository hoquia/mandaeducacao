import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LongonkeloHistoricoFormService } from './longonkelo-historico-form.service';
import { LongonkeloHistoricoService } from '../service/longonkelo-historico.service';
import { ILongonkeloHistorico } from '../longonkelo-historico.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { LongonkeloHistoricoUpdateComponent } from './longonkelo-historico-update.component';

describe('LongonkeloHistorico Management Update Component', () => {
  let comp: LongonkeloHistoricoUpdateComponent;
  let fixture: ComponentFixture<LongonkeloHistoricoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let longonkeloHistoricoFormService: LongonkeloHistoricoFormService;
  let longonkeloHistoricoService: LongonkeloHistoricoService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LongonkeloHistoricoUpdateComponent],
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
      .overrideTemplate(LongonkeloHistoricoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LongonkeloHistoricoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    longonkeloHistoricoFormService = TestBed.inject(LongonkeloHistoricoFormService);
    longonkeloHistoricoService = TestBed.inject(LongonkeloHistoricoService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const longonkeloHistorico: ILongonkeloHistorico = { id: 456 };
      const utilizador: IUser = { id: 40611 };
      longonkeloHistorico.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 29909 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ longonkeloHistorico });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const longonkeloHistorico: ILongonkeloHistorico = { id: 456 };
      const utilizador: IUser = { id: 55169 };
      longonkeloHistorico.utilizador = utilizador;

      activatedRoute.data = of({ longonkeloHistorico });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.longonkeloHistorico).toEqual(longonkeloHistorico);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILongonkeloHistorico>>();
      const longonkeloHistorico = { id: 123 };
      jest.spyOn(longonkeloHistoricoFormService, 'getLongonkeloHistorico').mockReturnValue(longonkeloHistorico);
      jest.spyOn(longonkeloHistoricoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ longonkeloHistorico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: longonkeloHistorico }));
      saveSubject.complete();

      // THEN
      expect(longonkeloHistoricoFormService.getLongonkeloHistorico).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(longonkeloHistoricoService.update).toHaveBeenCalledWith(expect.objectContaining(longonkeloHistorico));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILongonkeloHistorico>>();
      const longonkeloHistorico = { id: 123 };
      jest.spyOn(longonkeloHistoricoFormService, 'getLongonkeloHistorico').mockReturnValue({ id: null });
      jest.spyOn(longonkeloHistoricoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ longonkeloHistorico: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: longonkeloHistorico }));
      saveSubject.complete();

      // THEN
      expect(longonkeloHistoricoFormService.getLongonkeloHistorico).toHaveBeenCalled();
      expect(longonkeloHistoricoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILongonkeloHistorico>>();
      const longonkeloHistorico = { id: 123 };
      jest.spyOn(longonkeloHistoricoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ longonkeloHistorico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(longonkeloHistoricoService.update).toHaveBeenCalled();
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
