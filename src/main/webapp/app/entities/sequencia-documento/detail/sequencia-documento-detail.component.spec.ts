import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SequenciaDocumentoDetailComponent } from './sequencia-documento-detail.component';

describe('SequenciaDocumento Management Detail Component', () => {
  let comp: SequenciaDocumentoDetailComponent;
  let fixture: ComponentFixture<SequenciaDocumentoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SequenciaDocumentoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ sequenciaDocumento: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SequenciaDocumentoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SequenciaDocumentoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load sequenciaDocumento on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.sequenciaDocumento).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
