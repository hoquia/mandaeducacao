import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TransacaoFormService } from './transacao-form.service';
import { TransacaoService } from '../service/transacao.service';
import { ITransacao } from '../transacao.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { MatriculaService } from 'app/entities/matricula/service/matricula.service';
import { IMeioPagamento } from 'app/entities/meio-pagamento/meio-pagamento.model';
import { MeioPagamentoService } from 'app/entities/meio-pagamento/service/meio-pagamento.service';
import { IConta } from 'app/entities/conta/conta.model';
import { ContaService } from 'app/entities/conta/service/conta.service';

import { TransacaoUpdateComponent } from './transacao-update.component';

describe('Transacao Management Update Component', () => {
  let comp: TransacaoUpdateComponent;
  let fixture: ComponentFixture<TransacaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transacaoFormService: TransacaoFormService;
  let transacaoService: TransacaoService;
  let userService: UserService;
  let lookupItemService: LookupItemService;
  let matriculaService: MatriculaService;
  let meioPagamentoService: MeioPagamentoService;
  let contaService: ContaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TransacaoUpdateComponent],
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
      .overrideTemplate(TransacaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransacaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transacaoFormService = TestBed.inject(TransacaoFormService);
    transacaoService = TestBed.inject(TransacaoService);
    userService = TestBed.inject(UserService);
    lookupItemService = TestBed.inject(LookupItemService);
    matriculaService = TestBed.inject(MatriculaService);
    meioPagamentoService = TestBed.inject(MeioPagamentoService);
    contaService = TestBed.inject(ContaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const transacao: ITransacao = { id: 456 };
      const utilizador: IUser = { id: 75590 };
      transacao.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 44704 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transacao });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LookupItem query and add missing value', () => {
      const transacao: ITransacao = { id: 456 };
      const moeda: ILookupItem = { id: 10853 };
      transacao.moeda = moeda;

      const lookupItemCollection: ILookupItem[] = [{ id: 84625 }];
      jest.spyOn(lookupItemService, 'query').mockReturnValue(of(new HttpResponse({ body: lookupItemCollection })));
      const additionalLookupItems = [moeda];
      const expectedCollection: ILookupItem[] = [...additionalLookupItems, ...lookupItemCollection];
      jest.spyOn(lookupItemService, 'addLookupItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transacao });
      comp.ngOnInit();

      expect(lookupItemService.query).toHaveBeenCalled();
      expect(lookupItemService.addLookupItemToCollectionIfMissing).toHaveBeenCalledWith(
        lookupItemCollection,
        ...additionalLookupItems.map(expect.objectContaining)
      );
      expect(comp.lookupItemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Matricula query and add missing value', () => {
      const transacao: ITransacao = { id: 456 };
      const matricula: IMatricula = { id: 13436 };
      transacao.matricula = matricula;

      const matriculaCollection: IMatricula[] = [{ id: 87720 }];
      jest.spyOn(matriculaService, 'query').mockReturnValue(of(new HttpResponse({ body: matriculaCollection })));
      const additionalMatriculas = [matricula];
      const expectedCollection: IMatricula[] = [...additionalMatriculas, ...matriculaCollection];
      jest.spyOn(matriculaService, 'addMatriculaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transacao });
      comp.ngOnInit();

      expect(matriculaService.query).toHaveBeenCalled();
      expect(matriculaService.addMatriculaToCollectionIfMissing).toHaveBeenCalledWith(
        matriculaCollection,
        ...additionalMatriculas.map(expect.objectContaining)
      );
      expect(comp.matriculasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call MeioPagamento query and add missing value', () => {
      const transacao: ITransacao = { id: 456 };
      const meioPagamento: IMeioPagamento = { id: 91792 };
      transacao.meioPagamento = meioPagamento;

      const meioPagamentoCollection: IMeioPagamento[] = [{ id: 56632 }];
      jest.spyOn(meioPagamentoService, 'query').mockReturnValue(of(new HttpResponse({ body: meioPagamentoCollection })));
      const additionalMeioPagamentos = [meioPagamento];
      const expectedCollection: IMeioPagamento[] = [...additionalMeioPagamentos, ...meioPagamentoCollection];
      jest.spyOn(meioPagamentoService, 'addMeioPagamentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transacao });
      comp.ngOnInit();

      expect(meioPagamentoService.query).toHaveBeenCalled();
      expect(meioPagamentoService.addMeioPagamentoToCollectionIfMissing).toHaveBeenCalledWith(
        meioPagamentoCollection,
        ...additionalMeioPagamentos.map(expect.objectContaining)
      );
      expect(comp.meioPagamentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Conta query and add missing value', () => {
      const transacao: ITransacao = { id: 456 };
      const conta: IConta = { id: 7265 };
      transacao.conta = conta;

      const contaCollection: IConta[] = [{ id: 93650 }];
      jest.spyOn(contaService, 'query').mockReturnValue(of(new HttpResponse({ body: contaCollection })));
      const additionalContas = [conta];
      const expectedCollection: IConta[] = [...additionalContas, ...contaCollection];
      jest.spyOn(contaService, 'addContaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transacao });
      comp.ngOnInit();

      expect(contaService.query).toHaveBeenCalled();
      expect(contaService.addContaToCollectionIfMissing).toHaveBeenCalledWith(
        contaCollection,
        ...additionalContas.map(expect.objectContaining)
      );
      expect(comp.contasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const transacao: ITransacao = { id: 456 };
      const utilizador: IUser = { id: 99604 };
      transacao.utilizador = utilizador;
      const moeda: ILookupItem = { id: 79599 };
      transacao.moeda = moeda;
      const matricula: IMatricula = { id: 20273 };
      transacao.matricula = matricula;
      const meioPagamento: IMeioPagamento = { id: 48068 };
      transacao.meioPagamento = meioPagamento;
      const conta: IConta = { id: 15942 };
      transacao.conta = conta;

      activatedRoute.data = of({ transacao });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.lookupItemsSharedCollection).toContain(moeda);
      expect(comp.matriculasSharedCollection).toContain(matricula);
      expect(comp.meioPagamentosSharedCollection).toContain(meioPagamento);
      expect(comp.contasSharedCollection).toContain(conta);
      expect(comp.transacao).toEqual(transacao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransacao>>();
      const transacao = { id: 123 };
      jest.spyOn(transacaoFormService, 'getTransacao').mockReturnValue(transacao);
      jest.spyOn(transacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transacao }));
      saveSubject.complete();

      // THEN
      expect(transacaoFormService.getTransacao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(transacaoService.update).toHaveBeenCalledWith(expect.objectContaining(transacao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransacao>>();
      const transacao = { id: 123 };
      jest.spyOn(transacaoFormService, 'getTransacao').mockReturnValue({ id: null });
      jest.spyOn(transacaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transacao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transacao }));
      saveSubject.complete();

      // THEN
      expect(transacaoFormService.getTransacao).toHaveBeenCalled();
      expect(transacaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransacao>>();
      const transacao = { id: 123 };
      jest.spyOn(transacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transacaoService.update).toHaveBeenCalled();
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

    describe('compareMeioPagamento', () => {
      it('Should forward to meioPagamentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(meioPagamentoService, 'compareMeioPagamento');
        comp.compareMeioPagamento(entity, entity2);
        expect(meioPagamentoService.compareMeioPagamento).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareConta', () => {
      it('Should forward to contaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(contaService, 'compareConta');
        comp.compareConta(entity, entity2);
        expect(contaService.compareConta).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
