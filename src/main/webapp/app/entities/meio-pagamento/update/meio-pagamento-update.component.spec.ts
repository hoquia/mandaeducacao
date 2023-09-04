import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MeioPagamentoFormService } from './meio-pagamento-form.service';
import { MeioPagamentoService } from '../service/meio-pagamento.service';
import { IMeioPagamento } from '../meio-pagamento.model';

import { MeioPagamentoUpdateComponent } from './meio-pagamento-update.component';

describe('MeioPagamento Management Update Component', () => {
  let comp: MeioPagamentoUpdateComponent;
  let fixture: ComponentFixture<MeioPagamentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let meioPagamentoFormService: MeioPagamentoFormService;
  let meioPagamentoService: MeioPagamentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MeioPagamentoUpdateComponent],
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
      .overrideTemplate(MeioPagamentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MeioPagamentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    meioPagamentoFormService = TestBed.inject(MeioPagamentoFormService);
    meioPagamentoService = TestBed.inject(MeioPagamentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const meioPagamento: IMeioPagamento = { id: 456 };

      activatedRoute.data = of({ meioPagamento });
      comp.ngOnInit();

      expect(comp.meioPagamento).toEqual(meioPagamento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMeioPagamento>>();
      const meioPagamento = { id: 123 };
      jest.spyOn(meioPagamentoFormService, 'getMeioPagamento').mockReturnValue(meioPagamento);
      jest.spyOn(meioPagamentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ meioPagamento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: meioPagamento }));
      saveSubject.complete();

      // THEN
      expect(meioPagamentoFormService.getMeioPagamento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(meioPagamentoService.update).toHaveBeenCalledWith(expect.objectContaining(meioPagamento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMeioPagamento>>();
      const meioPagamento = { id: 123 };
      jest.spyOn(meioPagamentoFormService, 'getMeioPagamento').mockReturnValue({ id: null });
      jest.spyOn(meioPagamentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ meioPagamento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: meioPagamento }));
      saveSubject.complete();

      // THEN
      expect(meioPagamentoFormService.getMeioPagamento).toHaveBeenCalled();
      expect(meioPagamentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMeioPagamento>>();
      const meioPagamento = { id: 123 };
      jest.spyOn(meioPagamentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ meioPagamento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(meioPagamentoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
