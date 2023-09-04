import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OcorrenciaFormService } from './ocorrencia-form.service';
import { OcorrenciaService } from '../service/ocorrencia.service';
import { IOcorrencia } from '../ocorrencia.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { MatriculaService } from 'app/entities/matricula/service/matricula.service';
import { ICategoriaOcorrencia } from 'app/entities/categoria-ocorrencia/categoria-ocorrencia.model';
import { CategoriaOcorrenciaService } from 'app/entities/categoria-ocorrencia/service/categoria-ocorrencia.service';
import { ILicao } from 'app/entities/licao/licao.model';
import { LicaoService } from 'app/entities/licao/service/licao.service';

import { OcorrenciaUpdateComponent } from './ocorrencia-update.component';

describe('Ocorrencia Management Update Component', () => {
  let comp: OcorrenciaUpdateComponent;
  let fixture: ComponentFixture<OcorrenciaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ocorrenciaFormService: OcorrenciaFormService;
  let ocorrenciaService: OcorrenciaService;
  let userService: UserService;
  let docenteService: DocenteService;
  let matriculaService: MatriculaService;
  let categoriaOcorrenciaService: CategoriaOcorrenciaService;
  let licaoService: LicaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OcorrenciaUpdateComponent],
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
      .overrideTemplate(OcorrenciaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OcorrenciaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ocorrenciaFormService = TestBed.inject(OcorrenciaFormService);
    ocorrenciaService = TestBed.inject(OcorrenciaService);
    userService = TestBed.inject(UserService);
    docenteService = TestBed.inject(DocenteService);
    matriculaService = TestBed.inject(MatriculaService);
    categoriaOcorrenciaService = TestBed.inject(CategoriaOcorrenciaService);
    licaoService = TestBed.inject(LicaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Ocorrencia query and add missing value', () => {
      const ocorrencia: IOcorrencia = { id: 456 };
      const referencia: IOcorrencia = { id: 962 };
      ocorrencia.referencia = referencia;

      const ocorrenciaCollection: IOcorrencia[] = [{ id: 92384 }];
      jest.spyOn(ocorrenciaService, 'query').mockReturnValue(of(new HttpResponse({ body: ocorrenciaCollection })));
      const additionalOcorrencias = [referencia];
      const expectedCollection: IOcorrencia[] = [...additionalOcorrencias, ...ocorrenciaCollection];
      jest.spyOn(ocorrenciaService, 'addOcorrenciaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ocorrencia });
      comp.ngOnInit();

      expect(ocorrenciaService.query).toHaveBeenCalled();
      expect(ocorrenciaService.addOcorrenciaToCollectionIfMissing).toHaveBeenCalledWith(
        ocorrenciaCollection,
        ...additionalOcorrencias.map(expect.objectContaining)
      );
      expect(comp.ocorrenciasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call User query and add missing value', () => {
      const ocorrencia: IOcorrencia = { id: 456 };
      const utilizador: IUser = { id: 2031 };
      ocorrencia.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 75736 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ocorrencia });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Docente query and add missing value', () => {
      const ocorrencia: IOcorrencia = { id: 456 };
      const docente: IDocente = { id: 44295 };
      ocorrencia.docente = docente;

      const docenteCollection: IDocente[] = [{ id: 94256 }];
      jest.spyOn(docenteService, 'query').mockReturnValue(of(new HttpResponse({ body: docenteCollection })));
      const additionalDocentes = [docente];
      const expectedCollection: IDocente[] = [...additionalDocentes, ...docenteCollection];
      jest.spyOn(docenteService, 'addDocenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ocorrencia });
      comp.ngOnInit();

      expect(docenteService.query).toHaveBeenCalled();
      expect(docenteService.addDocenteToCollectionIfMissing).toHaveBeenCalledWith(
        docenteCollection,
        ...additionalDocentes.map(expect.objectContaining)
      );
      expect(comp.docentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Matricula query and add missing value', () => {
      const ocorrencia: IOcorrencia = { id: 456 };
      const matricula: IMatricula = { id: 79571 };
      ocorrencia.matricula = matricula;

      const matriculaCollection: IMatricula[] = [{ id: 6055 }];
      jest.spyOn(matriculaService, 'query').mockReturnValue(of(new HttpResponse({ body: matriculaCollection })));
      const additionalMatriculas = [matricula];
      const expectedCollection: IMatricula[] = [...additionalMatriculas, ...matriculaCollection];
      jest.spyOn(matriculaService, 'addMatriculaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ocorrencia });
      comp.ngOnInit();

      expect(matriculaService.query).toHaveBeenCalled();
      expect(matriculaService.addMatriculaToCollectionIfMissing).toHaveBeenCalledWith(
        matriculaCollection,
        ...additionalMatriculas.map(expect.objectContaining)
      );
      expect(comp.matriculasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CategoriaOcorrencia query and add missing value', () => {
      const ocorrencia: IOcorrencia = { id: 456 };
      const estado: ICategoriaOcorrencia = { id: 49424 };
      ocorrencia.estado = estado;

      const categoriaOcorrenciaCollection: ICategoriaOcorrencia[] = [{ id: 62104 }];
      jest.spyOn(categoriaOcorrenciaService, 'query').mockReturnValue(of(new HttpResponse({ body: categoriaOcorrenciaCollection })));
      const additionalCategoriaOcorrencias = [estado];
      const expectedCollection: ICategoriaOcorrencia[] = [...additionalCategoriaOcorrencias, ...categoriaOcorrenciaCollection];
      jest.spyOn(categoriaOcorrenciaService, 'addCategoriaOcorrenciaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ocorrencia });
      comp.ngOnInit();

      expect(categoriaOcorrenciaService.query).toHaveBeenCalled();
      expect(categoriaOcorrenciaService.addCategoriaOcorrenciaToCollectionIfMissing).toHaveBeenCalledWith(
        categoriaOcorrenciaCollection,
        ...additionalCategoriaOcorrencias.map(expect.objectContaining)
      );
      expect(comp.categoriaOcorrenciasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Licao query and add missing value', () => {
      const ocorrencia: IOcorrencia = { id: 456 };
      const licao: ILicao = { id: 46272 };
      ocorrencia.licao = licao;

      const licaoCollection: ILicao[] = [{ id: 88941 }];
      jest.spyOn(licaoService, 'query').mockReturnValue(of(new HttpResponse({ body: licaoCollection })));
      const additionalLicaos = [licao];
      const expectedCollection: ILicao[] = [...additionalLicaos, ...licaoCollection];
      jest.spyOn(licaoService, 'addLicaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ocorrencia });
      comp.ngOnInit();

      expect(licaoService.query).toHaveBeenCalled();
      expect(licaoService.addLicaoToCollectionIfMissing).toHaveBeenCalledWith(
        licaoCollection,
        ...additionalLicaos.map(expect.objectContaining)
      );
      expect(comp.licaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const ocorrencia: IOcorrencia = { id: 456 };
      const referencia: IOcorrencia = { id: 46761 };
      ocorrencia.referencia = referencia;
      const utilizador: IUser = { id: 79428 };
      ocorrencia.utilizador = utilizador;
      const docente: IDocente = { id: 14570 };
      ocorrencia.docente = docente;
      const matricula: IMatricula = { id: 68744 };
      ocorrencia.matricula = matricula;
      const estado: ICategoriaOcorrencia = { id: 54459 };
      ocorrencia.estado = estado;
      const licao: ILicao = { id: 38445 };
      ocorrencia.licao = licao;

      activatedRoute.data = of({ ocorrencia });
      comp.ngOnInit();

      expect(comp.ocorrenciasSharedCollection).toContain(referencia);
      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.docentesSharedCollection).toContain(docente);
      expect(comp.matriculasSharedCollection).toContain(matricula);
      expect(comp.categoriaOcorrenciasSharedCollection).toContain(estado);
      expect(comp.licaosSharedCollection).toContain(licao);
      expect(comp.ocorrencia).toEqual(ocorrencia);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOcorrencia>>();
      const ocorrencia = { id: 123 };
      jest.spyOn(ocorrenciaFormService, 'getOcorrencia').mockReturnValue(ocorrencia);
      jest.spyOn(ocorrenciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ocorrencia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ocorrencia }));
      saveSubject.complete();

      // THEN
      expect(ocorrenciaFormService.getOcorrencia).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(ocorrenciaService.update).toHaveBeenCalledWith(expect.objectContaining(ocorrencia));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOcorrencia>>();
      const ocorrencia = { id: 123 };
      jest.spyOn(ocorrenciaFormService, 'getOcorrencia').mockReturnValue({ id: null });
      jest.spyOn(ocorrenciaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ocorrencia: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ocorrencia }));
      saveSubject.complete();

      // THEN
      expect(ocorrenciaFormService.getOcorrencia).toHaveBeenCalled();
      expect(ocorrenciaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOcorrencia>>();
      const ocorrencia = { id: 123 };
      jest.spyOn(ocorrenciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ocorrencia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ocorrenciaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareOcorrencia', () => {
      it('Should forward to ocorrenciaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(ocorrenciaService, 'compareOcorrencia');
        comp.compareOcorrencia(entity, entity2);
        expect(ocorrenciaService.compareOcorrencia).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareDocente', () => {
      it('Should forward to docenteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(docenteService, 'compareDocente');
        comp.compareDocente(entity, entity2);
        expect(docenteService.compareDocente).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareCategoriaOcorrencia', () => {
      it('Should forward to categoriaOcorrenciaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(categoriaOcorrenciaService, 'compareCategoriaOcorrencia');
        comp.compareCategoriaOcorrencia(entity, entity2);
        expect(categoriaOcorrenciaService.compareCategoriaOcorrencia).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareLicao', () => {
      it('Should forward to licaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(licaoService, 'compareLicao');
        comp.compareLicao(entity, entity2);
        expect(licaoService.compareLicao).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
