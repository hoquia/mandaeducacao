import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResponsavelAreaFormacaoFormService } from './responsavel-area-formacao-form.service';
import { ResponsavelAreaFormacaoService } from '../service/responsavel-area-formacao.service';
import { IResponsavelAreaFormacao } from '../responsavel-area-formacao.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IAreaFormacao } from 'app/entities/area-formacao/area-formacao.model';
import { AreaFormacaoService } from 'app/entities/area-formacao/service/area-formacao.service';

import { ResponsavelAreaFormacaoUpdateComponent } from './responsavel-area-formacao-update.component';

describe('ResponsavelAreaFormacao Management Update Component', () => {
  let comp: ResponsavelAreaFormacaoUpdateComponent;
  let fixture: ComponentFixture<ResponsavelAreaFormacaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let responsavelAreaFormacaoFormService: ResponsavelAreaFormacaoFormService;
  let responsavelAreaFormacaoService: ResponsavelAreaFormacaoService;
  let userService: UserService;
  let areaFormacaoService: AreaFormacaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResponsavelAreaFormacaoUpdateComponent],
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
      .overrideTemplate(ResponsavelAreaFormacaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResponsavelAreaFormacaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    responsavelAreaFormacaoFormService = TestBed.inject(ResponsavelAreaFormacaoFormService);
    responsavelAreaFormacaoService = TestBed.inject(ResponsavelAreaFormacaoService);
    userService = TestBed.inject(UserService);
    areaFormacaoService = TestBed.inject(AreaFormacaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const responsavelAreaFormacao: IResponsavelAreaFormacao = { id: 456 };
      const utilizador: IUser = { id: 93119 };
      responsavelAreaFormacao.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 18870 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ responsavelAreaFormacao });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AreaFormacao query and add missing value', () => {
      const responsavelAreaFormacao: IResponsavelAreaFormacao = { id: 456 };
      const areaFormacao: IAreaFormacao = { id: 99787 };
      responsavelAreaFormacao.areaFormacao = areaFormacao;

      const areaFormacaoCollection: IAreaFormacao[] = [{ id: 47240 }];
      jest.spyOn(areaFormacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: areaFormacaoCollection })));
      const additionalAreaFormacaos = [areaFormacao];
      const expectedCollection: IAreaFormacao[] = [...additionalAreaFormacaos, ...areaFormacaoCollection];
      jest.spyOn(areaFormacaoService, 'addAreaFormacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ responsavelAreaFormacao });
      comp.ngOnInit();

      expect(areaFormacaoService.query).toHaveBeenCalled();
      expect(areaFormacaoService.addAreaFormacaoToCollectionIfMissing).toHaveBeenCalledWith(
        areaFormacaoCollection,
        ...additionalAreaFormacaos.map(expect.objectContaining)
      );
      expect(comp.areaFormacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const responsavelAreaFormacao: IResponsavelAreaFormacao = { id: 456 };
      const utilizador: IUser = { id: 80822 };
      responsavelAreaFormacao.utilizador = utilizador;
      const areaFormacao: IAreaFormacao = { id: 30096 };
      responsavelAreaFormacao.areaFormacao = areaFormacao;

      activatedRoute.data = of({ responsavelAreaFormacao });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.areaFormacaosSharedCollection).toContain(areaFormacao);
      expect(comp.responsavelAreaFormacao).toEqual(responsavelAreaFormacao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResponsavelAreaFormacao>>();
      const responsavelAreaFormacao = { id: 123 };
      jest.spyOn(responsavelAreaFormacaoFormService, 'getResponsavelAreaFormacao').mockReturnValue(responsavelAreaFormacao);
      jest.spyOn(responsavelAreaFormacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelAreaFormacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: responsavelAreaFormacao }));
      saveSubject.complete();

      // THEN
      expect(responsavelAreaFormacaoFormService.getResponsavelAreaFormacao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(responsavelAreaFormacaoService.update).toHaveBeenCalledWith(expect.objectContaining(responsavelAreaFormacao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResponsavelAreaFormacao>>();
      const responsavelAreaFormacao = { id: 123 };
      jest.spyOn(responsavelAreaFormacaoFormService, 'getResponsavelAreaFormacao').mockReturnValue({ id: null });
      jest.spyOn(responsavelAreaFormacaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelAreaFormacao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: responsavelAreaFormacao }));
      saveSubject.complete();

      // THEN
      expect(responsavelAreaFormacaoFormService.getResponsavelAreaFormacao).toHaveBeenCalled();
      expect(responsavelAreaFormacaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResponsavelAreaFormacao>>();
      const responsavelAreaFormacao = { id: 123 };
      jest.spyOn(responsavelAreaFormacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelAreaFormacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(responsavelAreaFormacaoService.update).toHaveBeenCalled();
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

    describe('compareAreaFormacao', () => {
      it('Should forward to areaFormacaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(areaFormacaoService, 'compareAreaFormacao');
        comp.compareAreaFormacao(entity, entity2);
        expect(areaFormacaoService.compareAreaFormacao).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
