import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PeriodoLancamentoNotaFormService } from './periodo-lancamento-nota-form.service';
import { PeriodoLancamentoNotaService } from '../service/periodo-lancamento-nota.service';
import { IPeriodoLancamentoNota } from '../periodo-lancamento-nota.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IClasse } from 'app/entities/classe/classe.model';
import { ClasseService } from 'app/entities/classe/service/classe.service';

import { PeriodoLancamentoNotaUpdateComponent } from './periodo-lancamento-nota-update.component';

describe('PeriodoLancamentoNota Management Update Component', () => {
  let comp: PeriodoLancamentoNotaUpdateComponent;
  let fixture: ComponentFixture<PeriodoLancamentoNotaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let periodoLancamentoNotaFormService: PeriodoLancamentoNotaFormService;
  let periodoLancamentoNotaService: PeriodoLancamentoNotaService;
  let userService: UserService;
  let classeService: ClasseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PeriodoLancamentoNotaUpdateComponent],
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
      .overrideTemplate(PeriodoLancamentoNotaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PeriodoLancamentoNotaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    periodoLancamentoNotaFormService = TestBed.inject(PeriodoLancamentoNotaFormService);
    periodoLancamentoNotaService = TestBed.inject(PeriodoLancamentoNotaService);
    userService = TestBed.inject(UserService);
    classeService = TestBed.inject(ClasseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const periodoLancamentoNota: IPeriodoLancamentoNota = { id: 456 };
      const utilizador: IUser = { id: 44039 };
      periodoLancamentoNota.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 76835 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ periodoLancamentoNota });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Classe query and add missing value', () => {
      const periodoLancamentoNota: IPeriodoLancamentoNota = { id: 456 };
      const classes: IClasse[] = [{ id: 14767 }];
      periodoLancamentoNota.classes = classes;

      const classeCollection: IClasse[] = [{ id: 32954 }];
      jest.spyOn(classeService, 'query').mockReturnValue(of(new HttpResponse({ body: classeCollection })));
      const additionalClasses = [...classes];
      const expectedCollection: IClasse[] = [...additionalClasses, ...classeCollection];
      jest.spyOn(classeService, 'addClasseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ periodoLancamentoNota });
      comp.ngOnInit();

      expect(classeService.query).toHaveBeenCalled();
      expect(classeService.addClasseToCollectionIfMissing).toHaveBeenCalledWith(
        classeCollection,
        ...additionalClasses.map(expect.objectContaining)
      );
      expect(comp.classesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const periodoLancamentoNota: IPeriodoLancamentoNota = { id: 456 };
      const utilizador: IUser = { id: 96638 };
      periodoLancamentoNota.utilizador = utilizador;
      const classe: IClasse = { id: 39259 };
      periodoLancamentoNota.classes = [classe];

      activatedRoute.data = of({ periodoLancamentoNota });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.classesSharedCollection).toContain(classe);
      expect(comp.periodoLancamentoNota).toEqual(periodoLancamentoNota);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPeriodoLancamentoNota>>();
      const periodoLancamentoNota = { id: 123 };
      jest.spyOn(periodoLancamentoNotaFormService, 'getPeriodoLancamentoNota').mockReturnValue(periodoLancamentoNota);
      jest.spyOn(periodoLancamentoNotaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ periodoLancamentoNota });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: periodoLancamentoNota }));
      saveSubject.complete();

      // THEN
      expect(periodoLancamentoNotaFormService.getPeriodoLancamentoNota).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(periodoLancamentoNotaService.update).toHaveBeenCalledWith(expect.objectContaining(periodoLancamentoNota));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPeriodoLancamentoNota>>();
      const periodoLancamentoNota = { id: 123 };
      jest.spyOn(periodoLancamentoNotaFormService, 'getPeriodoLancamentoNota').mockReturnValue({ id: null });
      jest.spyOn(periodoLancamentoNotaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ periodoLancamentoNota: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: periodoLancamentoNota }));
      saveSubject.complete();

      // THEN
      expect(periodoLancamentoNotaFormService.getPeriodoLancamentoNota).toHaveBeenCalled();
      expect(periodoLancamentoNotaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPeriodoLancamentoNota>>();
      const periodoLancamentoNota = { id: 123 };
      jest.spyOn(periodoLancamentoNotaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ periodoLancamentoNota });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(periodoLancamentoNotaService.update).toHaveBeenCalled();
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

    describe('compareClasse', () => {
      it('Should forward to classeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(classeService, 'compareClasse');
        comp.compareClasse(entity, entity2);
        expect(classeService.compareClasse).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
