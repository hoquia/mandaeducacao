jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PlanoCurricularService } from '../service/plano-curricular.service';

import { PlanoCurricularDeleteDialogComponent } from './plano-curricular-delete-dialog.component';

describe('PlanoCurricular Management Delete Component', () => {
  let comp: PlanoCurricularDeleteDialogComponent;
  let fixture: ComponentFixture<PlanoCurricularDeleteDialogComponent>;
  let service: PlanoCurricularService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PlanoCurricularDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(PlanoCurricularDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PlanoCurricularDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PlanoCurricularService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
