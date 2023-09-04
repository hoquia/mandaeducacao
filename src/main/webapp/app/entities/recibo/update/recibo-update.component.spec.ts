import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReciboFormService } from './recibo-form.service';
import { ReciboService } from '../service/recibo.service';
import { IRecibo } from '../recibo.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { MatriculaService } from 'app/entities/matricula/service/matricula.service';
import { IDocumentoComercial } from 'app/entities/documento-comercial/documento-comercial.model';
import { DocumentoComercialService } from 'app/entities/documento-comercial/service/documento-comercial.service';
import { ITransacao } from 'app/entities/transacao/transacao.model';
import { TransacaoService } from 'app/entities/transacao/service/transacao.service';

import { ReciboUpdateComponent } from './recibo-update.component';

describe('Recibo Management Update Component', () => {
  let comp: ReciboUpdateComponent;
  let fixture: ComponentFixture<ReciboUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let reciboFormService: ReciboFormService;
  let reciboService: ReciboService;
  let userService: UserService;
  let matriculaService: MatriculaService;
  let documentoComercialService: DocumentoComercialService;
  let transacaoService: TransacaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReciboUpdateComponent],
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
      .overrideTemplate(ReciboUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReciboUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    reciboFormService = TestBed.inject(ReciboFormService);
    reciboService = TestBed.inject(ReciboService);
    userService = TestBed.inject(UserService);
    matriculaService = TestBed.inject(MatriculaService);
    documentoComercialService = TestBed.inject(DocumentoComercialService);
    transacaoService = TestBed.inject(TransacaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const recibo: IRecibo = { id: 456 };
      const utilizador: IUser = { id: 55649 };
      recibo.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 15427 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ recibo });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Matricula query and add missing value', () => {
      const recibo: IRecibo = { id: 456 };
      const matricula: IMatricula = { id: 31493 };
      recibo.matricula = matricula;

      const matriculaCollection: IMatricula[] = [{ id: 98258 }];
      jest.spyOn(matriculaService, 'query').mockReturnValue(of(new HttpResponse({ body: matriculaCollection })));
      const additionalMatriculas = [matricula];
      const expectedCollection: IMatricula[] = [...additionalMatriculas, ...matriculaCollection];
      jest.spyOn(matriculaService, 'addMatriculaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ recibo });
      comp.ngOnInit();

      expect(matriculaService.query).toHaveBeenCalled();
      expect(matriculaService.addMatriculaToCollectionIfMissing).toHaveBeenCalledWith(
        matriculaCollection,
        ...additionalMatriculas.map(expect.objectContaining)
      );
      expect(comp.matriculasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DocumentoComercial query and add missing value', () => {
      const recibo: IRecibo = { id: 456 };
      const documentoComercial: IDocumentoComercial = { id: 38338 };
      recibo.documentoComercial = documentoComercial;

      const documentoComercialCollection: IDocumentoComercial[] = [{ id: 19795 }];
      jest.spyOn(documentoComercialService, 'query').mockReturnValue(of(new HttpResponse({ body: documentoComercialCollection })));
      const additionalDocumentoComercials = [documentoComercial];
      const expectedCollection: IDocumentoComercial[] = [...additionalDocumentoComercials, ...documentoComercialCollection];
      jest.spyOn(documentoComercialService, 'addDocumentoComercialToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ recibo });
      comp.ngOnInit();

      expect(documentoComercialService.query).toHaveBeenCalled();
      expect(documentoComercialService.addDocumentoComercialToCollectionIfMissing).toHaveBeenCalledWith(
        documentoComercialCollection,
        ...additionalDocumentoComercials.map(expect.objectContaining)
      );
      expect(comp.documentoComercialsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Transacao query and add missing value', () => {
      const recibo: IRecibo = { id: 456 };
      const transacao: ITransacao = { id: 85274 };
      recibo.transacao = transacao;

      const transacaoCollection: ITransacao[] = [{ id: 82331 }];
      jest.spyOn(transacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: transacaoCollection })));
      const additionalTransacaos = [transacao];
      const expectedCollection: ITransacao[] = [...additionalTransacaos, ...transacaoCollection];
      jest.spyOn(transacaoService, 'addTransacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ recibo });
      comp.ngOnInit();

      expect(transacaoService.query).toHaveBeenCalled();
      expect(transacaoService.addTransacaoToCollectionIfMissing).toHaveBeenCalledWith(
        transacaoCollection,
        ...additionalTransacaos.map(expect.objectContaining)
      );
      expect(comp.transacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const recibo: IRecibo = { id: 456 };
      const utilizador: IUser = { id: 17992 };
      recibo.utilizador = utilizador;
      const matricula: IMatricula = { id: 58327 };
      recibo.matricula = matricula;
      const documentoComercial: IDocumentoComercial = { id: 62181 };
      recibo.documentoComercial = documentoComercial;
      const transacao: ITransacao = { id: 38077 };
      recibo.transacao = transacao;

      activatedRoute.data = of({ recibo });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.matriculasSharedCollection).toContain(matricula);
      expect(comp.documentoComercialsSharedCollection).toContain(documentoComercial);
      expect(comp.transacaosSharedCollection).toContain(transacao);
      expect(comp.recibo).toEqual(recibo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRecibo>>();
      const recibo = { id: 123 };
      jest.spyOn(reciboFormService, 'getRecibo').mockReturnValue(recibo);
      jest.spyOn(reciboService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recibo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: recibo }));
      saveSubject.complete();

      // THEN
      expect(reciboFormService.getRecibo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(reciboService.update).toHaveBeenCalledWith(expect.objectContaining(recibo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRecibo>>();
      const recibo = { id: 123 };
      jest.spyOn(reciboFormService, 'getRecibo').mockReturnValue({ id: null });
      jest.spyOn(reciboService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recibo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: recibo }));
      saveSubject.complete();

      // THEN
      expect(reciboFormService.getRecibo).toHaveBeenCalled();
      expect(reciboService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRecibo>>();
      const recibo = { id: 123 };
      jest.spyOn(reciboService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recibo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(reciboService.update).toHaveBeenCalled();
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

    describe('compareMatricula', () => {
      it('Should forward to matriculaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(matriculaService, 'compareMatricula');
        comp.compareMatricula(entity, entity2);
        expect(matriculaService.compareMatricula).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDocumentoComercial', () => {
      it('Should forward to documentoComercialService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(documentoComercialService, 'compareDocumentoComercial');
        comp.compareDocumentoComercial(entity, entity2);
        expect(documentoComercialService.compareDocumentoComercial).toHaveBeenCalledWith(entity, entity2);
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
