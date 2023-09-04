jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { EnderecoDiscenteService } from '../service/endereco-discente.service';

import { EnderecoDiscenteDeleteDialogComponent } from './endereco-discente-delete-dialog.component';

describe('EnderecoDiscente Management Delete Component', () => {
  let comp: EnderecoDiscenteDeleteDialogComponent;
  let fixture: ComponentFixture<EnderecoDiscenteDeleteDialogComponent>;
  let service: EnderecoDiscenteService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [EnderecoDiscenteDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(EnderecoDiscenteDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EnderecoDiscenteDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EnderecoDiscenteService);
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
