import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TransferenciaSaldoFormService } from './transferencia-saldo-form.service';
import { TransferenciaSaldoService } from '../service/transferencia-saldo.service';
import { ITransferenciaSaldo } from '../transferencia-saldo.model';
import { IDiscente } from 'app/entities/discente/discente.model';
import { DiscenteService } from 'app/entities/discente/service/discente.service';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { ITransacao } from 'app/entities/transacao/transacao.model';
import { TransacaoService } from 'app/entities/transacao/service/transacao.service';

import { TransferenciaSaldoUpdateComponent } from './transferencia-saldo-update.component';

describe('TransferenciaSaldo Management Update Component', () => {
  let comp: TransferenciaSaldoUpdateComponent;
  let fixture: ComponentFixture<TransferenciaSaldoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transferenciaSaldoFormService: TransferenciaSaldoFormService;
  let transferenciaSaldoService: TransferenciaSaldoService;
  let discenteService: DiscenteService;
  let userService: UserService;
  let lookupItemService: LookupItemService;
  let transacaoService: TransacaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TransferenciaSaldoUpdateComponent],
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
      .overrideTemplate(TransferenciaSaldoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransferenciaSaldoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transferenciaSaldoFormService = TestBed.inject(TransferenciaSaldoFormService);
    transferenciaSaldoService = TestBed.inject(TransferenciaSaldoService);
    discenteService = TestBed.inject(DiscenteService);
    userService = TestBed.inject(UserService);
    lookupItemService = TestBed.inject(LookupItemService);
    transacaoService = TestBed.inject(TransacaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Discente query and add missing value', () => {
      const transferenciaSaldo: ITransferenciaSaldo = { id: 456 };
      const de: IDiscente = { id: 7218 };
      transferenciaSaldo.de = de;
      const para: IDiscente = { id: 17006 };
      transferenciaSaldo.para = para;

      const discenteCollection: IDiscente[] = [{ id: 42298 }];
      jest.spyOn(discenteService, 'query').mockReturnValue(of(new HttpResponse({ body: discenteCollection })));
      const additionalDiscentes = [de, para];
      const expectedCollection: IDiscente[] = [...additionalDiscentes, ...discenteCollection];
      jest.spyOn(discenteService, 'addDiscenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transferenciaSaldo });
      comp.ngOnInit();

      expect(discenteService.query).toHaveBeenCalled();
      expect(discenteService.addDiscenteToCollectionIfMissing).toHaveBeenCalledWith(
        discenteCollection,
        ...additionalDiscentes.map(expect.objectContaining)
      );
      expect(comp.discentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call User query and add missing value', () => {
      const transferenciaSaldo: ITransferenciaSaldo = { id: 456 };
      const utilizador: IUser = { id: 61073 };
      transferenciaSaldo.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 98090 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transferenciaSaldo });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LookupItem query and add missing value', () => {
      const transferenciaSaldo: ITransferenciaSaldo = { id: 456 };
      const motivoTransferencia: ILookupItem = { id: 87040 };
      transferenciaSaldo.motivoTransferencia = motivoTransferencia;

      const lookupItemCollection: ILookupItem[] = [{ id: 74776 }];
      jest.spyOn(lookupItemService, 'query').mockReturnValue(of(new HttpResponse({ body: lookupItemCollection })));
      const additionalLookupItems = [motivoTransferencia];
      const expectedCollection: ILookupItem[] = [...additionalLookupItems, ...lookupItemCollection];
      jest.spyOn(lookupItemService, 'addLookupItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transferenciaSaldo });
      comp.ngOnInit();

      expect(lookupItemService.query).toHaveBeenCalled();
      expect(lookupItemService.addLookupItemToCollectionIfMissing).toHaveBeenCalledWith(
        lookupItemCollection,
        ...additionalLookupItems.map(expect.objectContaining)
      );
      expect(comp.lookupItemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Transacao query and add missing value', () => {
      const transferenciaSaldo: ITransferenciaSaldo = { id: 456 };
      const transacoes: ITransacao[] = [{ id: 32487 }];
      transferenciaSaldo.transacoes = transacoes;

      const transacaoCollection: ITransacao[] = [{ id: 54002 }];
      jest.spyOn(transacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: transacaoCollection })));
      const additionalTransacaos = [...transacoes];
      const expectedCollection: ITransacao[] = [...additionalTransacaos, ...transacaoCollection];
      jest.spyOn(transacaoService, 'addTransacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transferenciaSaldo });
      comp.ngOnInit();

      expect(transacaoService.query).toHaveBeenCalled();
      expect(transacaoService.addTransacaoToCollectionIfMissing).toHaveBeenCalledWith(
        transacaoCollection,
        ...additionalTransacaos.map(expect.objectContaining)
      );
      expect(comp.transacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const transferenciaSaldo: ITransferenciaSaldo = { id: 456 };
      const de: IDiscente = { id: 90688 };
      transferenciaSaldo.de = de;
      const para: IDiscente = { id: 25519 };
      transferenciaSaldo.para = para;
      const utilizador: IUser = { id: 37553 };
      transferenciaSaldo.utilizador = utilizador;
      const motivoTransferencia: ILookupItem = { id: 25727 };
      transferenciaSaldo.motivoTransferencia = motivoTransferencia;
      const transacoes: ITransacao = { id: 45117 };
      transferenciaSaldo.transacoes = [transacoes];

      activatedRoute.data = of({ transferenciaSaldo });
      comp.ngOnInit();

      expect(comp.discentesSharedCollection).toContain(de);
      expect(comp.discentesSharedCollection).toContain(para);
      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.lookupItemsSharedCollection).toContain(motivoTransferencia);
      expect(comp.transacaosSharedCollection).toContain(transacoes);
      expect(comp.transferenciaSaldo).toEqual(transferenciaSaldo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransferenciaSaldo>>();
      const transferenciaSaldo = { id: 123 };
      jest.spyOn(transferenciaSaldoFormService, 'getTransferenciaSaldo').mockReturnValue(transferenciaSaldo);
      jest.spyOn(transferenciaSaldoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transferenciaSaldo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transferenciaSaldo }));
      saveSubject.complete();

      // THEN
      expect(transferenciaSaldoFormService.getTransferenciaSaldo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(transferenciaSaldoService.update).toHaveBeenCalledWith(expect.objectContaining(transferenciaSaldo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransferenciaSaldo>>();
      const transferenciaSaldo = { id: 123 };
      jest.spyOn(transferenciaSaldoFormService, 'getTransferenciaSaldo').mockReturnValue({ id: null });
      jest.spyOn(transferenciaSaldoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transferenciaSaldo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transferenciaSaldo }));
      saveSubject.complete();

      // THEN
      expect(transferenciaSaldoFormService.getTransferenciaSaldo).toHaveBeenCalled();
      expect(transferenciaSaldoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransferenciaSaldo>>();
      const transferenciaSaldo = { id: 123 };
      jest.spyOn(transferenciaSaldoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transferenciaSaldo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transferenciaSaldoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDiscente', () => {
      it('Should forward to discenteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(discenteService, 'compareDiscente');
        comp.compareDiscente(entity, entity2);
        expect(discenteService.compareDiscente).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareTransacao', () => {
      it('Should forward to transacaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(transacaoService, 'compareTransacao');
        comp.compareTransacao(entity, entity2);
        expect(transacaoService.compareTransacao).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
