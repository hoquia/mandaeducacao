import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TransferenciaTurmaFormService } from './transferencia-turma-form.service';
import { TransferenciaTurmaService } from '../service/transferencia-turma.service';
import { ITransferenciaTurma } from '../transferencia-turma.model';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { MatriculaService } from 'app/entities/matricula/service/matricula.service';

import { TransferenciaTurmaUpdateComponent } from './transferencia-turma-update.component';

describe('TransferenciaTurma Management Update Component', () => {
  let comp: TransferenciaTurmaUpdateComponent;
  let fixture: ComponentFixture<TransferenciaTurmaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transferenciaTurmaFormService: TransferenciaTurmaFormService;
  let transferenciaTurmaService: TransferenciaTurmaService;
  let turmaService: TurmaService;
  let userService: UserService;
  let lookupItemService: LookupItemService;
  let matriculaService: MatriculaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TransferenciaTurmaUpdateComponent],
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
      .overrideTemplate(TransferenciaTurmaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransferenciaTurmaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transferenciaTurmaFormService = TestBed.inject(TransferenciaTurmaFormService);
    transferenciaTurmaService = TestBed.inject(TransferenciaTurmaService);
    turmaService = TestBed.inject(TurmaService);
    userService = TestBed.inject(UserService);
    lookupItemService = TestBed.inject(LookupItemService);
    matriculaService = TestBed.inject(MatriculaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Turma query and add missing value', () => {
      const transferenciaTurma: ITransferenciaTurma = { id: 456 };
      const de: ITurma = { id: 20625 };
      transferenciaTurma.de = de;
      const para: ITurma = { id: 6512 };
      transferenciaTurma.para = para;

      const turmaCollection: ITurma[] = [{ id: 4707 }];
      jest.spyOn(turmaService, 'query').mockReturnValue(of(new HttpResponse({ body: turmaCollection })));
      const additionalTurmas = [de, para];
      const expectedCollection: ITurma[] = [...additionalTurmas, ...turmaCollection];
      jest.spyOn(turmaService, 'addTurmaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transferenciaTurma });
      comp.ngOnInit();

      expect(turmaService.query).toHaveBeenCalled();
      expect(turmaService.addTurmaToCollectionIfMissing).toHaveBeenCalledWith(
        turmaCollection,
        ...additionalTurmas.map(expect.objectContaining)
      );
      expect(comp.turmasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call User query and add missing value', () => {
      const transferenciaTurma: ITransferenciaTurma = { id: 456 };
      const utilizador: IUser = { id: 93150 };
      transferenciaTurma.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 40062 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transferenciaTurma });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LookupItem query and add missing value', () => {
      const transferenciaTurma: ITransferenciaTurma = { id: 456 };
      const motivoTransferencia: ILookupItem = { id: 60224 };
      transferenciaTurma.motivoTransferencia = motivoTransferencia;

      const lookupItemCollection: ILookupItem[] = [{ id: 84302 }];
      jest.spyOn(lookupItemService, 'query').mockReturnValue(of(new HttpResponse({ body: lookupItemCollection })));
      const additionalLookupItems = [motivoTransferencia];
      const expectedCollection: ILookupItem[] = [...additionalLookupItems, ...lookupItemCollection];
      jest.spyOn(lookupItemService, 'addLookupItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transferenciaTurma });
      comp.ngOnInit();

      expect(lookupItemService.query).toHaveBeenCalled();
      expect(lookupItemService.addLookupItemToCollectionIfMissing).toHaveBeenCalledWith(
        lookupItemCollection,
        ...additionalLookupItems.map(expect.objectContaining)
      );
      expect(comp.lookupItemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Matricula query and add missing value', () => {
      const transferenciaTurma: ITransferenciaTurma = { id: 456 };
      const matricula: IMatricula = { id: 39574 };
      transferenciaTurma.matricula = matricula;

      const matriculaCollection: IMatricula[] = [{ id: 60892 }];
      jest.spyOn(matriculaService, 'query').mockReturnValue(of(new HttpResponse({ body: matriculaCollection })));
      const additionalMatriculas = [matricula];
      const expectedCollection: IMatricula[] = [...additionalMatriculas, ...matriculaCollection];
      jest.spyOn(matriculaService, 'addMatriculaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transferenciaTurma });
      comp.ngOnInit();

      expect(matriculaService.query).toHaveBeenCalled();
      expect(matriculaService.addMatriculaToCollectionIfMissing).toHaveBeenCalledWith(
        matriculaCollection,
        ...additionalMatriculas.map(expect.objectContaining)
      );
      expect(comp.matriculasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const transferenciaTurma: ITransferenciaTurma = { id: 456 };
      const de: ITurma = { id: 98011 };
      transferenciaTurma.de = de;
      const para: ITurma = { id: 68812 };
      transferenciaTurma.para = para;
      const utilizador: IUser = { id: 97176 };
      transferenciaTurma.utilizador = utilizador;
      const motivoTransferencia: ILookupItem = { id: 422 };
      transferenciaTurma.motivoTransferencia = motivoTransferencia;
      const matricula: IMatricula = { id: 45427 };
      transferenciaTurma.matricula = matricula;

      activatedRoute.data = of({ transferenciaTurma });
      comp.ngOnInit();

      expect(comp.turmasSharedCollection).toContain(de);
      expect(comp.turmasSharedCollection).toContain(para);
      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.lookupItemsSharedCollection).toContain(motivoTransferencia);
      expect(comp.matriculasSharedCollection).toContain(matricula);
      expect(comp.transferenciaTurma).toEqual(transferenciaTurma);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransferenciaTurma>>();
      const transferenciaTurma = { id: 123 };
      jest.spyOn(transferenciaTurmaFormService, 'getTransferenciaTurma').mockReturnValue(transferenciaTurma);
      jest.spyOn(transferenciaTurmaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transferenciaTurma });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transferenciaTurma }));
      saveSubject.complete();

      // THEN
      expect(transferenciaTurmaFormService.getTransferenciaTurma).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(transferenciaTurmaService.update).toHaveBeenCalledWith(expect.objectContaining(transferenciaTurma));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransferenciaTurma>>();
      const transferenciaTurma = { id: 123 };
      jest.spyOn(transferenciaTurmaFormService, 'getTransferenciaTurma').mockReturnValue({ id: null });
      jest.spyOn(transferenciaTurmaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transferenciaTurma: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transferenciaTurma }));
      saveSubject.complete();

      // THEN
      expect(transferenciaTurmaFormService.getTransferenciaTurma).toHaveBeenCalled();
      expect(transferenciaTurmaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransferenciaTurma>>();
      const transferenciaTurma = { id: 123 };
      jest.spyOn(transferenciaTurmaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transferenciaTurma });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transferenciaTurmaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTurma', () => {
      it('Should forward to turmaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(turmaService, 'compareTurma');
        comp.compareTurma(entity, entity2);
        expect(turmaService.compareTurma).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareLookupItem', () => {
      it('Should forward to lookupItemService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(lookupItemService, 'compareLookupItem');
        comp.compareLookupItem(entity, entity2);
        expect(lookupItemService.compareLookupItem).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMatricula', () => {
      it('Should forward to matriculaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(matriculaService, 'compareMatricula');
        comp.compareMatricula(entity, entity2);
        expect(matriculaService.compareMatricula).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
