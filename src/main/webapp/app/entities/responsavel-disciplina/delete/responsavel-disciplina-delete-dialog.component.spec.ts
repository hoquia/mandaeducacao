jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ResponsavelDisciplinaService } from '../service/responsavel-disciplina.service';

import { ResponsavelDisciplinaDeleteDialogComponent } from './responsavel-disciplina-delete-dialog.component';

describe('ResponsavelDisciplina Management Delete Component', () => {
  let comp: ResponsavelDisciplinaDeleteDialogComponent;
  let fixture: ComponentFixture<ResponsavelDisciplinaDeleteDialogComponent>;
  let service: ResponsavelDisciplinaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ResponsavelDisciplinaDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(ResponsavelDisciplinaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ResponsavelDisciplinaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ResponsavelDisciplinaService);
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
