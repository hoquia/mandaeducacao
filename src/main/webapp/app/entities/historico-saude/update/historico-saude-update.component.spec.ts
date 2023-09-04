import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HistoricoSaudeFormService } from './historico-saude-form.service';
import { HistoricoSaudeService } from '../service/historico-saude.service';
import { IHistoricoSaude } from '../historico-saude.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDiscente } from 'app/entities/discente/discente.model';
import { DiscenteService } from 'app/entities/discente/service/discente.service';

import { HistoricoSaudeUpdateComponent } from './historico-saude-update.component';

describe('HistoricoSaude Management Update Component', () => {
  let comp: HistoricoSaudeUpdateComponent;
  let fixture: ComponentFixture<HistoricoSaudeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let historicoSaudeFormService: HistoricoSaudeFormService;
  let historicoSaudeService: HistoricoSaudeService;
  let userService: UserService;
  let discenteService: DiscenteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HistoricoSaudeUpdateComponent],
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
      .overrideTemplate(HistoricoSaudeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HistoricoSaudeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    historicoSaudeFormService = TestBed.inject(HistoricoSaudeFormService);
    historicoSaudeService = TestBed.inject(HistoricoSaudeService);
    userService = TestBed.inject(UserService);
    discenteService = TestBed.inject(DiscenteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const historicoSaude: IHistoricoSaude = { id: 456 };
      const utilizador: IUser = { id: 47276 };
      historicoSaude.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 84523 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ historicoSaude });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Discente query and add missing value', () => {
      const historicoSaude: IHistoricoSaude = { id: 456 };
      const discente: IDiscente = { id: 73628 };
      historicoSaude.discente = discente;

      const discenteCollection: IDiscente[] = [{ id: 79105 }];
      jest.spyOn(discenteService, 'query').mockReturnValue(of(new HttpResponse({ body: discenteCollection })));
      const additionalDiscentes = [discente];
      const expectedCollection: IDiscente[] = [...additionalDiscentes, ...discenteCollection];
      jest.spyOn(discenteService, 'addDiscenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ historicoSaude });
      comp.ngOnInit();

      expect(discenteService.query).toHaveBeenCalled();
      expect(discenteService.addDiscenteToCollectionIfMissing).toHaveBeenCalledWith(
        discenteCollection,
        ...additionalDiscentes.map(expect.objectContaining)
      );
      expect(comp.discentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const historicoSaude: IHistoricoSaude = { id: 456 };
      const utilizador: IUser = { id: 3843 };
      historicoSaude.utilizador = utilizador;
      const discente: IDiscente = { id: 18548 };
      historicoSaude.discente = discente;

      activatedRoute.data = of({ historicoSaude });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.discentesSharedCollection).toContain(discente);
      expect(comp.historicoSaude).toEqual(historicoSaude);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHistoricoSaude>>();
      const historicoSaude = { id: 123 };
      jest.spyOn(historicoSaudeFormService, 'getHistoricoSaude').mockReturnValue(historicoSaude);
      jest.spyOn(historicoSaudeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ historicoSaude });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: historicoSaude }));
      saveSubject.complete();

      // THEN
      expect(historicoSaudeFormService.getHistoricoSaude).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(historicoSaudeService.update).toHaveBeenCalledWith(expect.objectContaining(historicoSaude));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHistoricoSaude>>();
      const historicoSaude = { id: 123 };
      jest.spyOn(historicoSaudeFormService, 'getHistoricoSaude').mockReturnValue({ id: null });
      jest.spyOn(historicoSaudeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ historicoSaude: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: historicoSaude }));
      saveSubject.complete();

      // THEN
      expect(historicoSaudeFormService.getHistoricoSaude).toHaveBeenCalled();
      expect(historicoSaudeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHistoricoSaude>>();
      const historicoSaude = { id: 123 };
      jest.spyOn(historicoSaudeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ historicoSaude });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(historicoSaudeService.update).toHaveBeenCalled();
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
